package project

import org.apache.log4j.LogManager
import org.apache.spark.{SparkConf, SparkContext}
import algorithm.Apriori
import algorithm.SONImpl

object ProjectImpl {
  def main(args: Array[String]): Unit = {

    val t1= System.nanoTime
    
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
    
    // filtering by MaxID if required while testing. Commenting for now
    //val filteredInp = input.filter(x => (x.split("\t")(0)).toInt < 100000 && (x.split("\t")(1)).toInt < 100000)

    // Generating followeeList as List(followee1, followee2)
    val followeeList = input.map(x => (x.split("\t")(0),x.split("\t")(1).toInt)).groupByKey().map(y => (y._2.toList))
    followeeList.repartition(60)
    println("\n\n\t\tFollowee List generated\n\n")

    var locals = followeeList.mapPartitions(partition => Apriori.execute(partition, minSupport), preservesPartitioning=true).map(x => (x,1)).reduceByKey(_+_).collect.toList
    println("\n\n\t\tLocals generated\n\n")
    //val myLocals = locals.
    //myLocals.foreach(x => println(x))
    val globals = followeeList.mapPartitions(partition => SONImpl.execute(partition, minSupport, locals), preservesPartitioning=true).reduceByKey(_+_)
    val minsp = followeeList.count * minSupport
    globals.filter(x => x._2 >= minsp).sortBy(_._2, false).saveAsTextFile(output)
    println("\n\n\nTime = "+(System.nanoTime - t1)/1e9d)

  }
  
}
