package tadp.tp.argentinaexpress

class Avion (override val volumenDeCarga : Int = 200,
    override val costoPorKm : Int = 500,
    override val velocidad : Int = 500,
    override val serviciosExtra : Set[ServicioExtra])
extends Transporte (volumenDeCarga, costoPorKm, velocidad, serviciosExtra){

}