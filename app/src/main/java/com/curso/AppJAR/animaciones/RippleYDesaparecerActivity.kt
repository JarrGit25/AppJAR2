package com.curso.AppJAR.animaciones

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.curso.AppJAR.R
import kotlin.math.hypot

class RippleYDesaparecerActivity : AppCompatActivity() {
    lateinit var imageView:ImageView
    lateinit var boton:Button
    lateinit var boton2:Button
    var imagenVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ripple_ydesaparecer)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        this.imageView = findViewById<ImageView>(R.id.imagenAnimada)
        this.boton = findViewById<Button>(R.id.botonRipple)
        this.boton2 = findViewById<Button>(R.id.botonRipple2)

        this.imageView.visibility = View.INVISIBLE

        this.boton.setOnClickListener {
            if (imagenVisible)
            {
                ocultarImagen()
            } else {
                mostrarImagen()
            }
            imagenVisible = !imagenVisible
        }

        this.boton2.setOnClickListener {
            //transito a TransitionActivity
            val intentTransicion = Intent(this, TransitionActivity::class.java)
            startActivity(intentTransicion, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            //programáticamente evitamos la transición definida en el tema
            //startActivity(intentTransicion, null)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add("Efecto Ocultar")
        menu.add("Efecto Mostrar")

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.title == "Efecto Ocultar")
        {
            ocultarImagen ()
        } else
        {
            mostrarImagen ()
        }
        return super.onOptionsItemSelected(item)
    }

    fun ocultarImagen ()
    {
        val cx = imageView.width / 2
        val cy = imageView.height / 2
        val initialRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()
        val anim = ViewAnimationUtils.createCircularReveal(imageView, cx, cy, initialRadius, 0f)
        anim.duration = 1000
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                imageView.visibility = View.INVISIBLE
            }
        })
        anim.start()
    }

    fun mostrarImagen ()
    {
        val cx = imageView.width / 2
        val cy = imageView.height / 2
        val finalRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()
        val anim = ViewAnimationUtils.createCircularReveal(imageView, cx, cy, 0f, finalRadius)
        imageView.visibility = View.VISIBLE
        anim.duration = 1000
        anim.start()
    }

}