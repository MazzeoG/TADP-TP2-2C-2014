package tadp.tp.argentinaexpress

import scala.collection.immutable.Set

class CasaCentral(transporte : Set[Transporte],override val volumenTotal : Int, override val pais: String) 
extends Sucursal(transporte,volumenTotal,pais) {

}