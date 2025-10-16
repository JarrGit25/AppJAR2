package com.curso.AppJAR.basedatos.repository

import androidx.room.Transaction
import com.curso.AppJAR.basedatos.dao.CocheDao
import com.curso.AppJAR.basedatos.dao.EmpleoDao
import com.curso.AppJAR.basedatos.dao.PersonaDao
import com.curso.AppJAR.basedatos.entity.Coche
import com.curso.AppJAR.basedatos.entity.Empleo
import com.curso.AppJAR.basedatos.entity.Persona

/*
    Esta clase actua como intermediaria entre la interfaz y la fuente de datos
 */

class Repositorio (private val personaDao: PersonaDao, private val empleoDao: EmpleoDao, private val cocheDao: CocheDao) {

    val todasLasPersonas = personaDao.obtenerTodas()

    suspend fun insertar(persona:Persona):Long
    {
        return personaDao.insertar(persona)
    }

    suspend fun borrar(persona:Persona)
    {
        personaDao.borrar(persona)
    }

    suspend fun contarPersonas():Int
    {
        return personaDao.countPersonas()
    }

    @Transaction
    suspend fun insertarPersonaYEmpleo (persona: Persona, empleo: Empleo)
    {
        val idpersona = insertar(persona)
        empleo.personaId = idpersona
        empleoDao.insert(empleo)
    }

    suspend fun insertarCoche (coche: Coche)
    {
        cocheDao.insertCoche(coche)
    }

    suspend fun borrarCoche (coche: Coche)
    {
        cocheDao.deleteCoche(coche)
    }

    suspend fun leerCochesPersona(personaId:Int):List<Coche>
    {
        return cocheDao.getCochesDePersona(personaId.toLong())
    }

}