package dot.yayas.types

// Class for dot-yayas atoms
case class YayasAtom(val value: String) extends YayasType {

	// Returns the dot-yayas type of the data (Atom)
	def get_yayas_type(): String = "Atom"

	// Applies a substitution to a dot-yayas atom
	override def apply(substitution: Map[YayasAtom, YayasType]): YayasType =
		substitution.get(this) match {
			case Some(expr) => expr
			case None => this
		}

}