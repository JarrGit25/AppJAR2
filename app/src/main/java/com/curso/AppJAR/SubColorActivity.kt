package com.curso.AppJAR

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SubColorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_color)
       //finish()
    }

    fun colorSeleccionado(view: View) {
        val fondo = view.background
        val colorfondo = fondo as ColorDrawable // casting
        val color= colorfondo.color
        Log.d(Constantes.TAG_LOG,"COLOR SELECCIONADO =  $color")

        // guardar el color seleccionado como el resultado de la actividad
        // y finalizar la actividad
        val intent_resultado = Intent() // este intent repreenta el valor devuelto por la actividad (Memoria temporal)
        intent_resultado.putExtra("COLOR_ELEGIDO", color) // guardo el color dentro del Intent
        setResult(RESULT_OK, intent_resultado)
        finish()
    }

}