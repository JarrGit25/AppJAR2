package com.curso.AppJAR.canciones

data class RespuestaCanciones(
    val resultCount: Int,
    val results: List<Cancion>
)