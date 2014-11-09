package tadp.tp.argentinaexpress

<<<<<<< HEAD
import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

class Envio (val caracteristicas : Set[Envio],
=======
class Envio (val caracteristicas : Set[ServicioExtra],
>>>>>>> origin/master
    val sucursalOrigen :Sucursal,
    val sucursalDestino :Sucursal,
    val volumen :Int,
    val fecha :Tuple3[Int,Int,Int]) // Yo lo manejo como Tuple3[dd,mm,yyyy].
    extends CalculadorDistancia{
  
	val valorRefrigeracion : Int = 0;
  
	def precio():Int = ???
	
	def costo():Int = ???
	
	def calcularCostoEnvio (transporte: Transporte, costoDeTransporte : Double) : Double = {

	  //Los vehiculos terrestres se ven afectados por peajes, donde los camiones pagan $12 por cada peaje y las furgonetas pagan $6.
	  //Dentro de los vehiculos terrestres, los paquetes que necesiten refrigeracion suman al costo $5 por cada uno.
	  
	  var costoParcial1 : Double = (costoDeTransporte + transporte.precioPeajes(this)+ valorRefrigeracion)* transporte.multiplicador
	  
	  // Los aviones que no se ven afectados por peajes pero pagan 10% de impuestos cuando se desplazan entre sucursales de distintos paises.
	  
	  if (this.sucursalOrigen.pais == this.sucursalDestino.pais){
	    costoParcial1 = costoParcial1 * transporte.impuestoAvion
	  }
	  costoParcial1
	  
	  

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