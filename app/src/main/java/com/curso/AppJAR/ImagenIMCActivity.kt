package com.curso.AppJAR

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ImagenIMCActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // obtengo el valor que pasamos desde la otra actividad
        val estado = intent.getStringExtra("param_estado")
        Log.d("IMC", "Estado: $estado")

        enableEdgeToEdge()
        setContentView(R.layout.activity_imagen_imcactivity)

        // El estado podria ser nulo ,poniendo estado !! control este nulo
        cambiarTextoEImagen(estado!!)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    fun cambiarTextoEImagen (estado:String)
    {
        //crea un objeto de ImageView por el ID
        val ImageView:ImageView = findViewById<ImageView>(R.id.fotoIMC)

        // establece una imagen por defecto de la carpeta drawable de Resources
        when (estado) {
            "FAMÉLICO" -> ImageView.setImageResource(R.drawable.imc_famelico)
            "DESNUTRIDO" -> ImageView.setImageResource(R.drawable.imc_desnutrido)
            "DELGADO" -> ImageView.setImageResource(R.drawable.imc_delgado)
            "IDEAL" -> ImageView.setImageResource(R.drawable.imc_ideal)
            "SOBREPESO" -> ImageView.setImageResource(R.drawable.imc_sobrepeso)
            "OBESO" -> ImageView.setImageResource(R.drawable.imc_obeso)
            "COLOSAL" -> ImageView.setImageResource(R.drawable.imc_colosal)
            else -> ImageView.setImageResource(R.drawable.ic_launcher_background)
        }

        val txtresultado = findViewById<TextView>(R.id.leyenda) // Trae la caja de texto

        //cambia el valor de la caja de texto
        txtresultado.text = "ESTAS $estado"

    }

}