package com.curso.AppJAR.contactos

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.curso.AppJAR.Constantes
import com.curso.AppJAR.R


/*
EN este ejemplo accederemos al ContentProvider  11.8 del libro
 */
class SeleccionContactoActivity : AppCompatActivity() {

    //Preparamos una funcion anonima donde se recogera
    // esto lanza la actividad y recupera la info a la vuelta
    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
        // esta funcion se ejecuta a la vuelta del listado de contactos
        // tambien se puede hacer con el iterador it
        // it.
        resultado ->
            Log.d(Constantes.ETIQUETA_LOG, "A la vuelta de contactos ...")
            if (resultado.resultCode== RESULT_OK)
            {
                Log.d(Constantes.ETIQUETA_LOG, "La selección del contacto fue BIEN")
                Log.d(Constantes.ETIQUETA_LOG, " uri contactos = ${resultado.data}")
                mostrarDatosContacto(resultado.data!!)
            } else {
                Log.d(Constantes.ETIQUETA_LOG, "La selección del contacto fue MAL")
            }
    }

    private fun mostrarDatosContacto(intent: Intent) {
        // metodo para acceder al contenido
        val cursor = contentResolver.query(intent.data!!, null, null, null, null)
        // nos movemos al primer registro recorriendo las columnas de la Tabla
        cursor!!.moveToFirst()
        //accedo a las columnas nombre y número por el nombre de la columna
        val columnaNombre = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
        val columnaNumero = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
        val nombre = cursor.getString(columnaNombre)
        val numero = cursor.getString(columnaNumero)
        Log.d(Constantes.ETIQUETA_LOG, "NOMBRE = $nombre y NÚMERO = $numero")
        cursor.close()

        /* tambien se puede hacer asi sin repetir cursor */
        /*
        with(cursor) {
            //accedo a las columnas nombre y número por el nombre de la columna
            val colNombreW = getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            val colNumeroW = getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            //Cargo el contenido de dichas columnas
            val nombreW = getString(colNombreW)
            val numeroW = getString(colNumeroW)
            Log.d(Constantes.ETIQUETA_LOG, "Nombre= $nombreW y Número  = $numeroW")
            // importante cerrar el cursor una vez hemos accedido
            close()
        }
         */

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccion_contacto)
        selectContact()
    }

    private fun selectContact() {
        Log.d(Constantes.ETIQUETA_LOG, "Lanzando la app de contactos ...")
        //preparo el intent para acceder a la app de contactos del movil
        // Con ACTON_PICK Cargamos los contactos en el intent
        // ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)

        // al menos existe alguna app de contactos en el telefono
        if(intent.resolveActivity(packageManager)!=null)
        {
            Log.d(Constantes.ETIQUETA_LOG, "SI HAY APP DE CONTACTOS")
            startForResult.launch(intent)  // lanzamos
        }
        else{
            Log.d(Constantes.ETIQUETA_LOG, "NO HAY APP DE CONTACTOS")
        }
    }


}