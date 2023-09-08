package org.dandelion.scheduling.rabbitmq.example.delay.jvm;


import org.dandelion.scheduling.rabbitmq.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * jdk 延时队列测试
 * 优点：效率高，任务触发时间延迟低。
 * 缺点: 服务器重启后，数据全部消失，怕宕机；集群扩展相当麻烦；因为内存条件限制的原因，比如下单未付款的订单数太多，那么很容易就出现OOM异常；代码复杂度较高
 *
 * @author L
 * @version 1.0
 * @date 2021/11/1 14:05
 */
public class JdkDelayedQueue implements Delayed {

    /**
     * 到期时间
     */
    private long timeOut;
    /**
     * 测试失效 id
     */
    private String id;


    JdkDelayedQueue(String id, long timeOut) {
        this.timeOut = timeOut + System.nanoTime();
        this.id = id;
    }

    /**
     * 在给定的时间单位中返回与此对象相关联的剩余延迟
     *
     * @param unit 时间单位
     * @return 剩余的延迟; 零或负值表示延迟已经过去
     * @author L
     */
    @Override
    public long getDelay(TimeUnit unit) {
        // 使用 纳秒 做单位
        return unit.convert(timeOut - System.nanoTime(), TimeUnit.NANOSECONDS);
    }


    /**
     * 将此对象与指定的对象进行比较以进行排序。 返回一个负整数，零或正整数，因为该对象小于，等于或大于指定对象。
     * 实现程序必须确保sgn(x.compareTo(y)) == -sgn(y.compareTo(x))所有x和y。 （这意味着x.compareTo(y)必须抛出异常iff y.compareTo(x)引发异常。）
     * 实施者还必须确保关系是可传递的： (x.compareTo(y)>0 && y.compareTo(z)>0)表示x.compareTo(z)>0 。
     * 最后，实施者必须确保x.compareTo(y)==0意味着sgn(x.compareTo(z)) == sgn(y.compareTo(z)) ，对于所有z 。
     * 强烈建议，但不要严格要求(x.compareTo(y)==0) == (x.equals(y)) 。 一般来说，任何实现Comparable接口并违反这种情况的类应清楚地表明这一点。 推荐的语言是“注意：此类具有与equals不一致的自然排序”。
     * 在前面的描述中，符号sgn( ) 表达式表示数学符号函数，其定义根据表达式的值是否为负，零或正返回的-1一个，0，或1。
     *
     * @param o 要比较的对象
     * @return 负整数，零或正整数，因为该对象小于，等于或大于指定对象
     * @throws NullPointerException 如果指定的对象为空
     * @throws ClassCastException   如果指定的对象的类型阻止它与该对象进行比较
     * @author L
     */
    @Override
    public int compareTo(Delayed o) {
        if (o == this) {
            return 0;
        }
        JdkDelayedQueue t = (JdkDelayedQueue) o;
        long d = (getDelay(TimeUnit.NANOSECONDS) - t
                .getDelay(TimeUnit.NANOSECONDS));
        System.out.println(" ====== > " + d);

        return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
    }

    /**
     * 失效执行方法
     *
     * @author L
     */
    void implement() {
        System.out.println("执行：" + DateUtils.getNowDateTime(DateUtils.getNowDateTime()) + " - " + id);
    }

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        list.add("00000001");
        list.add("00000002");
        list.add("00000003");
        list.add("00000004");
        list.add("00000005");


        DelayQueue<JdkDelayedQueue> queue = new DelayQueue<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            // 延迟 3 秒执行
            queue.put(new JdkDelayedQueue(list.get(i), TimeUnit.NANOSECONDS.convert(3, TimeUnit.SECONDS)));
            try {
                queue.take().implement();
                System.out.println("After " + (System.currentTimeMillis() - start) + " MilliSeconds");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
