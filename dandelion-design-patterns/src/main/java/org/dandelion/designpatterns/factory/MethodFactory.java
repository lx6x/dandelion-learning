package org.dandelion.designpatterns.factory;

/**
 * TODO 工厂方法
 *      工厂方法模式的主要角色如下。
 *      抽象工厂（Abstract Factory）：提供了创建产品的接口，调用者通过它访问具体工厂的工厂方法 newProduct() 来创建产品。
 *      具体工厂（ConcreteFactory）：主要是实现抽象工厂中的抽象方法，完成具体产品的创建。
 *      抽象产品（Product）：定义了产品的规范，描述了产品的主要特性和功能。
 *      具体产品（ConcreteProduct）：实现了抽象产品角色所定义的接口，由具体工厂来创建，它同具体工厂之间一一对应。
 *
 * @author L
 * @version 1.0
 * @date 2021/11/15 13:46
 */
public class MethodFactory {
    public static void main(String[] args) {
        MethodFactoryParent methodFactorySon01 = new MethodFactorySon01();
        methodFactorySon01.productMethod().production();
    }
}

interface MethodFactoryParent {
    ProductMethod productMethod();
}

class MethodFactorySon01 implements MethodFactoryParent {

    @Override
    public ProductMethod productMethod() {
        System.out.println("工厂 - 01");
        return new ProductMethodImpl01();
    }
}

class MethodFactorySon02 implements MethodFactoryParent {

    @Override
    public ProductMethod productMethod() {
        System.out.println("工厂 - 02");
        return new ProductMethodImpl02();
    }
}

interface ProductMethod {
    void production();
}

class ProductMethodImpl01 implements ProductMethod {

    @Override
    public void production() {
        System.out.println("生产 - 01");
    }
}

class ProductMethodImpl02 implements ProductMethod {

    @Override
    public void production() {
        System.out.println("生产-02");
    }
}
