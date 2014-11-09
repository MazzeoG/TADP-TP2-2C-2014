package tadp.tp.argentinaexpress

class Sucursal (var transporte : Set[Transporte], val volumenTotal : Int, val pais : String) extends CalculadorDistancia{

  var envios : Set[Envio] = Set();
  var volumen:Int = 0;
   
  
  def volumenDisponible():Int={
    (this.volumenTotal) - (this.volumenEnviosEnSucursal);
    }
  
  def agregarTransporte(tran : Transporte) ={
    this.transporte = this.transporte ++ Set(tran)
  }
  
  def volumenEnviosEnSucursal() : Int ={
	this.transporte.map((t:Transporte) => t.volumenEnvios).sum
    //this.transporte.foreach((t:Transporte) => volumen+=t.volumenEnvios);
    //volumen;
  }

  def volumenEnviosASucursal(destino : Sucursal) : Int ={
	this.transporte.filter((t: Transporte)=> t.sucursalDestino == destino).map((t:Transporte) => t.volumenEnvios).sum
    //this.transporte.foreach((t:Transporte) => volumen+=t.volumenEnvios);
    //volumen;
  } 
  
  def asignarEnvioATransporte(envio: Envio): Boolean = {
    var transporteAsignado : Option[Transporte] = None;

    transporteAsignado = transporte.find((t: Transporte) => t.puedeCargar(envio))
    
    if (!transporteAsignado.isEmpty && entraPedido(envio)) 
    	transporteAsignado.foreach(_.agregarEnvio(envio))
    
    !transporteAsignado.isEmpty
  }
  
  def entraPedido(envio:Envio): Boolean = { 
    envio.sucursalDestino.volumenDisponible >= (envio.volumen + this.volumenEnviosASucursal(envio.sucursalDestino))
  }
}