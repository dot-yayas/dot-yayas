package dot.yayas.types

// Class for dot-yayas integers
case class YayasInt(val value: Long) extends YayasType {

	// Returns the dot-yayas type of the data (Integer)
	def get_yayas_type(): String = "Integer"

}