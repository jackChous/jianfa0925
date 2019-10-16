package com.qf.bigdata.mr.rate;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.htrace.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class RateMapper extends Mapper<LongWritable, Text, Text, RateBean> {
    private Text k = new Text();
    ObjectMapper objectMapper;

    //    该方法在map（）执行前执行且只执行一次，一般用于一些资源的初始化
    protected void setup(Context context) {
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        String line=value.toString();
        RateBean rateBean = objectMapper.readValue(value.toString(), RateBean.class);
        k.set(rateBean.getUid());
        context.write(k,rateBean);
    }
}
