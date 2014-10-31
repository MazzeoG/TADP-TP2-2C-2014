package tadp.tp.argentinaexpress

trait CalculadorDistancia {
def distanciaTerrestreEntre(sucursal1: Sucursal, sucursal2: Sucursal): Double
def distanciaAereaEntre(sucursal1: Sucursal, sucursal2: Sucursal): Double
def cantidadPeajesEntre(sucursal1: Sucursal, sucursal2: Sucursal): Int
}