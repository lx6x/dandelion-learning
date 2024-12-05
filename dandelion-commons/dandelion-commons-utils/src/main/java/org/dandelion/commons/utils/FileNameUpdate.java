package org.dandelion.commons.utils;

import java.io.IOException;
import java.nio.file.*;

/**
 * 文件名称修改
 *
 * @author lx6x
 */
public class FileNameUpdate {

    static String source = "\\source\\";
    static String target = "\\target\\";

    public static void main(String[] args) throws IOException {
//        File directory = new File(target);
//        if (directory.isDirectory()) {
//            File[] files = directory.listFiles();
//            if (files != null) {
//                for (File file : files) {
//                    String name = file.getName();
//
//                    System.out.println(name);
//
//                }
//            }
//
//        }

        Path sourceDir = Paths.get(source);
        Path targetDir = Paths.get(target);
        copyFolder(sourceDir, targetDir);

//        System.out.println(string);
    }

    /**
     * 为没有后缀的文件名添加指定的后缀，如果文件名已有后缀则跳过
     *
     * @param fileName  文件名
     * @param extension 要添加的后缀（不包括点号）
     * @return 处理后的文件名（如果需要的话，已添加后缀）
     */
    public static String appendExtensionIfNeeded(String fileName, String extension) {
        // 假设后缀只包含字母、数字和可能的下划线，并且不以点号开头
        // 这里使用正则表达式来检查文件名是否以点号后跟至少一个字母/数字/下划线结尾
        String regex = "\\.[a-zA-Z0-9_]+$";

        // 如果文件名不匹配正则表达式，则认为它没有后缀
        if (!fileName.matches(regex)) {
            // 拼接上点号和后缀
            return fileName + "." + extension;
        }

        // 如果文件名已经包含后缀，则直接返回原文件名
        return fileName;
    }

    public static void copyFolder(Path source, Path target) throws IOException {
        Files.createDirectories(target);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(source)) {
            for (Path file : stream) {
                Path targetPath = target.resolve(source.relativize(file));
                if (Files.isDirectory(file)) {
                    copyFolder(file, targetPath);
                } else {
                    Path modifiedTargetPath = modifyFileName(targetPath);
                    // 如果是文件，则复制文件，并在复制前修改文件名
                    Files.copy(file, modifiedTargetPath, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        } catch (DirectoryIteratorException e) {
            e.printStackTrace();
            throw e;
        }
    }


    public static String swapNameAndNumber(String fileName) {
        // 假设文件名总是以 "名字数字.扩展名" 的格式出现
        int lastIndexOfDot = fileName.lastIndexOf('.');
        if (lastIndexOfDot == -1) {
            // 如果没有找到扩展名，就返回原文件名或抛出异常
            return fileName; // 或者 throw new IllegalArgumentException("File name must have an extension");
        }

        String extension = fileName.substring(lastIndexOfDot); // 获取扩展名
        String prefix = fileName.substring(0, lastIndexOfDot); // 获取名字+数字

        // 查找数字序列开始的位置
        int numberStartIndex = -1;
        for (int i = 0; i < prefix.length(); i++) {
            if (Character.isDigit(prefix.charAt(i))) {
                numberStartIndex = i;
                break;
            }
        }

        // 如果找到了数字序列，就交换名字和数字的位置
        if (numberStartIndex != -1) {
            String name = prefix.substring(0, numberStartIndex); // 名字部分
            String number = prefix.substring(numberStartIndex); // 数字部分
            return number + name + extension; // 拼接新的文件名
        }

        // 如果没有找到数字（理论上不应该发生，除非文件名不符合预期格式），就返回原文件名
        return fileName;
    }


    // 修改文件名的辅助方法
    private static Path modifyFileName(Path path) {
        // 获取文件名
        String fileName = path.getFileName().toString();

        fileName = fileName.replace("._", "");
        fileName = fileName.replace("-", "");
        fileName = fileName.replace("+", "");
        fileName = fileName.replace("_", "");
        fileName = fileName.replace(" ", "");
        fileName = appendExtensionIfNeeded(fileName, "jpg");
        fileName = removeExtraExtension(fileName);
        fileName = swapNameAndNumber(fileName);
        fileName = insertUnderscoreAfterNumbers(fileName);
//        fileName = convertIfNameBeforeNumber(fileName);

        // 如果原路径是文件，则只修改文件名部分；如果是目录，则保持原样
        try {
            if (path.getParent() != null) {
                return path.getParent().resolve(fileName);
            } else {
                // 如果path没有父路径（即它是根目录或类似的东西），你可能需要特别处理
                // 但在这个上下文中，我们假设path总是有一个父路径
                throw new IllegalArgumentException("Path has no parent");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(path);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static String addUnderscoreAfterNumber(String fileName) {
        // 假设文件名以数字开头，并且我们想要在这个数字后面添加下划线
        // 找到第一个非数字字符的索引
        int nonDigitIndex = 0;
        while (nonDigitIndex < fileName.length() && Character.isDigit(fileName.charAt(nonDigitIndex))) {
            nonDigitIndex++;
        }

        // 如果文件名全部由数字组成，或者没有找到非数字字符，则不添加下划线
        if (nonDigitIndex == fileName.length()) {
            return fileName; // 或者你可以决定如何处理这种情况
        }

        // 在数字和非数字字符之间插入下划线
        return fileName.substring(0, nonDigitIndex) + "_" + fileName.substring(nonDigitIndex);
    }


    /**
     * 去除文件名中多余的扩展名。
     * 如果文件名包含多于一个点（`.`），则假定存在多余的扩展名，并尝试去除最后一个点及其后的内容。
     *
     * @param fileName 要处理的文件名
     * @return 修正后的文件名
     */
    public static String removeExtraExtension(String fileName) {
        // 检查文件名是否包含多于一个点
        int lastIndex = fileName.lastIndexOf('.');
        if (lastIndex != -1 && fileName.lastIndexOf('.', lastIndex - 1) != -1) {
            // 存在多余的扩展名，去除最后一个点及其后的内容
            return fileName.substring(0, lastIndex);
        }
        // 没有多余的扩展名，返回原文件名
        return fileName;
    }

    public static String insertUnderscoreAfterNumbers(String fileName) {
        int length = fileName.length();
        int i = 0;

        // 找到数字序列的末尾（即第一个非数字字符的索引）
        while (i < length && Character.isDigit(fileName.charAt(i))) {
            i++;
        }

        // 如果我们找到了数字序列的末尾（即i < length），则在它后面插入下划线
        if (i < length && i > 0) { // 确保数字序列后面还有字符，且数字序列不是空的
            return fileName.substring(0, i) + "_" + fileName.substring(i);
        }

        // 如果没有找到数字序列（理论上不应该发生，除非文件名是空的或非法的），或者数字序列后面没有字符，
        // 就返回原文件名（但在这个例子中，我们假设文件名总是有效的）
        return fileName;
    }


}
