package org.dandelion.arithmetic;

import java.util.HashMap;

/**
 * 力扣 - 无重复字符的最长子串
 * @author lx6x
 * @date 2022/11/14
 */
public class LengthOfLongestSubstring {

    /**
     * 无重复字符的最长子串
     * <p>
     * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
     * <p>
     * 示例 1:
     * <p>
     * 输入: s = "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     * 示例 2:
     * <p>
     * 输入: s = "bbbbb"
     * 输出: 1
     * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     * 示例 3:
     * <p>
     * 输入: s = "pwwkew"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     * 请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
     *
     * @param s 输入
     * @return 输出
     */
    public static int lengthOfLongestSubstring(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        int max = 0, start = 0;
        for (int end = 0; end < s.length(); end++) {
            char ch = s.charAt(end);
            if (map.containsKey(ch)) {
                start = Math.max(map.get(ch) + 1, start);
            }
            max = Math.max(max, end - start + 1);
            map.put(ch, end);
        }
        System.out.println(map);
        return max;
    }

    public static void main(String[] args) {

        int lengthOfLongestSubstring = lengthOfLongestSubstring("asadw");
        System.out.println(lengthOfLongestSubstring);
    }
}
