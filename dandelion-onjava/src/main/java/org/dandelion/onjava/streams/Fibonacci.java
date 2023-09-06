package org.dandelion.onjava.streams;

import java.util.stream.Stream;

/**
 * 14.2.4 iterate
 * 斐波那契数列
 *
 * @author lx6x
 * @date 2023/9/6
 */
public class Fibonacci {

    int x = 1;
    Stream<Integer> numbers() {
        return Stream.iterate(0, i -> {
            int result = x + i;
            x = i;
            return result;
        });
    }

    public static void main(String[] args) {
        new Fibonacci().numbers()
                .skip(20) // 不使用前20个
                .limit(10) // 然后从中取10个
                .forEach(System.out::println);
    }
}
