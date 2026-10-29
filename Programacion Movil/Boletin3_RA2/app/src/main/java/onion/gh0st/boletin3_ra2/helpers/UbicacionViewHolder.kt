package onion.gh0st.boletin3_ra2.helpers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import onion.gh0st.boletin3_ra2.R
import onion.gh0st.boletin3_ra2.data.Ubicacion

class UbicacionViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val imgUbicacion: ImageView = view.findViewById(R.id.imgUbicacion)
    private val tvNombre: TextView = view.findViewById(R.id.tvNombre)
    private val tvDetalle: TextView = view.findViewById(R.id.tvDetalle)

    /**
     * Añade los datos de una ubicacion en la fila
     */
    fun bind(ubicacion: Ubicacion) {
        imgUbicacion.setImageResource(ubicacion.fotoResId)
        tvNombre.text = ubicacion.nombre
        tvDetalle.text = ubicacion.detalle()
    }
}