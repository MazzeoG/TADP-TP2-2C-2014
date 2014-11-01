package tadp.tp.argentinaexpress

class Sucursal (val transporte : Set[Transporte], val volumenTotal : Int) extends CalculadorDistancia{

  var envios : Set[Envio];
   var volumen:Int;
  
  def volumenDisponible(){
    (this.volumenTotal) - (this.volumenEnvios);
  }
  
  def volumenEnvios() : Int ={
   
    this.transporte.foreach((t:Transporte) => volumen+=t.volumenEnvios);
    volumen;
  }
}