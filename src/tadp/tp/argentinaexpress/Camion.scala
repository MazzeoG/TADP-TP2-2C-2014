package tadp.tp.argentinaexpress

class Camion extends Transporte {

  def capacidad()={45}
  def costo()={100}
  def velocidad()={60}
  def puedeCargarRefrigerados() ={
    true
  } //tienen refrigeracion
}