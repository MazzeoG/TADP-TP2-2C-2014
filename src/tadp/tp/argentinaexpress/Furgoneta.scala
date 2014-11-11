package tadp.tp.argentinaexpress

class Furgoneta (override val serviciosExtra : Set[ServicioExtra])
extends Transporte (serviciosExtra) {
  override val volumenDeCarga : Int = 9
  override val costoPorKm : Int = 40
  override val velocidad : Int = 80
  override val valorPeaje : Int = 6


 override def multiplicador(envio: Envio):Int= {
   if( (this.volumenDeCarga/5 >= this.volumenEnvios) && (this.tiene3oMasPaquetesUrgentes)){      //falta poner si lleva 3 urgentes
     2
   }
   else {
     1
   }
 }
  
 override def precioPeajes(envio:Envio):Int={
    (cantidadPeajesEntre(envio.sucursalOrigen,envio.sucursalDestino)*6)
 }
 
 def tiene3oMasPaquetesUrgentes(): Boolean = {
   this.enviosAsignados.count((e: Envio) => e.esUrgente()) >= 3
 }
  
}