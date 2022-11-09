package org.dandelion.scheduling.kafka.producers;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * TODO 生产者
 *
 * @author L
 * @version 1.0
 * @date 2022/1/24 16:27
 */
public class ProducerMessage {

    private static KafkaProducer<String, String> producer;

    private static Properties properties;

    public static void main(String[] args) {

        properties = new Properties();

        // kafka broker连接地址，多个可[ ，]隔开
        properties.put("bootstrap.servers", "192.168.44.128:9092");

        // 序列化类
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("ack", "0");
        properties.put("retries", 0);
        properties.put("batch.size", 16384);
        properties.put("linger.ms", 1);
        properties.put("buffer.memory", 33554432);

//        sendMessage();
        synchronizeSendMessage();

//        asynchronousSendMessage();
    }


    /**
     * 默认方式发送消息（发送后不管结果）
     *
     * @author L
     */
    public static void sendMessage() {

        producer = new KafkaProducer<>(properties);

        ProducerRecord<String, String> record = new ProducerRecord<>("topic-01", "key-01", "value-01");

        System.out.println(" ------ 默认方式发送");
        producer.send(record);
    }

    /**
     * 同步发送
     *
     * @author L
     */
    public static void synchronizeSendMessage() {
        producer = new KafkaProducer<>(properties);

        ProducerRecord<String, String> record = new ProducerRecord<>("topic-01", "1", "1 2 3 4 5 6 7");

        try {
            RecordMetadata recordMetadata = producer.send(record).get();
            System.out.printf("同步发送：%s，分区：%d，offset：%d\n", recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步发送
     *
     * @author L
     */
    public static void asynchronousSendMessage() {
        producer = new KafkaProducer<>(properties);

        ProducerRecord<String, String> record = new ProducerRecord<>("topic-01", "key-01", "value-03");

        producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e != null) {
                    e.printStackTrace();
                } else {
                    System.out.printf("异步发送：%s，分区：%d，offset：%d\n", recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());
                }
            }
        });

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
