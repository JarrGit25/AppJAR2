package com.curso.AppJAR.lista

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.curso.AppJAR.Constantes
import com.curso.AppJAR.R
import com.curso.AppJAR.Usuario
import com.curso.AppJAR.databinding.ActivityListausuariosBinding
import kotlin.system.measureNanoTime

class ListaUsuariosActivity : AppCompatActivity() {

    var listaUsuarios = listOf<Usuario>(Usuario("Vale", 41, 'M', true, R.color.mirojo),
        Usuario("JuanMa", 45, 'M', true, R.color.miazul),
        Usuario("Cristina", 56, 'F', true, R.color.miverde),
        Usuario("Patri", 46, 'F', true, R.color.minaranja),
        Usuario("JoseAntonio", 53, 'M', true, R.color.mirojo),
        Usuario("Marcos", 31, 'M', true, R.color.miazul),
        Usuario("Jorge", 18, 'M', true, R.color.miactionbarcolor),
        Usuario("Jorge", 33, 'M', true, R.color.miverde))

    lateinit var binding: ActivityListausuariosBinding
    lateinit var adapter: UsuariosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityListausuariosBinding.inflate((layoutInflater))
        setContentView(binding.root)


        this.adapter = UsuariosAdapter(this.listaUsuarios)
        this.binding.recViewUsuarios.adapter = this.adapter
        // formato vertical ordenadcion normal empezando por el primer registro
        this.binding.recViewUsuarios.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false  )
        // formato vertical ordenacion inversa empezando por el ultimo registro
        //this.binding.recViewUsuarios.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, true  )
        // formto horizontal deslizable
        //this.binding.recViewUsuarios.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, true  )
        // formato grid a 2 columnas
        //this.binding.recViewUsuarios.layoutManager = GridLayoutManager(this, 2)

    }

    fun ordenarPorNombre(view: View) {
        //this.listaUsuarios.sortedBy { usuario -> usuario.nombre }

        var ns = measureNanoTime {  this.listaUsuarios = this.listaUsuarios.sortedBy { it.nombre }}
        Log.d(Constantes.TAG_LOG, "EN ORDENAR POR NOMBRE TARDA ${ns} nanosegundos")

        //this.listaUsuarios = this.listaUsuarios.sortedByDescending { it.nombre }//de mayor a menor
        this.adapter = UsuariosAdapter(this.listaUsuarios)
        this.binding.recViewUsuarios.adapter = this.adapter
    }
    fun ordenarPorEdad(view: View) {
        var ns1 = measureNanoTime { this.listaUsuarios = this.listaUsuarios.sortedWith { usuario0, usuario1 -> usuario0.edad - usuario1.edad } }
        var ns2 = measureNanoTime {  this.listaUsuarios = this.listaUsuarios.sortedByDescending { it.edad } }

        this.adapter = UsuariosAdapter(this.listaUsuarios)
        Log.d(Constantes.TAG_LOG, "EN ORDENAR CON WITH TARDA ${ns1} nanosegundos y con BY ${ns2}")

        this.binding.recViewUsuarios.adapter = this.adapter

    }

    fun ordenarPorNombreYEdad(view: View) {

        var ns = measureNanoTime {
            this.listaUsuarios = this.listaUsuarios.sortedWith(
                compareBy<Usuario> { it.nombre }.
                thenBy { it.edad }

                //.thenByDescending { it.edad }
            )}
        Log.d(Constantes.TAG_LOG, "EN ORDENAR POR NOMBRE y edad TARDA ${ns} nanosegundos")
        this.adapter = UsuariosAdapter(this.listaUsuarios)
        this.binding.recViewUsuarios.adapter = this.adapter
    }

}