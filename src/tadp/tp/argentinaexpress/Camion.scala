package tadp.tp.argentinaexpress

class Camion (override val serviciosExtra : Set[ServicioExtra])
extends Transporte (serviciosExtra){
  
  val volumenDeCarga : Int = 45;
  val costoPorKm : Int = 100;
  val velocidad : Int = 60;

  override def puedeCargarRefrigerados() ={
    true
  } //tienen refrigeracion
  
  def costo(){
    //(cantidadPeajesEntre(sucursal1, sucursal2)*12)+...;
  }
}