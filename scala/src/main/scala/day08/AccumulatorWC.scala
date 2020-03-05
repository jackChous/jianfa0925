package day08

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.util.AccumulatorV2

import scala.collection.mutable

class AccumulatorWC extends AccumulatorV2[String,mutable.HashMap[String,Int]]{
 private val hashMap =new mutable.HashMap[String,Int]()
  override def isZero: Boolean = hashMap.isEmpty

  override def copy(): AccumulatorV2[String, mutable.HashMap[String, Int]] = {
    val wc =new AccumulatorWC
    hashMap.synchronized{
      wc.hashMap.++=(hashMap)
    }
    wc
  }

  override def reset(): Unit = hashMap.clear()

  override def add(v: String): Unit = {
    hashMap.get(v) match {
      case Some(a) =>hashMap.put(v,a+1)
      case None =>hashMap.put(v,1)
    }
  }

  override def merge(other: AccumulatorV2[String, mutable.HashMap[String, Int]]): Unit = {
    other match {
      case m:AccumulatorV2[String,mutable.HashMap[String,Int]] =>{
        for ((k,v)<-m.value){
          hashMap.get(k) match {
            case None => hashMap.put(k,v)
            case Some(a) =>hashMap.put(k,a+v)
          }
        }
      }
    }
  }

  override def value: mutable.HashMap[String, Int] = hashMap
}

object AccumulatorWC{
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("accwc").setMaster("local")
    val sc=new  SparkContext(conf)
    val acc = new AccumulatorWC
//    注册
    sc.register(acc,"wc")
    val rdd=sc.makeRDD(Array("w","c","w","c"),2)
    rdd.foreach(x=>acc.add(x))
    println(acc.value)
    sc.stop()
  }
}















