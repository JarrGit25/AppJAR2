package com.curso.AppJAR.perros

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.ArrayAdapter
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.curso.AppJAR.BienvenidaActivity
import com.curso.AppJAR.R
import com.curso.AppJAR.Constantes
import com.curso.AppJAR.databinding.ActivityPerrosBinding

class PerrosActivity : AppCompatActivity() {

    lateinit var binding: ActivityPerrosBinding
    var primeraVez = true
    var razaSeleccionada = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerrosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActivity()
    }

    private fun initActivity() {
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.razas,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) //estilo lista desplegada

        binding.spinnerRazas.adapter = adapter

        binding.spinnerRazas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                opcionTocada: View?,
                position: Int,
                id: Long
            ) {
                razaSeleccionada = (opcionTocada as TextView).text.toString()
                Log.d(Constantes.ETIQUETA_LOG, "RAZA seleccionada =  $razaSeleccionada")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d(Constantes.ETIQUETA_LOG, "OPCIÓN ELIMINADA DEL SPINNER")
            }
        }
    }

    fun buscarFotos(view: View) {
        Log.d(Constantes.ETIQUETA_LOG, "A buscar Fotos de  =  $razaSeleccionada")
        //TODO
        /*
        1 TRANSITAR A GALERIA PERROS ACTIVITY PASANDO EN EL INTENT LA RAZA
        2 OBTENGO EL LISTADO DE FOTOS DE ESA RAZA LLAMANDO A https://dog.ceo/api/breed/{RAZA}/images
        3 UNA VEZ OBTENIDO EL LISTADO LO MOSTRAREMOS CON FRAGMENTS
        * */

        // crea el Intent y Navega a la otra actividad GaleriaPerrosActivity con INTENT
        val intent = Intent(this, GaleriaPerrosActivity::class.java)

        // antes de lanzar la actividad guardo el texto que quiero pasar
        intent.putExtra("RAZA",razaSeleccionada)

        startActivity(intent) // Navega a la otra actividad
    }
}