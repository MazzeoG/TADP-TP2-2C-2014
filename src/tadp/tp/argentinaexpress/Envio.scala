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
  //val costoBase:Int
//  def costo()={
//    this.costoBase+transporte.costo()
//  }
//  
  def ganancia(){
    this.precio-this.costo;
  }
}