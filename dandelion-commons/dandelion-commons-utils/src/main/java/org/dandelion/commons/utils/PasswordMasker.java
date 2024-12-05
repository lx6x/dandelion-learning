package org.dandelion.commons.utils;

/**
 * TODO
 *
 * @author lx6x
 */
public class PasswordMasker {
    public static String maskPassword(String password) {
        if (password == null || password.length() <= 2) {
            // 如果密码为空或长度小于等于2，则直接返回原密码或进行特殊处理
            // 这里我们假设如果长度小于等于2，则不进行任何替换
            return password;
        }

        StringBuilder maskedPassword = new StringBuilder(password.length());

        // 添加前两位字符
        maskedPassword.append(password.charAt(0));
        maskedPassword.append(password.charAt(1));

        // 添加中间部分的星号
        for (int i = 2; i < password.length() - 1; i++) {
            maskedPassword.append('*');
        }

        // 添加最后一位字符
        maskedPassword.append(password.charAt(password.length() - 1));

        return maskedPassword.toString();
    }

    public static void main(String[] args) {
        String password = "examplePassword123";
        String maskedPassword = maskPassword(password);
        System.out.println("Original Password: " + password);
        System.out.println("Masked Password: " + maskedPassword);
    }
}
