package tadp.tp.argentinaexpress

import tadp.tp.argentinaexpress._
import org.junit.Assert._
import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith



class HibridoTest {
  
  @Test
 def `cargo un camion` = {
    val sucursal1= new Sucursal(Set[Transporte](), 100, "argentina");
    val sucursal2= new Sucursal(Set[Transporte](), 100, "argentina");
   var camion = new Camion(Set[ServicioExtra]());
   var envio= new Envio(Set[Envio] (),sucursal1,sucursal2,50,Tuple3(19,7,1993));
   camion.agregarEnvio(envio);
    assertEquals("Tamaño envios", 1, camion.enviosAsignados.size)
  }

  @Test
 def `cargo un avion` = {
    val sucursal1= new Sucursal(Set[Transporte](), 100, "argentina");
    val sucursal2= new Sucursal(Set[Transporte](), 100, "argentina");
   var avion = new Avion(Set[ServicioExtra]());
   var envio= new Envio(Set[Envio] (),sucursal1,sucursal2,50,Tuple3(19,7,1993));
   avion.agregarEnvio(envio);
    assertEquals("Tamaño envios", 1, avion.enviosAsignados.size)
  }
  
  
  @Test
 def `cargo una furgoneta` = {
    val sucursal1= new Sucursal(Set[Transporte](), 100, "argentina");
    val sucursal2= new Sucursal(Set[Transporte](), 100, "argentina");
   var furgoneta = new Furgoneta(Set[ServicioExtra]());
   var envio= new Envio(Set[Envio] (),sucursal1,sucursal2,50,Tuple3(19,7,1993));
   furgoneta.agregarEnvio(envio);
    assertEquals("Tamaño envios", 1, furgoneta.enviosAsignados.size)
  }
}