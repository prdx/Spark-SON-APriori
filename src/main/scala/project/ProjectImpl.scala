package project

import org.apache.log4j.LogManager
import org.apache.spark.{SparkConf, SparkContext}

object ProjectImpl {
  def main(args: Array[String]): Unit = {
    val logger: org.apache.log4j.Logger = LogManager.getRootLogger

    if (args.length != 3) {
      logger.error("Usage:\nproject.APrioriImpl <input> <output> <min-support>")
    }

    val conf = new SparkConf().setMaster("local")
      .setAppName("Project - APriori")

    val sc = new SparkContext(conf)
    val input = sc.textFile(args(0))
    val output = args(1)
    //val minSupport: Float = Float(args(2))
    
    //filtering by MaxID if required while testing.
    val filteredInp = input.filter(x => (x.split("\t")(0)).toInt < 100 && (x.split("\t")(1)).toInt < 100)
    filteredInp.foreach(x => println(x))
    
    
    
    
  }
  
  
  
}
