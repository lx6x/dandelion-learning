package org.dandelion.designpatterns.prototype.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * TODO 原型管理器
 *
 * @author L
 * @version 1.0
 * @date 2021/11/11 17:23
 */
public class ProtoTypeManager {

    public static Map<String, Calculate> calculateMap = new HashMap<>(16);

    public ProtoTypeManager() {
        calculateMap.put("round", new CalculateRound());
        calculateMap.put("square", new CalculateSquare());
    }

    public Calculate getCalculate(String key) {
        Calculate calculate = calculateMap.get(key);
        // 不再创建，直接进行复制
        return (Calculate) calculate.clone();
    }

    public static void main(String[] args) {
        ProtoTypeManager protoTypeManager = new ProtoTypeManager();
        Scanner scanner = new Scanner(System.in);
        System.out.print("输入 key：");
        String s = scanner.next();
        Calculate round = protoTypeManager.getCalculate(s);
        round.calculate();

    }
}
