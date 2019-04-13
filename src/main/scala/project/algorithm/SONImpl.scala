package project.algorithm

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks

object SONImpl {

	var freqItemset = ListBuffer[String]()


	def execute(_baskets: Iterator[List[Int]], sp: Float, locals: org.apache.spark.rdd.RDD[String]) :  Iterator[String] = {
        
        //val len = _baskets.length
	    //println("\n\nLEN\n\n"+len)
      
	    //val countThreshold = math.ceil(len * sp)
	    val baskets: List[List[Int]] = _baskets.toList//.map(basket => basket.toList.map(x => x.toString))
	    val len = baskets.length
	    println("\n\nLEN - "+len)
        
        baskets.foreach {basket =>
        	println("Basket" + basket)
        	locals.foreach { local =>
        		println("\n\n")
        		println("Local" + local)
        		println("Basket" + basket)
        		println("\n\n")
        		freqItemset += local
        	}}
        
		return freqItemset.iterator
	}

	/*def Phase2(count_threshold:Float, local_freq_itemsets) = {
        global_freq_itemsets = sc.parallelize(local_freq_itemsets).reduceByKey(_ + _)
    							 .filter((_itemset, _counts): _counts >= count_threshold).collect()
                
		return global_freq_itemsets
	}

	def setFreqItems(baskets: List[List[String]], sp: Float): mutable.HashMap[String, Int] = {
    val countTable = mutable.HashMap.empty[String, Int]

    baskets.foreach(basket =>
      basket.foreach(i =>
        countTable.get(i) match {
          case None => countTable += (i -> 1)
          case Some(x) => countTable(i) += 1
        }
      )
    )

    countTable
  }*/

}