package onion.gh0st.boletin3_ra2.data

class Evento(
    nombre : String,
    descripcion : String,
    fotoResId : Int,
    latitud : Double,
    longitud : Double,
    val fecha : String,
    val tipo : String
) : Ubicacion(nombre, descripcion, fotoResId, latitud, longitud) {

    override fun detalle() : String = "$tipo · $fecha"
}