package com.qf.bigdata.mr.rate;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class RateBean implements WritableComparable<RateBean> {
    private String movie;
    private String rate;
    private String timeStamp;
    private String uid;
    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.movie);
        dataOutput.writeUTF(this.rate);
        dataOutput.writeUTF(this.timeStamp);
        dataOutput.writeUTF(this.uid);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.movie = dataInput.readUTF();
        this.rate = dataInput.readUTF();
        this.timeStamp = dataInput.readUTF();
        this.uid = dataInput.readUTF();
    }

    @Override
    public String toString() {
        return movie+"\t"+rate;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public int compareTo(RateBean o) {
        return -this.rate.compareTo(o.rate);
    }
}
