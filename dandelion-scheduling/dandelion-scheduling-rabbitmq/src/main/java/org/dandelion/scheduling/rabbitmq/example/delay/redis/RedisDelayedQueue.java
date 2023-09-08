package org.dandelion.scheduling.rabbitmq.example.delay.redis;

import org.dandelion.scheduling.rabbitmq.utils.DateUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import java.util.Calendar;
import java.util.Set;

/**
 *  redis 延迟队列测试
 *
 * @author L
 * @version 1.0
 * @date 2021/11/1 16:37
 */
public class RedisDelayedQueue {

    private static final String ADDER = "192.168.254.128";
    private static final int PORT = 6379;
    private static final JedisPool jedisPool = new JedisPool(ADDER, PORT);

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

    /**
     * 模拟生产
     *
     * @author L
     */
    private static void production() {
        // 生成 5 条延迟数据
        for (int i = 0; i < 5; i++) {
            Calendar cal1 = Calendar.getInstance();
            cal1.add(Calendar.SECOND, 5 * i);
            int second3later = (int) (cal1.getTimeInMillis() / 1000);
            getJedis().zadd("id", second3later, "r-" + i);
            System.out.println(DateUtils.getNowDateTime(DateUtils.getNowDateTime()) + " ms:redis生成了一个数据：数据ID为" + "r-" + i);
        }
    }

    /**
     * 模拟消费
     *
     * @author L
     */
    private static void consumption() {
        Jedis jedis = getJedis();
        // 实时轮询
        while (true) {
            Set<Tuple> id = jedis.zrangeWithScores("id", 0, 1);
            if (null == id || id.isEmpty()) {
                System.out.println(DateUtils.getNowDateTime(DateUtils.getNowDateTime()) + " - 当前没有数据");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            int score = (int) ((Tuple) id.toArray()[0]).getScore();
            Calendar cal = Calendar.getInstance();
            int nowSecond = (int) (cal.getTimeInMillis() / 1000);

            if (nowSecond >= score) {
                String ids = ((Tuple) id.toArray()[0]).getElement();
                // 防止高并发时 消费同一条数据
                Long num = jedis.zrem("id", ids);
                if (null != num && num > 0) {
                    System.out.println(DateUtils.getNowDateTime(DateUtils.getNowDateTime()) + " ms: redis 消费了一个任务：消费的任务 id 为" + ids);
                }
            }
        }
    }

    public static void main(String[] args) {
        production();
        consumption();
    }

}
