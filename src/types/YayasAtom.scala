package dot.yayas.types
import dot.yayas.Environment

// Class for dot-yayas atoms
case class YayasAtom(val value: String) extends YayasType {

	// Returns the dot-yayas type of the data (Atom)
	def get_yayas_type(): String = "Atom"

	// Applies a substitution to a dot-yayas atom
	override def apply_substitution(substitution: Map[YayasAtom, YayasType]): YayasType =
		substitution.get(this) match {
			case Some(expr) => expr
			case None => this
		}
	
	// Returns a list of yayas terms
	override def to_list(): Option[List[YayasType]] =
		if(this.value == "nil") Some(List()) else None
	
	// Evaluates the dot-yayas atom into an environment
	override def eval(env: Environment): YayasType = {
		val result: YayasType = env.lookup(this)
		if(result.get_yayas_type() == "Function")
			return result.asInstanceOf[YayasFunction].with_environment(env)
		return result
	}

}