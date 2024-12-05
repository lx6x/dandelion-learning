package org.dandelion.scheduling.kafka.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * TODO 消费者
 *
 * @author L
 * @version 1.0
 * @date 2022/2/9 10:50
 */
public class ConsumerMessage {

    private static final String TOPIC_NAME = "topic-01";

    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers"
//                ,"192.168.56.105:9092,192.168.56.105:9093,192.168.56.105:9094"
                , "localhost:9092"
        );
        props.setProperty("group.id", "1");
        // 是否自动确认offset
        props.setProperty("enable.auto.commit", "true");
        // 自动确认offset的时间间隔
        props.setProperty("auto.commit.interval.ms", "1000");
        // earliest 最早的偏移量
        // latest   最后提交的偏移量
        // none     未找到消费者组的之前偏移量，则向消费者抛出e异常
        props.put("auto.offset.reset", "earliest");
        props.put("session.timeout.ms", "30000");

        // 序列化类
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(TOPIC_NAME));

        try {
            for (; ; ) {
                Thread.sleep(1000);
                // poll一次会返回一批数据
                Duration duration = Duration.ofMillis(1000);
                ConsumerRecords<String, String> records = consumer.poll(duration);
                for (ConsumerRecord<String, String> record : records) {
                    System.err.printf("消费消息：topic=%s, partition=%d, offset=%d, key=%s, value=%s\n",
                            record.topic(), record.partition(), record.offset(), record.key(), record.value());
                }
            }
        } finally {
            consumer.close();
        }
    }

}
