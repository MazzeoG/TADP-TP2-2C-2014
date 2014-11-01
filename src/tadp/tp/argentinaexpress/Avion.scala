package tadp.tp.argentinaexpress

class Avion (override val serviciosExtra : Set[ServicioExtra])
extends Transporte (serviciosExtra){
  
  val volumenDeCarga : Int = 200;
  val costoPorKm : Int = 500;
  val velocidad : Int = 500;

}