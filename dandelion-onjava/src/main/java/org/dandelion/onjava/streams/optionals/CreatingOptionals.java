package org.dandelion.onjava.streams.optionals;

import java.util.Optional;

/**
 * 14.4.2 创建 Optional
 *
 * @author lx6x
 * @date 2023/10/7
 */
public class CreatingOptionals {
    static void test(String testName, Optional<String> opt) {
        System.out.println(" === " + testName + " === ");
        System.out.println(opt.orElse("Null"));
    }

    public static void main(String[] args) {
        test("empty",Optional.empty());
        test("of",Optional.of("Howdy"));
        try {
            test("of",Optional.of(null));
        } catch (Exception e) {
            System.out.println(e);
        }
        test("ofNullable",Optional.ofNullable("Hi"));
        test("ofNullable",Optional.ofNullable(null));

    }
}
