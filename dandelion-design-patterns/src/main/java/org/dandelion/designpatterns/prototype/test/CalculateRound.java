package org.dandelion.designpatterns.prototype.test;

import java.util.Scanner;

/**
 * TODO 计算圆
 *
 * @author L
 * @version 1.0
 * @date 2021/11/11 17:15
 */
public class CalculateRound implements Calculate {
    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("clone 圆失败");
        }
        return null;
    }

    @Override
    public void calculate() {
        int r = 0;
        System.out.print("请输入圆的半径：");
        Scanner scanner = new Scanner(System.in);
        r = scanner.nextInt();
        System.out.println("圆的面积：" + (3.1415926 * r * r) + "\n");
    }
}
