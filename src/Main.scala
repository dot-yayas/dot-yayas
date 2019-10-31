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
		// Calls
		var env: Environment = new Environment()
		env.assign(new YayasAtom("x"), new YayasInt(88))
		env.assign(new YayasAtom("id"), new YayasFunction((
			List(new YayasAtom("x")),
			new YayasAtom("x"))
		, env))
		env.assign(new YayasAtom("+"), new YayasFunction((
			List(),
			new YayasCons(
				new YayasAtom("syscall"),
				new YayasCons(
					new YayasAtom("add"),
					new YayasCons(
						new YayasAtom("..."),
						new YayasAtom("nil")
					)
				)
			)
		), env))
		data = new YayasCons(
			new YayasAtom("id"),
			new YayasCons(new YayasInt(5),
			new YayasAtom("nil")))
		println(data.show() + " => " + data.eval(env).show())
		
		data = new YayasCons(
			new YayasAtom("syscall"),
			new YayasCons(
				new YayasAtom("add"),
				new YayasCons(
					new YayasIdentity(
						new YayasCons(new YayasInt(1),
						new YayasCons(new YayasAtom("x"),
						new YayasCons(new YayasInt(3),
						new YayasAtom("nil"))))),
					new YayasAtom("nil"))))
		println(data.show() + " => " + data.eval(env).show())

		data = new YayasCons(new YayasAtom("+"), new YayasAtom("nil"))
		println(data.show() + " => " + data.eval(env).show())
		
		data = new YayasCons(
			new YayasAtom("+"),
			new YayasCons(
				new YayasInt(1),
				new YayasCons(
					new YayasInt(2),
					new YayasAtom("nil"))))
		println(data.show() + " => " + data.eval(env).show())
	}

}