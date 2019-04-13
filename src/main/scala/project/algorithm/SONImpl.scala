package project.algorithm

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks

object SONImpl {

	def execute(_baskets: Iterator[List[Int]], sp: Float, myLocals:List[String]) :  Iterator[(String, Int)] = {
        
	    val baskets: List[List[Int]] = _baskets.toList//.map(basket => basket.toList.map(x => x.toString))
	    val len = baskets.length
	    val countThreshold = math.ceil(len * sp)
	    //var localSet = Set[String]()
	    val globalList  = ListBuffer[(String, Int)]()
	    myLocals.foreach(x => println("x-" + x))
        
        baskets.foreach {basket =>
        		println("Basket" + basket)
        	myLocals.foreach { local =>
 	       		
        		globalList += ((local,1))
        	}}
        
		return globalList.iterator
	}

	

	
}