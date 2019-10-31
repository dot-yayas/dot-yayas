package dot.yayas
import dot.yayas.types._
import scala.collection.mutable.Map

object Main {

	def main(args: Array[String]): Unit = {
		// Yayas terms
		var data: YayasCons = new YayasCons(
			new YayasAtom("visualizalo"),
			new YayasCons(YayasInt(42),
			new YayasCons(YayasFloat(6.73),
			new YayasCons(YayasChar('a'),
				new YayasAtom("nil")))))
		println(data.show())
		// Environments
		var env1: Environment = new Environment()
		var env2: Environment = new Environment(Some(env1))
		var env3: Environment = new Environment(Some(env1), Map(new YayasAtom("x") -> new YayasChar('a')))
		env1.assign(new YayasAtom("x"), new YayasInt(88))
		println(env1.lookup(new YayasAtom("x")).show())
		println(env2.lookup(new YayasAtom("x")).show())
		println(env3.lookup(new YayasAtom("x")).show())
		// Builtins
		var builtins: Environment = new Environment()
		builtins.assign(new YayasAtom("id"),
			new YayasFunction( (List(new YayasAtom("x")), new YayasAtom("x")) ))
		println(builtins.eval(new YayasCons(
			new YayasAtom("id"),
			new YayasCons(new YayasInt(5),
			new YayasAtom("nil")))).show())
	}

}