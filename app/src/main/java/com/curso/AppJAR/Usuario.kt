package com.curso.AppJAR

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Usuario(var nombre:String, var edad:Int, var sexo: Char, var esMayorEdad: Boolean, var colorFavorito: Int = 0, val uriFoto:String =""):Parcelable
{
    init {
        //se puede definir una lógica para cuando se crear el objeto
    }
}
