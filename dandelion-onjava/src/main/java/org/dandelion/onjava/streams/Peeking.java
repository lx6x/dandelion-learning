package org.dandelion.onjava.streams;

import java.io.IOException;

/**
 * 14.3.1 跟踪与调试
 * peek() 操作就是用来辅助调试的。它允许我们查看流对象而不是修改它们
 *
 * @author lx6x
 * @date 2023/9/23
 */
public class Peeking {
    public static void main(String[] args) throws IOException {
        FileToWords.stream("Cheese.dat")
                .skip(21)
                .limit(4)
                .map(w -> w + " ")
                .peek(System.out::println)
                .map(String::toUpperCase)
                .peek(System.out::println)
                .map(String::toLowerCase)
                .forEach(System.out::println);
    }
}
