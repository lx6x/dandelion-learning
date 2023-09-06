package org.dandelion.onjava.streams;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 14.2.3 generate
 *
 * @author lx6x
 * @date 2023/9/6
 */
public class Generate implements Supplier<String> {

    Random random = new Random(47);

    char[] letters = "ABCDEFGHIJKLMNOPQRSTUVWSYZ".toCharArray();

    @Override
    public String get() {
        return "" + letters[random.nextInt(letters.length)];
    }

    public static void main(String[] args) {
        String word = Stream.generate(new Generate())
                .limit(30)
                .collect(Collectors.joining());
        System.out.println(word);
    }
}

class Duplicator {
    public static void main(String[] args) {
        // 需要生成相同对象
        Stream.generate(() -> "duplicate")
                .limit(3) // 执行三次
                .forEach(System.out::println); // 遍历输出
    }
}

class Bubble {
    public final int i;

    public Bubble(int n) {
        i = n;
    }

    @Override
    public String toString() {
        return "Bubble ( " + i + " )";
    }

    private static int count = 0;

    public static Bubble bubble() {
        return new Bubble(count++);
    }
}

class Bubbles {
    public static void main(String[] args) {
        Stream.generate(Bubble::bubble).limit(10).forEach(System.out::println);
    }
}
