package com.curso.AppJAR.basedatos.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.curso.AppJAR.basedatos.dao.PersonaDao
import com.curso.AppJAR.basedatos.entity.Persona

// las tablas que tengamos o entities
@Database(entities = [Persona::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personaDao(): PersonaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "personas_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}