/*package project.algorithm

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks

object SONImpl {



	def execute(local_freq_itemsets : PairRDD[String], countThreshold: Float ) = {
        
        global_freq_itemsets = Phase2(countThreshold, local_freq_itemsets)
		return global_freq_itemsets
	}

	def Phase2(count_threshold:Float, local_freq_itemsets) = {
        global_freq_itemsets = sc.parallelize(local_freq_itemsets).reduceByKey(_ + _)
    							 .filter((_itemset, _counts): _counts >= count_threshold).collect()
                
		return global_freq_itemsets
	}

	// def getCount(local_freq_itemsets) = {
 //        local_freq_itemsets:
 //                list.append((_itemset, 1))      
	// 	return iter(list)
	// 	}


}*/