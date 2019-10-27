package dot.yayas.test
import dot.yayas.types._
import scala.collection.mutable.ListBuffer

object YayasTypeRandom {

	// Random generator
	val rand = scala.util.Random

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
	def atom(): YayasAtom =
		YayasAtom(rand.nextString(10))
	
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

	val prop_substitution_id = new Propertie[YayasType](
		"prop_substitution_id",
		"A term should be equal to itself after applying the identity substitution",
		YayasTypeRandom.term,
		None,
		x => x == x.apply_substitution(Map[YayasAtom, YayasType]()))
	
	val properties = List(prop_substitution_id)

}