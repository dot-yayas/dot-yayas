package dot.yayas
import dot.yayas.types._
import scala.collection.mutable.Map
import scala.collection.mutable.ListBuffer

class Environment(
	val parent: Option[Environment] = None,
	val assignments: Map[YayasAtom, YayasType] = Map()) {

	// Returns the value for the atom in the current environment.
	// If the atom is not in the current environment, it will be
	// searched in the parent environment, if exists. Otherwise,
	// returns the atom.
	def lookup(atom: YayasAtom): YayasType =
		this.assignments.get(atom) match {
			case Some(expr) => expr
			case None => this.parent match {
				case None => atom
				case Some(env) => env.lookup(atom)
			}
		}
	
	// Adds a new assignment to the current environment.
	def assign(atom: YayasAtom, expr: YayasType): Unit =
		this.assignments += (atom -> expr)
	
	// Returns the result of evaluating an expression in
	// the current environment.
	def eval(expr: YayasType): YayasType =
		expr.get_yayas_type() match {
			case "Atom" => this.lookup(expr.asInstanceOf[YayasAtom])
			case "Cons" => {
				val fn: YayasFunction = this.lookup(expr.asInstanceOf[YayasCons].value._1.asInstanceOf[YayasAtom]).asInstanceOf[YayasFunction]
				if(fn.get_yayas_type() != "Function")
					return expr
				var env: Environment = new Environment(Some(this))
				var cdr: YayasCons = expr.asInstanceOf[YayasCons].value._2.asInstanceOf[YayasCons]
				for(arg <- fn.formal_parameters) {
					env.assign(arg, this.eval(cdr.value._1))
					if(cdr.value._2.get_yayas_type() == "Cons")
						cdr = cdr.asInstanceOf[YayasCons].value._2.asInstanceOf[YayasCons]
				}
				env.eval(fn)
			}
			case "Function" => this.eval(expr.asInstanceOf[YayasFunction].body)
			case _ => expr
		}

}