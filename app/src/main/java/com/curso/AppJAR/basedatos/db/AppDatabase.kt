package com.curso.AppJAR.basedatos.db


import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.curso.AppJAR.Constantes
import com.curso.AppJAR.basedatos.converter.Conversor
import com.curso.AppJAR.basedatos.dao.CocheDao
import com.curso.AppJAR.basedatos.dao.EmpleoDao
import com.curso.AppJAR.basedatos.dao.PersonaDao
import com.curso.AppJAR.basedatos.entity.Coche
import com.curso.AppJAR.basedatos.entity.Empleo
import com.curso.AppJAR.basedatos.entity.Persona
import java.util.concurrent.Executors

@Database(entities = [Persona::class, Empleo::class, Coche::class], version = 1)
@TypeConverters(Conversor::class)//para que gaurde las fechas como timestamp y los enumerados como String
abstract class AppDatabase : RoomDatabase() {
    abstract fun personaDao(): PersonaDao
    abstract fun empleoDao(): EmpleoDao
    abstract fun cocheDao(): CocheDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "personas_db"
                ).setQueryCallback(
                    { consulta, parametros ->
                        Log.d(Constantes.ETIQUETA_LOG, "Consulta $consulta Parámetros $parametros")
                    }, Executors.newSingleThreadExecutor()
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}