package com.curso.AppJAR.receptor

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.curso.AppJAR.Constantes
import com.curso.AppJAR.MainMenuActivity
import com.curso.AppJAR.notificaciones.Notificaciones

class inicioMovilReceiver : BroadcastReceiver() {


    // necesita un permiso
    // permite que nuestra app este informada del inicio del dispositivo
    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.

        Log.d(Constantes.ETIQUETA_LOG, "En InicioMovil receiver")
        try {
            // Al reiniciar el Telefono lanza la notificacion
            Notificaciones.lanzarNotificacion(context)
        }catch (e:Exception)
        {
            Log.e(Constantes.ETIQUETA_LOG, "errro al lanzar noti ", e)
        }

    }
}