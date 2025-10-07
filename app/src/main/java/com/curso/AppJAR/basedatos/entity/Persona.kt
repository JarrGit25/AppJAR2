package com.curso.AppJAR.basedatos.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


// Anotaciones para generar la tabla
// @PrimaryKey(autoGenerate = true) para generar la primary key de forma automatica
@Entity(tableName = "personas")
data class Persona(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val nombre:String,
    val edad:Int
)
