package dot.yayas
import dot.yayas.types._

object Main {

	def main(args: Array[String]): Unit = {
		var data: YayasCons = YayasCons(
			YayasAtom("visualizalo"),
			YayasCons(YayasInt(42),
			YayasCons(YayasFloat(6.73),
			YayasCons(YayasChar('a'),
				YayasAtom("nil")))))
		println(data.show())
	}

}