package org.dandelion.designpatterns.prototype;

/**
 * TODO 深拷贝
 *      创建一个新对象，属性中引用的其他对象也会被克隆，不在指向原有对象地址
 *
 * @author L
 * @version 1.0
 * @date 2021/11/11 16:12
 */
public class DeepClonePrototype implements Cloneable {
    private String name;
    private ClonePrototype2 clonePrototype;

    public DeepClonePrototype() {
        this.clonePrototype = new ClonePrototype2();

    }

    public ClonePrototype2 getClonePrototype() {
        return clonePrototype;
    }

    public void setClonePrototype(ClonePrototype2 clonePrototype) {
        this.clonePrototype = clonePrototype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void println() {
        System.out.println("我是：" + this.name + " - 年龄：" + this.clonePrototype.getAge());
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        System.out.println("clone 成功");
        DeepClonePrototype deepClonePrototype = (DeepClonePrototype) super.clone();
        // 对 clonePrototype 调用其方法进行拷贝
        deepClonePrototype.clonePrototype= (ClonePrototype2) deepClonePrototype.getClonePrototype().clone();
        return deepClonePrototype;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        DeepClonePrototype deepClonePrototype = new DeepClonePrototype();
        deepClonePrototype.setName("李四");
        deepClonePrototype.println();
        System.out.println("============================ clone =============================");
        DeepClonePrototype clone = (DeepClonePrototype) deepClonePrototype.clone();
        clone.println();
        System.out.println("============================ clone =============================");
        clone.getClonePrototype().setAge(10);
        clone.println();
        deepClonePrototype.println();

    }
}

class ClonePrototype2 implements Cloneable{

    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


}
