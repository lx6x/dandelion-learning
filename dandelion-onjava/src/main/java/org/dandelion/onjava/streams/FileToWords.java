package org.dandelion.onjava.streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author lx6x
 * @date 2023/9/23
 */
public class FileToWords {
    public static Stream<String> stream(String filePath) throws IOException {
        return Files.lines(Paths.get(filePath)).skip(1)
                .flatMap(line -> Pattern.compile("\\W+").splitAsStream(line));

    }
}
