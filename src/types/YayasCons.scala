package dot.yayas.types
import scala.collection.mutable.ListBuffer

// Class for dot-yayas cons
case class YayasCons(val value: (YayasType, YayasType)) extends YayasType {

	// Returns the dot-yayas type of the data (Cons)
	def get_yayas_type(): String = "Cons"

	// Returns a string representation of the dot-yayas cons
	override def show(): String =
		"(" + this.value._1.show() + " . " + this.value._2.show() + ")"

	// Cheks weather the dot-yayas cons contains a given term
	override def contains(term: YayasType): Boolean =
		term == this || this.value._1.contains(term) || this.value._2.contains(term)

	// Applies a substitution to a dot-yayas cons
	override def apply_substitution(substitution: Map[YayasAtom, YayasType]): YayasType =
        YayasCons((
            this.value._1.apply_substitution(substitution),
            this.value._2.apply_substitution(substitution)
        ))

	// Returns a list of yayas terms
	override def to_list(): Option[List[YayasType]] = {
		var list: ListBuffer[YayasType] = ListBuffer()
		var expr: YayasCons = this
		list += expr.value._1
		while(expr.value._2.get_yayas_type() == "Cons") {
			expr = expr.value._2.asInstanceOf[YayasCons]
			list += expr.value._1
		}
		if(expr.value._2.get_yayas_type() != "Atom" || expr.value._2.asInstanceOf[YayasAtom].value != "nil")
			return None
		return Some(list.toList)
	}

}