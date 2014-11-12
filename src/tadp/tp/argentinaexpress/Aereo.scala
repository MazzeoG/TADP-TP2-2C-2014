package tadp.tp.argentinaexpress

abstract class Aereo (override val serviciosExtra : Set[ServicioExtra], sucursalOrigen: Sucursal)
extends Transporte (serviciosExtra, sucursalOrigen){

  override def distanciaEntreSucursales(): Double ={
    distanciaAereaEntre(sucursalOrigen, sucursalDestino)
  }
}