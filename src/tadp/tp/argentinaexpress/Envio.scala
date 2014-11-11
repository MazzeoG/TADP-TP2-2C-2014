package tadp.tp.argentinaexpress


import java.text.SimpleDateFormat

import java.util.{Calendar, Date}



class Envio (val caracteristicas : Set[ServicioExtra],

    val sucursalOrigen :Sucursal,
    val sucursalDestino :Sucursal,
    val volumen :Int,
    val fecha :Date) // Yo lo manejo como Tuple3[dd,mm,yyyy].
    extends CalculadorDistancia{
  
	val valorRefrigeracion : Int = 0;
  
	def precio():Int = ???
	
	def costo():Int = ???
	
	def calcularCostoEnvio (transporte: Transporte, costoDeTransporte : Double) : Double = {

	  //Los vehiculos terrestres se ven afectados por peajes, donde los camiones pagan $12 por cada peaje y las furgonetas pagan $6.
	  //Dentro de los vehiculos terrestres, los paquetes que necesiten refrigeracion suman al costo $5 por cada uno.
	  
	  var costoParcial : Double = (costoDeTransporte + transporte.precioPeajes(this)+ valorRefrigeracion)
	  
	  // Los aviones que no se ven afectados por peajes pero pagan 10% de impuestos cuando se desplazan entre sucursales de distintos paises.
	  
	  if (this.sucursalOrigen.pais == this.sucursalDestino.pais){
	    costoParcial = costoDeTransporte * transporte.impuestoAvion
	  }
	  
	  // Cuando un camion a la sucursal Casa Central en la ultima semana del mes, se le suma un 2% al costo ya
	  // que se lo dejara alli un dia para revision tecnica.
	  
	  if (this.sucursalDestino.esCasaCentral() && this.ultimaSemanaDelMes()) {
	    costoParcial += costoDeTransporte * 2/100
	  }
	  
	  // Los aviones que vayan a Casa Central pasado el dia 20, aprovechan para volver a la sucursal de origen
	  //enviandoles insumos necesarios para el funcionamiento de la empresa, de esta forma el costo de ese envio se
	  //vera reducido en un 20 %.
	  
	  if(this.sucursalDestino.esCasaCentral() && this.pasadoElDia20()){
	    costoParcial -= costoDeTransporte * 2/10
	  }
		
	  costoParcial
    }
  
	def ultimaSemanaDelMes(): Boolean = {
	  var cal: Calendar = Calendar.getInstance()
	  cal.setTime(this.fecha)

	  if (!(cal.get(Calendar.MONTH) == 1)){
	    
	    cal.get(Calendar.WEEK_OF_MONTH) == 5
	    
	  } else {
	    
	    cal.get(Calendar.WEEK_OF_MONTH) == 4
	  
	  }
	  
	}
	
	def pasadoElDia20(): Boolean = {
	  var cal: Calendar = Calendar.getInstance()
	  cal.setTime(this.fecha)
	  
	  cal.get(Calendar.DAY_OF_MONTH) > 20
	}
	
  //val costoBase:Int
//  def costo()={
//    this.costoBase+transporte.costo()
//  }
//  
    def ganancia():Int = {
      this.precio-this.costo
  }
  
}