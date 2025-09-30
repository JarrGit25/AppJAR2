package com.curso.AppJAR.canciones

data class Cancion (
    val artistName: String,
    val trackName: String,
    val primaryGenreName: String?,
    val artworkUrl60: String?,
    val previewUrl: String?
)