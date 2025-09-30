package com.curso.AppJAR

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Usuario(
    var nombre:String,
    var edad:Int,
    var sexo: Char,
    var esMayorEdad: Boolean,
    var colorFavorito:Int
    ) : Parcelable
