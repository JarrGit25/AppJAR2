package com.curso.AppJAR

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.curso.AppJAR.databinding.ActivityVideoBinding

class VideoActivity : AppCompatActivity() {
    // para crear el binding
    lateinit var binding: ActivityVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // Inicializa el binding
        binding = ActivityVideoBinding.inflate(layoutInflater)

        //setContentView(R.layout.activity_video)
        setContentView(binding.root)

        // oculta la appbar
        supportActionBar?.hide() // oculta la appbar

        // quita la barra de estado (hora , cobertura etc)
        window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_VISIBLE

        var rutaUriVideo = "android.resource://$packageName/${R.raw.video_inicio}"

        binding.videoView.setVideoURI(rutaUriVideo.toUri())
        // reproduce el video
        binding.videoView.start()
    }

    fun saltarPresentacion(view: View) {

    }

    fun noVolverAMostrar(view: View) {

    }
}