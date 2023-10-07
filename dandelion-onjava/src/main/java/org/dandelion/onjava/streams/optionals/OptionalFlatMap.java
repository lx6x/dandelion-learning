package org.dandelion.onjava.streams.optionals;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * 14.4.3 Optional 对象上的操作
 *
 * @author lx6x
 * @date 2023/10/7
 */
public class OptionalFlatMap {

    static String[] elements = {"12", "", "23", "45"};

    static Stream<String> testStream() {
        return Arrays.stream(elements);
    }

    static void test(String descr, Function<String, Optional<String>> func) {
        System.out.println(" ---( " + descr + " )--- ");
        for (int i = 0; i <= elements.length; i++) {
            System.out.println(
                    testStream().skip(i)
                            .findFirst() // 生成一个 Optional
                            .flatMap(func)
            );
        }
    }

    public static void main(String[] args) {
        // 如果 Optional 不为 empty ，在将其传给函数时，map() 首先会提取 Optional 中的对象
        test("Add brackets", s -> Optional.of(" [" + s + "] "));
        test("Increment", s -> {
            try {
                return Optional.of(Integer.parseInt(s) + 1 + "");
            } catch (NumberFormatException e) {
                return Optional.of(s);
            }
        });
        test("Replace", s -> Optional.of(s.replace("2", "9")));
        test("Take last digit", s -> Optional.of(s.length() > 0 ? s.charAt(s.length() - 1) + "" : s));
    }
}
