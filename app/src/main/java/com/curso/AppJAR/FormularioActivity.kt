package com.curso.AppJAR

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.curso.AppJAR.databinding.ActivityFormularioBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class FormularioActivity : AppCompatActivity() {

    //para lanzar una subactividad (un actividad que me da un resultado)
    lateinit var lanzador: ActivityResultLauncher<Intent>
    lateinit var binding: ActivityFormularioBinding
    var color: Int = 0
    lateinit var usuario:Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFormularioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //he ocultado la barra desde el tema del manifest específico para esta actividad
        //TODO formulario dinámico / ANIMADO


        //si hay datos en el fichero
        val ficheroUsuario = getSharedPreferences("usuario", MODE_PRIVATE)
        if (ficheroUsuario.all.isNotEmpty())
        {
            Log.d(Constantes.TAG_LOG, "NO ESTÁ VACÍO, el fichero tiene datos de un usuario")

            //cargarFormularioConDatosFichero(ficheroUsuario)

            //leo el fichero y relleno el formulario
            this.usuario = cargarFormularioConDatosFichero(ficheroUsuario)

            //NUEVO si existe un usuario , queremos mostrar en otra actividad un mensaje de bienvenida

            // crea el Intent y Navega a otra actividad con INTENT
            val intent = Intent(this,BienvenidaActivity::class.java)

            // antes de lanzar la actividad
            intent.putExtra("USUARIO",usuario)

            //developer.android.com/kotlin/parcelize?hl=es-419
            // hay que meter en el fichero build.gradle ( del modulo) y añadir en la seccion plugins
            /*
            id("kotlin-parcelize")

            en la clase Usuario hay que decirle que es : Parcelable
            y al principio de la clase @Parcelize
            * */

            //ademas en la clase hay que agregar en la clase que es de tipo

            startActivity(intent) // Navega a la otra actividad

        }
        else {
            Log.d(Constantes.TAG_LOG, "El fichero de preferencias está vacío")
            //si no, pues no hago nada
        }

        //cuando la caja de texto del nombre PIERDE EL FOCO setOnFocusChangeListener , se valide
        // y si el nombre no cumple la reestriccion se ponga en rojo
        //ejemplo con funcion anonima y de forma antigua llamando al contexto con this@
        /*
        binding.editTextNombreFormulario.setOnFocusChangeListener (fun(view:View, tieneFoco: Boolean) {
            if(tieneFoco)
            {
                Log.d(Constantes.TAG_LOG, "La caja nombre tiene el foco")
            }
            else {
                Log.d(Constantes.TAG_LOG, "La caja nombre ha perdido el foco")
                if(!this@FormularioActivity.esNombreValido(this@FormularioActivity.binding.editTextNombreFormulario.text.toString()))
                {
                    this@FormularioActivity.binding.tilnombre.error = "Nombre debe tener más de 2 caracteres"
                }
                else
                {
                    // quita el rojo de error de la caja de texto
                    this@FormularioActivity.binding.tilnombre.isErrorEnabled= false
                }
            }
        })
        */

        // EJEMPLO CON LAMBDA
        /*
        binding.editTextNombreFormulario.setOnFocusChangeListener { view: View, tieneFoco: Boolean ->
            if (tieneFoco)
            {
                Log.d(Constantes.ETIQUETA_LOG, "La caja de nombre tiene el foco")
            } else {
                Log.d(Constantes.ETIQUETA_LOG, "La caja de nombre ha perdido el foco")
                if (!this.esNombreValido(this.binding.editTextNombreFormulario.text.toString()))
                {
                    binding.tilnombre.error = "Nombre incorrecto"
                } else {
                    binding.tilnombre.isErrorEnabled = false
                }
            }
        }
*/

        //cuando la caja de texto del nombre PIERDE EL FOCO setOnFocusChangeListener , se valide
        // y si el nombre no cumple la reestriccion se ponga en rojo
        // otra forma de ejemplo con funcion anonima
        binding.editTextNombreFormulario.setOnFocusChangeListener(
            fun(view: View, tieneFoco: Boolean) {
                if (tieneFoco)//==true
                {
                    Log.d(Constantes.TAG_LOG, "La caja de nombre tiene el foco")
                } else {
                    Log.d(Constantes.TAG_LOG, "La caja de nombre ha perdido el foco")
                    if (!esNombreValido(binding.editTextNombreFormulario.text.toString())) {
                        binding.tilnombre.error = "Nombre incorrecto - longitud menor que 3"
                    } else {
                        binding.tilnombre.isErrorEnabled = false
                    }
                }

            })

        lanzador = registerForActivityResult (
            ActivityResultContracts.StartActivityForResult() //lo que lanzo es una actividad
        ){
            //la función que recibe el resultado
                result ->
            if (result.resultCode == Activity.RESULT_OK)
            {
                Log.d(Constantes.TAG_LOG, "La subactividad ha FINALIZADO BIEN ${result.resultCode}")
                val intent_resultado = result.data
                color = intent_resultado?.getIntExtra("COLOR_ELEGIDO", 0) ?: 0
                binding.colorFavorito.setBackgroundColor(color)
            } else {
                Log.d(Constantes.TAG_LOG, "La subactividad ha FINALIZADO MAL ${result.resultCode}")
            }

        }
    }

    fun seleccionarColorFavorito(view: View) {
        //DEBEMOS LANZAR LA OTRA ACTIVIDAD SUBCOLOR ACTIVITY, PERO COMO SUBACTIVIDAD
        val intent = Intent(this, SubColorActivity::class.java)
        //startActivity(intent)
        //startActivityForResult(intent, 99)
        lanzador.launch(intent)//aquí lanzo la subactividad
    }

    fun mostrarInfoFormulario(view: View) {
        //mostrar los datos del formulario
        Log.d(Constantes.TAG_LOG, "NOMBRE = ${binding.editTextNombreFormulario.text.toString()} EDAD = ${binding.editTextEdadFormulario.text.toString()} HOMBRE = ${binding.radioButtonHombre.isChecked} MUJER = ${binding.radioButtonMujer.isChecked} MAYOR EDAD = ${binding.checkBox.isChecked}" )
        //TODO crear una clase Usuario, para albergar toda la información obtenidad en el formulario
        val nombre: String = binding.editTextNombreFormulario.text.toString()
        val edad:Int = binding.editTextEdadFormulario.text.toString().toInt()

        val sexo:Char  = if (binding.radioButtonHombre.isChecked) {
            'M'
        }
        else if (binding.radioButtonMujer.isChecked) {
            'F'
        }
        else {
            'M'
        }

        val mayorEdad: Boolean = binding.checkBox.isChecked
        val usuario: Usuario = Usuario(nombre, edad, sexo, mayorEdad, this.color)
        Log.d(Constantes.TAG_LOG, "USUARIO = $usuario")
        guardarUsuario(usuario)

        // Mostrar el SNACKBAR --> Mensaje Guardado
        val snackbar: Snackbar = Snackbar.make(binding.main, "USUARIO GUARDADO mensaje En Snackbar", BaseTransientBottomBar.LENGTH_LONG)
        // añade funcinalidad con un boton deshacer
        snackbar.setAction ("DESHACER")
            {
            v: View -> Log.d(Constantes.TAG_LOG, "Ha tocado deshacer")
            // Borrar los datos guardados en el fichero SharedPreferences
            val ficheroUsuario = getSharedPreferences("usuario", MODE_PRIVATE)
            val editor = ficheroUsuario.edit()
            editor.clear()  // borra lo guardado en el fichero
            editor.apply()  // Confirmar el borrado
            }
        // agrega color a la barra para la accion
        snackbar.setTextColor(getColor(R.color.mirojo))
        //muestra el Snackbar
        snackbar.show()
    }

    /**
     *
    versión antigua
     */
    /*
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        caller: ComponentCaller
    ) {
        super.onActivityResult(requestCode, resultCode, data, caller)
        //obtenía el resultado
    }

     */


    // esta funcion grabara en  nuestra carpaeta shared preferens un fichero llamado usuario.xml
    fun guardarUsuario(usuario: Usuario) {
        //1 accede al fichero (se crea automaticamente si no exiete )
        val ficheroUsuario = getSharedPreferences("usuario",MODE_PRIVATE)

        val editor = ficheroUsuario.edit() // para poder escribir en el fichero a traves del edit
        // Enviamos las variables al fichero
        editor.putString("nombre", usuario.nombre)
        editor.putInt("edad", usuario.edad)
        editor.putString("sexo", usuario.sexo.toString())
        editor.putInt("color", usuario.colorFavorito)
        editor.putBoolean("mayorEdad", usuario.esMayorEdad)

        // Hacemos Commit o apply para confirmar el guardado de lo que acabamos de escribir en el fichero
        //editor.commit()
        editor.apply()
    }

    // al inicio de la actividad comprobar si hay dato guardados del usuario en el fichero
    // de preferencias y si los hay , iniciar la actividad , rellenando con los datos del fichero
    // del formulario
    // devolvemos un objeto de tipo Usuario
    fun cargarFormularioConDatosFichero (fichero: SharedPreferences) : Usuario
    {
        val nombre = fichero.getString("nombre", "")//leo del fichero
        binding.editTextNombreFormulario.setText(nombre)//lo mento en la caja del nombre

        val edad = fichero.getInt("edad", 0)//leo la edad
        binding.editTextEdadFormulario.setText(edad.toString())//la meto en la caja

        val color = fichero.getInt("color", 0)//leo el color
        binding.colorFavorito.setBackgroundColor(color)//pongo el color del fondo en el botón

        val sexo = fichero.getString("sexo", "M")
        if (sexo=="M")
        {
            binding.radioButtonHombre.isChecked = true
        } else {
            binding.radioButtonMujer.isChecked = true
        }

        val mayorEdad = fichero.getBoolean("mayorEdad", false)
        binding.checkBox.isChecked = mayorEdad

        //crea un objeto usuario
        val usuarioFichero = Usuario(nombre!!, edad, sexo!!.get(0), mayorEdad, color)

        // binding.checkBox.isChecked = fichero.getBoolean("mayorEdad", false)

        return usuarioFichero
    }

    fun esNombreValido (nombre:String):Boolean
    {
        var nombreValido: Boolean = false

        // Si nombre valido > 2 devuelve true else false
        nombreValido = (nombre.length>2)

        //pordriamos hacer en version super resumida con esto nos evitanmos declarar variables
        // return nombre.length>2

        return nombreValido
    }


}