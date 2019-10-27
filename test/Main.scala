package dot.yayas.test

object Main {

	val tests: List[Test] = List(TestTypes)

	def main(args: Array[String]): Unit = {
		println("Running tests...\n")
		val result = this.tests.map(test => test.run_all()).fold(true)(_ && _)
		if(result) println("True.") else println("False.")
	}

}