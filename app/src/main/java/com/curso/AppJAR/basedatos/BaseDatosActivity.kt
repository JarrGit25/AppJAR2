package com.curso.AppJAR.basedatos

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.curso.AppJAR.Constantes
import com.curso.AppJAR.R
import com.curso.AppJAR.basedatos.adapter.AdapterPersonas
import com.curso.AppJAR.basedatos.entity.Persona
import com.curso.AppJAR.basedatos.viewmodel.PersonaViewModel
import com.curso.AppJAR.databinding.ActivityBaseDatosBinding

/*
1 ) //añadimos esta línea en plugins del gradle del proyecto manualmente
id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false

2) ahora en el del módulo, igual en plugins, sólo el id manualmente

id("com.google.devtools.ksp")

3 ) Ahora También como dependencia desde el buscador : Androidx.lifecycle  --> lifecycle-livedata  --> versión 2.9.3

4 ) Añadir como dependencia desde el buscador : androidx.room --> room-common --> 2.7.2

6 ) Añadir como dependencia desde el buscador : androidx.room --> room-compiler --> 2.7.2

    En esta una vez añadida cambiar dentro del fichero build.graddle en lugar de
    implementation(libs.androidx.room-compiler) poner -->  ksp (libs.androidx.room-compiler) y

7 ) Añadir como dependencia desde el buscador : androidx.room --> room-ktx --> 2.7.2

8) --- EMNPEZAR EL PROYECTO CARPETA APARTE Nuevo package --> basedatos

Nueva Actividad vacia BaseDatosActivity

Dentro del package otra carpeta para meter el adapter --> la llamamos adapter

Dentro del package otra carpeta para meter el entity --> la llamamos entity
	Dentro de esta una DataClass Persona

La estructura Quedaría : 	basedatos (carpeta)
							adapter (carpeta)
							    AdapterPersonas (clase de Kotlin)
							    PersonaVH (Clase de Kotlin)
                            dao (carpeta donde iran las operaciones)
                            		PersonaDao (Interfaz Kotlin donde iran descritas las operaciones)
                            db (carpeta)
                            		AppDatabase (clase de Kotlin)
                            entity (capeta)
                            		Persona (Data class de Kotlin)
                            repository (carpeta Package)
                                    Repositorio (Clase normal de kotlin )
                            viewmodel (carpeta package)
                                    PersonaViewModel (Clase normal de kotlin)
                            BaseDatosActivity.kt (Actividad)

  En la parte del Layout tenemos el layout de la actividad "activity_base_datos.xml
  y fila_persona.xml para el reciclerView
 */

class BaseDatosActivity : AppCompatActivity() {
    //para la lista de personas debe ser mutable para poderse modificar
    val personas:MutableList<Persona> = mutableListOf()

    lateinit var binding: ActivityBaseDatosBinding
    lateinit var adapterPersonas: AdapterPersonas
    // crea la instancia del ViewModel y la enlaza a la actividad
    // aqui es donde guardamos los datos de la pantalla
    val personaViewModel:PersonaViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseDatosBinding.inflate((layoutInflater))
        setContentView(binding.root)

        adapterPersonas = AdapterPersonas(personas)
        binding.recview.adapter = adapterPersonas
        binding.recview.layoutManager = LinearLayoutManager(this)

        //ligamos las actualizaciones autómaticas de la lista
        personaViewModel.personas.observe(this, Observer {
            personas ->
            //Log.d(Constantes.ETIQUETA_LOG, "Personas = $personas")
            personas?.let {
                Log.d(Constantes.ETIQUETA_LOG, "Personas (${personas.size}) = $personas")
                adapterPersonas.listaPersonas = it
                adapterPersonas.notifyDataSetChanged()
            }
        })

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // llamamos al ViewModel desde la funcion insertarPersona
    fun insertarPersona(view: View) {
        personaViewModel.insertar(Persona(nombre="Andrés", edad=25))
    }
}