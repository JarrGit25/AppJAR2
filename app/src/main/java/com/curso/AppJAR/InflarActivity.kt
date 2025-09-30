package com.curso.AppJAR

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

// Lleva asociados dos layouts xml "activity_inflar.xml" y "mensaje_salida.xml"

class InflarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inflar)
        // se puede comentar se usa para guardar en el LOG los elementos inflados
        mostrarLayout(findViewById<View>(R.id.principal_layout))
    }

    fun longitudTexto(nombre: String): String {
        var mensaje: String? = null
        mensaje = "El nombre tiene " + nombre.length + " letras"
        return mensaje
    }

    fun mostrarMensajeSalida(view: View) {
        val editText = findViewById<EditText>(R.id.editText)
        val nombre = editText.text.toString()

        Log.d(javaClass.canonicalName, "Ha introducido el nombre = $nombre")

        val mensaje: String = longitudTexto(nombre)

        val caja_resultado = findViewById<LinearLayout>(R.id.resultado)

        // SI TIENE ALGUN HIJO
        if (caja_resultado.childCount > 0)  //la lista ya ha sido inflada
        {
            val text = findViewById<TextView>(R.id.mensaje_salida)
            text.text = mensaje
        } else {
            // INFLA EL LAYOUT AÑADIENDO EL XML mensaje_salida.xml
            val layoutInflater = layoutInflater //o LayoutInflater.from(a)

            // añade el xml mensaje_salida dentro del Layout activity_inflar.xml
            // concretamente dentro del LinearLayout "resultado"
            val vista_inflada: View =
                layoutInflater.inflate(R.layout.mensaje_salida, caja_resultado)
            // ahora que ya existe pinta en la caja el texto
            val text = vista_inflada.findViewById<TextView>(R.id.mensaje_salida)
            text.text = mensaje
        }

        mostrarLayout(findViewById<View>(R.id.principal_layout))
    }

    // se puede comentar se usa para guardar en el LOG los elementos inflados
    private fun mostrarLayout(vista: View) {
        Log.d(Constantes.TAG_LOG, vista.javaClass.canonicalName)

        if (vista is ViewGroup) {
            val viewGroup = vista

            for (i in 0..<viewGroup.childCount) {
                val vistahija = viewGroup.getChildAt(i)
                mostrarLayout(vistahija)
            }
        }
    }

}