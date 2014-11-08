package tadp.tp.argentinaexpress

abstract class Transporte (val serviciosExtra : Set[ServicioExtra])
    extends CalculadorDistancia{
  val volumenDeCarga : Int
  val costoPorKm : Int
  val velocidad : Int
  var sucursalDestino: Sucursal = null
  var enviosAsignados: Set[Envio] = Set()
  val valorPeaje: Int
  
  //Esta repetido! volumenDisponible()
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
  
  def sinEnviosAsignados : Boolean = {
    this.enviosAsignados.isEmpty
  }
  
  def volumenEnvios() :Int = {
//     var volumenOcupado:Int = 0;
//     this.enviosAsignados.foreach((e:Envio) =>volumenOcupado+= e.volumen)
//     volumenOcupado
     this.enviosAsignados.map((e:Envio) => e.volumen).sum
  }
  
  def volumenDisponible() :Int = {
    this.volumenDeCarga - this.volumenEnvios
  }
  
  def puedeCargar(envio:Envio) : Boolean ={
    var cargable : Boolean = coincideDestino(envio) && entraEnTransporte(envio) && entraEnAvion(envio);
    envio match {
  case envio :Fragil => cargable = cargable && puedeCargarFragiles
  case envio :Urgente => cargable = cargable && puedeCargarUrgentes
  case envio :Refrigeracion => cargable = cargable && puedeCargarRefrigerados
  case _ =>
    }
    cargable
  }
  
  def coincideDestino(envio:Envio) : Boolean = {
    if(this.sinEnviosAsignados)
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
  
  def entraEnAvion(envio : Envio) : Boolean ={
    if (this.esAvion){
      if (this.distanciaAereaEntre(envio.sucursalOrigen , envio.sucursalDestino ) > 1000)
        true
      else
        false
    }else
      true
  }
  
  def esAvion() : Boolean = {
    this match {
      case transporte: Avion => true
      case _ => false
    }
  }
  
  def calcularCostoViaje() : Int = {
    var costoFinal : Int = 0
    if (this.sinEnviosAsignados) 
      0
    else {
        this.enviosAsignados.foreach((e:Envio) => costoFinal += e.calcularCostoEnvio(this))
      }
    costoFinal
  }
  
  def costoTransporte(envio :Envio) : Int = {
    this match {
      case transporte : Avion => this.costoPorKm * this.distanciaAereaEntre(envio.sucursalOrigen , envio.sucursalDestino ).toInt
      case transporte : Camion => this.costoPorKm * this.distanciaTerrestreEntre(envio.sucursalOrigen , envio.sucursalDestino ).toInt
      case transporte : Furgoneta => this.costoPorKm * this.distanciaTerrestreEntre(envio.sucursalOrigen , envio.sucursalDestino ).toInt
    }
  }
  

  
  def precioPeajes(envio:Envio):Int={
    (cantidadPeajesEntre(envio.sucursalOrigen,envio.sucursalDestino) * this.valorPeaje)
  }
  
  def multiplicador():Int= {
	if( (this.volumenDeCarga/5 >= this.volumenEnvios)){      //falta poner si suc destino u origen es casa central
     1+(this.volumenEnvios/this.volumenDeCarga)
   }
   else {
     1
    }
  }
  
  def agregarEnvio(envio : Envio): Transporte = {
    this.enviosAsignados = this.enviosAsignados ++ Set(envio)
    if (this.enviosAsignados.size == 1)
    	this.sucursalDestino = envio.sucursalDestino
    this
  }
  
  def tieneSeguimientoGPS(): Boolean = {
    !this.serviciosExtra.find((s: ServicioExtra) => s.soyGPS).isEmpty
  }
  
  def tieneSeguimientoVideo(): Boolean = {
    !this.serviciosExtra.find((s: ServicioExtra) => s.soyVideo).isEmpty
  }
  
  def puedeLlevarAnimales() : Boolean = {
    !this.serviciosExtra.find((s: ServicioExtra) => s.soyInfraestructuraAnimales).isEmpty
  }
  
  def puedeLlevarSustancias() : Boolean = {
    !this.serviciosExtra.find((s: ServicioExtra) => s.soyInfraestructuraSustancias).isEmpty
  }
  
}