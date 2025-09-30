package com.curso.AppJAR.canciones

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import com.curso.AppJAR.canciones.CancionService

object CancionesRetrofitHelper {

    private const val URL_BASE = "https://itunes.apple.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL_BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getCancionServiceInstance (): CancionService
    {
        val cancionService =  retrofit.create(CancionService::class.java)
        return cancionService
    }
}