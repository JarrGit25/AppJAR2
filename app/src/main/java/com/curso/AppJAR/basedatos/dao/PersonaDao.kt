package com.curso.AppJAR.basedatos.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.curso.AppJAR.basedatos.entity.Persona
import com.curso.AppJAR.basedatos.entity.PersonaConDetalles

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
    suspend fun insertar(persona: Persona):Long

    @Delete
    suspend fun borrar(persona: Persona)

    // androidx.room
    // tiene que ser de tipo LiveData
    // TODO dato curioso de funcionamineto: cuando
    // se ejecuta un método del dao, se ejecutan
    // automáticamente los métodos que devuelven LIVEDATA
    // y su vez, se propagan los cambios a los suscriptores
    // de ese livedata

    //
    //@Query("SELECT * FROM personas WHERE id=1")
    @Query("SELECT * FROM personas ORDER BY id ASC")
    fun obtenerTodas():LiveData<List<PersonaConDetalles>>
    //TODO suspend probar

    @Query("SELECT COUNT(*) FROM personas")
    suspend fun countPersonas(): Int
}