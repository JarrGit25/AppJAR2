package com.curso.AppJAR

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.curso.AppJAR.databinding.ActivityCompartirTextoBinding

class CompartirTextoActivity : AppCompatActivity() {

    lateinit var binding: ActivityCompartirTextoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCompartirTextoBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    // creo Intent para enviar texto
    fun intentCompartir(view: View) {

        //obtener el término de búsqueda introducido por el usuario
        val busqueda: String = binding.cajaCompartir.text.toString()
        //lanzar un intent implicito para compartir en alguna de las aplicaciones disponibles


        val intentCompartirtexto = Intent(Intent.ACTION_SEND) // ENVIAR
        intentCompartirtexto.type = "text/plain"  // TIPO MIME de que tipo es la información  para que lo abra la extension oportuna
        // este sera el texto que compartimos
        intentCompartirtexto.putExtra(Intent.EXTRA_TEXT, busqueda)
        // createChooser da a elegir entre las apps disponibles en el sistema
        //este será el titulo de la ventana que aparecera al intentar compartir.-
        startActivity(Intent.createChooser(intentCompartirtexto, "Elige APP para Compartir texto:" ))
    }
}