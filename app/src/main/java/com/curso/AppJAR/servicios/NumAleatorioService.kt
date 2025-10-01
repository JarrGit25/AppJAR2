package com.curso.AppJAR.servicios

import android.app.Service
import android.content.Intent
import android.os.IBinder

class NumAleatorioService : Service() {

    // al iniciar el servicio cuando arranque
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    // se ejecuta al finalizar el servicio o se pare  el sistema que lo puede hacer
    override fun onDestroy() {
        super.onDestroy()
    }
}