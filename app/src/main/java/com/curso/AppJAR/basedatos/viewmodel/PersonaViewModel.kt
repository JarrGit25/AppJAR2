package com.curso.AppJAR.basedatos.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.curso.AppJAR.Constantes
import com.curso.AppJAR.basedatos.UltimaOperacionBD
import com.curso.AppJAR.basedatos.db.AppDatabase
import com.curso.AppJAR.basedatos.entity.Empleo
import com.curso.AppJAR.basedatos.entity.Persona
import com.curso.AppJAR.basedatos.entity.PersonaConDetalles
import com.curso.AppJAR.basedatos.repository.Repositorio
import kotlinx.coroutines.launch

/*
    Esta clase nos servira para que aunque el movil se gire persiste los datos
    aqui estarán definidas las operaciones del negocio
    Calcular, guardar, obterer datos etc...

    le pasamos el contexto com parametro a la clase que es el contexto Application
 */

//fun insertar se ejecuta en el ambito de la corrutina de forma asincrona en segundo plano

class PersonaViewModel(application: Application):AndroidViewModel(application) {

    private val repository: Repositorio
    //val personas: LiveData<List<Persona>>
    val personasDetalles: LiveData<List<PersonaConDetalles>>
    lateinit var ultimaOperacionBD:UltimaOperacionBD
    var posicionAfectada:Int = -1

    init {

        val personaDao = AppDatabase.getDatabase(application).personaDao()
        val empleoDao = AppDatabase.getDatabase(application).empleoDao()
        val cocheDao = AppDatabase.getDatabase(application).cocheDao()
        repository = Repositorio(personaDao, empleoDao, cocheDao)
        personasDetalles = repository.todasLasPersonas
        //personas = repository.todasLasPersonas
        ultimaOperacionBD = UltimaOperacionBD.NINGUNA
    }

    /*fun insertar(persona: Persona) = viewModelScope.launch {
        repository.insertar(persona)
    }*/

    fun insertar(persona: Persona, pos:Int) {
        viewModelScope.launch {
            repository.insertar(persona)
            ultimaOperacionBD = UltimaOperacionBD.INSERTAR
            posicionAfectada = pos
        }


    }

    fun insertarPersonaYEmpleo(persona: Persona, pos:Int, empleo: Empleo) {
        viewModelScope.launch {
            repository.insertarPersonaYEmpleo(persona, empleo)
            ultimaOperacionBD = UltimaOperacionBD.INSERTAR
            posicionAfectada = pos
        }
    }

    fun borrar(persona: Persona, pos:Int) {
        viewModelScope.launch {
            repository.borrar(persona)
            ultimaOperacionBD = UltimaOperacionBD.BORRAR
            posicionAfectada = pos
        }
    }

    fun contarPersonas() {
        viewModelScope.launch {
            val nper = repository.contarPersonas()
            Log.d(Constantes.ETIQUETA_LOG, "numpersonas  $nper")
        }
    }
}