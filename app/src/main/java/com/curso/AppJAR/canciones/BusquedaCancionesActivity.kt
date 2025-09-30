package com.curso.AppJAR.canciones

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.curso.AppJAR.databinding.ActivityBusquedaCancionesBinding
import com.curso.AppJAR.Constantes
import com.curso.AppJAR.util.RedUtil
import kotlinx.coroutines.launch

/*
PASOS PARA CREAR UN RECYCLERVIEW (MOSTRAR UNA COLECCIÓN/LISTA/TABLA)

-- fase estática/compilación
1) DEFINIR EL RECYCLERVIEW EN EL XML  hecho.
2) CREAR EL LAYOUT/FILA ITEM - ASPECTO hecho.
3) CREAR EL VIEWHOLDER
4) CREAR EL ADAPTER
-- fase dinámica/ejecución
5) OBTENER DATOS (RETROFIT HTTP https://my-json-server.typicode.com/miseon920/json-api/products)
6) INSTANCIAR EL ADAPTER PASÁNDOLE LOS DATOS DEL PUNTO 5
7) ASOCIO EL ADAPTER AL RECYCLER
8) DEFINIMOS UN LAYOUT MANAGER PARA EL RECYCLER
 */

class BusquedaCancionesActivity : AppCompatActivity() {

    //artistName
    //trackName
    //primaryGenreName
    //artworkUrl60
    //previewUrl

    lateinit var binding: ActivityBusquedaCancionesBinding

    lateinit var listaCanciones:  List<Cancion>
    lateinit var adapter: CancionesAdapter


    private fun rellenarListaCanciones(cadena: String?): Unit {
        Log.d(Constantes.ETIQUETA_LOG, "Mostrando Canciones del artista:  $cadena")

        if (RedUtil.hayInternet(this))
        {
            //el bloque que va dentro de este métod o, se ejecuta en un segundo plano (proceso a parte)
            Log.d(Constantes.ETIQUETA_LOG, "Hay internet, vamos a pedir ")
            //val haywifi = RedUtil.hayWifi(this)
            // Log.d(Constantes.ETIQUETA_LOG, "Es tipo wifi = ${haywifi} ")
            lifecycleScope.launch{
                //this en este función NO es Activity , es la propia corrutina
                //si necesito acceder al Contexto de la actvidad dentro
                //de la corrutina, debo usar this@ListaProductosActivity
                //val haywifi = RedUtil.hayWifi(this@ListaProductosActivity)
                // Log.d(Constantes.ETIQUETA_LOG, "Es tipo wifi = ${haywifi} ")
                val cancionService = CancionesRetrofitHelper.getCancionServiceInstance()
                Log.d(Constantes.ETIQUETA_LOG, "LANZNADO PETICIÓN HTTP 1")
                try{
                    //val respuesta = cancionService.obtenerCanciones() // devuelvo RespuestaCanciones
                    val respuesta = cancionService.obtenerCanciones(term = cadena.toString())
                    listaCanciones = respuesta.results
                }catch (ex: Exception){
                    //sección para caídas
                    Log.e(Constantes.ETIQUETA_LOG, "ERROR AL CONECTARNOS AL API de ITUNES", ex)
                    Toast.makeText(this@BusquedaCancionesActivity, "FALLO AL OBTENER LAS CANCIONES", Toast.LENGTH_LONG).show()
                }

                Log.d(Constantes.ETIQUETA_LOG, "RESPUESTA RX ...")
                //ocultar progress bar
                this@BusquedaCancionesActivity.binding.barraProgreso.visibility= View.GONE

                listaCanciones.forEach { Log.d(Constantes.ETIQUETA_LOG, it.toString()) }

                mostrarListaCanciones (listaCanciones)
            }

        }else
        {
            val noti = Toast.makeText(this, "SIN CONEXIÓN A INTERNET", Toast.LENGTH_LONG)
            noti.show()
        }
        Log.d(Constantes.ETIQUETA_LOG, "LANZNADO PETICIÓN HTTP 2")

    }

    private fun mostrarListaCanciones(listaProductos: List<Cancion>) {
        this.adapter = CancionesAdapter(listaCanciones)
        binding.recViewProductos.adapter = this.adapter
        binding.recViewProductos.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityBusquedaCancionesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        PlayerManager.initializePlayer(this)  // Inicializa el player

        // this.binding.cajaBusqueda.requestFocus()
        this.binding.cajaBusqueda.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(Constantes.ETIQUETA_LOG, "Buscando $query")

                //val textoBusqueda: String = query.toString()
                rellenarListaCanciones(query.toString())

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.isEmpty() ?: false)
                {
                    Log.d(Constantes.ETIQUETA_LOG, "CAJA LIMPIA")
                }
                else {
                    Log.d(Constantes.ETIQUETA_LOG, "Cambio $newText")
                }

                return true
            }

        })



    }

    // se llama antes de que la actividad se destruya para liberar recursos
    override fun onDestroy() {
        super.onDestroy()
        PlayerManager.release()
    }



    /** override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_canciones, menu)
    val menuItemBusqueda = menu?.findItem(R.id.menuBusqueda)
    val searchView = menuItemBusqueda?.actionView as SearchView
    searchView.queryHint = "Intro nombre o canción"
    return true
    }*/
}


