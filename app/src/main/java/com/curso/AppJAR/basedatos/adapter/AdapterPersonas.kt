package com.curso.AppJAR.basedatos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.curso.AppJAR.basedatos.entity.Persona
import com.curso.AppJAR.databinding.FilaPersonaBinding

class AdapterPersonas (var listaPersonas:List<Persona>): RecyclerView.Adapter<PersonaVH>() {
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
    }
}