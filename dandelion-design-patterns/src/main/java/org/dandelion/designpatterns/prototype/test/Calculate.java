package org.dandelion.designpatterns.prototype.test;

/**
 * TODO 计算接口
 *
 * @author L
 * @version 1.0
 * @date 2021/11/11 17:13
 */
public interface Calculate extends Cloneable {

    Object clone();

    void calculate();
}
