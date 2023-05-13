package org.dandelion.commons.utils.sftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.util.Objects;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022-12-29
 */
public class SftpFactory extends BasePooledObjectFactory<ChannelSftp> implements AutoCloseable {

    private Session session;
    private SftpProperties properties;


    /**
     * 初始化SftpFactory
     * 里面主要是创建目标session，后续可用通过这个session不断地创建ChannelSftp。
     *
     * @param properties
     */
    SftpFactory(SftpProperties properties) {
        this.properties = properties;
        String username = properties.getUsername();
        String password = properties.getPassword();
        String host = properties.getHost();
        int port = properties.getPort();
        String privateKeyFile = properties.getPrivateKeyFile();
        String passphrase = properties.getPassphrase();
        session = JschUtil.createSession(username, password, host, port, privateKeyFile, passphrase);
    }

    /**
     * 销毁对象，主要是销毁ChannelSftp
     *
     * @param p
     */
    @Override
    public void destroyObject(PooledObject<ChannelSftp> p) {
        p.getObject().disconnect();
    }

    /**
     * 创建对象ChannelSftp
     *
     * @return
     * @throws Exception
     */
    @Override
    public ChannelSftp create() throws Exception {
        int timeout = properties.getTimeout();
        return JschUtil.openSftpChannel(this.session, timeout);
    }

    /**
     * 包装创建出来的对象
     *
     * @param channelSftp
     * @return
     */
    @Override
    public PooledObject<ChannelSftp> wrap(ChannelSftp channelSftp) {
        return new DefaultPooledObject<>(channelSftp);
    }

    /**
     * 验证对象是否可用
     *
     * @param p
     * @return
     */
    @Override
    public boolean validateObject(PooledObject<ChannelSftp> p) {
        return p.getObject().isConnected();
    }

    /**
     * 销毁资源，关闭session
     *
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        if (Objects.nonNull(session)) {
            if (session.isConnected()) {
                session.disconnect();
            }
            session = null;
        }
    }
}
