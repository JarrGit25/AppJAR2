package com.curso.AppJAR.receptor

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.curso.AppJAR.Constantes
import com.curso.AppJAR.servicios.NumAleatorioService

class AlarmaReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(Constantes.ETIQUETA_LOG, "En AlarmaReceiver")
        //TODO lanzaremos un servicio en background

        // alarma receiver y inicio Movil tienen el mismo código
        val intentService = Intent(context, NumAleatorioService::class.java)
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService)
        }
        else {
            context.startService(intentService)
        }
        Log.d(Constantes.ETIQUETA_LOG, "Servicio Lanzado...")

    }
}