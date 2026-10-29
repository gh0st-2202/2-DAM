package onion.gh0st.boletin3_ra2.data

class Restaurante(
    nombre : String,
    descripcion : String,
    fotoResId : Int,
    latitud : Double,
    longitud : Double,
    val tipoCocina : String,
    val webUrl : String
) : Ubicacion(nombre, descripcion, fotoResId, latitud, longitud) {

    override fun detalle() : String = "$tipoCocina · $descripcion"
}