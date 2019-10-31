package dot.yayas.types
import dot.yayas.Environment

// Class for dot-yayas identities
case class YayasIdentity(val value: YayasType) extends YayasType {

	// Returns the dot-yayas type of the data (Function)
	def get_yayas_type(): String = "Identity"

    // Returns a string representation of the dot-yayas data
	override def show(): String = "'" + this.value.show()

}