package org.dandelion.scheduling.kafka.stream;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Arrays;
import java.util.Properties;

/**
 * TODO stream 操作
 *
 * @author L
 * @version 1.0
 * @date 2022/4/19 10:23
 */
public class KafkaStream {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-application-1");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.44.128:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();
        // 从输入主题TextLinesTopic构造一个KStream
        // 其中消息值代表文字行
        KStream<String, String> textLines = builder.stream("topic-01");

        textLines = textLines
                // 用空格将每行文本分割成多个单词
                .flatMapValues(textLine -> Arrays.asList(textLine.toLowerCase().split("\\W+")));


        textLines.foreach((k, v) -> {
            System.out.println(k + "    " + v);
        });

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
    }

}
