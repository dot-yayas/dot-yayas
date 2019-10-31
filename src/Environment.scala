package dot.yayas
import dot.yayas.types._
import scala.collection.mutable.Map
import scala.collection.mutable.ListBuffer

class Environment(
	val parent: Option[Environment] = None,
	val assignments: Map[YayasAtom, YayasType] = Map()) {

	// Returns the value for the atom in the current environment.
	// If the atom is not in the current environment, it will be
	// searched in the parent environment, if exists. Otherwise,
	// returns the atom.
	def lookup(atom: YayasAtom): YayasType =
		this.assignments.get(atom) match {
			case Some(expr) => expr
			case None => this.parent match {
				case None => atom
				case Some(env) => env.lookup(atom)
			}
		}
	
	// Adds a new assignment to the current environment.
	def assign(atom: YayasAtom, expr: YayasType): Unit =
		this.assignments += (atom -> expr)

}