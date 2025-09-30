package com.curso.AppJAR.canciones

import com.curso.AppJAR.canciones.Cancion
import com.curso.AppJAR.canciones.CancionViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.curso.AppJAR.databinding.FilaCancionBinding

class CancionesAdapter(var listaCanciones: List<Cancion>):RecyclerView.Adapter<CancionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CancionViewHolder {
        val filaCancion = FilaCancionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CancionViewHolder(filaCancion)
    }

    override fun getItemCount(): Int {
        return this.listaCanciones.size
    }

    override fun onBindViewHolder(holder: CancionViewHolder, position: Int) {
        val cancionActual = this.listaCanciones[position]
        holder.rellenarFilaCancion(cancionActual)
    }
}