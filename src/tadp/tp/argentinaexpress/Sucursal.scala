package tadp.tp.argentinaexpress

class Sucursal (val transporte : Set[Transporte], val volumenTotal : Int) extends CalculadorDistancia{

  var envios : Set[Envio];
  
  def volumenDisponible(){
    (this.volumenTotal) - (this.volumenEnvios);
  }
  
  def volumenEnvios() : Int {
    this.transporte.foreach((T:Transporte) => T.volumenEnvios);
    volumen;
  }
}