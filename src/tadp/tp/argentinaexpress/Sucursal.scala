package tadp.tp.argentinaexpress

class Sucursal (val transporte : Set[Transporte], val volumenTotal : Int, val Pais : String) extends CalculadorDistancia{

  var envios : Set[Envio];
   var volumen:Int;
   
  
  def volumenDisponible():Int={
    (this.volumenTotal) - (this.volumenEnvios);
    }
  
  def volumenEnvios() : Int ={
   
    this.transporte.foreach((t:Transporte) => volumen+=t.volumenEnvios);
    volumen;
  }
  
  def asignarEnvioATransporte(envio: Envio): Transporte = {
    var transporteAsignado : Transporte = null;
    envio match {
      case envio : Refrigeracion => transporteAsignado = transporte.find((t: Transporte) => t.puedeCargar(envio))
    }
    transporteAsignado
  }
  
}