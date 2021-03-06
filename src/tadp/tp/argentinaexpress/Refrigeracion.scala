package tadp.tp.argentinaexpress

import java.text.SimpleDateFormat

import java.util.{Calendar, Date}

class Refrigeracion (override val caracteristicas : Set[ServicioExtra],
    override val sucursalOrigen :Sucursal,
    override val sucursalDestino :Sucursal,
    override val volumen :Int,
    override val fecha :Date)
extends Envio (caracteristicas, sucursalOrigen, sucursalDestino, volumen, fecha){
	override def precio()={210}
	override def costoBase()={70}
	override val valorRefrigeracion : Int = 5;
	
	override def esCargablePor(transporte: Transporte) : Boolean = {
	  transporte.puedeCargarRefrigerados
	}
	
  override def puedeEnviarseCon (envio: Envio): Boolean =  envio.puedeEnviarseConRefrigeracion
  
  override def puedeEnviarseConFragiles : Boolean = false
  override def puedeEnviarseConNormal : Boolean = false
  override def puedeEnviarseConRefrigeracion : Boolean = true
  override def puedeEnviarseConUrgentes : Boolean = false
}