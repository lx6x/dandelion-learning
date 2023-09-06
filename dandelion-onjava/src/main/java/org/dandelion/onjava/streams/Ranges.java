package org.dandelion.onjava.streams;

import static java.util.stream.IntStream.range;

/**
 * 14.2.2 int 类型的区间范围
 *
 * @author lx6x
 * @date 2023/9/6
 */
public class Ranges {
    public static void main(String[] args) {

        // 传统遍历
        int result = 0;
        for (int i = 10; i < 20; i++) {
            result += i;
        }
        System.out.println(result);

        // for-in 搭配一个区间范围
        result = 0;
        for (int i : range(10, 20).toArray()) {
            result += i;
        }
        System.out.println(result);

        // 使用liu
        System.out.println(range(10, 20).sum());
    }
}

class Repeat {
    // TODO 一点点蒙
    public static void repeat(int n, Runnable runnable) {
        range(0, n).forEach(i -> runnable.run());
    }
}

class Looping {
    static void hi() {
        System.out.println("Hi!");
    }

    public static void main(String[] args) {
        Repeat.repeat(3, () -> System.out.println("Looping"));
        Repeat.repeat(2, Looping::hi);
    }
}