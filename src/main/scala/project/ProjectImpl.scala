package project

import org.apache.log4j.LogManager
import org.apache.spark.{SparkConf, SparkContext}
import algorithm.Apriori
import algorithm.SONImpl
object ProjectImpl {
  def main(args: Array[String]): Unit = {
    val logger: org.apache.log4j.Logger = LogManager.getRootLogger

    if (args.length != 3) {
      logger.error("Usage:\nproject.APrioriImpl <input> <output> <min-support>")
    }

    //read input params

    val conf = new SparkConf().setAppName("Project - APriori")

    val sc = new SparkContext(conf)
    val input = sc.textFile(args(0))
    val output = args(1)
    val minSupport = (args(2)).toFloat
    
    //filtering by MaxID if required while testing. Commenting for now
    /*val filteredInp = input.filter(x => (x.split("\t")(0)).toInt < 100 && (x.split("\t")(1)).toInt < 100)
    filteredInp.foreach(x => println(x))*/
    
    //Generating followeeList as List(followee1, followee2)
    val followeeList = input.map(x => (x.split("\t")(0),x.split("\t")(1).toInt)).groupByKey().map(y => (y._2.toList.sortBy(y => y)))
    //followeeList.coalesce(1).saveAsTextFile(args(1))

    var locals = followeeList.mapPartitions(partition => Apriori.execute(partition, minSupport))//.map(x => (x,1))//.reduceByKey(_+_)
    val myLocals = locals.collect.toList
    val globals = followeeList.mapPartitions(partition => SONImpl.execute(partition, minSupport, myLocals)).reduceByKey(_+_)
    globals.coalesce(1).saveAsTextFile(output)



  }
  
}