package com.curso.AppJAR

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun saludar(view: View) {
        //TODO poner en la caja de texto un Hola Mundo
        Log.d("ETIQUETA", "Botón Saludar Pulsado")
    }
    fun obtenerVersion(view: View) {
        //TODO poner en la caja de texto la versión de Android del dispositivo
        Log.d("ETIQUETA", "Botón Versión Pulsado")
    }
}