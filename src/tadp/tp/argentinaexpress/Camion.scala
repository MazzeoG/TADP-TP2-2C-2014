package tadp.tp.argentinaexpress

class Camion (override val serviciosExtra : Set[ServicioExtra], sucursalOrigen: Sucursal)
extends Terrestre (serviciosExtra, sucursalOrigen){
  
  override val volumenDeCarga : Int = 45
  override val costoPorKm : Int = 100
  override val velocidad : Int = 60
  override val valorPeaje : Int = 12

  override def puedeCargarRefrigerados() ={
    true
  } 
  
  override def multiplicador():Double ={
	if( (this.volumenDeCarga*volOcupadoMulti >= this.volumenEnvios)
	    && !(sucursalOrigen.esCasaCentral || sucursalDestino.esCasaCentral)) 
     1+(this.volumenEnvios.toDouble/this.volumenDeCarga.toDouble)
   else
     1  
  } 
  
  def costoRevisionTecnica(costoDeTransporte: Double): Double ={
    // Cuando un camion va a la sucursal Casa Central en la ultima semana del mes, se le suma un 2% al costo ya
    // que se lo dejara alli un dia para revision tecnica.
    if (this.sucursalDestino.esCasaCentral() && this.ultimaSemanaDelMes()) 
    	costoDeTransporte * 0.02
    else
    	0
   }
  
  override def costoSustanciasPeligrosas(): Double ={
    if(enviosAsignados.exists(_.caracteristicas.exists(_.soyInfraestructuraSustancias)))
      600 + (3 * enviosAsignados.filter(_.isInstanceOf[Urgente]).toList.map(_.volumen).sum / volumenDeCarga )
    else
      0    
  }
  
  override def costosExtra(costoDeTransporte:Double) : Double = {
    super.costosExtra(costoDeTransporte) + costoRevisionTecnica(costoDeTransporte)
  } 
 }