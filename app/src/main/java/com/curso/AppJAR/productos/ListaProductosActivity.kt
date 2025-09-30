package com.curso.AppJAR.productos

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.curso.AppJAR.Constantes
import com.curso.AppJAR.databinding.ActivityListaProductosBinding
import com.curso.AppJAR.perros.GaleriaPerrosActivity
import com.curso.AppJAR.util.RedUtil
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.math.roundToInt
import kotlin.system.measureNanoTime
import kotlin.toString

/*
PASOS PARA CREAR UN RECYCLERVIEW (MOSTRAR UNA COLECCIÓN/LISTA/TABLA)

-- fase estática/compilación
1) DEFINIR EL RECYCLERVIEW EN EL XML
2) CREAR EL LAYOUT/FILA ITEM - ASPECTO
3) CREAR EL VIEWHOLDER
4) CREAR EL ADAPTER
-- fase dinámica/ejecución
5) OBTENER DATOS (RETROFIT HTTP https://my-json-server.typicode.com/miseon920/json-api/products)
6) INSTANCIAR EL ADAPTER PASÁNDOLE LOS DATOS DEL PUNTO 5
7) ASOCIO EL ADAPTER AL RECYCLER
8) DEFINIMOS UN LAYOUT MANAGER PARA EL RECYCLER



 */

class ListaProductosActivity : AppCompatActivity() {

    lateinit var listaProductos:  List<Producto>
    lateinit var binding: ActivityListaProductosBinding
    lateinit var adapter: ProductosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaProductosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //preparo RetroFit




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
                val productoService = ProductosRetrofitHelper.getProductoServiceInstance()
                Log.d(Constantes.ETIQUETA_LOG, "LANZNADO PETICIÓN HTTP 1")
                try{
                    listaProductos = productoService.obtenerProductos()

                    }catch (ex: Exception){
                    //sección para caídas
                    Log.e(Constantes.ETIQUETA_LOG, "ERROR AL CONECTARNOS AL API DE PRODUCTOS", ex)
                    Toast.makeText(this@ListaProductosActivity, "FALLO AL OBTENER LOS PRODUCTOS", Toast.LENGTH_LONG).show()
                }

                Log.d(Constantes.ETIQUETA_LOG, "RESPUESTA RX ...")
                //ocultar progress bar
                this@ListaProductosActivity.binding.barraProgreso.visibility= View.GONE
                listaProductos.forEach { Log.d(Constantes.ETIQUETA_LOG, it.toString()) }
                mostrarListaProductos (listaProductos)
                dibujarSlider()

            }

        }else
        {
            val noti = Toast.makeText(this, "SIN CONEXIÓN A INTERNET", Toast.LENGTH_LONG)
            noti.show()
        }
        Log.d(Constantes.ETIQUETA_LOG, "LANZNADO PETICIÓN HTTP 2")

    }

    private fun dibujarSlider() {


        //obtenemos el producto más caro
        val productoMasCaro = this.listaProductos.maxBy { p:Producto -> p.price.toFloat() }
        val productoMasEconomico = this.listaProductos.minBy { p : Producto -> p.price.toFloat() }
        var precioMedio:Double = 0.0
        val tlambdas =  measureNanoTime {
            precioMedio = this.listaProductos.map { p->p.price.toFloat() }.average()

        }

        var tforClasico = measureNanoTime {
            var sumaPrecio:Float = 0f
            for (p in listaProductos.indices)
            {
                sumaPrecio = sumaPrecio + listaProductos[p].price.toFloat()
            }
            val media = sumaPrecio/listaProductos.size

        }
        Log.d(Constantes.ETIQUETA_LOG, "Tiempo lambdas ${tlambdas} vs Tiempo clásico ${tforClasico}")

        this.binding.precioMasCaro.text = productoMasCaro.price
        this.binding.precioMasBarato.text = productoMasEconomico.price
        this.binding.precioMedio.text = precioMedio.toString()

        //INiciamos el Slider
        this.binding.sliderPrecio.visibility = View.VISIBLE
        this.binding.sliderPrecio.valueFrom = productoMasEconomico.price.toFloat()
        this.binding.sliderPrecio.valueTo = productoMasCaro.price.toFloat()
        this.binding.sliderPrecio.value = productoMasCaro.price.toFloat()

        this.binding.sliderPrecio.setLabelFormatter { "${it.roundToInt()} precio máx" }

        this.binding.sliderPrecio.addOnChangeListener { slider, value, fromUser ->

            Log.d(Constantes.ETIQUETA_LOG, "Valor actual ${value} del usuario ${fromUser}")
            var listaProductosFiltrados = ArrayList<Producto>()
            //this.listaProductos.filter { producto -> if (producto.price.toFloat()<=value) {true} else {false} }.toCollection(listaProductosFiltrados)
            var lpf = this.listaProductos.filter { producto -> if (producto.price.toFloat()<=value) {true} else {false} }
            //this.adapter.listaProductos = listaProductosFiltrados
            this.adapter.listaProductos = lpf
            this.adapter.notifyDataSetChanged()//"Emite una señal y RecyclerView se repinta"
        }

    }

    private fun mostrarListaProductos(listaProductos: List<Producto>) {
        this.adapter = ProductosAdapter(listaProductos)
        binding.recViewProductos.adapter = this.adapter
        binding.recViewProductos.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false )
    }
}