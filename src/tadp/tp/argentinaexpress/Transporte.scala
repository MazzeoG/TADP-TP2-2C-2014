package tadp.tp.argentinaexpress

class Transporte (val serviciosExtra : Set[ServicioExtra])
    extends CalculadorDistancia{
  val volumenDeCarga : Int;
  val costoPorKm : Int;
  val velocidad : Int;
  var sucursalDestino: Sucursal;
  val enviosAsignados: Set[Envio];
//  def espacioDisponible():Int={
//    this.volumenDeCarga - this.volumenOcupado
//  }

  def puedeCargarUrgentes() ={
    false
  }
   def puedeCargarFragiles() ={
    false
  }
  def puedeCargarRefrigerados() ={
    false
  }
  
  def sinEnviosAsignados{
    this.enviosAsignados.isEmpty
  }
  
  def volumenEnvios {
    var volumenTotal = this.enviosAsignados.foreach((e:Envio) => e.volumen)
  }
  
}