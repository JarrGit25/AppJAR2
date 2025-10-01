package com.curso.AppJAR.servicios

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.curso.AppJAR.notificaciones.Notificaciones
import com.curso.AppJAR.receptor.FinServicioReceiver

class NumAleatorioService : Service() {

    // al iniciar el servicio cuando arranque
    var numeroAleatorio = 0

    //se ejecuta al iniciar el servicio
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //return super.onStartCommand(intent, flags, startId)

        //programamos la escucha del final del serivicio
        val localBroadcastManager = LocalBroadcastManager.getInstance(this)
        val finServicioReceiver = FinServicioReceiver()
        val intentFilter = IntentFilter("SERV_ALEATORIO_FINAL")
        //"nuestro receptor está pendiente de esa señal intent filter"
        localBroadcastManager.registerReceiver(finServicioReceiver, intentFilter)

        val notificacionSegundoPlano = Notificaciones.crearNotificacionSegundoPlano(this)
        startForeground(65, notificacionSegundoPlano)

        try{
            //simulamos que consumimos un API
            Thread.sleep(5000)
            numeroAleatorio = (Math.random()*100+1).toInt()

        }catch (e:Exception)
        {
            Log.e("MIAPP", e.message, e)
        }

        stopForeground(STOP_FOREGROUND_REMOVE)//elimina el servicio del primer plano (foregorund) y la notificación

        stopSelf()//detenemos el servicio lo paramos del tod o (si no, seguría en segundo plano)

        return START_NOT_STICKY//se ejecuta el servicio no se reinicia
    }

    override fun onBind(intent: Intent): IBinder {
        //es obligatorio implementarlo, pero no siempre necesitas programarlo
        TODO("Return the communication channel to the service.")
    }

    // se ejecuta al finalizar el servicio o se pare el sistema que lo puede hacer
    override fun onDestroy() {

        val intent_fin = Intent("SERV_ALEATORIO_FINAL")
        intent_fin.putExtra("NUM_ALEATORIO", this.numeroAleatorio)
        val localBroadcastManager = LocalBroadcastManager.getInstance(this)
        //lanzamos la señal, como diciendo que ha acabado nuestro servicio
        localBroadcastManager.sendBroadcast(intent_fin)

        super.onDestroy()
    }
}