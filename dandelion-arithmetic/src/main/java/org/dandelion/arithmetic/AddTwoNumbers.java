package org.dandelion.arithmetic;

import org.dandelion.arithmetic.model.ListNode;

/**
 * 力扣 - 两数相加
 * @author lx6x
 * @date 2022/11/14
 */
public class AddTwoNumbers {

    /**
     * 两数相加
     * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
     * <p>
     * 请你将两个数相加，并以相同形式返回一个表示和的链表。
     * <p>
     * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     * <p>
     * 示例 1：
     * 输入：l1 = [2,4,3], l2 = [5,6,4]
     * 输出：[7,0,8]
     * 解释：342 + 465 = 807.
     * 示例 2：
     * <p>
     * 输入：l1 = [0], l2 = [0]
     * 输出：[0]
     * 示例 3：
     * <p>
     * 输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
     * 输出：[8,9,9,9,0,0,0,1]
     *
     * @param l1 输入
     * @param l2 输入
     * @return 输出
     */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // 定义返回值
        ListNode head = null;
        // 定义移动指针
        ListNode tail = head;
        // 定义 进位 值
        int carry = 0;
        while (l1 != null || l2 != null) {
            // 判断两个节点是否为空，为空默认为0
            int n1 = l1 != null ? l1.getVal() : 0;
            int n2 = l2 != null ? l2.getVal() : 0;
            // 节点值相加，并且需要加上进位值
            int sum = n1 + n2 + carry;
            if (head == null) {
                head = tail = new ListNode(sum % 10);
            } else {
                // 赋值
                tail.setNext(new ListNode(sum % 10));
                // 移动指针
                tail = tail.getNext();
            }
            // 获取 进位 值
            carry = sum / 10;
            if (l1 != null) {
                l1 = l1.getNext();
            }
            if (l2 != null) {
                l2 = l2.getNext();
            }
        }
        // 判断进位值是否大于0，如果大于零则追加新节点
        if (carry > 0) {
            tail.setNext(new ListNode(carry));
        }
        return head;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1, new ListNode(4, new ListNode(3)));
        ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4)));
        // 两数相加
        ListNode listNode = addTwoNumbers(l1, l2);
        while (listNode != null) {
            System.out.println(listNode.getVal());
            if (listNode.getNext() != null) {
                listNode = listNode.getNext();
            } else {
                listNode = null;
            }
        }
    }

}
