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
				val name = expr.asInstanceOf[YayasCons].value._1
				if(name.get_yayas_type() != "Atom")
					return expr // Type error
				if(name.value == "syscall") {
					var args: ListBuffer[YayasType] = ListBuffer()
					var cdr: YayasCons = expr.asInstanceOf[YayasCons]
					while(cdr.value._2.get_yayas_type() == "Cons") {
						cdr = cdr.value._2.asInstanceOf[YayasCons]
						args += this.eval(cdr.value._1)
					}
					if(cdr.value._2.get_yayas_type() != "Atom" || cdr.value._2.asInstanceOf[YayasAtom].value != "nil")
						return expr // Type error
					val syscall: YayasType = args.head
					val sysargs: List[YayasType] = args.drop(1).toList
					if(syscall.get_yayas_type() != "Atom")
						return expr // Type error
					return syscall.value match {
						case "add" => sysargs(0).to_list() match {
							case None => expr // Type error
							case Some(list) => YayasInt(list.map(x => this.eval(x).asInstanceOf[YayasInt].value).sum)
						}
					}
				} else {
					val lookup: YayasType = this.lookup(name.asInstanceOf[YayasAtom])
					if(lookup.get_yayas_type() != "Function")
						return expr // Type error
					val fn: YayasFunction = lookup.asInstanceOf[YayasFunction]
					var env: Environment = new Environment(Some(this))
					var cdr: YayasCons = expr.asInstanceOf[YayasCons]
					for(arg <- fn.formal_parameters) {
						if(cdr.value._2.get_yayas_type() != "Cons")
							return expr // Type error
						cdr = cdr.value._2.asInstanceOf[YayasCons]
						env.assign(arg, this.eval(cdr.value._1))
					}
					env.assign(new YayasAtom("..."), cdr.value._2)
					/*if(cdr.value._2.get_yayas_type() != "Atom" || cdr.value._2.value != "nil")
						return expr // Type error*/
					return env.eval(fn)	
				}
			}
			case "Identity" => expr.asInstanceOf[YayasIdentity].value
			case "Function" => this.eval(expr.asInstanceOf[YayasFunction].body)
			case _ => expr
		}

}