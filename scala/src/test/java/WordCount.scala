import scala.io.BufferedSource

object WordCount {
  def main(args: Array[String]): Unit = {
    val source:BufferedSource = scala.io.Source.fromFile("spark.log")
    val lst = source.getLines().toList

    lst.flatMap(_.split("\\s+"))
      .filter(_.nonEmpty)
      .groupBy(x => x)
      .mapValues(_.length)
      .toList
      .sortBy(_._2)
      .reverse
      .foreach(println)

    source.close()
  }
}
