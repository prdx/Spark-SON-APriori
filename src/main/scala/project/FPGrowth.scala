/*
Golden standard from MLLib for testing purposes
 */

package project
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.fpm.FPGrowth


object FPGrowth {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster("local")
      .setAppName("Project - APriori")

    val sc = new SparkContext(conf)
    val input = sc.textFile(args(0))
    val output = args(1)
    val minSupport = args(2).toFloat

    val followeeList = input.map(x => (x.split(",")(0), x.split(",")(1).toInt)).groupByKey().map(f => f._2.toArray)
    followeeList.collect().foreach(print)

    val fpg = new FPGrowth()
    val model = fpg.setMinSupport(minSupport).run(followeeList)

    model.freqItemsets.saveAsTextFile(output)
  }
}