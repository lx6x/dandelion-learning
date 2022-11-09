package org.dandelion.designpatterns.singleton;

/**
 * TODO 懒汉式单例
 *      当程序第一次访问单例模式实例时才会创建
 *
 * @author L
 * @version 1.0
 * @date 2021/11/10 16:26
 */
public class LazyManSingleton {

    private LazyManSingleton() {
    }

    public static volatile LazyManSingleton lazyManSingleton = null;

    public static synchronized LazyManSingleton getLazyManSingleton() {
        /*
          在多线程下，容易出现不同步的问题，所以需要创建同步 “锁”
         */
        if (null == lazyManSingleton) {
            lazyManSingleton = new LazyManSingleton();
        }

        return lazyManSingleton;
    }
}
