package org.dandelion.onjava.exceptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 15.12 try-with-resources
 *
 * @author lx6x
 * @date 2023/10/9
 */
public class MessyExceptions {

    /**
     * 示例
     */
    public void v1() {
        InputStream in = null;
        try {
            in = new FileInputStream(new File("MessyExceptions.java"));
            int read = in.read();
            // ... 处理内容
        } catch (IOException e) {
            // ... 处理错误
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // ... 处理 close 错误
                }
            }
        }
    }

    /**
     * try-with-resources 语法,简化上述代码
     */
    public void v2() {
        // 需实现 AutoCloseable 接口, close()
        try (InputStream in = new FileInputStream(new File("MessyExceptions.java"))) {
            int read = in.read();
            // ... 处理内容
        } catch (IOException e) {
            // ... 处理错误
        }
    }
}
