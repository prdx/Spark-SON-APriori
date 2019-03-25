package project.algorithm

class Apriori {
  var sp: Float = 0f;

  def this(sp: Float, basket: Iterator[(Int, List[Int])]) {
    this()
    this.sp = sp
  }

  def execute(basket: Iterator[(Int, List[Int])]): Unit = {
    val basket = List(basket)
  }
}
