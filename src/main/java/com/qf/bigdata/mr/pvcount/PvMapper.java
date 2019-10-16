package com.qf.bigdata.mr.pvcount;


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @Description
 * @Author cqh <caoqingghai@1000phone.com>
 * @Version V1.0
 * @Since 1.0
 * @Date 2019/9/11 11：16
 */

/**
 * KEYIN：是框架要传递给map方法的输入的参数的key的数据类型
 * VALUEIN：是框架要传递给map方法的输入的参数的value的数据类型
 *
 * 默认情况下框架传入的key是从待处理数据中读取的某一行的起始偏移量
 * 所有key的类型是long,但是由于这些值要在框架中进行读写操作。原生的读写效率低
 * 所以hadoop对其进行了改造，有一些替代品
 * long LongWritable
 * int IntWritable
 * String Text
 * .....
 *
 * map方法处理完 数据 之后要写出一个键值对一个key一个value
 * KEYOUT:map方法处理完成后，输出结果的key的数据类型
 * VALUEOUT:map方法处理完成后，输出结果的value的数据类型
 */
public class PvMapper extends Mapper<LongWritable, Text,Text, IntWritable> {
    Text k =  new Text();
    IntWritable v = new IntWritable(1);
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        /**
         * map方法是框架提供的，每读取一条数据就会调用一次map()方法
         * 读一条数据就要处理一次，映射成一个键值对
         */
        //将value转换成string
        String line = value.toString();
        //将字符串拆分
        String[]fields = line.split(" ");
        //获取IP
        String ip = fields[0];
        //需要将结果写出，使用context.write(key,value)
        //key，value的类型与keyout、valueout相对应
        k.set(ip);
        context.write(k,v);
    }
}















