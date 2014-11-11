package tadp.tp.argentinaexpress

class Camion (override val serviciosExtra : Set[ServicioExtra])
extends Transporte (serviciosExtra){
  
  override val volumenDeCarga : Int = 45
  override val costoPorKm : Int = 100
  override val velocidad : Int = 60
  override val valorPeaje : Int = 12

  override def puedeCargarRefrigerados() ={
    true
  } //tienen refrigeracion
  
  override def esCamion(): Boolean = {
    true
  }
  
  override def multiplicador(envio: Envio):Int= {
   if( (this.volumenDeCarga/5 >= this.volumenEnvios) && !envio.sucursalOrigen.esCasaCentral && !envio.sucursalDestino.esCasaCentral){      
     1+(this.volumenEnvios/this.volumenDeCarga)
   }
   else {
     1
   }
 }
  
  
  override def precioPeajes(envio:Envio):Int={
    (cantidadPeajesEntre(envio.sucursalOrigen,envio.sucursalDestino)*valorPeaje)
  }
}