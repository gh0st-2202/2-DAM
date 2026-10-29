package onion.gh0st.boletin3_ra2.data

import android.content.Context
import onion.gh0st.boletin3_ra2.R

object DatosLeon {

    fun monumentos(context: Context): List<Monumento> = listOf(
        Monumento(
            nombre = context.getString(R.string.mon_catedral_nombre),
            descripcion = context.getString(R.string.mon_catedral_desc),
            fotoResId = R.drawable.foto_catedral,
            latitud = 42.59946, longitud = -5.56665,
            estilo = context.getString(R.string.mon_catedral_estilo),
            epoca = context.getString(R.string.mon_catedral_epoca)
        ),
        Monumento(
            nombre = context.getString(R.string.mon_sanisidoro_nombre),
            descripcion = context.getString(R.string.mon_sanisidoro_desc),
            fotoResId = R.drawable.foto_sanisidoro,
            latitud = 42.60075, longitud = -5.57094,
            estilo = context.getString(R.string.mon_sanisidoro_estilo),
            epoca = context.getString(R.string.mon_sanisidoro_epoca)
        ),
        Monumento(
            nombre = context.getString(R.string.mon_botines_nombre),
            descripcion = context.getString(R.string.mon_botines_desc),
            fotoResId = R.drawable.foto_botines,
            latitud = 42.59844, longitud = -5.57072,
            estilo = context.getString(R.string.mon_botines_estilo),
            epoca = context.getString(R.string.mon_botines_epoca)
        ),
        Monumento(
            nombre = context.getString(R.string.mon_guzmanes_nombre),
            descripcion = context.getString(R.string.mon_guzmanes_desc),
            fotoResId = R.drawable.foto_guzmanes,
            latitud = 42.59844, longitud = -5.57014,
            estilo = context.getString(R.string.mon_guzmanes_estilo),
            epoca = context.getString(R.string.mon_guzmanes_epoca)
        )
    )

    fun restaurantes(context: Context): List<Restaurante> {
        return listOf(
            Restaurante(
                nombre = context.getString(R.string.res_humedo_nombre),
                descripcion = context.getString(R.string.res_humedo_desc),
                fotoResId = R.drawable.foto_humedo,
                latitud = 42.59820, longitud = -5.57180,
                tipoCocina = context.getString(R.string.res_humedo_cocina),
                webUrl = context.getString(R.string.res_humedo_web)
            ),
            Restaurante(
                nombre = context.getString(R.string.res_mando_nombre),
                descripcion = context.getString(R.string.res_mando_desc),
                fotoResId = R.drawable.foto_mando,
                latitud = 42.59810, longitud = -5.57170,
                tipoCocina = context.getString(R.string.res_mando_cocina),
                webUrl = context.getString(R.string.res_mando_web)
            ),
            Restaurante(
                nombre = context.getString(R.string.res_cocodrilo_nombre),
                descripcion = context.getString(R.string.res_cocodrilo_desc),
                fotoResId = R.drawable.foto_cocodrilo,
                latitud = 42.59790, longitud = -5.57230,
                tipoCocina = context.getString(R.string.res_cocodrilo_cocina),
                webUrl = context.getString(R.string.res_cocodrilo_web)
            )
        )
    }

    fun eventos(context: Context): List<Evento> = listOf(
        Evento(
            nombre = context.getString(R.string.evt_froilan_nombre),
            descripcion = context.getString(R.string.evt_froilan_desc),
            fotoResId = R.drawable.foto_froilan,
            latitud = 42.59780, longitud = -5.57100,
            fecha = context.getString(R.string.evt_froilan_fecha),
            tipo = context.getString(R.string.evt_froilan_tipo)
        ),
        Evento(
            nombre = context.getString(R.string.evt_jazz_nombre),
            descripcion = context.getString(R.string.evt_jazz_desc),
            fotoResId = R.drawable.foto_jazz,
            latitud = 42.60150, longitud = -5.57320,
            fecha = context.getString(R.string.evt_jazz_fecha),
            tipo = context.getString(R.string.evt_jazz_tipo)
        ),
        Evento(
            nombre = context.getString(R.string.evt_mercado_nombre),
            descripcion = context.getString(R.string.evt_mercado_desc),
            fotoResId = R.drawable.foto_mercado,
            latitud = 42.59830, longitud = -5.57020,
            fecha = context.getString(R.string.evt_mercado_fecha),
            tipo = context.getString(R.string.evt_mercado_tipo)
        )
    )

    fun todas( context: Context): List<Ubicacion> =
        monumentos(context) + restaurantes(context) + eventos(context)
}