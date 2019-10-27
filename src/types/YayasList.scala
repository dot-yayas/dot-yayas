package dot.yayas.types

// Class for dot-yayas lists
case class YayasList(val value: List[YayasType]) extends YayasType {

	// Number of elements in the list
	val length: Int = value.length

	// Returns the dot-yayas type of the data (List)
	def get_yayas_type(): String = "List"

	// Returns a string representation of the dot-yayas list
	override def to_string(): String =
		"(" + this.value.map(x => x.to_string()).mkString(" ") + ")"

	// Applies a substitution to a dot-yayas list
	override def apply(substitution: Map[YayasAtom, YayasType]): YayasType =
		YayasList(this.value.map(x => x.apply(substitution)))

}