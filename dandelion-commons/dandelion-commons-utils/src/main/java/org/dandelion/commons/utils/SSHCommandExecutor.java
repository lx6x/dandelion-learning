package org.dandelion.commons.utils;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class SSHCommandExecutor implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(SSHCommandExecutor.class);


	/**
	 * 退格
	 */
	private static final String BACKSPACE = new String(new byte[] { 8 });
 
	/**
	 * ESC
	 */
	private static final String ESC = new String(new byte[] { 27 });
	
	/**
	 * 空格
	 */
	private static final String BLANKSPACE = new String(new byte[] {32});
	
	/**
	 * 某些设备回显数据中的控制字符
	 */
	private static final String[] PREFIX_STRS = { BACKSPACE + "+" + BLANKSPACE + "+" + BACKSPACE + "+", "(" + ESC + "\\[\\d+[A-Z]" + BLANKSPACE + "*)+" };
	
	/**
	 * 保存当前命令的回显信息
	 */
	protected StringBuffer currEcho = new StringBuffer();
	/**
	 * 保存所有的回显信息
	 */
	protected StringBuffer totalEcho = new StringBuffer();
	private int sleepTime = 200;
	 
	/**
	 * 连接超时(单次命令总耗时)
	 */
	private int timeout = 4000;
	
	private String moreEcho = "---- More ----";
	private String moreCmd = BLANKSPACE;
	private String endEcho = "#,?,>,:";
	
	private JSch jsch = null;
	private Session session;
	private Channel channel;
	
	private String username;
	private String password;
	private String ip;
	private int port = 22;
	
	public SSHCommandExecutor(String username, String password, String ip, int port) {
		this.username = username;
		this.password = password;
		this.ip = ip;
		this.port = port;
	}

	@Override
	public void run() {
		InputStream is;
		try {
			is = channel.getInputStream();
			String echo = readOneEcho(is);
			while (echo != null) {
				log.info(echo);
				currEcho.append(echo);
				String[] lineStr = echo.split("\\n");
				if (lineStr != null && lineStr.length > 0) {
					String lastLineStr = lineStr[lineStr.length - 1];
					if (lastLineStr != null && lastLineStr.indexOf(moreEcho) > 0) {
						totalEcho.append(echo.replace(lastLineStr, ""));
					} else {
						totalEcho.append(echo);
					}
				}
				echo = readOneEcho(is);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected String readOneEcho(InputStream instr) {
		byte[] buff = new byte[1024];
		int ret_read = 0;
		try {
			ret_read = instr.read(buff);
		} catch (IOException e) {
			return null;
		}
		if (ret_read > 0) {
			String result = new String(buff, 0, ret_read);
			for (String PREFIX_STR : PREFIX_STRS) {
				result = result.replaceFirst(PREFIX_STR, "");
			}
			try {
				return new String(result.getBytes(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}
	
	private boolean login() {
		jsch = new JSch();
		try {
			session = jsch.getSession(username, this.ip, this.port);
			session.setPassword(password);
			UserInfo ui = new SSHUserInfo() {
				@Override
				public void showMessage(String message) {
				}
 
				@Override
				public boolean promptYesNo(String message) {
					return true;
				}
			};
			session.setUserInfo(ui);
			session.connect(30000);
			channel = session.openChannel("shell");
			channel.connect(3000);
			
			new Thread(this).start(); 
			try { 
				Thread.sleep(sleepTime); 
			} catch (Exception e) {}
			return true;
		} catch (JSchException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	protected void sendCommand(String command) {
		try {
			OutputStream os = channel.getOutputStream();
			os.write(command.getBytes());
			//os.write(ENTER.getBytes());
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String batchCommand(String[] cmds) {
		StringBuffer sb = new StringBuffer();
		for (String cmd : cmds) {
			if (cmd.equals("")) {
				continue;
			}
			cmd += (char) 10;
			String resultEcho = runCommand(cmd);
			sb.append(resultEcho);
		}
		close();
		return totalEcho.toString();
	}
	
	private void close() {
		if (session != null) {
			session.disconnect();
		}
		if (channel != null) {
			channel.disconnect();
		}
	}
	
	private String runCommand(String command) {
		currEcho = new StringBuffer();
		sendCommand(command);
		int time = 0;
		if (endEcho == null || endEcho.equals("")) {
			while (currEcho.toString().equals("")) {
				try {
					Thread.sleep(sleepTime);
					time += sleepTime;
					if (time >= timeout) {
						break;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else {
			while (!containsEchoEnd(currEcho.toString())) {
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				time += sleepTime;
				if (time >= timeout) {
					break;
				}
				String[] lineStrs = currEcho.toString().split("\\n");
				if (lineStrs != null && lineStrs.length > 0) {
					if (moreEcho != null && lineStrs[lineStrs.length - 1] != null
							&& lineStrs[lineStrs.length - 1].contains(moreEcho)) {
						sendCommand(moreCmd);
						currEcho.append("\n");
						time = 0;
						continue;
					}
				}
			}
		}
		return currEcho.toString();
	}
	
	
	
	protected boolean containsEchoEnd(String echo) {
		boolean contains = false;
		if (endEcho == null || endEcho.trim().equals("")) {
			return contains;
		}
		String[] eds = endEcho.split(",");
		for (String ed : eds) {
			if (echo.trim().endsWith(ed)) {
				contains = true;
				break;
			}
		}
		return contains;
	}
	
	public String execute(String[] cmds) {
		if (cmds == null || cmds.length == 0) {
			log.error("{} ssh cmds is null", this.ip);
			return null;
		}
		if (login()) {
			return batchCommand(cmds);
		}
		log.error("{} ssh login error", this.ip);
		return null;
	}
	
	public static void main(String[] args) {
		SSHCommandExecutor executor = new SSHCommandExecutor("root","hpinvent1qaz@WSX", "192.168.36.240", 22);
		String[] cmds = {"cd /opt/mmc", "./mmc-status.sh all"};
		String result = executor.execute(cmds);
		//System.out.println(executive);
	}
	
	private abstract class SSHUserInfo implements UserInfo, UIKeyboardInteractive {
		@Override
		public String getPassword() {
			return null;
		}

		@Override
		public boolean promptYesNo(String str) {
			return true;
		}

		@Override
		public String getPassphrase() {
			return null;
		}

		@Override
		public boolean promptPassphrase(String message) {
			return true;
		}

		@Override
		public boolean promptPassword(String message) {
			return true;
		}

		@Override
		public void showMessage(String message) {
		}

		@Override
		public String[] promptKeyboardInteractive(String destination, String name, String instruction, String[] prompt,
				boolean[] echo) {
			return null;
		}
	}
	
}
