package com.curso.AppJAR.basedatos.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.curso.AppJAR.basedatos.db.AppDatabase
import com.curso.AppJAR.basedatos.entity.Persona
import com.curso.AppJAR.basedatos.repository.Repositorio
import kotlinx.coroutines.launch

/*
    Esta clase nos servira para que aunque el movil se gire persiste los datos
    aqui estarán definidas las operaciones del negocio
    Calcular, guardar, obterer datos etc...

    le pasamos el contexto com parametro a la clase que es el contexto Application
 */

class PersonaViewModel(application: Application):AndroidViewModel(application)
{
        private val repository: Repositorio
        val personas: LiveData<List<Persona>>

        // hace de constructor
        // recupera una instancia de la base de Datos de Persona Dao y trae Todas las Personas
        init {
            val dao = AppDatabase.getDatabase(application).personaDao()
            repository = Repositorio(dao)
            personas = repository.todasLasPersonaDao
        }

        /*fun insertar(persona: Persona) = viewModelScope.launch {
            repository.insertar(persona)
        }*/

        // se ejecuta en el ambito de la corrutina de forma asincrona en segundo plano
        fun insertar(persona: Persona) {
            viewModelScope.launch {
                repository.insertar(persona)
            }
        }

        fun borrar(persona: Persona) {
            viewModelScope.launch {
                repository.borrar(persona)
            }
        }
}