package tadp.tp.argentinaexpress

class Envio (val caracteristicas : Set[Envio],
    val sucursalOrigen :Sucursal,
    val sucursalDestino :Sucursal,
    val volumen :Int,
    val fecha :Tuple3[Int,Int,Int]) // Yo lo manejo como Tuple3[dd,mm,yyyy].
    extends CalculadorDistancia{
  
	def precio():Int{
	  
	}
	
	def costo():Int{
	  
	}
	
	def calcularCostoEnvio (transporte: Transporte) : Int = {
	  var valorRefrigeracion : Int = 0;
	  this match {
      	case envio: Refrigeracion => valorRefrigeracion = 5
      	case _ =>
      	}
	  (transporte.precioPeajes(this)+ valorRefrigeracion)* transporte.multiplicador
    }
  

	
  //val costoBase:Int
//  def costo()={
//    this.costoBase+transporte.costo()
//  }
//  
    def ganancia():Int = {
      this.precio-this.costo
  }
  
}