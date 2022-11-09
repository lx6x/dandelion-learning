package org.dandelion.designpatterns.prototype;

/**
 * TODO  浅拷贝
 *       创建一个新对象，新对象的属性和原来的对象完全相同，对于 [非] 基本类型属性，仍指向原有属性所指向的对象内存地址
 *
 * @author L
 * @version 1.0
 * @date 2021/11/10 17:01
 */
public class ShallowClonePrototype implements Cloneable {

    private String name;
    private ClonePrototype1 clonePrototype;

    public ShallowClonePrototype() {
        this.clonePrototype = new ClonePrototype1();
    }

    public ClonePrototype1 getClonePrototype() {
        return clonePrototype;
    }

    public void setClonePrototype(ClonePrototype1 clonePrototype) {
        this.clonePrototype = clonePrototype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        System.out.println("clone 成功");
        return super.clone();
    }

    public void println() {
        System.out.println("我是：" + this.name + " - 年龄：" + this.clonePrototype.getAge());
    }

    public static void main(String[] args) throws CloneNotSupportedException {

        // 浅拷贝
        ShallowClonePrototype prototype = new ShallowClonePrototype();
        prototype.setName("李四");
        prototype.getClonePrototype().setAge(10);
        prototype.println();

        System.out.println("============================ clone =============================");
        ShallowClonePrototype clone = (ShallowClonePrototype) prototype.clone();
        System.out.println("相等：" + (prototype == clone));
        clone.println();
        System.out.println("============================ 不变 =============================");
        clone.setName("李四 变成了 张三");
        clone.println();
        prototype.println();
        System.out.println("============================ 变化 =============================");
        clone.getClonePrototype().setAge(20);
        clone.println();
        prototype.println();
    }

}

class ClonePrototype1 {

    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

