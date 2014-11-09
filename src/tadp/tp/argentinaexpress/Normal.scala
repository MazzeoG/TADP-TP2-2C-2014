package tadp.tp.argentinaexpress

class Normal (override val caracteristicas : Set[ServicioExtra],
    override val sucursalOrigen :Sucursal,
    override val sucursalDestino :Sucursal,
    override val volumen :Int,
    override val fecha :Tuple3[Int,Int,Int])
extends Envio (caracteristicas, sucursalOrigen, sucursalDestino, volumen, fecha){
	override def precio()={80}
	def costoBase()={10}
}