package project.algorithm

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks

object SONImpl {



	def execute(baskets:List[List[String]], minSup: Float, algorithm = 'A-Priori') = {
		val _baskets = baskets
        val _mini_sup = minSup
        

        if _baskets == '' and baskets == '': return None
        elif _baskets == '' and baskets != '': _baskets = baskets

        if _mini_sup is None and mini_sup is None: return None
        elif (_mini_sup is None) and (mini_sup is not None): _mini_sup = mini_sup        
        
        #creating RDD from external file for Baskets.
        if (_chunks is None):
            _rddStrBaskets = sc.textFile(_baskets, use_unicode=USE_UNICODE) # ["1,2,4,10,14,15,16"]
        else:
            _rddStrBaskets = sc.textFile(_baskets, minPartitions=_chunks, use_unicode=USE_UNICODE) #['1,2,3', '1,2,5', '1,3,4',...]
        
        total_buskets = _rddStrBaskets.count()
        
        global_freq_itemsets = Phase2(_rddStrBaskets, _mini_sup, local_freq_itemsets)
        if PRINT_TIME : print ('SON.execute=>Finish=>%s'%(str(datetime.now())))         
			return global_freq_itemsets
	}

	def Phase2(rddbaskets, mini_sup, local_freq_itemsets) = {
        global_freq_itemsets = ListBuffer[String]
        count_threshold = math.ceil(total_buskets * mini_sup)
        
        local_freq_itemsets = sc.broadcast(local_freq_itemsets) #broadcast the itemsets data to each worker in distribution environment.
        # local_freq_itemsets.value to retrieve the data list.
        rddglobal_counts = rddbaskets.mapPartitions(lambda basket: SON.getCount(basket, local_freq_itemsets)).collect()
        
        if DEBUG : print ('SON.Phase2=>rddglobal_counts=%s'%(str(rddglobal_counts)))
        global_freq_itemsets = sc.parallelize(rddglobal_counts).reduceByKey(lambda _accum, _val: _accum + _val).filter(lambda (_itemset, _counts): _counts >= count_threshold).collect()
                
        if DEBUG : print ('SON.Phase2=>global_freq_itemsets=%s'%(str(global_freq_itemsets)))
        if PRINT_TIME : print ('SON.Phase2=>Finish=>%s'%(str(datetime.now())))         
		return global_freq_itemsets
	}

	def getCount(baskets, local_freq_itemsets) = {
        list = []
        count = 0
        for _basket in baskets: 
            for _itemset in local_freq_itemsets.value:
                count += 1 
                #print('count=%d, str(_itemset[0]).split(SPLITTER)=%s, _basket.split(SPLITTER)=%s,set(str(_itemset[0]).split(SPLITTER)).issubset(_basket.split(SPLITTER))=%r'%(count,str(_itemset[0]).split(SPLITTER), _basket.split(SPLITTER), set(str(_itemset[0]).split(SPLITTER)).issubset(_basket.split(SPLITTER))))
                if set(str(_itemset[0]).split(SPLITTER)).issubset(_basket.split(SPLITTER)) :
                    list.append((_itemset[0], 1))      
        if DEBUG : print('str(list))=%s'%(str(list)))
				return iter(list)
		}


}