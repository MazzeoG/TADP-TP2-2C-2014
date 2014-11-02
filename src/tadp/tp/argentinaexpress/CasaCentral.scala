package tadp.tp.argentinaexpress

import scala.collection.immutable.Set

class CasaCentral(override val transporte : Set[Transporte],override val volumenTotal : Int, override val Pais: String) 
extends Sucursal(transporte,volumenTotal,Pais) {

}