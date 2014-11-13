package tadp.tp.argentinaexpress

import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

abstract class Transporte (val serviciosExtra : Set[ServicioExtra], var sucursalOrigen: Sucursal)
    extends CalculadorDistancia{
  val volumenDeCarga : Int
  val costoPorKm : Int
  val velocidad : Int
  var sucursalDestino: Sucursal = null
  var enviosAsignados: Set[Envio] = Set()
  var fechaEnvio :Date = null
  val valorPeaje: Int
  val volOcupadoMulti: Double = 0.2 
  // Inicializacion de los transportes
  if (sucursalOrigen != null)
	  sucursalOrigen.agregarTransporte(this)
  
  
  
  //Esta repetido! volumenDisponible()
  def espacioDisponible():Int={
    this.volumenDeCarga - this.volumenEnvios
  }

  def puedeCargarUrgentes() ={
    true
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
  
  //Funcion utilizada para validar que un transporte pueda cargar un envio
  def puedeCargar(envio:Envio) : Boolean ={
    var cargable : Boolean = coincideDestino(envio) && entraEnTransporte(envio) && entraEnAvion(envio) && infraestructuraNecesaria(envio)
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

  //Si el transporte cuyo envio esta siendo cargado es un avion, valida que la distancia sea mayor a 1000
  def entraEnDestino(envio:Envio): Boolean ={
    envio.sucursalDestino.volumenDisponible >= envio.volumen
  }
  
  def entraEnTransporte(envio:Envio) : Boolean ={
    this.volumenDisponible >= envio.volumen
  }
  
  def entraEnAvion(envio : Envio) : Boolean ={
    true
  }
  
  def infraestructuraNecesaria(envio: Envio) : Boolean ={
    envio.caracteristicas.forall(carac => this.serviciosExtra.contains(carac)) 
  }
  //Calcula los costos de todos los envios
  def calcularCostoViaje() : Double = {
    var costoDeTransporte : Double = 0
    var costoDeEnvios : Double = 0
    var costoTotal : Double = 0
    if (!this.sinEnviosAsignados) {
    	costoDeTransporte = this.costoTransporte(sucursalOrigen , sucursalDestino) // Costo de transporte base
        this.enviosAsignados.foreach((e:Envio) => costoDeEnvios += e.costoBase()) // Costo base de los envios
        costoTotal += costoDeEnvios 

        //EXTRA FACTORES
        //Los vehiculos terrestres se ven afectados por peajes, donde los camiones pagan $12 por cada peaje y las furgonetas pagan $6.
        //Dentro de los vehiculos terrestres, los paquetes que necesiten refrigeracion suman al costo $5 por cada uno.
        costoTotal += precioPeajes()
        
        // Dentro de los terrestres, los paquetes que necesiten refrigeracion suman al costo $5 por c/u
        costoTotal += costoDeRefrigeracion()
        
        // Los aviones que no se ven afectados por peajes pero pagan 10% de impuestos cuando se desplazan entre sucursales de distintos paises.
	    if (sucursalOrigen.pais != this.sucursalDestino.pais){
	    	costoTotal += (costoDeTransporte * impuestoAvion)
	    }
    	
    	// Cuando un camion va a la sucursal Casa Central en la ultima semana del mes, se le suma un 2% al costo ya
    	// que se lo dejara alli un dia para revision tecnica.
    	if (this.sucursalDestino.esCasaCentral() && this.ultimaSemanaDelMes()) {
    		costoTotal += costoRevisionTecnica(costoDeTransporte)
    	}
    	
    	// Pasado el dia 20 los aviones que viajen a casa central reducen sus costos en 20% al traer insumos de vuelta
    	if (pasadoElDia20 && sucursalDestino.esCasaCentral)
    	  costoTotal -= reduccionInsumos(costoDeTransporte)
    	  
    	// Los transportes que hagan un viaje con menos del 20% de su volumen ocupado afectan su costo. 
    	// Aviones x3 | Furgonetas x2 (Salvo 3+ paquetes urgentes)| Camiones x(1+(VolOcupado/VolTotal)) Salvo Casa Central  
    	costoTotal += costoDeTransporte * multiplicador
   
    	// Los vehiculos que posean GPS suman al costo $0.5 por KM recorrido (ida y vuelta)
    	costoTotal += costoGPS
    	
    	// Los vehiculos que posean Video suman al costo $3.74 por KM recorrido (ida y vuelta)
    	costoTotal += costoVideo
    	
    	// Si en un envio hay sustancias peligrosas => +$600; Los camiones tienen un extra por Urgentes
    	costoTotal += costoSustanciasPeligrosas
    	
    	// Si en un envio hay animales => +$50 < 100km ; +$86 < 200km ; +$137 > 200km
    	costoTotal += costoAnimales
      }
    costoTotal
  }
  
  def costoTransporte(sucursalOrigen: Sucursal, sucursalDestino: Sucursal) : Double = {
    this match {
      case transporte : Avion => this.costoPorKm * this.distanciaAereaEntre(sucursalOrigen , sucursalDestino)
      case transporte : Camion => this.costoPorKm * this.distanciaTerrestreEntre(sucursalOrigen , sucursalDestino)
      case transporte : Furgoneta => this.costoPorKm * this.distanciaTerrestreEntre(sucursalOrigen , sucursalDestino)
    }
  }
  
  def precioPeajes():Int={
    (cantidadPeajesEntre(sucursalOrigen,sucursalDestino) * this.valorPeaje)
  }
  
  def multiplicador():Double= {
	1
  }
  
  def agregarEnvio(envio : Envio): Transporte = {
    this.enviosAsignados = this.enviosAsignados ++ Set(envio)
    if (enviosAsignados.size == 1) {
      sucursalDestino = envio.sucursalDestino
      fechaEnvio = envio.fecha
    }
    	
    this
  }
  
  def tieneSeguimientoGPS(): Boolean = {
    !this.serviciosExtra.find((s: ServicioExtra) => s.soyGPS).isEmpty
  }
  
  def tieneSeguimientoVideo(): Boolean = {
    !this.serviciosExtra.find((s: ServicioExtra) => s.soyVideo).isEmpty
  }
  
   // Falta definir la mutua exclusion
  def puedeLlevarAnimales() : Boolean = {
    !this.serviciosExtra.find((s: ServicioExtra) => s.soyInfraestructuraAnimales).isEmpty
  }
  
  def puedeLlevarSustancias() : Boolean = {
    !this.serviciosExtra.find((s: ServicioExtra) => s.soyInfraestructuraSustancias).isEmpty
  }
  
  def impuestoAvion() : Double = {
    0
  }
  
  def costoDeRefrigeracion(): Double ={
    0
  }

  def ultimaSemanaDelMes(): Boolean = {
    var cal: Calendar = Calendar.getInstance()
	cal.setTime(fechaEnvio)
	
	if (!(cal.get(Calendar.MONTH) == 1)){
	  cal.get(Calendar.WEEK_OF_MONTH) == 5
	} else {
	  cal.get(Calendar.WEEK_OF_MONTH) == 4
	}  
  }
	
  def pasadoElDia20(): Boolean = {
	var cal: Calendar = Calendar.getInstance()
	cal.setTime(fechaEnvio)
	cal.get(Calendar.DAY_OF_MONTH) > 20
  }
  
  def costoRevisionTecnica(costoDeTransporte: Double): Double ={
    0
  }
  
  def costoGPS(): Double ={
    if(serviciosExtra.exists(_.soyGPS))
      distanciaEntreSucursales()*2*0.5
    else
      0
  }
  
  def costoVideo(): Double ={
    if(serviciosExtra.exists(_.soyVideo))
      distanciaEntreSucursales()*2*3.74
    else
      0
  }  

  def distanciaEntreSucursales(): Double ={
    0
  }
  
  def costoSustanciasPeligrosas(): Double ={
    if(enviosAsignados.exists(_.caracteristicas.exists(_.soyInfraestructuraSustancias)))
      600
    else
      0
  }
  
    def costoAnimales(): Double ={
    val dist = distanciaEntreSucursales 
    if(enviosAsignados.exists(_.caracteristicas.exists(_.soyInfraestructuraAnimales)))
      if (dist < 100)
       	50
      else if (dist < 200)
       	86
      else
       	137
    else
    	0
  }
    
  def reduccionInsumos(costoDeTransporte: Double): Double = {
    0
  }
  
  def regresarASucursal() ={
   sucursalOrigen.agregarTransporte(this) 
   sucursalDestino = null
  }
}


