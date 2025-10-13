package com.curso.AppJAR.realtimedatabase

data class Cliente(
    val edad: Long,
    val localidad:String,
    val nombre:String,
    val email:String,
    var clave: String="")
