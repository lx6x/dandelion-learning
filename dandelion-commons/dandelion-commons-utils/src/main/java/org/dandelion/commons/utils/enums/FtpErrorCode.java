package org.dandelion.commons.utils.enums;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022-12-28
 */
public enum FtpErrorCode {

    CONNECT_SERVER_FAILER("连接sftp服务器失败"),
    DISCONNECT_SERVER_GET_CONNECT_FAILER("断开sftp服务器获取连接失败"),
    CREATE_DIRECTORY_FAILER("创建SFTP目录失败"),
    GET_LOCAL_FILE_INPUT_STREAM("获取本地文件输入流失败"),
    UPLOAD_FILE_FAILER("上传文件失败"),
    GET_LOCAL_FILE_OUTPUT_STREAM("获取本地输出流失败"),
    DELETE_FTP_FILE_FAILER("删除FTP文件失败"),
    CD_REMOTE_PATH_FAILER("进入远程目录失败"),
    LS_REMOTE_PATH_FAILER("进入远程目录失败");

    private String errorDesc;


    FtpErrorCode(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

}
