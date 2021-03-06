package tadp.tp.argentinaexpress

import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

class Normal (override val caracteristicas : Set[ServicioExtra],
    override val sucursalOrigen :Sucursal,
    override val sucursalDestino :Sucursal,
    override val volumen :Int,
    override val fecha :Date)
extends Envio (caracteristicas, sucursalOrigen, sucursalDestino, volumen, fecha){
	override def precio()={80}
	override def costoBase()={10}
	
  override def puedeEnviarseCon (envio: Envio): Boolean =  envio.puedeEnviarseConNormal
  
  override def puedeEnviarseConFragiles : Boolean = false
  override def puedeEnviarseConNormal : Boolean = true
  override def puedeEnviarseConRefrigeracion : Boolean = false
  override def puedeEnviarseConUrgentes : Boolean = false
}