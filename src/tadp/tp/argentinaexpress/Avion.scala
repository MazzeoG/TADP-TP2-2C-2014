package tadp.tp.argentinaexpress

class Avion (override val serviciosExtra : Set[ServicioExtra])
extends Transporte (serviciosExtra){
  
  override val volumenDeCarga : Int = 200;
  override val costoPorKm : Int = 500;
  override val velocidad : Int = 500;
  override val valorPeaje: Int = 0;
  
  override def entraEnAvion(envio : Envio) : Boolean ={
    this.distanciaAereaEntre(envio.sucursalOrigen , envio.sucursalDestino ) > 1000
  }
  
  override def impuestoAvion() : Double = {
    0.1
  }

}