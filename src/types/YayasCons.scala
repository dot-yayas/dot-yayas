package dot.yayas.types
import dot.yayas.Environment
import scala.collection.mutable.ListBuffer

// Class for dot-yayas cons
case class YayasCons(val value: (YayasType, YayasType)) extends YayasType {

	// Returns the dot-yayas type of the data (Cons)
	def get_yayas_type(): String = "Cons"

	// Returns a string representation of the dot-yayas cons
	override def show(): String =
		"(" + this.value._1.show() + " . " + this.value._2.show() + ")"

	// Cheks weather the dot-yayas cons contains a given term
	override def contains(term: YayasType): Boolean =
		term == this || this.value._1.contains(term) || this.value._2.contains(term)

	// Applies a substitution to a dot-yayas cons
	override def apply_substitution(substitution: Map[YayasAtom, YayasType]): YayasType =
        YayasCons((
            this.value._1.apply_substitution(substitution),
            this.value._2.apply_substitution(substitution)
        ))

	// Returns a list of dot-yayas terms
	override def to_list(): Option[List[YayasType]] = {
		var list: ListBuffer[YayasType] = ListBuffer()
		var expr: YayasCons = this
		list += expr.value._1
		while(expr.value._2.get_yayas_type() == "Cons") {
			expr = expr.value._2.asInstanceOf[YayasCons]
			list += expr.value._1
		}
		if(expr.value._2.get_yayas_type() != "Atom" || expr.value._2.asInstanceOf[YayasAtom].value != "nil")
			return None
		return Some(list.toList)
	}

	// Evaluates the dot-yayas cons into an environment
	override def eval(env: Environment): YayasType = {
		var result: YayasType = this
		val name: YayasType = this.value._1
		if(name.get_yayas_type() != "Atom")
			return this // TODO: Type error
		// system call
		if(name.value == "syscall") {
			var args: ListBuffer[YayasType] = ListBuffer()
			var cdr: YayasCons = this
			while(cdr.value._2.get_yayas_type() == "Cons") {
				cdr = cdr.value._2.asInstanceOf[YayasCons]
				args += cdr.value._1.eval(env)
			}
			if(cdr.value._2.get_yayas_type() != "Atom" || cdr.value._2.asInstanceOf[YayasAtom].value != "nil")
				return this // TODO: Type error
			val syscall: YayasType = args.head
			val sysargs: List[YayasType] = args.drop(1).toList
			if(syscall.get_yayas_type() != "Atom")
				return this // TODO: Type error
			result = syscall.value match {
				case "add" => sysargs(0).asInstanceOf[YayasIdentity].value.eval(env).to_list() match {
					case None => this // TODO: Type error
					case Some(list) => YayasInt(list.map(x => x.eval(env).asInstanceOf[YayasInt].value).sum)
				}
			}
		// user-defined call
		} else {
			val fn: YayasFunction = name.get_yayas_type() match {
				case "Atom" => {
					val lookup: YayasType = env.lookup(name.asInstanceOf[YayasAtom])
					if(lookup.get_yayas_type() != "Function")
						return this
					lookup.asInstanceOf[YayasFunction]
				}
				case "Function" => name.asInstanceOf[YayasFunction]
				case _ => return this // TODO: Type error
			}
			var argenv: Environment = new Environment(Some(fn.environment))
			var cdr: YayasCons = this
			for(arg <- fn.formal_parameters) {
				if(cdr.value._2.get_yayas_type() != "Cons")
					return this // TODO: Type error
				cdr = cdr.value._2.asInstanceOf[YayasCons]
				argenv.assign(arg, cdr.value._1.eval(env))
			}
			argenv.assign(new YayasAtom("..."), new YayasIdentity(cdr.value._2))
			result = fn.body.eval(argenv)
		}
		if(result.get_yayas_type() == "Function")
			return result.asInstanceOf[YayasFunction].with_environment(env)
		return result
	}

}