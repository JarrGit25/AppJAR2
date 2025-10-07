package com.curso.AppJAR.basedatos.adapter

import androidx.recyclerview.widget.RecyclerView
import com.curso.AppJAR.basedatos.entity.Persona
import com.curso.AppJAR.databinding.FilaPersonaBinding

class PersonaVH(val filaPersona: FilaPersonaBinding): RecyclerView.ViewHolder(filaPersona.root) {

    fun rellenarFila (persona: Persona)
    {
        this.filaPersona.idpersona.text = persona.id.toString()
        this.filaPersona.nombre.text = persona.nombre
        this.filaPersona.edad.text = persona.edad.toString()
    }
}