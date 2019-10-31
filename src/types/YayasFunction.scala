package dot.yayas.types

// Class for dot-yayas functions
case class YayasFunction(val value: (List[YayasAtom], YayasType)) extends YayasType {

    // Returns the formal parameters of the yayas function
    val formal_parameters: List[YayasAtom] = this.value._1

    // Returns the body of the yayas function
    val body: YayasType = this.value._2

	// Returns the dot-yayas type of the data (Function)
	def get_yayas_type(): String = "Function"

}