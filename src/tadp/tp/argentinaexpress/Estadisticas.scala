package tadp.tp.argentinaexpress

import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

trait Estadisticas {
  def filtrarPorSucursalDestino(viajes: Set[Viaje], sucursalBuscada : Sucursal): Set[Viaje] ={
    viajes.filter(_.sucursalDestino == sucursalBuscada)
  }
  
  def filtrarPorSucursalOrigen(viajes: Set[Viaje], sucursalBuscada : Sucursal): Set[Viaje] ={
    viajes.filter(_.sucursalOrigen == sucursalBuscada)
  }
  
  def filtrarPorFecha(viajes: Set[Viaje], fechaBuscada : Date): Set[Viaje] ={
    viajes.filter(_.fecha == fechaBuscada)
  }

  def filtrarPorRangoFecha(viajes: Set[Viaje], fechaIni : Date, fechaFin : Date): Set[Viaje] ={
    viajes.filter(v => v.fecha.after(fechaIni) && v.fecha.before(fechaFin) )
  }
    
  def filtrarPorTipoDeEnvio(viajes: Set[Viaje], tipo : String): Set[Viaje] ={
    viajes.filter(_.tipoEnvio == tipo)
  }  

  def filtrarPorTipoDeTransporte(viajes: Set[Viaje], tipo : String): Set[Viaje] ={
    viajes.filter(v => nombreClase(v.transporte) == tipo)
  } 
  
  def tiempoPromedioDeViajes(viajes: Set[Viaje]) : Double = {
    if(!viajes.isEmpty)
    	viajes.toList.map(_.tiempoHr).sum / viajes.size
    else
    	0
  }  
  
  def costoPromedioDeViajes(viajes: Set[Viaje]) : Double = {
    if(!viajes.isEmpty)
    	viajes.toList.map(_.costo).sum / viajes.size
    else
    	0
  }

  def gananciaPromedioDeViajes(viajes: Set[Viaje]) : Double = {
    if(!viajes.isEmpty)
    	viajes.toList.map(_.ganancia).sum / viajes.size
    else
    	0
  }
  
  def cantidadDeEnvios(viajes: Set[Viaje]) : Int ={
    viajes.toList.map(_.pedidos.size).sum
  }
  
  def cantidadDeViajes(viajes: Set[Viaje]) : Int ={
    viajes.size
  }
  
  def facturacionTotal(viajes: Set[Viaje]) : Double ={
    viajes.toList.map(_.ganancia).sum
  }
  
  def viajesPorTipoTransporte(viajes: Set[Viaje]) ={
    val viajesCamion : Set[Viaje] = filtrarPorTipoDeTransporte(viajes, "Camion")
    val viajesFurgoneta : Set[Viaje] = filtrarPorTipoDeTransporte(viajes, "Furgoneta")
    val viajesAvion : Set[Viaje] = filtrarPorTipoDeTransporte(viajes, "Avion")
    
    println("Transporte  |  Cantidad")
    println("Camion      :  " + viajesCamion.size)
    println("Furgoneta   :  " + viajesFurgoneta.size)
    println("Avion       :  " + viajesAvion.size)
  }
  
  def nombreClase(obj : Object): String = {
    var nombre : String = obj.getClass().toString()
    nombre = nombre.substring(nombre.lastIndexOf('.')+1)  
    nombre
  }  
}