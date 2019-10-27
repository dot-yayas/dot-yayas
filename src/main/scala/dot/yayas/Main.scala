package dot.yayas
import dot.yayas.types._

object Main {

	def main(args: Array[String]): Unit = {
		var data: YayasList = YayasList(List(
			YayasAtom("visualizalo"),
			YayasInt(42),
			YayasFloat(6.73),
			YayasChar('a'),
			YayasList(List())
		))
		println(data.to_string())
	}

}