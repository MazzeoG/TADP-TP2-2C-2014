package tadp.tp.argentinaexpress

class Furgoneta (override val serviciosExtra : Set[ServicioExtra])
extends Transporte (serviciosExtra) {
  val volumenDeCarga : Int = 9;
  val costoPorKm : Int = 40;
  val velocidad : Int = 80;

}