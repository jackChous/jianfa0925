package com.qf.bigdata.mr.wc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/**
 * @Description
 * @Author cqh <caoqingghai@1000phone.com>
 * @Version V1.0
 * @Since 1.0
 * @Date 2019/9/11 11：47
 */
public class WCJobSubmitter {
    public static void main(String[] args) throws Exception {
        /**
         * 程序启动需要做一些参数设置，要告诉程序中的一些信息，比如数据的输入路径
         * 这些信息很零散，可以把这些信息封装在一个对象中，这个对象就是job
         */
        Configuration conf = new Configuration();
        
        Job job = Job.getInstance(conf);//可以带参数
        //然后需要使用job封装一些信息
        //首先程序提交需要提交jar包，那么就要指定jar包的位置
        //通过类名获取jar包位置
        job.setJarByClass(WCJobSubmitter.class);
        //指定job中使用的mapper和reducer类
        job.setMapperClass(WcMapper.class);
        job.setReducerClass(WcReducer.class);
        /**
         * 还要说明map逻辑类返回给框架的结果的数据类型是什么
         * 这样框架才可以帮我们进行序列化和反序列化
         */
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        /**
         * 说明最终的输出的结果的数据类型
         */
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        /**
         * 设置框架读取数据的组件，如果是普通文本文件，就用TextInputFormat去读取
         */
        job.setInputFormatClass(TextInputFormat.class);
        /**
         * 设置数据的读取位置
         */
        FileInputFormat.setInputPaths(job,new Path("d:/data/wc"));


        /**
         * 设置写出数据的组件,如果是普通文本文件，就用TextOutputFormat去写出
         */
        job.setOutputFormatClass(TextOutputFormat.class);
        /**
         * 定义写出的位置
         */
        FileOutputFormat.setOutputPath(job,new Path("d:/out/wc"));

//设置combiner,combiner可能会调用多次
        job.setCombinerClass(WcReducer.class);

        //可以设置reducetask的数量
        job.setNumReduceTasks(2);
        /**
         * 信息设置完成后就可以调用方法向yarn提交
         * waitForCompletion方法会将jar包和信息提交给RM
         * 然后RM将分发到其他机器进行启动运算
         */
        /**
         * 如果传入参数true，集群运行的时候会有进度显示，这个进度将在客户端打印
         */
        boolean res = job.waitForCompletion(true);
        //设置客户端有返回状态码的退出
        //shell中可以获取上一个程序的返回的状态码，可以根据状态码的不同有不同的处理方式
        System.exit(res?0:1);
    }
}