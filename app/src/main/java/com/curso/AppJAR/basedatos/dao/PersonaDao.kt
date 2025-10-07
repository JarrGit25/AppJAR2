package com.curso.AppJAR.basedatos.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.curso.AppJAR.basedatos.entity.Persona

/*
Describimpos las operaciones que se pueden realizar
en la base de datos con una persona

@Dao anotacion para decirle que se sepa que ahi van descritas las operaciones

@Insert anotacion para indicarle que es un tipo de operacion INSERT

@Delere anotacion para indicarle que es un tipo de operacion DELETE

Son funciones suspend por tanto deben ser suspend
 */
@Dao
interface PersonaDao {
    @Insert
    suspend fun insertar(persona: Persona)

    @Delete
    suspend fun borrar(persona: Persona)

    // androidx.room
    // tiene que ser de tipo LiveData
    @Query("SELECT * FROM personas ORDER BY nombre ASC")
    fun obtenerTodas():LiveData<List<Persona>>
}