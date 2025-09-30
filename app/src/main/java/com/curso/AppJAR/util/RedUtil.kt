package com.curso.AppJAR.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/*
Para comprobar el estado de la conexion a internet
* */
object RedUtil {

    // comprueba que haya algun tipo de red
    fun hayInternet (context: Context): Boolean {
        var hay = false

        // retorna si hay internet o no
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        hay = (cm.activeNetwork!=null)

        return hay
    }

    // comprueba que haya wifi
    fun hayWifi (context: Context): Boolean {
        var hayWifi = false

        // retorna si hay internet o no
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val caps = cm.getNetworkCapabilities(cm.activeNetwork)
        hayWifi = caps?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ?: false

        return hayWifi
    }
}