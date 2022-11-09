package org.dandelion.designpatterns.factory;

/**
 * TODO 简单工厂
 *      没有使用 static
 *
 * @author L
 * @version 1.0
 * @date 2021/11/12 15:00
 */
public class SimpleFactory {

    public ProductSimple getProduct(String type) {
        switch (type) {
            case "book":
                return new Book();
            case "pen":
                return new Pen();
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    public static void main(String[] args) {
        SimpleFactory simpleFactory = new SimpleFactory();
        simpleFactory.getProduct("book").initProduct();
        simpleFactory.getProduct("pen").initProduct();
    }
}

interface ProductSimple {
    void initProduct();
}

class Book implements ProductSimple {
    @Override
    public void initProduct() {
        System.out.println("产品：书");
    }
}

class Pen implements ProductSimple {

    @Override
    public void initProduct() {
        System.out.println("产品：笔");
    }
}