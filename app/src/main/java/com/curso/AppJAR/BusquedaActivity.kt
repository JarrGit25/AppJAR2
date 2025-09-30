package com.curso.AppJAR

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.curso.AppJAR.databinding.ActivityBusquedaBinding
import androidx.core.net.toUri

class BusquedaActivity : AppCompatActivity() {

    /**
     * VER
     * +
     * https://www.google.com/search?q=real+madrid
     *
     * -->
     *
     * ME abre el navegador web de mi dispositivo
     */

    lateinit var binding: ActivityBusquedaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusquedaBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun buscarEnGoogle(view: View) {
        //obtener el término de búsqueda introducido por el usuario
        val busqueda: String = binding.textoBusqueda.text.toString()
        //lanzar un intent para buscar en google
        Log.d(Constantes.TAG_LOG, "El usuario quiere buscar $busqueda")

        //https://www.google.com/search?q=real+madrid
        val url: String = "https://www.google.com/search?q=$busqueda"
        val web: Uri = url.toUri() //  Uri.parse(url) //para eliminar espacios, tildes, la url la formamos bien con este método
        val intentBusqueda = Intent(Intent.ACTION_VIEW, web) //INTENT IMPLÍCITO
        try {
            if (intentBusqueda.resolveActivity(packageManager) != null) {
                Log.d(Constantes.TAG_LOG, "El dispositivo puede navegar por internet")
                //startActivity(intentBusqueda)
                startActivity(Intent.createChooser(intentBusqueda,"ELIGE TU NAVEGADOR")) // permite elegir la aplicacion
            } else {
                Toast.makeText(this, "No se ha detectado un navegador", Toast.LENGTH_LONG).show()
            }
            /*   val intentTienda = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.android.chrome"))
               Log.d(Constantes.TAG_LOG, "Le invitamos a que instale Google Chrome de la tienda")
               if (intentTienda.resolveActivity(packageManager) != null) {
                   startActivity(intentTienda)
               } else {
                   Log.d(Constantes.TAG_LOG, "El dispositivo ni tiene navegador ni tiene Play Store")

               }
            */
        }
        catch(e: Throwable) {
            Log.e(Constantes.TAG_LOG,"excepcion No hay navegador", e)
        }


    }

}