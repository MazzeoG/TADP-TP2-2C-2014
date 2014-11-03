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
  
  def asignarEnvioATransporte(envio: Envio): Option[Transporte] = {
    var transporteAsignado : Option[Transporte] = null;

    transporteAsignado = transporte.find((t: Transporte) => t.puedeCargar(envio))
    
    transporteAsignado.foreach(_.agregarEnvio(envio)) // No estoy seguro que esto ande
    
    transporteAsignado
  }
  
}