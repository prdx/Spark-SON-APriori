package project.algorithm

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks

/**
	Phase 2 of the SON Apriori implementation
*/
object SONImpl {

	def execute(_baskets: Iterator[List[Int]], sp: Float, myLocals:List[(String,Int)]) :  Iterator[(String, Int)] = {
        
        //Partition of the transactions generated from the input file
	    val baskets: List[List[String]] = _baskets.toList.map(basket => basket.toList.map(x => x.toString))
	    val len = baskets.length
	    val countThreshold = math.ceil(len * sp)
	    val globalList  = ListBuffer[(String, Int)]()
		
		//variable to handle each barket as a set        
	    var bsk = Set[String]()

	    //Loop that iterates over the locals and partition of the original transactions to get global frequent items
        baskets.foreach {basket =>
        	bsk = basket.toSet	
        	myLocals.foreach { local =>
 	       		var lsk = local._1.split(",").toSet
        		if (lsk.subsetOf(bsk))
        			globalList += ((local._1,1))
        	}}
        
		return globalList.iterator
	}

	

	
}