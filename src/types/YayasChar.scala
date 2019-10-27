package dot.yayas.types

// Class for dot-yayas characters
case class YayasChar(val value: Char) extends YayasType {

	// Returns the dot-yayas type of the data (Character)
	def get_yayas_type(): String = "Character"

	// Returns a string representation of the dot-yayas character
	override def show(): String = "'" + this.value.toString() + "'"

}