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
	  
	  var costoAcumulado : Double = (costoDeTransporte + transporte.precioPeajes(this)+ valorRefrigeracion)
	  
	  // Los aviones que no se ven afectados por peajes pero pagan 10% de impuestos cuando se desplazan entre sucursales de distintos paises.
	  
	  if (this.sucursalOrigen.pais == this.sucursalDestino.pais){
	    costoAcumulado = costoDeTransporte * transporte.impuestoAvion
	  }
	  
	  // Cuando un camion a la sucursal Casa Central en la ultima semana del mes, se le suma un 2% al costo ya
	  // que se lo dejara alli un dia para revision tecnica.
	  
	  if (this.sucursalDestino.esCasaCentral() && this.ultimaSemanaDelMes()) {
	    costoAcumulado += costoDeTransporte * 2/100
	  }
	  
	  // Los aviones que vayan a Casa Central pasado el dia 20, aprovechan para volver a la sucursal de origen
	  //enviandoles insumos necesarios para el funcionamiento de la empresa, de esta forma el costo de ese envio se
	  //vera reducido en un 20 %.
	  
	  if(this.sucursalDestino.esCasaCentral() && this.pasadoElDia20()){
	    costoAcumulado -= costoDeTransporte * 0.2
	  }
	  
	  //Los transportes que hagan un viaje con menos del 20% de su volumen ocupado afectaran a su costo. Los
	  //aviones multiplicaran su costo por 3. Las furgonetas multiplicaran su costo por 2, salvo que esten llevando
	  //3 o mas paquetes urgentes. Los camiones multiplicaran su costo por 1 + (volumenocupado/volumentotal),
	  //salvo que se este dirigiendo a Casa Central o saliendo desde casa central.
	  
	  costoAcumulado *= transporte.multiplicador(this)
	  
	  //Los vehiculos que posean un sistema de GPS suman al costo $0.5 por kilometro recorrido (considerar la ida
	  //y la vuelta del viaje).
	  
	  if (transporte.tieneSeguimientoGPS){
	    costoAcumulado += 0.5 * transporte.distanciaEntreSucursales(this.sucursalOrigen , this.sucursalDestino ) * 2
	  }
	  
	  //Los vehiculos que posean un sistema de Video suman al costo $3.74 por kilometro recorrido (considerar la
	  //ida y la vuelta del viaje).
		
	  if (transporte.tieneSeguimientoVideo){
	    costoAcumulado += 3.74 * transporte.distanciaEntreSucursales(this.sucursalOrigen , this.sucursalDestino ) * 2
	  }
	  
	  //Si en un envio se transportan sustancias peligrosas el costo asciende en $600, mientas que si el envio transporta
	  //animales, se sumara $50 si el viaje es de menos de 100km, $86 si es de menos de 200km, y $137 si es de mas
	  //de 200km.
	  
	  if (this.tieneSustanciasPeligrosas) {
	    
	    costoAcumulado += 600
	    
	  } else if (this.tieneAnimales){ 
	    
	    var distancia: Double = transporte.distanciaEntreSucursales(this.sucursalOrigen , this.sucursalDestino)
	    
	    if (distancia  < 100){
	      
	      costoAcumulado += 50
	      
	    }else if(distancia < 200){
	      
	      costoAcumulado += 86
	      
	    }else {
	      
	      costoAcumulado += 137
	      
	    }
	    
	  }
	  
	  //Si un camion transporta sustancia peligrosas, y ademas posee paquetes urgentes, se debe sumar al costo
	  //$3 * (volumenpaqueteurgente=volumendeltransporte)
	  
	  if(transporte.esCamion && this.tieneSustanciasPeligrosas && this.esUrgente){
	    
	    costoAcumulado += 3 * (this.volumen / transporte.volumenDeCarga)
	    
	  }
	  
	  costoAcumulado
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
	
	def esUrgente(): Boolean = {
	  false
	}
	
    def ganancia():Int = {
      this.precio-this.costo
    }
    
    def tieneSustanciasPeligrosas() :Boolean ={
      !this.caracteristicas.find((s: ServicioExtra) => s.soyInfraestructuraSustancias).isEmpty
    }
    
    def tieneAnimales() :Boolean ={
      !this.caracteristicas.find((s: ServicioExtra) => s.soyInfraestructuraAnimales).isEmpty
    }
  
}