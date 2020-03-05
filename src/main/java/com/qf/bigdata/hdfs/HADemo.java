package com.qf.bigdata.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @Description
 * @Author cqh <caoqingghai@1000phone.com>
 * @Version V1.0
 * @Since 1.0
 * @Date 2019/9/10 16：45
 */
public class HADemo {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
//        Configuration conf = new Configuration();
//        conf.set("dfs.nameservices","ns1");
//        conf.set("dfs.ha.namenodes.ns1","nn1,nn2");
//        conf.set("dfs.namenode.rpc-address.ns1.nn1","mini1:9000");
//        conf.set("dfs.namenode.rpc-address.ns1.nn2","mini2:9000");
//        conf.set("dfs.client.failover.proxy.provider.ns1","org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
//        FileSystem fs = FileSystem.get(new URI("hdfs://ns1/"),conf,"root");
//        fs.mkdirs(new Path("/data"));
//        fs.close();
        testConnect();
    }

    public static void testConnect() throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();
        conf.addResource("core-site.xml");
        conf.addResource("hdfs-site.xml");
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop01:9000/"),conf,"root");
        fs.mkdirs(new Path("/data3"));
        fs.close();
    }
}