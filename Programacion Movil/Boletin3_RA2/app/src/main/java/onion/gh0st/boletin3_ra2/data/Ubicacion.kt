package onion.gh0st.boletin3_ra2.data

abstract class Ubicacion (
    val nombre : String,
    val descripcion : String,
    val fotoResId : Int,
    val latitud : Double,
    val longitud : Double
) {
    abstract fun detalle(): String
}