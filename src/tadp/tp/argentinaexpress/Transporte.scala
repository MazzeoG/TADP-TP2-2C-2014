package tadp.tp.argentinaexpress

import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

abstract class Transporte (val serviciosExtra : Set[ServicioExtra], var sucursalOrigen: Sucursal)
    {
  val volumenDeCarga : Int
  val costoPorKm : Int
  val velocidad : Int
  var sucursalDestino: Sucursal = null
  var enviosAsignados: Set[Envio] = Set()
  var fechaEnvio :Date = null
  val valorPeaje: Int
  val volOcupadoMulti: Double = 0.2
  var viajesRealizados : Set[Viaje] = Set()
  
  // Inicializacion de los transportes
  if (sucursalOrigen != null)
	  sucursalOrigen.agregarTransporte(this)
  
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
     this.enviosAsignados.toList.map((e:Envio) => e.volumen).sum
  }
  
  def volumenDisponible() :Int = {
    this.volumenDeCarga - this.volumenEnvios
  }
  
  //Funcion utilizada para validar que un transporte pueda cargar un envio
  def puedeCargar(envio:Envio) : Boolean ={
    coincideDestino(envio) && entraEnTransporte(envio) &&
    infraestructuraNecesaria(envio) && coincideTipoDeEnvio(envio) && envio.esCargablePor(this)
  }
  
  def coincideDestino(envio:Envio) : Boolean = {
    if(this.sinEnviosAsignados)
      true
    else {
      this.enviosAsignados.forall((e:Envio) => e.sucursalDestino==envio.sucursalDestino);
    }
  }

  def coincideTipoDeEnvio(envio: Envio) : Boolean ={
    enviosAsignados.forall(_.puedeEnviarseCon(envio))
  }
    
  def entraEnTransporte(envio:Envio) : Boolean ={
    this.volumenDisponible >= envio.volumen
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
        costoTotal += ((costoDeTransporte * multiplicador) + costosExtra(costoDeTransporte))
      }
    costoTotal
  }
  
  def costoTransporte(sucursalOrigen: Sucursal, sucursalDestino: Sucursal) : Double = {
    this.costoPorKm * this.distanciaEntreSucursales()
  }
  
  // Los transportes que hagan un viaje con menos del 20% de su volumen ocupado afectan su costo. 
  // Aviones x3 | Furgonetas x2 (Salvo 3+ paquetes urgentes)| Camiones x(1+(VolOcupado/VolTotal)) Salvo Casa Central
  def multiplicador():Double= 1
  
  def agregarEnvio(envio : Envio): Transporte = {
    this.enviosAsignados = this.enviosAsignados ++ Set(envio)
    if (enviosAsignados.size == 1) {
      sucursalDestino = envio.sucursalDestino
      fechaEnvio = envio.fecha
    }
    this
  }
  
  def tieneSeguimientoGPS(): Boolean = !this.serviciosExtra.find((s: ServicioExtra) => s.soyGPS).isEmpty
  
  def tieneSeguimientoVideo(): Boolean = !this.serviciosExtra.find((s: ServicioExtra) => s.soyVideo).isEmpty
  
  def puedeLlevarAnimales() : Boolean = !this.serviciosExtra.find((s: ServicioExtra) => s.soyInfraestructuraAnimales).isEmpty
  
  def puedeLlevarSustancias() : Boolean = !this.serviciosExtra.find((s: ServicioExtra) => s.soyInfraestructuraSustancias).isEmpty
  
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
  
  def costoGPS(): Double ={
    // Los vehiculos que posean GPS suman al costo $0.5 por KM recorrido (ida y vuelta)
    if(serviciosExtra.exists(_.soyGPS))
      distanciaEntreSucursales()*2*0.5
    else
      0
  }
  
  def costoVideo(): Double ={
    // Los vehiculos que posean Video suman al costo $3.74 por KM recorrido (ida y vuelta)
    if(serviciosExtra.exists(_.soyVideo))
      distanciaEntreSucursales()*2*3.74
    else
      0
  }  

  def distanciaEntreSucursales(): Double = 0
  
  def costoSustanciasPeligrosas(): Double ={
    // Si en un envio hay sustancias peligrosas => +$600; Los camiones tienen un extra por Urgentes
    if(enviosAsignados.exists(_.caracteristicas.exists(_.soyInfraestructuraSustancias)))
      600
    else
      0
  }
  
  def costoAnimales(): Double ={
    // Si en un envio hay animales => +$50 < 100km ; +$86 < 200km ; +$137 > 200km
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
      
  def regresarASucursal() ={
   sucursalOrigen.agregarTransporte(this) 
   sucursalDestino = null
  }
  
  def calcularGananciaBruta() : Double = enviosAsignados.toList.map(_.precio).sum
  
  def calcularGananciaNeta() : Double = calcularGananciaBruta - calcularCostoViaje
  
  def calcularTiempoViaje() : Double = distanciaEntreSucursales / velocidad
  
  def costosExtra(costoDeTransporte:Double) : Double = {
    costoGPS + costoVideo + costoSustanciasPeligrosas + costoAnimales
  }
}

