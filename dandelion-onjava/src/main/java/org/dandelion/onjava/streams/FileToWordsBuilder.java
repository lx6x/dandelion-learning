package org.dandelion.onjava.streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * 14.2.5 流生成器 Builder
 *
 * @author lx6x
 * @date 2023/9/6
 */
public class FileToWordsBuilder {

    Stream.Builder<String> builder = Stream.builder();

    public FileToWordsBuilder(String filePath) throws IOException {
        Files.lines(Paths.get(filePath))
                .skip(1)
                .forEach(line -> {
//                    for (String w : line.split("[ .?,]+")) {
//                        builder.add(w);
//                    }
                    builder.add(line);
                });
    }

    Stream<String> stream() {
        return builder.build();
    }

    public static void main(String[] args) throws IOException {
        String currentDirectory = System.getProperty("user.dir");
        String path = currentDirectory + "/dandelion-onjava/doc/FileToWordsBuilder.txt";

        new FileToWordsBuilder(path).stream()
                .limit(7)
                .map(w -> w + " ")
                .forEach(System.out::println);

    }
}
