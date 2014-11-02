package tadp.tp.argentinaexpress

class Camion (override val serviciosExtra : Set[ServicioExtra])
extends Transporte (serviciosExtra){
  
  val volumenDeCarga : Int = 45;
  val costoPorKm : Int = 100;
  val velocidad : Int = 60;
  val valorPeaje : Int = 12;

  override def puedeCargarRefrigerados() ={
    true
  } //tienen refrigeracion
  
  def costo(envio:Envio){
    envio match {
      case envio: Fragil => (precioPeajes(envio)+ 5)*multiplicador
      case _ => (precioPeajes(envio))*multiplicador
    }
  }
  
  def multiplicador():Int= {
   if( (this.volumenDeCarga/5 >= this.volumenOcupado)){      //falta poner si suc destino u origen es casa central
     1+(this.volumenOcupado/this.volumenDeCarga)
   }
   else {
     1
   }
 }
  
  
  def precioPeajes(envio:Envio):Int={
    (cantidadPeajesEntre(envio.sucursalOrigen,envio.sucursalDestino)*12)
  }
}