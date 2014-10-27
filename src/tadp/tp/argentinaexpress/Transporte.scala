package tadp.tp.argentinaexpress

class Transporte (val volumenDeCarga : Int, val costoPorKm : Int, val velocidad : Int, var serviciosExtra : Set[ServicioExtra]){

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