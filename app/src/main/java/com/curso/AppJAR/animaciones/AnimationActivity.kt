package com.curso.AppJAR.animaciones

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.curso.AppJAR.Constantes
import com.curso.AppJAR.R


//para las animaciones, la clase debe implementar la interfaz AnimatorListener
class AnimationActivity : AppCompatActivity(), AnimationListener {

    lateinit var imagen1:ImageView
    lateinit var imagen2:ImageView
    lateinit var layoutPadre:LinearLayout
    lateinit var animacion:Animation
    var pararAnimacion = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)

        this.imagen1 = findViewById<ImageView>(R.id.imagen1anim)
        this.imagen2 = findViewById<ImageView>(R.id.imagen2anim)
        this.layoutPadre = findViewById<LinearLayout>(R.id.layout_padre)

        this.imagen1.setImageResource(R.drawable.imc_colosal)
        this.imagen2.setImageResource(R.drawable.imc_colosal)

        // ligamos la animacion
        this.animacion = AnimationUtils.loadAnimation(this, R.anim.animacion)
        this.animacion.reset()
        this.layoutPadre.startAnimation(this.animacion)

        animacion.setAnimationListener(this)
    }

    override fun onAnimationStart(animation: Animation?) {
        Log.d(Constantes.ETIQUETA_LOG, "onAnimationStart")
    }

    //cuando finaliza la animacion se actualizan las imagenes y se relanza
    override fun onAnimationEnd(animation: Animation?) {
        Log.d(Constantes.ETIQUETA_LOG, "onAnimationEnd")
        if(!pararAnimacion){
            this.imagen1.setImageResource(R.mipmap.ic_launcher)
            this.imagen2.setImageResource(R.mipmap.ic_launcher_round)

            this.layoutPadre.startAnimation(animation)
        }

    }

    override fun onAnimationRepeat(animation: Animation?) {
        Log.d(Constantes.ETIQUETA_LOG, "onAnimationRepeat")
    }

    fun toque(view: View){
        Log.d(Constantes.ETIQUETA_LOG, "imagen pulsada ... detengo la animación")
        this.animacion.cancel()
        this.pararAnimacion = true
    }


}