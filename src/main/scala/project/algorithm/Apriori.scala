package project.algorithm

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks

object Apriori {
  
    var k1FreqItemset = ListBuffer[String]()


  def execute(_baskets: Iterator[List[Int]], sp: Float): Iterator[String] = {
    
    var freqItemset = ListBuffer[String]()

    val baskets: List[List[String]] = _baskets.toList.map(basket => basket.map(x => x.toString))
    val len = baskets.length
    var itemPairs = 1
    var hasFreqItems = !baskets.isEmpty

    // First we get the count table for every item
    var countTable = setFreqItems(baskets, sp, itemPairs)

    // We set the new threshold based on the new length of the partition
    val countThreshold = math.ceil(len * sp)

    while(hasFreqItems) {
      if (itemPairs > 1) {
        var retVal = getFreqItems(baskets, freqItemset, countTable, sp, itemPairs, countThreshold)
        hasFreqItems = retVal._1
        freqItemset = retVal._2
      }

      // We check frequent threshold
      val _countTable = countTable
      _countTable.foreach(item =>
        if(item._2 >= countThreshold) {
          k1FreqItemset += item._1
          freqItemset += item._1
        }
      )
      itemPairs += 1
      countTable = getCountTable(k1FreqItemset, itemPairs)
      k1FreqItemset = ListBuffer[String]()
    }
     freqItemset.iterator
  }

  def setFreqItems(baskets: List[List[String]], sp: Float, numberOfPair: Int): mutable.HashMap[String, Int] = {
    // We build the count table here
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
  }

 def getFreqItems(baskets: List[List[String]], freqItemset: ListBuffer[String],  countTable: mutable.HashMap[String, Int], sp: Float, itemPairs: Int, countThreshold: Double): (Boolean, ListBuffer[String]) = {
    val _countTable: mutable.HashMap[String, Int] = countTable
    var hasFreqItem = false

    baskets.foreach { basket =>
      _countTable.foreach { item =>
        // Append to the frequent itemlist if the count is more than threshold
        if (item._2 >= countThreshold) {
          k1FreqItemset += item._1
          freqItemset += item._1
          countTable -= item._1
        }
      }
      _countTable.foreach { item =>
        if (item._1.split(",").toSet.subsetOf(basket.toSet)) {
          hasFreqItem = true
          countTable(item._1) += 1
        }
      }
    }

    (hasFreqItem, freqItemset)
  }

  def getCountTable(freqItemset: ListBuffer[String], itemPairs: Int): mutable.HashMap[String, Int] = {
    var itemSet = Set[String]()
    var tmp = Set[String]()
    var countTable = mutable.HashMap.empty[String, Int]

    // Get each item from frequent itemsets
    freqItemset.foreach { item =>
      item.split(",").foreach { i =>
        tmp += i
      }
    }

    // Construct a new combination from the previous pair
    tmp.subsets(itemPairs).foreach { c =>
      itemSet += c.toList.map(x => x.toInt).sorted.toSet.mkString(",")
    }

    val itemSetList = itemSet.toList
    for (i <- 0 to itemSet.size - 1) {
      val c = itemSetList(i).split(",").toSet.subsets(itemPairs - 1).toList
      val outerLoop = new Breaks

      outerLoop.breakable {
        for (j <- 0 to c.size - 1) {
          // Check new candidate should also be in previous itemsets
          var isExists = false
          val innerLoop = new Breaks
          val freqItemSetList = freqItemset.toList

          innerLoop.breakable {
            for (k <- 0 to freqItemSetList.size - 1) {
              if (freqItemSetList(k) == c(j).mkString(",")) {
                isExists = true
                innerLoop.break()
              }
            }
          }

          // If the constructed combination does not exist in the previous itemset
          if (!isExists) {
            outerLoop.break()
          } else {
            countTable += (itemSetList(i) -> 0)
          }
        }
      }
    }

    countTable
  }
}