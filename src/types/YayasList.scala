package dot.yayas.types

// Class for dot-yayas lists
case class YayasList(val value: List[YayasType]) extends YayasType {

	// Number of elements in the list
	val length: Int = value.length

	// Returns the dot-yayas type of the data (List)
	def get_yayas_type(): String = "List"

	// Returns a string representation of the dot-yayas list
	override def show(): String =
		"(" + this.value.map(x => x.show()).mkString(" ") + ")"

	// Applies a substitution to a dot-yayas list
	override def apply_substitution(substitution: Map[YayasAtom, YayasType]): YayasType =
		YayasList(this.value.map(x => x.apply_substitution(substitution)))

}