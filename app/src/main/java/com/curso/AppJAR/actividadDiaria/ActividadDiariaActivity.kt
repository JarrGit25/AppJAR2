package com.curso.AppJAR.actividadDiaria

import android.Manifest
import android.app.AppOpsManager
import android.app.PendingIntent
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.curso.AppJAR.R
import java.util.concurrent.TimeUnit

class ActividadDiariaActivity : AppCompatActivity() {

    private lateinit var tvScreenTime: TextView
    private lateinit var tvUnlocks: TextView
    private lateinit var tvSteps: TextView
    private lateinit var tvSleep: TextView

    private val activityRecognitionClient by lazy {
        com.google.android.gms.location.ActivityRecognition.getClient(this)
    }

    private val PERMISO_RECONOCIMIENTO = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad_diaria)

        tvScreenTime = findViewById(R.id.tv_screen_time)
        tvUnlocks = findViewById(R.id.tv_unlocks)
        tvSteps = findViewById(R.id.tv_steps)
        tvSleep = findViewById(R.id.tv_sleep)

        if (!tienePermisoUsoApps()) {
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        } else {
            cargarUsoApps()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.ACTIVITY_RECOGNITION),
                    PERMISO_RECONOCIMIENTO
                )
            } else {
                iniciarDeteccionActividad()
            }
        } else {
            iniciarDeteccionActividad()
        }

    }

    private fun iniciarDeteccionActividad() {
        val intent = Intent(this, DeteccionActividadReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACTIVITY_RECOGNITION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        activityRecognitionClient.requestActivityUpdates(
            5000, // cada 5 segundos
            pendingIntent
        ).addOnSuccessListener {
            Log.d("TrackUno", "Detección de actividad iniciada")
        }.addOnFailureListener {
            Log.e("TrackUno", "Error al iniciar detección de actividad", it)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISO_RECONOCIMIENTO) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                iniciarDeteccionActividad()
            } else {
                Log.w("TrackUno", "Permiso de reconocimiento de actividad denegado")
            }
        }
    }

    private fun tienePermisoUsoApps(): Boolean {
        val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val modo = appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            android.os.Process.myUid(),
            packageName
        )
        return modo == AppOpsManager.MODE_ALLOWED
    }

    private fun cargarUsoApps() {
        val usageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val tiempoFin = System.currentTimeMillis()
        val tiempoInicio = tiempoFin - TimeUnit.DAYS.toMillis(1)

        val stats = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY, tiempoInicio, tiempoFin
        )

        var tiempoTotal = 0L
        stats?.forEach { uso ->
            tiempoTotal += uso.totalTimeInForeground
        }

        val minutosTotales = TimeUnit.MILLISECONDS.toMinutes(tiempoTotal)
        tvScreenTime.text = "Tiempo en pantalla: $minutosTotales minutos"

        tvUnlocks.text = "Desbloqueos: pendiente implementar"
        tvSteps.text = "Tiempo caminando: pendiente implementar"
        tvSleep.text = "Tiempo estimado de sueño: pendiente implementar"
    }
}
