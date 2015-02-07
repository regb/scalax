package scalax

object Main {

  trait SemiGroup[T] {
    def append(t1: T, t2: T): T
  }

  trait Monoid[T] extends SemiGroup[T] {
    def zero: T
  }

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

  def sum[T](l: List[T])(implicit T: Monoid[T]): T = l.foldLeft(T.zero)(T.append)

  implicit def listMonoid[T](implicit T: Monoid[T]): Monoid[List[T]] = new Monoid[List[T]] {
    override def append(l1: List[T], l2: List[T]): List[T] = l1 ++ l2
    override def zero: List[T] = Nil
  }

  trait ExprsTrees[T] {
    sealed abstract class Expr
    case class Const(t: T) extends Expr
    case class Var(id: String) extends Expr
  }

  trait SemiGroupExprsTrees[T] extends ExprsTrees[T] {
    case class Plus(e1: Expr, e2: Expr) extends Expr
  }

  trait MonoidExprsTrees[T] extends SemiGroupExprsTrees[T] {
    case object Zero extends Expr
  }

  implicit def semiGroupExprsTree[T](implicit trees: SemiGroupExprsTrees[T], T: SemiGroup[T]): SemiGroup[trees.Expr] = {
    new SemiGroup[trees.Expr] {
      override def append(t1: trees.Expr, t2: trees.Expr) = trees.Plus(t1, t2)
    }
  }

  implicit def monoidExprsTree[T](implicit trees: MonoidExprsTrees[T], T: Monoid[T]): Monoid[trees.Expr] = {
    new Monoid[trees.Expr] {
      override def append(t1: trees.Expr, t2: trees.Expr) = trees.Plus(t1, t2)
      override def zero = trees.Zero
    }
  }

  implicit object BigIntExprs extends MonoidExprsTrees[BigInt]

}
