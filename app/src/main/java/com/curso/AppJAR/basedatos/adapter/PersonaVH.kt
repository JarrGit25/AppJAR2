package com.curso.AppJAR.basedatos.adapter

import androidx.recyclerview.widget.RecyclerView
import com.curso.AppJAR.basedatos.entity.PersonaConDetalles
import com.curso.AppJAR.databinding.FilaPersonaBinding

class PersonaVH(val filaPersona: FilaPersonaBinding): RecyclerView.ViewHolder(filaPersona.root) {

    fun rellenarFila (personaConDetalles: PersonaConDetalles)
    {
        this.filaPersona.idpersona.text = personaConDetalles.persona.id.toString()
        this.filaPersona.nombre.text = personaConDetalles.persona.nombre
        this.filaPersona.edad.text = personaConDetalles.persona.edad.toString()
        this.filaPersona.nombreEmpleo.text = personaConDetalles.empleo?.nombre
        // this.itemView.tag = personaConDetalles.persona.id.toString() // otra idea para luego saber con gettag el id de la persona tocada
    }
}