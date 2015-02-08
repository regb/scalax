package scalax


trait SemiGroup[T] {
  def append(t1: T, t2: T): T
}

trait Monoid[T] extends SemiGroup[T] {
  def zero: T
}

object Monoid {
  implicit object BigIntMonoid extends Monoid[BigInt] {
    override def append(t1: BigInt, t2: BigInt): BigInt = t1 + t2
    override def zero: BigInt = BigInt(0)
  }
  
  implicit object IntMonoid extends Monoid[Int] {
    override def append(t1: Int, t2: Int): Int = t1 + t2
    override def zero: Int = 0
  }
  
  implicit object StringMonoid extends Monoid[String] {
    override def append(t1: String, t2: String): String = t1 + t2
    override def zero: String = ""
  }

  implicit def listMonoid[T]: Monoid[List[T]] = new Monoid[List[T]] {
    override def append(l1: List[T], l2: List[T]): List[T] = l1 ++ l2
    override def zero: List[T] = Nil
  }

}
