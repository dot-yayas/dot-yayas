package dot.yayas.test
import dot.yayas.types._
import scala.collection.mutable.ListBuffer

object YayasTypeRandom {

	// Random generator
	val rand = scala.util.Random

	// Last atom generated
	var last_random_atom: Option[YayasAtom] = None

	// Generates a random dot-yayas integer
	def int(): YayasInt =
		YayasInt(rand.nextInt(100))
	
	// Generates a random dot-yayas float
	def float(): YayasFloat =
		YayasFloat(rand.nextDouble())

	// Generates a random dot-yayas character
	def char(): YayasChar =
		YayasChar(rand.nextPrintableChar())

	// Generates a random dot-yayas atom
	def atom(): YayasAtom = {
		var atom = YayasAtom(rand.nextString(10))
		last_random_atom = Some(atom)
		return atom
	}
	
	// Generates a random dot-yayas list
	def list(): YayasList = {
		val len = rand.nextInt(10)
		var list = new ListBuffer[YayasType]()
		for(i <- 0 to len)
			list += term()
		YayasList(list.toList)
	}
	
	// Generates a random dot-yayas term
	def term(): YayasType =
		rand.nextInt(4) match {
			case 0 => int()
			case 1 => float()
			case 2 => char()
			case 3 => atom()
			case 4 => list()
		}

}

object TestTypes extends Test {

	val prop_substitution_id = new Property[YayasType](
		"prop_substitution_id",
		"A term should be equal to itself after applying the identity substitution.",
		YayasTypeRandom.term,
		x => Some(x == x.apply_substitution(Map[YayasAtom, YayasType]()))
	)
	
	val prop_substitution_domain = new Property[YayasType](
		"prop_substitution_domain",
		"A term should not contain an atom after applying a substitution whose domain contains that atom.",
		YayasTypeRandom.term,
		x => {
			var last_atom = YayasTypeRandom.last_random_atom
			var atom = last_atom match {
				case None => YayasTypeRandom.atom()
				case Some(atom) => atom
			}
			var value = YayasTypeRandom.term()
			var subs = Map[YayasAtom, YayasType]((atom, value))
			atom != value && x.contains(atom) match {
				case true => Some(!x.apply_substitution(subs).contains(atom))
				case false => None
			}
		}
	)

	val prop_contains_reflexive = new Property[YayasType](
		"prop_contains_reflexive",
		"A term should contain itself.",
		YayasTypeRandom.term,
		x => Some(x.contains(x))
	)
	
	val properties = List(
		prop_substitution_id,
		prop_substitution_domain,
		prop_contains_reflexive)

}