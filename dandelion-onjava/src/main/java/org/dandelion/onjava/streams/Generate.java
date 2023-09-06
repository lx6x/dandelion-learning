package org.dandelion.onjava.streams;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
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
