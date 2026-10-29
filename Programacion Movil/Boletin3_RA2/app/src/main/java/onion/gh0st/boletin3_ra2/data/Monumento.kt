package onion.gh0st.boletin3_ra2.data

class Monumento(
    nombre : String,
    descripcion : String,
    fotoResId : Int,
    latitud : Double,
    longitud : Double,
    val estilo : String,
    val epoca : String
) : Ubicacion(nombre, descripcion, fotoResId, latitud, longitud) {

    override fun detalle() : String = "$estilo · $epoca"
}