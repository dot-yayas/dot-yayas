package dot.yayas.types

// Trait for dot-yayas expressions
trait YayasType {

	// Stored data
	val value: Any

	// Returns the dot-yayas type of the data
	def get_yayas_type(): String

	// Returns a string representation of the dot-yayas data
	def to_string(): String = this.value.toString()

	// Applies a substitution to a dot-yayas expression
	def apply(substitution: Map[YayasAtom, YayasType]): YayasType = this

}