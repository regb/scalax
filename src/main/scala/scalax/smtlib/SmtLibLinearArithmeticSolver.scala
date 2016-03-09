package scalax
package smtlib

import _root_.smtlib.parser.Commands._
import _root_.smtlib.parser.CommandsResponses._
import _root_.smtlib.parser.Terms._
import _root_.smtlib.theories.{Ints, Core}
import _root_.smtlib.interpreters._

import ExprsTrees.LinearIntegerArithmeticExprsTrees._

object SmtLibLinearArithmeticSolver extends LinearArithmeticSolver {


  def solveForSatisfiability(expr: Expr): Option[Map[Var, Const]] = {
    val interpreter = Z3Interpreter.buildForV3
    val vars: Set[Var] = Set(Var("x"), Var("y"), Var("z"))
    val declareVars: Set[DeclareFun] = vars.map(v => DeclareFun(SSymbol(v.id), Seq(), Ints.IntSort()))

    declareVars.foreach(dv => interpreter.eval(dv))

    interpreter.eval(Assert(toTerm(expr)))
    interpreter.eval(CheckSat()) match {
      case CheckSatStatus(SatStatus) => {
        println("was sat")
        val model = interpreter.eval(GetModel()).asInstanceOf[GetModelResponseSuccess]
        println("model was: " + model)
        Some(fromModel(model))
      }
      case CheckSatStatus(UnsatStatus) => {
        println("was unsat")
        None
      }
      case CheckSatStatus(UnknownStatus) => None
      case res => sys.error("unexpected response from solver: " + res)
    }
  }

      
      

  def toTerm(expr: Expr): Term = expr match {
    case Zero => Ints.NumeralLit(0)
    case Plus(e1, e2) => Ints.Add(toTerm(e1), toTerm(e2))
    case LessThan(e1, e2) => Ints.LessThan(toTerm(e1), toTerm(e2))
    case Equals(e1, e2) => Core.Equals(toTerm(e1), toTerm(e2))
    case Const(n) if n >= 0 => Ints.NumeralLit(n)
    case Const(n) => Ints.Neg(Ints.NumeralLit(-n))
    case Var(x) => QualifiedIdentifier(Identifier(SSymbol(x)))
  }

  def fromTerm(term: Term): Const = term match {
    case Ints.NumeralLit(n) => Const(n)
    case Ints.Neg(Ints.NumeralLit(n)) => Const(-n)
  }

  def fromModel(model: GetModelResponseSuccess): Map[Var, Const] = {
    model.model.map {
      case DefineFun(FunDef(SSymbol(name), Seq(), sort, body)) => (Var(name) -> fromTerm(body))
    }.toMap
  }

}
