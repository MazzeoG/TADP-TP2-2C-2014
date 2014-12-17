package tadp.tp.argentinaexpress

abstract class Terrestre (override val serviciosExtra : Set[ServicioExtra], sucursalOrigen: Sucursal)
extends Transporte (serviciosExtra, sucursalOrigen){

  def costoDeRefrigeracion(): Double ={
    // Dentro de los terrestres, los paquetes que necesiten refrigeracion suman al costo $5 por c/u
    this.enviosAsignados.map(e => e.valorRefrigeracion).sum
  }
  
  override def distanciaEntreSucursales(): Double ={
    var calc =new CalculadorDistancia
    calc.distanciaTerrestreEntre(sucursalOrigen, sucursalDestino)
  }
  
  override def costosExtra(costoDeTransporte:Double) : Double = {
    super.costosExtra(costoDeTransporte) + precioPeajes + costoDeRefrigeracion
  }
  
  def precioPeajes():Double={
    //Los vehiculos terrestres se ven afectados por peajes, donde los camiones pagan $12 por cada peaje y las furgonetas pagan $6.
    //Dentro de los vehiculos terrestres, los paquetes que necesiten refrigeracion suman al costo $5 por cada uno.
    val calc = new CalculadorDistancia
    (calc.cantidadPeajesEntre(sucursalOrigen,sucursalDestino) * this.valorPeaje)
  }
}