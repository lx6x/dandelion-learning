package org.dandelion.arithmetic;

/**
 * 力扣 - 14. 最长公共前缀
 *
 * @author lx6x
 * @date 2023/9/25
 */
public class LongestCommonPrefix {

    /**
     * 编写一个函数来查找字符串数组中的最长公共前缀。
     * <p>
     * 如果不存在公共前缀，返回空字符串 ""。
     * <p>
     * 示例 1：
     * <p>
     * 输入：strs = ["flower","flow","flight"]
     * 输出："fl"
     * 示例 2：
     * <p>
     * 输入：strs = ["dog","racecar","car"]
     * 输出：""
     * 解释：输入不存在公共前缀
     *
     * @param stirs .
     * @return 如果不存在公共前缀，返回空字符串 ""
     */
    public static String longestCommonPrefix(String[] stirs) {

        if (stirs.length == 0) {
            return "";
        }
        String stir = stirs[0];
        for (String str : stirs) {
            // 对比前缀是否一致
            while (!str.startsWith(stir)) {
                if (stirs.length == 0) {
                    return "";
                }
                // 不一致长度减一
                stir = stir.substring(0, stir.length() - 1);
            }
        }
        return stir;
    }

    public static void main(String[] args) {
        String[] stirs = {"flower", "flow", "flight"};
        String s = longestCommonPrefix(stirs);
        System.out.println(s);
    }
}
