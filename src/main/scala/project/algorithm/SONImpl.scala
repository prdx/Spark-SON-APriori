package project.algorithm

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks

object SONImpl {

	def execute(_baskets: Iterator[List[Int]], sp: Float, myLocals:List[String]) :  Iterator[(String, Int)] = {
        
	    val baskets: List[List[String]] = _baskets.toList.map(basket => basket.toList.map(x => x.toString))
	    val len = baskets.length
	    val countThreshold = math.ceil(len * sp)
	    val globalList  = ListBuffer[(String, Int)]()
        
	    var bsk = Set[String]()

        baskets.foreach {basket =>
        	bsk = basket.toSet	
        	myLocals.foreach { local =>
 	       		var lsk = local.split(",").toSet
        		if (lsk.subsetOf(bsk))
        			globalList += ((local,1))
        	}}
        
		return globalList.iterator
	}

	

	
}