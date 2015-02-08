package scalax

import scala.language.higherKinds

object GenericOps {

  def sum[F[_], T](fa: F[T])(implicit F: Foldable[F], T: Monoid[T]): T = F.foldMap(fa)(x => x)

}
