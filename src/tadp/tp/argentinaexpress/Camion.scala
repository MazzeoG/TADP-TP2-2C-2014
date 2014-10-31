package tadp.tp.argentinaexpress

class Camion (override val volumenDeCarga : Int = 45,
    override val costoPorKm : Int = 100,
    override val velocidad : Int = 60,
    override val serviciosExtra : Set[ServicioExtra])
extends Transporte (volumenDeCarga, costoPorKm, velocidad, serviciosExtra){

  override def puedeCargarRefrigerados() ={
    true
  } //tienen refrigeracion
  
  def costo(){
    (cantidadPeajesEntre(sucursal1, sucursal2)*12)+...;
  }
}