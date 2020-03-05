import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;

public class JavaLamdeWC {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("JavaLamdeWC").setMaster("local[2]");
        JavaSparkContext jsc = new JavaSparkContext(conf);
        JavaRDD<String> lines = jsc.textFile("D:\\InstallConfig.ini");
        JavaRDD<String> words = lines.flatMap(line -> Arrays.asList(line.split(" ")).iterator());
        JavaPairRDD<String, Integer> tup = words.mapToPair(word -> new Tuple2<>(word, 1));
        JavaPairRDD<String, Integer> aggred = tup.reduceByKey((v1, v2) -> v1 + v2);
        JavaPairRDD<Integer, String> swaped = aggred.mapToPair(tuple -> tuple.swap());
        JavaPairRDD<Integer, String> sorted = swaped.sortByKey(false);
        JavaPairRDD<String, Integer> res = sorted.mapToPair(tuple -> tuple.swap());
        System.out.println(res.collect());
        res.foreach(x-> System.out.println(x));

//        res.saveAsTextFile("out1");
        jsc.stop();
    }
}
