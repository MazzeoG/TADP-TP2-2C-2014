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
  
  def puedeCargar(envio:Envio) {
    envio match {
  case envio :Fragil => puedeCargarFragiles
  case envio :Urgente => puedeCargarUrgentes
  case envio :Refrigeracion => puedeCargarRefrigerados
  case _ => 
    }
       
    def coincideDestino(envio:Envio){
      if(this.enviosAsignados.isEmpty){true} 
      else {
        this.enviosAsignados.forall((e:Envio) => e.sucursalDestino==envio.sucursalDestino)
      } 
    }
    
    def entraEnDestino(envio:Envio){
      envio.sucursalDestino.volumenDisponible >= envio.volumen 
    }
  }
}