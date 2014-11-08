package tadp.tp.argentinaexpress
import org.junit.Test
import org.junit.Assert._

class TransporteTest {
  
/*  Copy Paste Cheatsheet
    val avion1 = new Avion(Set())
    val furgoneta1 = new Furgoneta(Set())
    val camion1 = new Camion(Set())
    val avion2 = new Avion(Set())
    val furgoneta2 = new Furgoneta(Set())
    val camion2 = new Camion(Set())
    val sucursalArg = new Sucursal(Set(avion1,furgoneta1,camion1),1000,"Argentina")   
    val sucursalChi = new Sucursal(Set(avion2,furgoneta2,camion2),500,"Chile")
    val laCasaCentral = new CasaCentral(Set(),1500,"EEUU")
*/
  
  
  @Test
  def `el transporte debe tener espacio disponible` = {
    val camion1 = new Camion(Set())
    val sucursalArg = new Sucursal(Set(camion1),1000,"Argentina")   
    val sucursalChi = new Sucursal(Set(),500,"Chile")
    
    var unEnvioGrande = new Normal(Set(),sucursalArg,sucursalChi,100,(7,11,2014))
    var unEnvioChico = new Normal(Set(),sucursalArg,sucursalChi,10,(7,11,2014))
   
    assertEquals(true, camion1.puedeCargar(unEnvioChico))
    assertEquals(false, camion1.puedeCargar(unEnvioGrande))    
  }

  @Test
  def `los pedidos se agregan a un transporte disponible` = {
    val camion1 = new Camion(Set())
    val sucursalArg = new Sucursal(Set(camion1),1000,"Argentina")   
    val sucursalChi = new Sucursal(Set(),500,"Chile")
    
    var unEnvioChico = new Normal(Set(),sucursalArg,sucursalChi,10,(7,11,2014))
   
    sucursalArg.asignarEnvioATransporte(unEnvioChico)
    assertEquals(true,camion1.enviosAsignados.contains(unEnvioChico))	  
  }
  
  @Test
  def `las caracteristicas deben ser las mismas` = {
    val camion1 = new Camion(Set())
    val sucursalArg = new Sucursal(Set(camion1),1000,"Argentina")   
    val sucursalChi = new Sucursal(Set(),500,"Chile")
    
    var unEnvioNormal = new Normal(Set(),sucursalArg,sucursalChi,10,(7,11,2014))
    var otroEnvioNormal = new Normal(Set(),sucursalArg,sucursalChi,10,(7,11,2014))
    var unEnvioUrgente = new Urgente(Set(),sucursalArg,sucursalChi,10,(7,11,2014))
    
    sucursalArg.asignarEnvioATransporte(unEnvioNormal)
    sucursalArg.asignarEnvioATransporte(otroEnvioNormal)
    sucursalArg.asignarEnvioATransporte(unEnvioUrgente)
    assertEquals(true,camion1.enviosAsignados.contains(unEnvioNormal))
    assertEquals(true,camion1.enviosAsignados.contains(otroEnvioNormal))
    assertEquals(false,camion1.enviosAsignados.contains(unEnvioUrgente))
  }  

  
  @Test
  def `todos los pedidos deben tener el mismo destino` = {
    val camion1 = new Camion(Set())
    val sucursalArg = new Sucursal(Set(camion1),1000,"Argentina")   
    val sucursalChi = new Sucursal(Set(),500,"Chile")
    val laCasaCentral = new CasaCentral(Set(),1500,"EEUU")
    
    var unEnvioNormal = new Normal(Set(),sucursalArg,sucursalChi,10,(7,11,2014))
    var otroEnvioNormal = new Normal(Set(),sucursalArg,laCasaCentral,10,(7,11,2014))
    
    sucursalArg.asignarEnvioATransporte(unEnvioNormal)
    sucursalArg.asignarEnvioATransporte(otroEnvioNormal)
    assertEquals(true,camion1.enviosAsignados.contains(unEnvioNormal))
    assertEquals(false,camion1.enviosAsignados.contains(otroEnvioNormal))    
  }
  
 
  @Test
  def `la sucursal debe tener espacio disponible` = {
    val camion1 = new Camion(Set())
    val sucursalArg = new Sucursal(Set(camion1),1000,"Argentina")   
    val sucursalChi = new Sucursal(Set(),30,"Chile")
    
    var unEnvioNormal = new Normal(Set(),sucursalArg,sucursalChi,10,(7,11,2014))
    var otroEnvioNormal = new Normal(Set(),sucursalArg,sucursalChi,25,(7,11,2014))
    
    sucursalArg.asignarEnvioATransporte(unEnvioNormal)
    sucursalArg.asignarEnvioATransporte(otroEnvioNormal)
    assertEquals(true,camion1.enviosAsignados.contains(unEnvioNormal))
    assertEquals(false,camion1.enviosAsignados.contains(otroEnvioNormal))    
  }   

 /*  
  @Test
  def `los aviones solo viajan a mas de 1000 km` = {
    
  }
  
  @Test
  def `un transporte que transporte animales` = {
    
  }  

  @Test
  def `un transporte que transporte sustancias peligrosas` = {
    
  }
 
  */  
}

/*
 class PrecioYCostoTest {

  @Before
  def before = {
    //Servicios
    var unGps = new SeguimientoGPS
    var unVideo = new SeguimientoVideo
        
    
  }  
    
  @Test
  def `calculo de costo base` = {
    
  }

  @Test
  def `calculo con peajes` = {
    
  }  
  
  @Test
  def `calculo con refrigeracion` = {
    
  }
  
  @Test
  def `los aviones pagan impuestos entre paises` = {
    
  }  
  
  @Test
  def `un camion a CC en la ultima semana` = {
    
  }  
  
  @Test
  def `los aviones a CC pasado el 20 de mes` = {
    
  }  
  
  @Test
  def `los transportes con menos del 20% del volumen afectan el costo` = {
    
  }  
  
  @Test
  def `el GPS agrega costo` = {
    
  }  
  
  @Test
  def `el Video agrega costo` = {
    
  }  
   
  @Test
  def `costo si viajan sustancias peligrosas` = {
    
  }
  
  @Test
  def `costo si viajan animales` = {
    
  }  
  
  @Test
  def `sustancias peligrosas y paquetes urgentes a la vez` = {
    
  }  
  
  @Test
  def `calculo de la ganancia bruta` = {
    
  }  
}

class EstadisticasTest {
  
  @Test
  def `asd` = {
    
  }
  
}
*/