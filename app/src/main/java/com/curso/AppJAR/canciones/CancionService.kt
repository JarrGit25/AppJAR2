package com.curso.AppJAR.canciones

import retrofit2.http.GET
import retrofit2.http.Query

interface CancionService {

    ///search/?media=music&term=jose antonio ramirez roman
    //@GET("/search/?media=music&term=jose antonio ramirez roman")
    //suspend se utiliza para marcar una función como "suspendida",
    // lo que significa que puede ejecutarse de forma asíncrona sin bloquear el hilo principal (como el UI thread).
    //Esta función puede pausar su ejecución y reanudarla más tarde sin bloquear el hilo actual."
    //suspend fun obtenerCanciones(): List<Cancion>

    // Debo crear una clase envoltorio (RespuestaCanciones) para reflejar la estructura real del JSON de iTunes
    @GET("search")
    suspend fun obtenerCanciones(
        @Query("media") media: String = "music",
        @Query("term") term: String
    ): RespuestaCanciones
}