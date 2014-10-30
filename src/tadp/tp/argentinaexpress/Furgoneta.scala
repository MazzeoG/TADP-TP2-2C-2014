package tadp.tp.argentinaexpress

class Furgoneta (override val volumenDeCarga : Int = 9,
    override val costoPorKm : Int = 40,
    override val velocidad : Int = 80,
    override val serviciosExtra : Set[ServicioExtra])
extends Transporte (volumenDeCarga, costoPorKm, velocidad, serviciosExtra) {

}