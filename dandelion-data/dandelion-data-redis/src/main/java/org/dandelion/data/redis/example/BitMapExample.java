package org.dandelion.data.redis.example;

import org.dandelion.data.redis.utils.RedisUtils;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Redis BitMap 基本用法
 *
 * @author lx6x
 * @date 2023/8/2
 */
@RestController
public class BitMapExample {

    //1. 获取登录用户
    private static final Long USER_ID = 1L;
    private static final String USER_SIGN_KEY = "USER_SIGN_KEY";

    @Resource
    private RedisUtils redisUtils;

    /**
     * 签到
     *
     * @return .
     */
    @PostMapping("/sign")
    public String sign() {
        //2. 获取日期
        LocalDateTime now = LocalDateTime.now();
        //3. 拼接key
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String key = USER_SIGN_KEY + ":" + USER_ID + keySuffix;
        //4. 获取今天是本月的第几天
        int dayOfMonth = now.getDayOfMonth();
        //5. 写入redis setbit key offset 1
        redisUtils.getRedisTemplate().opsForValue().setBit(key, dayOfMonth - 1, true);
        return "ok";
    }

    /**
     * 签到个数
     *
     * @return count
     */
    @GetMapping("/signCount")
    public int signCount() {

        //2. 获取日期
        LocalDateTime now = LocalDateTime.now();
        //3. 拼接key
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String key = USER_SIGN_KEY + ":" + USER_ID + keySuffix;
        //4. 获取今天是本月的第几天
        int dayOfMonth = now.getDayOfMonth();
        //5. 获取本月截至今天为止的所有的签到记录，返回的是一个十进制的数字 BITFIELD sign:5:202301 GET u3 0
        List<Long> result = redisUtils.getRedisTemplate().opsForValue().bitField(
                key,
                BitFieldSubCommands.create()
                        .get(BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth)).valueAt(0));
        //没有任务签到结果
        if (result == null || result.isEmpty()) {

            return 0;
        }
        Long num = result.get(0);
        if (num == null || num == 0) {

            return 0;
        }
        //6. 循环遍历
        int count = 0;
        while (true) {

            //6.1 让这个数字与1 做与运算，得到数字的最后一个bit位 判断这个数字是否为0
            if ((num & 1) == 0) {

                //如果为0，签到结束
                break;
            } else {

                count++;
            }
            num >>>= 1;
        }
        return count;
    }

}
