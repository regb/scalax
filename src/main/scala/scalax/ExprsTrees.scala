package scalax

import Main._

object ExprsTrees {

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
  
  implicit object LinearIntegerArithmeticExprsTrees extends MonoidExprsTrees[BigInt]

}
