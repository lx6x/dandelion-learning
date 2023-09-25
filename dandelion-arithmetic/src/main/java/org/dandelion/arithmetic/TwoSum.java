package org.dandelion.arithmetic;

/**
 * 力扣 - 两数之和
 *
 * @author lx6x
 * @date 2022/11/14
 */
public class TwoSum {

    /**
     * 两数之和
     * <p>
     * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
     * 你可以按任意顺序返回答案。
     * <p>
     * 示例 1：
     * <p>
     * 输入：nums = [2,7,11,15], target = 9
     * 输出：[0,1]
     * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
     */
    public static String twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            int i1 = nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                int j1 = nums[j];
                if (i1 + j1 == target) {
                    return i + " " + j;
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4};
        int target = 7;
        // 两数之和
        String ints = twoSum(nums, target);
        System.out.println(ints);
    }
}
