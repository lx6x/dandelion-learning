package org.dandelion.commons.utils;

import java.io.IOException;

/**
 * @date 2023/5/15
 */
public class StartAndShutdown {

    /**
     * 默认60秒后关机
     *
     * @date 2023/5/15
     **/
    public void shutdown() {
        try {
            Runtime.getRuntime().exec("shutdown /s /t  60");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据输入的时间秒数关机
     *
     * @param s 秒
     * @date 2023/5/15
     **/
    public void shutdown(String s) {
        try {
            //关机时间可以自动设置
            Runtime.getRuntime().exec("shutdown /s /t " + s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重启
     *
     * @date 2023/5/15
     **/
    public void restart() {
        try {
            Runtime.getRuntime().exec("shutdown -r ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
