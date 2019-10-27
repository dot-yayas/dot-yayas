package dot.yayas.types

// Trait for dot-yayas expressions
trait YayasType {

	// Stored data
	val value: Any

	// Returns the dot-yayas type of the data
	def get_yayas_type(): String

	// Returns a string representation of the dot-yayas data
	def show(): String = this.value.toString()

	// Cheks weather the dot-yayas value contains a given term
	def contains(term: YayasType): Boolean = term == this

	// Applies a substitution to a dot-yayas expression
	def apply_substitution(substitution: Map[YayasAtom, YayasType]): YayasType = this

}