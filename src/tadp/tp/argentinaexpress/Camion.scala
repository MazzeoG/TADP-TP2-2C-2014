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
  
 /* def costo(envio:Envio){
    envio match {
      case envio: Refrigeracion => (precioPeajes(envio)+ 5)*multiplicador
      case _ => (precioPeajes(envio))*multiplicador
    }
  }
  * 
  */
  
  override def multiplicador():Int= {
   if( (this.volumenDeCarga/5 >= this.volumenEnvios)){      //falta poner si suc destino u origen es casa central
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