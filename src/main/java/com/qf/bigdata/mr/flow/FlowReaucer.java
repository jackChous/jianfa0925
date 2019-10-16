package com.qf.bigdata.mr.flow;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

public class FlowReaucer extends Reducer<Text, FlowBean, Text, FlowBean> {
    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
        Iterator<FlowBean> it=values.iterator();
        long upFlow=0;
        long downFlow=0;
        while (it.hasNext()) {
            FlowBean bean=it.next();
//            upFlow += bean.getUpflow();
//            downFlow += bean.getDownflow();
        }
//        FlowBean totar=new FlowBean()
    }
}
