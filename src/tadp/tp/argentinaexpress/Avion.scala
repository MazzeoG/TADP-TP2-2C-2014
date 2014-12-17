package tadp.tp.argentinaexpress

class Avion (override val serviciosExtra : Set[ServicioExtra], sucursalOrigen: Sucursal)
extends Aereo (serviciosExtra, sucursalOrigen){
  
  override val volumenDeCarga : Int = 200
  override val costoPorKm : Int = 500
  override val velocidad : Int = 500
  override val valorPeaje: Int = 0
  
  def entraEnAvion(envio : Envio) : Boolean ={
    val calc = new CalculadorDistancia
    calc.distanciaAereaEntre(envio.sucursalOrigen , envio.sucursalDestino ) > 1000
  }
  
  def impuestoAvion() : Double = {
    0.1
  }
  
  override def multiplicador():Double ={
	if( (this.volumenDeCarga*volOcupadoMulti >= this.volumenEnvios)) 
	 3
   else
     1  
  }

  def reduccionInsumos(costoDeTransporte: Double): Double = {
    // Pasado el dia 20 los aviones que viajen a casa central reducen sus costos en 20% al traer insumos de vuelta
    if (pasadoElDia20 && sucursalDestino.esCasaCentral)
    	costoDeTransporte * 0.2
    else
    	0
  }
  
  override def puedeCargar(envio:Envio) : Boolean ={
    super.puedeCargar(envio) && entraEnAvion(envio)
  }
  
  def impuestoVueloInternacional(costoDeTransporte:Double) : Double = {
     // Los aviones que no se ven afectados por peajes pero pagan 10% de impuestos cuando se desplazan entre sucursales de distintos paises.
	    if (sucursalOrigen.pais != this.sucursalDestino.pais)
	    	costoDeTransporte * impuestoAvion
	    else
	        0
  }
  
  override def costosExtra(costoDeTransporte:Double) : Double = {
    super.costosExtra(costoDeTransporte) + impuestoVueloInternacional(costoDeTransporte) - reduccionInsumos(costoDeTransporte)
  }  
}