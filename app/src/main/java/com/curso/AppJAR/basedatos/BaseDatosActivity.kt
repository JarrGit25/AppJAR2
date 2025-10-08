package com.curso.AppJAR.basedatos

import android.content.DialogInterface
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.curso.AppJAR.Constantes
import com.curso.AppJAR.R
import com.curso.AppJAR.basedatos.adapter.AdapterPersonas
import com.curso.AppJAR.basedatos.entity.Persona
import com.curso.AppJAR.basedatos.viewmodel.PersonaViewModel
import com.curso.AppJAR.databinding.ActivityBaseDatosBinding
import com.google.android.material.snackbar.Snackbar

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

        // para poder borrar un elemento de la lista
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recview)

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

    }

    // llamamos al ViewModel desde la funcion insertarPersona
    fun insertarPersona(view: View) {
        personaViewModel.insertar(Persona(nombre="Andrés", edad=25))
        personaViewModel.contarPersonas()
    }

    fun alertaborrado(): Boolean {

        // prepara el AlertDialog
        var alerta = AlertDialog.Builder(this)
            .setTitle("Atención") // i18n
            .setMessage("¿Desea Eliminar el registro?") // i18n
            .setIcon(R.drawable.outline_chevron_forward_24)
            .setPositiveButton(R.string.boton_si){ dialogo, opcion ->
                this.finish();
            }
            .setNegativeButton(R.string.boton_no){ dialogo: DialogInterface, opcion: Int ->
                dialogo.dismiss()
            }
            .setNeutralButton (R.string.boton_neutro){ dialogo: DialogInterface, opcion: Int ->
                dialogo.cancel()
            }

        alerta.show() // Ahora muestra el AlertDialog

        return true
    }


    val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        override fun onMove(
            recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
        ): Boolean {
            return false // No necesitamos mover los elementos, solo manejar el deslizamiento
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val persona = this@BaseDatosActivity.adapterPersonas.listaPersonas[position] // Método que debes crear en tu adaptador


            //////////////////////////////////////////////
            // prepara el AlertDialog
            var alerta = AlertDialog.Builder(this@BaseDatosActivity)
                .setTitle("Atención") // i18n
                .setMessage("¿Desea Eliminar el registro?") // i18n
                .setIcon(R.drawable.outline_chevron_forward_24)
                .setPositiveButton(R.string.boton_si){ dialogo, opcion ->
                    // Aquí es donde eliminamos el ítem
                    personaViewModel.borrar(persona)

                    // Mostrar Snackbar para deshacer la eliminación
                    Snackbar.make(this@BaseDatosActivity.binding.recview, "Persona eliminada", Snackbar.LENGTH_LONG)
                        .setAction("Deshacer") {
                            // Si el usuario quiere deshacer, simplemente reinsertamos el ítem
                            personaViewModel.insertar(persona)
                        }
                        .show()
                }
                .setNegativeButton(R.string.boton_no){ dialogo: DialogInterface, opcion: Int ->
                    dialogo.dismiss()
                    // con esto repinto el recicler a lo bestia
                    adapterPersonas.notifyDataSetChanged()
                }
                .setNeutralButton (R.string.boton_neutro){ dialogo: DialogInterface, opcion: Int ->
                    dialogo.cancel()
                    // con esto repinto el recicler a lo bestia
                    adapterPersonas.notifyDataSetChanged()
                }

            alerta.show() // Ahora muestra el AlertDialog

            //////////////////////////////////////////////


            // Aquí es donde eliminamos el ítem
            //personaViewModel.borrar(persona)

            // Mostrar Snackbar para deshacer la eliminación
//            Snackbar.make(this@BaseDatosActivity.binding.recview, "Persona eliminada", Snackbar.LENGTH_LONG)
//                .setAction("Deshacer") {
//                    // Si el usuario quiere deshacer, simplemente reinsertamos el ítem
//                    personaViewModel.insertar(persona)
//                }
//                .show()
        }

        // para Borrar una fila con su registro del recicler
        // y de la base de datos
        // si se toca deslizando hacia la izquierda
        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float, // desplazamiento horizontal
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

            // Solo aplicar si se está deslizando hacia la izquierda
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && dX < 0) {
                val itemView = viewHolder.itemView
                val paint = Paint()
                paint.color = Color.RED

                // Dibuja el fondo rojo
                c.drawRect(
                    itemView.right.toFloat() + dX, // izquierda del fondo
                    itemView.top.toFloat(),
                    itemView.right.toFloat(),      // derecha del fondo
                    itemView.bottom.toFloat(),
                    paint
                )

                // Carga el icono
                val deleteIcon =
                    ContextCompat.getDrawable(recyclerView.context, R.drawable.ic_delete)
                val iconMargin = 32
                val iconSize = 64

                deleteIcon?.let {
                    val iconTop = itemView.top + (itemView.height - iconSize) / 2
                    val iconLeft = itemView.right - iconMargin - iconSize
                    val iconRight = itemView.right - iconMargin
                    val iconBottom = iconTop + iconSize

                    it.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                    it.draw(c)

                    // 3. Texto "Eliminar"
                    val text = "Eliminar"
                    val textPaint = Paint()
                    textPaint.color = Color.WHITE
                    textPaint.textSize = 40f
                    textPaint.isAntiAlias = true
                    textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)

                    // Calcular ancho del texto
                    val textWidth = textPaint.measureText(text)

                    // Dibujar texto a la izquierda del ícono
                    val textX = iconLeft - textWidth - 20f
                    val textY = itemView.top + itemView.height / 2f + 15f // Ajuste vertical

                    c.drawText(text, textX, textY, textPaint)
                }
            }
        }
    }

}