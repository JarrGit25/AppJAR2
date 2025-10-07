package com.curso.AppJAR.basedatos.repository

import com.curso.AppJAR.basedatos.dao.PersonaDao
import com.curso.AppJAR.basedatos.entity.Persona

/*
    Esta clase actua como intermediaria entre la interfaz y la fuente de datos
 */

class Repositorio (private val personaDao: PersonaDao){

    val todasLasPersonaDao = personaDao.obtenerTodas()

    suspend fun insertar(persona: Persona)
    {
        personaDao.insertar(persona)
    }

    suspend fun borrar(persona: Persona)
    {
        personaDao.borrar(persona)
    }

}