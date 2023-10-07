package org.dandelion.onjava.streams.optionals;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * 14.4.1 便捷函数
 *
 * @author lx6x
 * @date 2023/10/7
 */
public class Optionals {
    static void basics(Optional<String> optString) {
        if (optString.isPresent()) {
            System.out.println(optString.get());
        } else {
            System.out.println("Noting inside!");
        }
    }

    static void ifPresent(Optional<String> optString) {
        optString.ifPresent(System.out::println);
    }

    static void orElse(Optional<String> optString) {
        System.out.println(optString.orElse("Nada"));
    }

    static void orElseGet(Optional<String> optString) {
        System.out.println(optString.orElseGet(() -> "Generated"));
    }

    static void orElseThrow(Optional<String> optString) {
        try {
            System.out.println(optString.orElseThrow(() -> new Exception("Supplied")));
        } catch (Exception e) {
            System.out.println("Caught" + e);
        }
    }

    static void test(String testName,Consumer<Optional<String >> cos){
        System.out.println(" === "+ testName + " === ");
        cos.accept(Stream.of("Epithets").findFirst());
        cos.accept(Stream.<String>empty().findFirst());
    }

    public static void main(String[] args) {
        test("basics",Optionals::basics);
        test("ifPresent",Optionals::ifPresent);
        test("orElse",Optionals::orElse);
        test("orElseGet",Optionals::orElseGet);
        test("orElseThrow",Optionals::orElseThrow);
    }
}
