package tadp.tp.argentinaexpress

class Transporte (val volumenDeCarga : Int, val costoPorKm : Int, val velocidad : Int, val serviciosExtra : Set[ServicioExtra])extends CalculadorDistancia{
var sucursalDestino: Sucursal;
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
  
}