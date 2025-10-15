package com.curso.AppJAR.basedatos.adapter

import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.curso.AppJAR.Constantes
import com.curso.AppJAR.R
import com.curso.AppJAR.basedatos.entity.PersonaConDetalles
import com.curso.AppJAR.databinding.FilaPersonaBinding
import com.curso.AppJAR.util.LogUtil

//class AdapterPersonas (var listaPersonas:List<Persona>): Adapter<PersonaVH>() {
class AdapterPersonas (var listaPersonas: List<PersonaConDetalles>, val onClickFila: (PersonaConDetalles) -> Unit): Adapter<PersonaVH>() {

    lateinit var itemSeleccionado:View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonaVH {
        val filaPersona = FilaPersonaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonaVH(filaPersona)
    }

    override fun getItemCount(): Int {
        return listaPersonas.size
    }

    override fun onBindViewHolder(holder: PersonaVH, position: Int) {
        val personaActual = this.listaPersonas[position]
        holder.rellenarFila(personaActual)
        /*holder.itemView.setOnClickListener {
            Log.d(Constantes.ETIQUETA_LOG, "Tocado fila $personaActual")
            onClickFila(personaActual)//TODO crear un sólo listener para todas las filas
            //clickFila(personaActual)
        }*/
        holder.itemView.tag = personaActual

        // para optimizar y que solo haya un listener para todas las filas
        holder.itemView.setOnClickListener (::listenerFila)
        //POPUP MENU al dejar una pulsacion larga en cualquier fila
        holder.itemView.setOnLongClickListener {
            val p = android.widget.PopupMenu(it.context, it)
            p.getMenuInflater().inflate(R.menu.menu_item_personas, p.getMenu());
            p.gravity = Gravity.CENTER//No tiene efecto, el menú sale pegado a la izquierda
            p.show()
            it.setBackgroundColor(Color.GRAY)
            //TODO desmarcar
            itemSeleccionado = it
            p.setOnDismissListener {
                itemSeleccionado?.setBackgroundColor(Color.TRANSPARENT)
                // Quitar selección al cerrar el menú
                //selectedPosition = RecyclerView.NO_POSITION
                //notifyDataSetChanged()
            }
            p.setOnMenuItemClickListener {
                Log.d(Constantes.ETIQUETA_LOG, " ${LogUtil.getLogInfo()} Opción Tocada ${it.title}")
                true
            }

            true
        }



    }

    fun listenerFila(view:View)
    {
        val personaFila = view.tag as PersonaConDetalles
        onClickFila(personaFila)
    }
}