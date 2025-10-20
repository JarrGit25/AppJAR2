package com.curso.AppJAR.actividadDiaria

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.ActivityRecognitionResult
import com.google.android.gms.location.DetectedActivity

class DeteccionActividadReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (ActivityRecognitionResult.hasResult(intent)) {
            val result = ActivityRecognitionResult.extractResult(intent)
            val probableActivities = result!!.probableActivities

            for (activity in probableActivities) {
                Log.d("ActividadDetectada", "Tipo: ${tipoActividad(activity.type)}, Confianza: ${activity.confidence}")
                if (activity.type == DetectedActivity.WALKING || activity.type == DetectedActivity.ON_FOOT) {
                    // Aquí podrías guardar el tiempo o contar sesiones activas
                }
            }
        }
    }

    private fun tipoActividad(type: Int): String {
        return when (type) {
            DetectedActivity.IN_VEHICLE -> "En vehículo"
            DetectedActivity.ON_BICYCLE -> "En bicicleta"
            DetectedActivity.ON_FOOT -> "A pie"
            DetectedActivity.RUNNING -> "Corriendo"
            DetectedActivity.STILL -> "Quieto"
            DetectedActivity.TILTING -> "Inclinado"
            DetectedActivity.WALKING -> "Caminando"
            DetectedActivity.UNKNOWN -> "Desconocida"
            else -> "Otra"
        }
    }
}
