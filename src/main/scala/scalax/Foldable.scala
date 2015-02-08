package scalax

import scala.language.higherKinds

trait Foldable[F[_]] {

  def foldMap[A, B](fa: F[A])(f: (A) => B)(implicit B: Monoid[B]): B
  
  def foldRight[A, B](fa: F[A], z: B)(f: (A, B) => B): B

}

object Foldable {

  implicit object ListFoldable extends Foldable[List] {
    override def foldMap[A, B](fa: List[A])(f: (A) => B)(implicit B: Monoid[B]): B =
      fa.foldLeft(B.zero)((acc, el) => B.append(acc, f(el)))

    override def foldRight[A, B](fa: List[A], z: B)(f: (A, B) => B): B = fa.foldRight(z)(f)
  }

}
