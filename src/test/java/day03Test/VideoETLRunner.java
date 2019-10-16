package day03Test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;

public class VideoETLRunner implements Tool {
    private Configuration configuration = null;

    @Override
    public int run(String[] args) throws Exception {
        configuration=this.getConf();
        configuration.set("inpath", args[0]);
        configuration.set("outpath",args[1]);

        Job job = Job.getInstance(configuration);
        job.setJarByClass(VideoETLRunner.class);
        job.setMapperClass(VideoETLMapper.class);
        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(Text.class);
        job.setNumReduceTasks(0);

        this.initJobInputPath(job);
        this.initJobOutputPath(job);
        return job.waitForCompletion(true)?0:1;
    }

    @Override
    public void setConf(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Configuration getConf() {
        return this.configuration;
    }

    private void initJobOutputPath(Job job) throws IOException {
        Configuration configuration = job.getConfiguration();
        String outPathString = configuration.get("outpath");
        FileSystem fileSystem = FileSystem.get(configuration);

        Path outPath = new Path(outPathString);
        if (fileSystem.exists(outPath)) {
            fileSystem.delete(outPath, true);
        }
        FileOutputFormat.setOutputPath(job,outPath);
    }

    private void initJobInputPath(Job job) throws IOException {
        Configuration configuration = job.getConfiguration();
        String inPathString = configuration.get("inpath");

        FileSystem fileSystem = FileSystem.get(configuration);

        Path inPath = new Path(inPathString);
        if (fileSystem.exists(inPath)) {
            FileInputFormat.addInputPath(job, inPath);
        }else {
            throw new RuntimeException("HDFS中该文件目录不存在" + inPathString);
        }
    }

    public static void main(String[] args) {
        try {
            int resultCode = ToolRunner.run(new VideoETLRunner(), args);
            if (resultCode == 0) {
                System.out.println("Success");
            } else {
                System.out.println("Fail");
            }
            System.exit(resultCode);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
