package com.curso.AppJAR.notificaciones

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.curso.AppJAR.MainMenuActivity
import com.curso.AppJAR.R
import android.net.Uri
import android.media.AudioAttributes
import android.util.Log
import androidx.annotation.RequiresApi
import com.curso.AppJAR.Constantes

object Notificaciones {

    // dos atributos necesarios para definir al canal
    val NOTIFICATION_CHANNEL_ID = "UNO"
    val NOTIFICATION_CHANNEL_NAME = "CANAL_ADF"

    // CON estas anotaciones puedo usar cosas de la version indicada dentro de la funcion
    // ademas con Requires valida que la funcion llamante se haga
    // POr contra , con Target no valida que la funcion llamante gestione/asegure la version correcta y deja llamar sin comprobarlo
    // con TargetApi indicamos que a partir de esa version
    //@TargetApi(Build.VERSION_CODES.O) // si la version es OREO
    @RequiresApi(Build.VERSION_CODES.O)
    private fun crearCanalNotificacion ( context: Context
    ): NotificationChannel?
    {
        var notificationChannel : NotificationChannel? = null


        notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT )
        notificationChannel.enableLights(true)
        notificationChannel.enableVibration(true)
        //vibración patron suena 500 ms, no vibra otros 500 ms
        notificationChannel.vibrationPattern = longArrayOf(
            500,
            500,
            500,
            500,
            500,
            500,
        )
        notificationChannel.lightColor = context.applicationContext.getColor(R.color.mirojo)
        //sonido de  la notificación si el api es inferior a la 8, hay que setear el sonido aparte
        //si es igual o superior, la notificación "hereda" el sonido del canal asociado
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build()

        notificationChannel.setSound(
            Uri.parse("android.resource://" + context.packageName + "/" + R.raw.sonido_noti),
            audioAttributes
        )

        notificationChannel.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC

        return notificationChannel
    }

    // recibe el contexto
    fun lanzarNotificacion(context:Context) {

        // a traves del contexto accedo al servicio de NOTIFICACIONES del sistema
        Log.d(Constantes.ETIQUETA_LOG, "Lanzando notificación ...")
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //creo el canal
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            var notificationChannel = crearCanalNotificacion(context)
            notificationManager.createNotificationChannel(notificationChannel!!)
        }

        //CREAMOS LA NOTIFICACIÓN
        var nb = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(R.drawable.outline_notifications_active_24)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.emoticono_risa))
            .setContentTitle("BUENOS DÍAS")
            .setSubText("aviso")
            .setContentText("Vamos a entrar en ADF")
            .setAutoCancel(true)//es para que cuando toque la noti, desaparezca
            .setDefaults(Notification.DEFAULT_ALL)

        // lanzamos un intent para llamar a otra actividad
        val intentDestino = Intent(context, MainMenuActivity::class.java)
        // que el intent se lance solo una vez y no se pueda modificar
        // permite lanzar el intent como si estuviese dentro de mi app
        // y este seria un intent securizado
        val pendingIntent = PendingIntent.getActivity(context, 100,
            intentDestino,PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)

        nb.setContentIntent(pendingIntent) // asocio el intent a la notificacion

        // si estoy en api anterior, debo setear el sonido fuera porque o hay canal donde pasarlo
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            nb.setSound(Uri.parse("android.resource://" + context.packageName + "/" + R.raw.sonido_noti))
        }

        val notificacion = nb.build() // devuelve la notificacion ya construida

        // lanza la notificacion necesita añadir permisos POST_NOTIFICATIONS
        notificationManager.notify(500, notificacion)
    }

}