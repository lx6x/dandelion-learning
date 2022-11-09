package org.dandelion.designpatterns.prototype.test;

import java.util.Scanner;

/**
 * TODO 计算正方形
 *
 * @author L
 * @version 1.0
 * @date 2021/11/11 17:20
 */
public class CalculateSquare implements Calculate {
    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("clone 正方形失败");
        }
        return null;
    }

    @Override
    public void calculate() {
        int b = 0;
        System.out.print("请输入正方形的边长：");
        Scanner scanner = new Scanner(System.in);
        b = scanner.nextInt();
        System.out.println("正方形的面积：" + (b * b) + "\n");
    }
}
