package FileSystemTest;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

import java.io.IOException;

public class TestFileSystem {
    public static void main(String[] args) throws IOException {
        // 首先连接HDFS 之前，需要获得一个配置类，用于封装连接信息
        Configuration configuration = new Configuration();
        // 课题通过set的方法，设置属性与内容（连接HDFS 的一些相关信息）
        configuration.set("fs.defaultFS","hdfs://hadoop04:8020");
        // 获得文件系统 可以理解为客户端的概念
        FileSystem fileSystem = FileSystem.get(configuration);
        System.out.println(fileSystem);
        System.out.print("连接成功");
    }
}
