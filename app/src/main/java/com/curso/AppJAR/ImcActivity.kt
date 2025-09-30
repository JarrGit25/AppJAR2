package com.curso.AppJAR

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.delay
import kotlin.math.log

/**
 * Si tu IMC ESTA POR DEBAJO DE 16 , tu IMC es DESNUTRIDO
 * Si tu IMC ES MAYOR O IGUAL A DE 16 y MENOR de 18, tu IMC es DELGADO
 * Si tu IMC ES MAYOR O IGUAL A DE 18 y MENOR de 25, tu IMC es IDEAL
 * Si tu IMC ES MAYOR O IGUAL A DE 25 y MENOR de 31, tu IMC es SOBREPESO
 * SI tu IMC ES MAYOR O IGUAL ed 31 es OBESO
* */

// TODO pasar del valor numerico al valor nominal ejemplo si es 20 --> Estas Ideal

class ImcActivity : AppCompatActivity() {

    var contBoton:Int = 0
    //var txtresultado:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_imc)

//        // Aqui recuperamos el valor de lo que se ha guardado en bundle si tuviese algo
//        // SI bundle no es nulo recupero el valor
//        if (savedInstanceState!=null)
//        {
//            Log.d(Constantes.TAG_LOG, "El saco tiene cosas. La actividad viene de recrearse")
//            txtresultado = savedInstanceState.getString("txtresultado", "")
//            val cajaResultado = findViewById<TextView>(R.id.boxResultado)
//            cajaResultado.text = txtresultado
//            cajaResultado.visibility = View.VISIBLE
//            // Si es nulo no hago nada
//        } else {
//            Log.d(Constantes.TAG_LOG, "La actividad se ha creado por primera vez")
//        }
//
//        // funciones avanzadas Kotlin solo obtiene txtresultado si no es null es lo mismo de arriba pero en sintaxis mas corta
//        // con el interrogante tambien controla el nulo para asignar a la variable
//        txtresultado = savedInstanceState?.getString("txtresultado") ?: "Sin valor"



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Controlo el boton hacia atrás
        onBackPressedDispatcher.addCallback{
            Log.d(Constantes.ETIQUETA_LOG, "Ha tocado el botón hacia atrás 1")
            haciaAtras()
        }

    }

    fun haciaAtras ()
    {
        // prepara el AlertDialog
        Log.d(Constantes.TAG_LOG,"El usuario ha pulsado salir")
        var alerta = AlertDialog.Builder(this)
            //.setTitle("AVISO")
            .setTitle((R.string.titulo_dialogo_salir)) // i18n
            //.setMessage("Desea Salir?")
            .setMessage((R.string.mensaje_dialogo_salir)) // i18n
            .setIcon(R.drawable.outline_chevron_forward_24)

            .setPositiveButton(R.string.boton_si){ dialogo, opcion ->
                Log.d(Constantes.TAG_LOG, "Opción positiva salir =  $opcion")
                this.finish()
            }
            .setNegativeButton(R.string.boton_no){ dialogo: DialogInterface, opcion: Int ->
                Log.d(Constantes.TAG_LOG, "Opción negativa  =  $opcion")
                dialogo.dismiss()
            }

            .setNeutralButton (R.string.boton_neutro){ dialogo: DialogInterface, opcion: Int ->
                Log.d(Constantes.TAG_LOG, "Opción Neutra  =  $opcion")
                dialogo.cancel()
            }

        alerta.show() // Ahora muestra el AlertDialog
    }

    //para dibujar un menú en la parte superior de la pantalla hay que
    // sobreescribir el metodo onCreateOptionsMeu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Para Representar el menu usamos menuInflater
        // con esto le decimos que pinte el menu en la barra de menu
        menuInflater.inflate(R.menu.menu_imc,menu)
        return super.onCreateOptionsMenu(menu)
    }

    // este método es invocado cuando el usuario toca una opcion del menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // preguntar si dice que SI quiere salir
        // si confirma haremos finish
        // si no quiere solo borramos el cuadro de dialogo
        when(item.itemId)
        {
            R.id.opcionSalir -> {
                Log.d(Constantes.TAG_LOG,"El usuario ha pulsado salir")

                // prepara el AlertDialog
                var alerta = AlertDialog.Builder(this)

                    //.setTitle("AVISO")
                    .setTitle((R.string.titulo_dialogo_salir)) // i18n
                    //.setMessage("Desea Salir?")
                    .setMessage((R.string.mensaje_dialogo_salir)) // i18n

                    .setIcon(R.drawable.outline_chevron_forward_24)
                    // funciones anonimas
//                    .setPositiveButton("SI",fun(dialogo: DialogInterface, opcion: Int){
//                        this.finish()
//                    })

//                    .setNegativeButton("NO",fun(dialogo: DialogInterface, opcion: Int){
//                        //dialogo.cancel()
//                        dialogo.dismiss()
//                    })

                    // lambda
//                    .setPositiveButton("SÍ"){ dialogo, opcion ->
//                        Log.d(Constantes.TAG_LOG, "Opción positiva salir =  $opcion")
//                        this.finish()
//                    }
                    .setPositiveButton(R.string.boton_si){ dialogo, opcion ->
                        Log.d(Constantes.TAG_LOG, "Opción positiva salir =  $opcion")
                        this.finish()
                    }
//                    .setNegativeButton("NO"){ dialogo: DialogInterface, opcion: Int ->
//                        Log.d(Constantes.TAG_LOG, "Opción negativa  =  $opcion")
//                        dialogo.dismiss()
//                    }
                    .setNegativeButton(R.string.boton_no){ dialogo: DialogInterface, opcion: Int ->
                        Log.d(Constantes.TAG_LOG, "Opción negativa  =  $opcion")
                        dialogo.dismiss()
                    }

//                    .setNeutralButton ("CANCEL"){ dialogo: DialogInterface, opcion: Int ->
//                        Log.d(Constantes.TAG_LOG, "Opción Neutra  =  $opcion")
//                        dialogo.cancel()
//                    }
                    .setNeutralButton (R.string.boton_neutro){ dialogo: DialogInterface, opcion: Int ->
                        Log.d(Constantes.TAG_LOG, "Opción Neutra  =  $opcion")
                        dialogo.cancel()
                    }

                alerta.show() // Ahora muestra el AlertDialog

                //finish()
            }
            R.id.opcionlimpiar -> {
                Log.d(Constantes.TAG_LOG,"El usuario ha pulsado Limpiar")
                //ponemos las cajas a vacio
                findViewById<EditText>(R.id.cajaPeso).setText("")
                findViewById<EditText>(R.id.cajaAltura).setText("")
                finish()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        Log.d(Constantes.TAG_LOG, "en onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(Constantes.TAG_LOG, "en onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(Constantes.TAG_LOG, "en onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(Constantes.TAG_LOG, "en onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(Constantes.TAG_LOG, "en onDestroy")
    }

    // Guarda el estado de la actividad en el saco
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        Log.d("TAG","en onSaveInstanceState")
//
//        // Guardo en el saco bundle el valor de  la variable resultado
//        outState.putString("resultadoNombre",txtresultado)
//    }

    fun calcularIMC(view: View) {
        //LISTEREN / CALLBACK
        //TODO Programar Boton
        Log.d("TAG","Boton Calcular IMC Pulsado")

        //InformarContBoton()

        var peso:Float = obtenerPeso()
        Log.d("IMC", "Peso introducido $peso")

        var altura:Float = obtenerAltura()
        Log.d("IMC", "Altura introducida $altura")

        //IMC = peso / altura al cuadrado
        var imc:Float= calcularImcNumerico(peso, altura)

        informarResultadoIMC(imc)

        //cerrar solo la activity
        //this.finish()

        //salir de la app / cerrar todas las ventanas
        //this.finishAffinity()

    }

    fun InformarContBoton (){
        contBoton++
        var cad:String = "El usuario ha pulsado: $contBoton"

        //crea la notificacion  Toast
        //val toast:Toast = Toast.makeText(this,"Boton Calcular IMC Pulsado", Toast.LENGTH_LONG)
        val toast:Toast = Toast.makeText(this,cad , Toast.LENGTH_SHORT)

        // Muestra la notificacion
        toast.show()
    }

    fun obtenerPeso():Float {
        //uso var cuando la variable declarada puede cambiar su valor
        var peso:Float = 0f // inicializo la variabvble de salida

        // obtener el valor de la caja de texto
        //Val para variable que no cambia de valor
        val editTextPeso:EditText = findViewById<EditText>(R.id.cajaPeso)
        peso = editTextPeso.text.toString().toFloat() // accedo al contenido de la caja de texto peso y convertimos a FLoat
        // devolver el valor Peso

        return peso
    }


    fun obtenerAltura():Float {
        //uso var cuando la variable declarada puede cambiar su valor
        var altura:Float = 0f // inicializo la variabvble de salida

        // obtener el valor de la caja de texto
        //Val para variable que no cambia de valor
        val editTextAltura:EditText = findViewById<EditText>(R.id.cajaAltura)
        altura = editTextAltura.text.toString().toFloat() // accedo al contenido de la caja de texto altura y convertimos a FLoat
        // devolver el valor Altura

        return altura
    }

    fun calcularImcNumerico(peso:Float, altura:Float):Float {
        //IMC = peso / altura al cuadrado
        var IMC:Float = 0f
        IMC = peso / cuadrado(altura)

        return IMC
    }

    fun cuadrado(n: Float): Float {
        return n * n
    }

    fun informarResultadoIMC (resul: Float){
        /**
         * Si tu IMC ESTA POR DEBAJO DE 16 , tu IMC es DESNUTRIDO
         * Si tu IMC ES MAYOR O IGUAL A DE 16 y MENOR de 18, tu IMC es DELGADO
         * Si tu IMC ES MAYOR O IGUAL A DE 18 y MENOR de 25, tu IMC es IDEAL
         * Si tu IMC ES MAYOR O IGUAL A DE 25 y MENOR de 31, tu IMC es SOBREPESO
         * SI tu IMC ES MAYOR O IGUAL ed 31 es OBESO
         * */

        val estadoIMC = when {
            resul < 14 -> "FAMÉLICO"
            resul >= 14 && resul < 16 -> "DESNUTRIDO"
            resul >= 16 && resul < 18 -> "DELGADO"
            resul >= 18 && resul < 25 -> "IDEAL"
            resul >= 25 && resul < 31 -> "SOBREPESO"
            resul >= 31 && resul < 38 -> "OBESO"
            else -> "COLOSAL"
        }

        var resulFinal:String = "Su IMC indica: $estadoIMC"

        // Trae la caja de texto
        var txtresultado = findViewById<TextView>(R.id.boxResultado)
        //cambia el valor de la caja de texto
        txtresultado.text = resulFinal
        // pone la caja visible
        txtresultado.visibility = View.VISIBLE

        //crea la notificacion  Toast
        //val toast:Toast = Toast.makeText(this,"Boton Calcular IMC Pulsado", Toast.LENGTH_LONG)
        val toast:Toast = Toast.makeText(this,resulFinal , Toast.LENGTH_LONG)

        // Muestra la notificacion
        toast.show()

        //TODO transitar a la ventana nueva
        // esta en esta pantalla "this" y transito a la nueva  ImagenIMCActivity
        // intent nos sirve para movernos a otro lado
        val intent:Intent = Intent(this,ImagenIMCActivity::class.java)

        // guarda en el intent un parametro
        intent.putExtra("param_estado",estadoIMC)
        // navega hacia la nueva actividad
        startActivity(intent)

    }
}