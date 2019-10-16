package com.qf.bigdata.mr.flow;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class FlowBean implements Writable {
    long upflow;
    long downflow;
    long sumflow;

    @Override
//    定义了有参的构造方法，这个时候一定要显示的定义无参的构造方法
    public void write(DataOutput dataOutput) throws IOException {
//        out.writeLong(upflow);
    }

//    反序列化，从输入流读取各字段的信息
    @Override
    public void readFields(DataInput dataInput) throws IOException {
//        upflow = in.readLong();
//        downflow=in.readLong();
//        sumflow=in.
    }

    public FlowBean(long upflow, long downflow) {
        this.upflow=upflow;
        this.downflow=downflow;
        this.sumflow=upflow+downflow;
    }

    public FlowBean() {

    }

    public long getUpflow() {
        return upflow;
    }

    public void setUpflow(long upflow) {
        this.upflow = upflow;
    }

    public long getDownflow() {
        return downflow;
    }

    public void setDownflow(long downflow) {
        this.downflow = downflow;
    }

    public long getSumflow() {
        return sumflow;
    }

    public void setSumflow(long sumflow) {
        this.sumflow = sumflow;
    }
}
