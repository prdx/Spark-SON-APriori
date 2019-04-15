/*
Golden standard from MLLib for testing purposes
 */

package project
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.fpm.FPGrowth


object FPGrowth {
  def main(args: Array[String]): Unit = {

    val t1= System.nanoTime
    val conf = new SparkConf().setMaster("local")
      .setAppName("Project - APriori")

    val sc = new SparkContext(conf)
    val input = sc.textFile(args(0))
    val output = args(1)
    val minSupport = args(2).toFloat

    val followeeList = input.map(x => (x.split("\t")(0), x.split("\t")(1).toInt)).groupByKey().map(f => f._2.toArray)
    println("\n\n\nFolloweeList Count- "+followeeList.count)
    //followeeList.collect().foreach(print)

    val fpg = new FPGrowth()
    val model = fpg.setMinSupport(minSupport).run(followeeList)

    model.freqItemsets.saveAsTextFile(output)
    println("\n\n\nTime = "+(System.nanoTime - t1)/1e9d)
  }
}
