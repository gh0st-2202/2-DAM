package onion.gh0st.boletin3_ra2.helpers

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import onion.gh0st.boletin3_ra2.R
import onion.gh0st.boletin3_ra2.data.Restaurante
import onion.gh0st.boletin3_ra2.data.Ubicacion

/**
 * Clase encargada de formatear y añadir los datos al ReciclerView
 */
class UbicacionAdapter(private val ubicaciones: List<Ubicacion>) : RecyclerView.Adapter<UbicacionViewHolder>() {

    // A) Crea una fila nueva a partir de item_ubicacion.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UbicacionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ubicacion, parent, false)
        return UbicacionViewHolder(view)
    }

    // B) Rellena la fila con la ubicacion de esa posición
    override fun onBindViewHolder(holder: UbicacionViewHolder, position: Int) {
        val ubicacion = ubicaciones[position]
        holder.bind(ubicacion)

        holder.itemView.setOnClickListener {
            if (ubicacion is Restaurante) {
                val intent = Intent(holder.itemView.context, WebActivity::class.java)
                intent.putExtra(WebActivity.EXTRA_URL, ubicacion.webUrl)
                holder.itemView.context.startActivity(intent)
            } else Toast.makeText(holder.itemView.context, "${ubicacion.nombre}\n${ubicacion.detalle()}\n${ubicacion.descripcion}", 2).show()

        }
    }

    // C) Dice cuántos elementos hay
    override fun getItemCount(): Int = ubicaciones.size
}