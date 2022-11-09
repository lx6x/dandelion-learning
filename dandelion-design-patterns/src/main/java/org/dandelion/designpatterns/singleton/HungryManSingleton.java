package org.dandelion.designpatterns.singleton;

/**
 * TODO 饿汉式单例
 *      系统启动时或者单例模式被加载时，该对象就已被创建
 *      因为是在项目启动时就创建好的一个静态对象供外部使用，除非重启，这个对象是不会改变，所以本身是线程安全的
 *
 * @author L
 * @version 1.0
 * @date 2021/11/10 16:15
 */
public class HungryManSingleton {

    public static final HungryManSingleton HUNGRY_MAN_SINGLETON = new HungryManSingleton();

    private HungryManSingleton() {

    }

    public static HungryManSingleton getHungryManSingleton() {
        return HUNGRY_MAN_SINGLETON;
    }
}
