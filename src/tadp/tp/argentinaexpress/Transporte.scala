package tadp.tp.argentinaexpress

class Transporte (val serviciosExtra : Set[ServicioExtra])
    extends CalculadorDistancia{
  val volumenDeCarga : Int;
  val costoPorKm : Int;
  val velocidad : Int;
  var sucursalDestino: Sucursal;
  val enviosAsignados: Set[Envio];
  var volumenOcupado:Int;
  
  def espacioDisponible():Int={
    this.volumenDeCarga - this.volumenEnvios
  }

  def puedeCargarUrgentes() ={
    false
  }
   def puedeCargarFragiles() ={
    false
  }
  def puedeCargarRefrigerados() ={
    false
  }
  
  def sinEnviosAsignados{
    this.enviosAsignados.isEmpty
  }
  
  def volumenEnvios() :Int = {
     this.enviosAsignados.foreach((e:Envio) =>volumenOcupado+= e.volumen)
     volumenOcupado
  }
  
  def volumenDisponible() :Int = {
    this.volumenDeCarga - this.volumenEnvios
  }
  
  def puedeCargar(envio:Envio) : Boolean ={
    var cargable : Boolean = coincideDestino(envio) && entraEnDestino(envio) && entraEnTransporte(envio);
    envio match {
  case envio :Fragil => cargable = cargable && puedeCargarFragiles
  case envio :Urgente => cargable = cargable && puedeCargarUrgentes
  case envio :Refrigeracion => cargable = cargable && puedeCargarRefrigerados
  case _ =>
    }
    cargable
  }
  

  def coincideDestino(envio:Envio) : Boolean = {
    if(this.enviosAsignados.isEmpty)
      true
    else {
      this.enviosAsignados.forall((e:Envio) => e.sucursalDestino==envio.sucursalDestino);
    }
  }

  def entraEnDestino(envio:Envio): Boolean ={
    envio.sucursalDestino.volumenDisponible >= envio.volumen 
  }
  
  def entraEnTransporte(envio:Envio) : Boolean ={
    this.volumenDisponible >= envio.volumen
  }
  
}