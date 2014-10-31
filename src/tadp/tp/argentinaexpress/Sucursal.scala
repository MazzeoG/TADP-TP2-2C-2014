package tadp.tp.argentinaexpress

class Sucursal (var trasporte : Set[Transporte], val volumenTotal : Int) extends CalculadorDistancia{

  var envios : Set[Envio];
  def volumenDisponible(){
    (this.volumenTotal) - (this.pesoEnvios);
  }
  def pesoEnvios():Int{
    //sumar envios
  }
}