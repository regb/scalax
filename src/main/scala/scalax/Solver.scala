package scalax

import ExprsTrees._

trait Solver[T] {

  val exprsTrees: ExprsTrees[T]

  def solveForSatisfiability(expr: exprsTrees.Expr): Map[exprsTrees.Var, exprsTrees.Const]

}

trait LinearArithmeticSolver extends Solver[BigInt] {

  val exprsTrees = LinearIntegerArithmeticExprsTrees

}
