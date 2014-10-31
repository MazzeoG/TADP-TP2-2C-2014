package tadp.tp.argentinaexpress

class Envio (val caracteristicas : Set[CaractEnvio], val sucursalOrigen: Sucursal, val sucursalDestino :Sucursal) extends CalculadorDistancia{
	val precio:Int;
	val costo: Int;
  //val costoBase:Int
//  def costo()={
//    this.costoBase+transporte.costo()
//  }
//  
  def ganancia(){
    this.precio-this.costo;
  }
}