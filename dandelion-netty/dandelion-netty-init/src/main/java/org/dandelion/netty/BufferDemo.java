package org.dandelion.netty;

import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022/4/27 16:44
 */
public class BufferDemo {

    private static RandomAccessFile file;

    public static void main(String[] args) {
        try {
            // Test the NIO Buffer!
            file = new RandomAccessFile("D:/nio.txt", "r");
            FileChannel channel = file.getChannel();

            //定义一个Buffer，并且初始化大小
            ByteBuffer buf = ByteBuffer.allocate(48);
            getBufferSign("初始化Buffer", buf);

            //将Channel中的数据读到buffer中
            channel.read(buf);
            getBufferSign("数据读入Buffer", buf);

            //flip将Buffer从写模式切换为读模式
            buf.flip();
            getBufferSign("Buffer切换为读模式", buf);

            //Buffer读数据
            while (buf.hasRemaining()) {
                byte b = buf.get();
                System.out.print((char) b);
            }
            System.out.println();
            getBufferSign("Buffer读数据后", buf);

            buf.rewind();  // 重读
            getBufferSign("调用rewind()方法后", buf);

            //compact()
            for (int i = 0; i < 5; i++) {
                buf.get();
            }
            getBufferSign("读完5个byte后", buf);
            buf.compact(); // 整理
            getBufferSign("compact后", buf);

            byte a = buf.get();
            System.out.print((char) a);
            getBufferSign("compact后", buf);

//			buf.flip();
            buf.position(0);
            buf.limit(20);
            getBufferSign("切换读写模式之后", buf);
            //Buffer读数据
            while (buf.hasRemaining()) {
                byte b = buf.get();
                System.out.print((char) b);
            }
            System.out.println();
            getBufferSign("Buffer读数据后", buf);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void getBufferSign(String str, Buffer buf) {
        System.out.println(str + ": Buffer capacity:" + buf.capacity() +
                "; position:" + buf.position() +
                "; limit:" + buf.limit());
    }
}
