package tadp.tp.argentinaexpress

class Fragil (override val caracteristicas : Set[ServicioExtra],
    override val sucursalOrigen :Sucursal,
    override val sucursalDestino :Sucursal,
    override val volumen :Int,
    override val fecha :Tuple3[Int,Int,Int])
extends Envio (caracteristicas, sucursalOrigen, sucursalDestino, volumen, fecha){
  override def precio()={120}
  def costoBase()={18}
}