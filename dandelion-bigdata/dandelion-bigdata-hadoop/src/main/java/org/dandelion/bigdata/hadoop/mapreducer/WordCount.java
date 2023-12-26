package org.dandelion.bigdata.hadoop.mapreducer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Mapreduce 官网示例一
 *
 * @author L
 * @version 1.0
 * @date 2023-02-22
 */
public class WordCount {

    public static class WordCountMapper extends Mapper<Object, Text, Text, IntWritable> {

        /**
         * 赋值变量值为 1
         */
        private final static IntWritable ONE = new IntWritable(1);
        private Text word = new Text();

        /**
         * @param key     输入键值
         * @param value   输入的数据值
         * @param context 当前的全局上下文
         * @throws IOException
         * @throws InterruptedException
         */
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            StringTokenizer itr = new StringTokenizer(value.toString());
            while (itr.hasMoreTokens()) {
                String s = itr.nextToken();
                System.err.println(s);
                word.set(s);
                context.write(word, ONE);
            }
        }
    }

    public static class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        private IntWritable result = new IntWritable();

        /**
         * @param key     map 结果后生成的key
         * @param values  map 结果后生成的 value
         * @param context 当前全局上下问
         * @throws IOException
         * @throws InterruptedException
         */
        public void reduce(Text key, Iterable<IntWritable> values, Context context)
                throws IOException, InterruptedException {
            int sum = 0;
            // 遍历 map 后的结果，然后统计个数
            for (IntWritable val : values) {
                sum += val.get();
            }
            result.set(sum);
            context.write(key, result);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        // conf.set("mapreduce.app-submission.cross-platform", "true");
        // Windows上开发必须配置hadoop.home.dir
        System.setProperty("hadoop.home.dir", "E:\\code\\total\\winutils-master\\hadoop-3.0.0");
        // 设置 hadoop 用户执行
        System.setProperty("HADOOP_USER_NAME", "hadoop");
        // 必须加载hadoop.dll动态链接库
        System.load("E:\\code\\total\\winutils-master\\hadoop-3.0.0\\bin\\hadoop.dll");
        Job job = Job.getInstance(conf, "word count");
        job.setJarByClass(WordCount.class);
        job.setMapperClass(WordCountMapper.class);
        job.setCombinerClass(WordCountReducer.class);
        job.setReducerClass(WordCountReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        String args_0 = "hdfs://node1:9000/test/mapreducer/wordcount.txt";
        String args_1 = "hdfs://node1:9000/test/mapreducer/out/wordcount";
        // 输入路径
        FileInputFormat.addInputPath(job, new Path(args_0));
        // 输出路径
        FileOutputFormat.setOutputPath(job, new Path(args_1));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

}
