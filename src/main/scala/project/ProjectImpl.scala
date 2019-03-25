package project

import org.apache.log4j.LogManager
import org.apache.spark.{SparkConf, SparkContext}

object ProjectImpl {
  def processInput(line: String): (Int, Int) = {
    val nodes = line.split("\\s+")
    (nodes(0).toInt, nodes(1).toInt)
  }

  def main(args: Array[String]): Unit = {
    val logger: org.apache.log4j.Logger = LogManager.getRootLogger

    if (args.length != 3) {
      logger.error("Usage:\nproject.APrioriImpl <input> <output> <min-support>")
    }

    val conf = new SparkConf().setMaster("local")
      .setAppName("Project - APriori")

    val sc = new SparkContext(conf)
    val input = args(0)
    val output = args(1)
    val minSupport: Float = args(2).toFloat

    val textFile = sc.textFile(input)
    val pairs = textFile.map(line => processInput(line)).groupByKey()


    val debug = pairs.collect()
    debug.foreach(println)
  }
}
