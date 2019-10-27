package dot.yayas.test

class Propertie[A](
	// The name of the test
	val name: String,
	// A short description of the condition
	val description: String,
	// Generator of random values for the input
	val generate: () => A,
	// A precondition for the input
	val precondition: Option[A => Boolean],
	// The propertie to be satisfied
	val propertie: A => Boolean)
{

	// Number of succed tests
	var success = 100

	// Ratio of admissible discarded values
	var ratio = 10

	// Runs the test
	def run(): Boolean = {
		var passed = 0
		var discarded = 0
		var max_discarded = this.success * this.ratio
		val precondition: (A => Boolean) = this.precondition match {
			case Some(fn) => fn
			case None => (x => true)
		}
		println("=== " + this.name)
		println("=== " + this.description)
		while(passed < this.success && discarded < max_discarded) {
			var data = generate()
			if(precondition(data)) {
				if(!propertie(data)) {
					println(s"*** Failed! Falsified (after $passed tests):")
					println(data + "\n")
					return false
				}
			} else {
				discarded = discarded+1
			}
			passed += 1
		}
		println(s"+++ OK, passed $passed tests; $discarded discarded.\n")
		return true
	}

}

trait Test {

	// List of properties to test
	val properties: List[Propertie[_]]

	// Runs all the tests
	def run_all(): Boolean =
		this.properties.map(prop => prop.run()).fold(true)(_ && _)

}