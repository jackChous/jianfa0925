package com.qf.bigdata.mr.rate;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RateReducer extends Reducer<Text, RateBean, Text, RateBean> {


    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);
    }

    @Override
    protected void reduce(Text key, Iterable<RateBean> values, Context context) throws IOException, InterruptedException {
//        定义一个list,将value放入list中，然后按照评分进行排序
        List<RateBean> rateBeans = new ArrayList();
        for (RateBean r : values) {
            RateBean newBean=new RateBean();
            newBean.setUid(r.getUid());
            newBean.setMovie(r.getMovie());
            newBean.setTimeStamp(r.getTimeStamp());
            newBean.setRate(r.getRate());

            rateBeans.add(newBean);
        }
        Configuration configuration=context.getConfiguration();
        int topN = configuration.getInt("topN", 5);
        Collections.sort(rateBeans);
        for (int i = 0; i < topN; i++) {
            context.write(key,rateBeans.get(i));
        }
    }

//    最后仅执行一次，用于释放资源
    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        super.cleanup(context);
    }
}
