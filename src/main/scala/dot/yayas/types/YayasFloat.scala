package dot.yayas.types

// Class for dot-yayas floats
case class YayasFloat(val value: Double) extends YayasType {

	// Returns the dot-yayas type of the data (Float)
	def get_yayas_type(): String = "Float"

}