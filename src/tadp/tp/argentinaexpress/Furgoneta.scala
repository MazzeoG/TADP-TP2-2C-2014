package tadp.tp.argentinaexpress

class Furgoneta (override val serviciosExtra : Set[ServicioExtra])
extends Transporte (serviciosExtra) {
  val volumenDeCarga : Int = 9;
  val costoPorKm : Int = 40;
  val velocidad : Int = 80;
  val valorPeaje : Int = 6;

  def costo(envio:Envio){
    var costoFinal = 
    envio match {
      case envio: Fragil => (precioPeajes(envio)+ 5)*multiplicador
      case _ => (precioPeajes(envio))*multiplicador
    }
   }
 def multiplicador():Int= {
   if( (this.volumenDeCarga/5 >= this.volumenEnvios)){      //falta poner si lleva 3 urgentes
     2
   }
   else {
     1
   }
 }
  def precioPeajes(envio:Envio):Int={
    (cantidadPeajesEntre(envio.sucursalOrigen,envio.sucursalDestino)*6)
  }
  
  
}