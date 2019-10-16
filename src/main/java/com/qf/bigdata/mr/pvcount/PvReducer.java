package com.qf.bigdata.mr.pvcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @Description
 * @Author cqh <caoqingghai@1000phone.com>
 * @Version V1.0
 * @Since 1.0
 * @Date 2019/9/11 11：33
 */

/**
 * KEYIN,VALUEIN:对应map端输出的key和value的类型
 * KEYOUT：reduce程序处理完成后输出的key的类型
 * VALUEOUT：reduce程序处理完成后输出的value的类型
 */
public class PvReducer extends Reducer<Text, IntWritable,Text,IntWritable> {
    /**
     * 框架在reduce端整理好一组相同的key的数据后，一组数据调用一次reduce方法
     * @param key
     * @param values
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        //一组数据调用一次reduce方法
        //192.168.91.3  1
        //192.168.91.3  1
        //192.168.91.3  1
        //我们需要遍历values，取出每一个value累加
        //先定义一个计数器
        int count=0;
        for (IntWritable value:values){
           //value是 IntWritable类型的要转换成int类型，然后累加
            count+=value.get();
        }
        //如何将值输出
        context.write(key,new IntWritable(count));
        //现在map阶段程序和reduce阶段程序已经完成，现在需要打成jar包交给yarn去执行
        //需要写一个yarn的客户端程序，将程序交给yarn去执行，yarn拿到jar包之后会将
        //jar包分发到其他机器
    }
}











