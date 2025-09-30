package com.curso.AppJAR

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.curso.AppJAR.databinding.ActivitySportsNewsBinding
import com.curso.AppJAR.databinding.ActivityWebViewBinding

class SportsNewsActivity : AppCompatActivity() {

    // URLs de las páginas web
    //        val web1 = "https://as.com/"
    //        val web2 = "https://www.marca.com/"
    //        val web3 = "https://www.mundodeportivo.com/"
    //        val web4 = "https://www.sport.es/es/"

    // Lista de URLs
    val webUrls = listOf("https://as.com/", "https://www.marca.com/", "https://www.mundodeportivo.com/", "https://www.sport.es/es/")

    lateinit var binding: ActivitySportsNewsBinding // clase intermedia que se genera automaticamente y da acceso a las vistas del activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySportsNewsBinding.inflate((layoutInflater))
        setContentView(binding.root)

        // Si cargo una pagina sin el permiso de internet en la app, falla, Hay que declarar el permiso en el fichero AndroidManifest.xml
        // después de la primera liea de manifest hay que poner esto:
        // <uses-permission android:name="android.permission.INTERNET" />

        // Lista de WebViews
        val webViews = listOf(binding.webview1, binding.webview2, binding.webview3, binding.webview4)

        // Recorro las listas al mismo tiempo activando el Javascrip y cargando la url
        for (i in webViews.indices) {
            webViews[i].settings.javaScriptEnabled = true  // Habilita JavaScript en el WebView
            webViews[i].loadUrl(webUrls[i])  // Carga la URL correspondiente en el WebView
        }

    }


    fun seleccionaWeb(view : View) {
        when (view) {
            binding.button1 -> {
                Log.d(Constantes.TAG_LOG, "Pulsado Botón $view.id Navegar a: $webUrls[0]")
                abrirWeb(webUrls[0])
            }
            binding.button2 -> {
                Log.d(Constantes.TAG_LOG, "Pulsado Botón $view.id Navegar a: $webUrls[1]")
                abrirWeb(webUrls[1])
            }
            binding.button3 -> {
                Log.d(Constantes.TAG_LOG, "Pulsado Botón $view.id Navegar a: $webUrls[2]")
                abrirWeb(webUrls[2])
            }
            binding.button4 -> {
                Log.d(Constantes.TAG_LOG, "Pulsado Botón $view.id Navegar a: $webUrls[3]")
                abrirWeb(webUrls[3])
            }
        }
    }

    fun abrirWeb(sitioWeb : String) {
        val web: Uri = sitioWeb.toUri() //  Uri.parse(url) //para eliminar espacios, tildes, la url la formamos bien con este método
        val intentBusqueda = Intent(Intent.ACTION_VIEW, web) //INTENT
        try {
            if (intentBusqueda.resolveActivity(packageManager) != null) {
                Log.d(Constantes.TAG_LOG, "El dispositivo puede navegar por internet")
                //startActivity(intentBusqueda)
                startActivity(Intent.createChooser(intentBusqueda,"ELIGE TU NAVEGADOR")) // permite elegir la aplicacion
            } else {
                Toast.makeText(this, "No se ha detectado un navegador", Toast.LENGTH_LONG).show()
            }

        }
        catch(e: Throwable) {
            Log.e(Constantes.TAG_LOG,"excepcion No hay navegador", e)
        }


    }

}