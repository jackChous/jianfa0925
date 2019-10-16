package FileSystemTest;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;

import java.io.IOException;

public class TestStatus {
    public static void main(String[] args) throws IOException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://hadoop04:8020");
        FileSystem fs = FileSystem.get(conf);
// 获取文件的详细属性
        RemoteIterator<LocatedFileStatus> listFile =fs.listFiles(new Path("/"),
                true);
        while(listFile.hasNext()) {
            LocatedFileStatus status=listFile.next();
// 输出文件的详细信息
//输出文件的名称
            System.out.print("文件的名称-->"+status.getPath().getName()+"\t");
// 输出文件的长度
            System.out.print("文件的长度-->"+status.getLen()+"\t");
            //输出文件的权限
            System.out.print("文件的权限-->"+status.getPermission()+"\t");
// 输出文件的块大小
            System.out.println("文件的块大小-->"+status.getBlockSize());
        }
    }
}

