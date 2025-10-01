package com.curso.AppJAR.receptor

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.curso.AppJAR.Constantes
import com.curso.AppJAR.alarma.GestorAlarma
import com.curso.AppJAR.notificaciones.Notificaciones

class FinServicioReceiver : BroadcastReceiver() {

    // el intent de esta funcion es el INtent del servicio
    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        Log.d(Constantes.ETIQUETA_LOG, "Servicio Finalizado")

        // inspecciono el INtent del servicio para ver el resultado del mismo (Numero aleatorio)
        val numAleatorio = intent.getIntExtra("NUM_ALEATORIO", -1)
        Log.d(Constantes.ETIQUETA_LOG, "Numero aleatorio = $numAleatorio")
        // Si num aleatorio es >= 60 --> " hay mas mensajes y lanzamos una notificacion
        // si no  --> no hago nada
        if (numAleatorio>=60) {
            Notificaciones.lanzarNotificacion(context)
            Log.d(Constantes.ETIQUETA_LOG, "El servicio nos da un numero mayor a 60")
        }
        // Reprogramo la Alarma
        GestorAlarma.programarAlarma(context)
        Log.d(Constantes.ETIQUETA_LOG, "Reprogramo la alarma")

        //desregistrar el receptor para que la proxima vez que acaba el servicio no salte de nuevo
        LocalBroadcastManager.getInstance(context).unregisterReceiver(this)
    }
}