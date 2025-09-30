package com.curso.AppJAR.productos

import retrofit2.http.GET

interface ProductoService {

    @GET("miseon920/json-api/products")
    //suspend se utiliza para marcar una función como "suspendida",
    // lo que significa que puede ejecutarse de forma asíncrona sin bloquear el hilo principal (como el UI thread).
    //Esta función puede pausar su ejecución y reanudarla más tarde sin bloquear el hilo actual."
    suspend fun obtenerProductos(): List<Producto>
}