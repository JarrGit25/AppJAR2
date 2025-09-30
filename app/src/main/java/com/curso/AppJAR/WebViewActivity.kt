package com.curso.AppJAR

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.curso.AppJAR.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    //val webAdf:String = "https://adf-formacion.es/"
    val rutaLocalWebAdf: String = "file:///android_asset/prueba.html"
    lateinit var binding: ActivityWebViewBinding // clase intermedia que se genera automaticamente y da acceso a las vistas del activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate((layoutInflater))
        setContentView(binding.root)

        //activo Javascript en el navegador
        binding.webView.settings.javaScriptEnabled = true
        // Si cargo una pagina sin el permiso de internet en la app, falla, Hay que declarar el permiso en el fichero Manifest
        //binding.webView.loadUrl(webAdf)// con esto cargamos la pagina web en webview)
        binding.webView.loadUrl(rutaLocalWebAdf)// con esto cargamos la pagina web en webview)

    }
}