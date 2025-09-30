package com.curso.AppJAR

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.curso.AppJAR.databinding.ActivityBlackGameBinding

class BlackGameActivity : AppCompatActivity() {

    // para usar el binding
    lateinit var binding: ActivityBlackGameBinding
    var primeraVezCaja1: Boolean = true
    var primeraVezCaja2: Boolean = true
    var primeraVezCaja3: Boolean = true
    var primeraVezCaja4: Boolean = true

    var sumaClickCaja1: Int = 0
    var sumaClickCaja2: Int = 0
    var sumaClickCaja3: Int = 0
    var sumaClickCaja4: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlackGameBinding.inflate(layoutInflater) // para poder acceder a los IDs
        setContentView(binding.root)


        // Restauro el estado si es que hubiese algo guardado en el saco recogiendo los contadores de veces pulsada cada caja
        // si alguna tiene mayor a 0 quiere decir que fue pulsada y debe estar en negro
        if (savedInstanceState != null) {
            sumaClickCaja1 = savedInstanceState.getInt("sacoSumaClickCaja1",0)
            if (sumaClickCaja1>0){
                binding.caja1.setBackgroundColor(Color.BLACK)
            }
            sumaClickCaja2 = savedInstanceState.getInt("sacoSumaClickCaja2",0)
            if (sumaClickCaja2>0){
                binding.caja2.setBackgroundColor(Color.BLACK)
            }
            sumaClickCaja3 = savedInstanceState.getInt("sacoSumaClickCaja3",0)
            if (sumaClickCaja3>0){
                binding.caja3.setBackgroundColor(Color.BLACK)
            }
            sumaClickCaja4 = savedInstanceState.getInt("sacoSumaClickCaja4",0)
            if (sumaClickCaja4>0){
                binding.caja4.setBackgroundColor(Color.BLACK)
            }
        }
    }


    // Al momento de destruirse la actividad  por haber girado el movil onSaveInstanceState,
    // Guardo las veces que se han pulsado cada LinearLayout (cada cajita ) un saco que he llamado miBolsa
    // ahi guardo cada variable llamadas sacoSumaClickCaja1 , 2, 3, 4 respectivamente
    override fun onSaveInstanceState(miBolsa: Bundle) {
        super.onSaveInstanceState(miBolsa)
        miBolsa.putInt("sacoSumaClickCaja1", this.sumaClickCaja1)
        miBolsa.putInt("sacoSumaClickCaja2", this.sumaClickCaja2)
        miBolsa.putInt("sacoSumaClickCaja3", this.sumaClickCaja3)
        miBolsa.putInt("sacoSumaClickCaja4", this.sumaClickCaja4)
    }

    // esta funcion la llamo en cada caja de los linear layout para que realice el cambio a negro
    // y haga los conteos de las pulsaciones
    fun cajaSeleccionada(view: View) {

        val nombreCaja = resources.getResourceEntryName(view.id)

        when (view.id) {
            binding.caja1.id -> {
                if (primeraVezCaja1) {
                    binding.caja1.setBackgroundColor(Color.BLACK)
                    Log.d(Constantes.TAG_LOG, "Caja seleccionada: caja1")
                    primeraVezCaja1 = false
                }
                sumaClickCaja1 = sumaClickCaja1 + 1
                mensaje(sumaClickCaja1, nombreCaja)
            }

            binding.caja2.id -> {
                if (primeraVezCaja2) {
                    binding.caja2.setBackgroundColor(Color.BLACK)
                    Log.d(Constantes.TAG_LOG, "Caja seleccionada: caja2")
                    primeraVezCaja2 = false
                }
                sumaClickCaja2 = sumaClickCaja2 + 1
                mensaje(sumaClickCaja2, nombreCaja)
            }

            binding.caja3.id -> {
                if (primeraVezCaja3) {
                    binding.caja3.setBackgroundColor(Color.BLACK)
                    Log.d(Constantes.TAG_LOG, "Caja seleccionada: caja3")
                    primeraVezCaja3 = false
                }
                sumaClickCaja3 = sumaClickCaja3 + 1
                mensaje(sumaClickCaja3, nombreCaja)
            }

            binding.caja4.id -> {
                if (primeraVezCaja4) {
                    binding.caja4.setBackgroundColor(Color.BLACK)
                    Log.d(Constantes.TAG_LOG, "Caja seleccionada: caja4")
                    primeraVezCaja4 = false
                }
                sumaClickCaja4 = sumaClickCaja4 + 1
                mensaje(sumaClickCaja4, nombreCaja)
            }

            else -> {
                Log.w(Constantes.TAG_LOG, "Vista desconocida: ${view.id}")
            }
        }
    }

    // funcion para mostra el mensaje cuando se pulsa una caja o queremos finalizar la actividad cuando esten todas en negro.
    // El ultima caja cambiada a negro he decidido que muestre un mensaje distinto "SAYONARA BABY" para que quede mejor
    fun mensaje(vecesPulsada: Int, nombreCaja: String) {

        // si todas las cajas han sido pulsadas más de una vez, terminamos la actividad
        if (sumaClickCaja1>0 && sumaClickCaja2>0 && sumaClickCaja3>0 && sumaClickCaja4>0)
        {
            Toast.makeText(this, "SAYONARA BABY", Toast.LENGTH_SHORT).show()
            finish()
        }
        else {
            Toast.makeText(this, "$nombreCaja, Pulsada: ${vecesPulsada.toString()} veces. \nSolo cambia Negro 1ª vez", Toast.LENGTH_SHORT).show()
        }
    }

}