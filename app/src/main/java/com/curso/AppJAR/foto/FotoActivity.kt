package com.curso.AppJAR.foto

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.curso.AppJAR.Constantes
import com.curso.AppJAR.R
import com.curso.AppJAR.databinding.ActivityFotoBinding
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date

// la actividad accede a la camará y sacar una foto
// incluye permisos delicados en el Manifest

// usaremos un FileProvider que es un componente que habra que declarar en el manifest y
// hará de nexo de enlace entre las rutas
// en al fichero de manifest  dentro de aplication es donde hay que meterlo
//creamos un fichero xml en xml rutas_provider.xml


class FotoActivity : AppCompatActivity() {

    lateinit var binding: ActivityFotoBinding
    // defino una variable global para la URI que necesitaremos varias veces
    lateinit var uriFoto: Uri
    private val viewModel: FotoViewModel by viewModels()//obtenemos una instancia ya implementada: es delegación de propiedad, no de implementación

    val launcherIntentFoto = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
            resultado ->
        if (resultado.resultCode== RESULT_OK)
        {
            Log.d(Constantes.ETIQUETA_LOG, "La foto fue bien")
            binding.fotoTomada.setImageURI(this.uriFoto)
            viewModel.uriFoto = this.uriFoto
        } else {
            Log.d(Constantes.ETIQUETA_LOG, "La foto fue mal")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityFotoBinding.inflate((layoutInflater))
        setContentView(binding.root)

        viewModel.uriFoto?.let {
            binding.fotoTomada.setImageURI(it)
        }
    }

    fun tomarFoto(view: View) {
        pedirPermisos()
    }

    private fun pedirPermisos() {
        // Lo Primero es solicitar el permiso a la camara  que ya hemos puesto en el manifest
        //con un numero  aleatorio para
        requestPermissions((arrayOf(android.Manifest.permission.CAMERA)), 500)

        //a la vuelta de la ejecucion de la solicitud de permisos
        // se ejecuta el metodo  onRequestPermissionsResult ()
        // por tanto se debe sobreescribir para programarlo
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // preguntamos si ya tenemos permisos o no con el grantResults
        // como solo hemos pedido 1 permiso accedemos a la posicion 0 del array
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            Log.d(Constantes.ETIQUETA_LOG, "PERMISO DE CAMARA CONCEDIDO")
            lanzarCamara()  // lanzamos la camara
        }
        else{
            Log.d(Constantes.ETIQUETA_LOG, "PERMISO DE CAMARA NO CONCEDIDO")
            Toast.makeText(this, "SIN PERMISO DE CAMARA PARA HACER FOTOS", Toast.LENGTH_LONG)
        }

    }

    private fun lanzarCamara() {
        // 1 creamos un fichero destino para que cree la ruta donde se almacenara el fichero
        val uri = crearFicheroDestino()
        uri?.let { //si uri es != null
            this.uriFoto = it
            Log.d(Constantes.ETIQUETA_LOG, "URI FOTO = ${this.uriFoto}")
            // creo el INTENT
            val intentFoto = Intent()
            intentFoto.setAction(MediaStore.ACTION_IMAGE_CAPTURE)
            intentFoto.putExtra(MediaStore.EXTRA_OUTPUT, this.uriFoto)
            launcherIntentFoto.launch(intentFoto)
        } ?: run {
            Toast.makeText(this, "NO FUE POSIBLE CREAR EL FICHERO DESTINO", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Scoped Storage (Almacenamiento con Ámbito) — Desde Android 11 (versión "R")
     * "A partir de Android R (Android 11), no podrás acceder al contenido de la carpeta interna compartida (/Android/data/, /Android/obb/) desde este gestor de archivos u otras apps.
     *
     * Con Scoped Storage, ninguna app puede acceder libremente al almacenamiento de otras apps o al directorio compartido /Android/data/, incluso aunque antes fuera posible
     *
     * Esto dice la teoría. Con navegadores de serie de algunos dispositivos sí se puede acceder
     *
     * Programáticamente se puede seguir accediendo y escribiendo sin permisos en el almacenamiento interno compartido
     * "
     */

    private fun crearFicheroDestino():Uri? {
        var rutaUriFoto:Uri? = null
        val fechaActual = Date() // para guardar la fecha
        val momentoActual = SimpleDateFormat("yyyyMMdd_HHmmss").format(fechaActual)
        val nombreFichero = "FOTO_ADF_$momentoActual.jpg"
        // creo la ruta donde se va a generar la foto

        // prueba 1 ruta privada de nuestra app solo visible desde Android Studio
        //val rutaFoto = "${filesDir.path}/$nombreFichero"
        ///data/user/0/com.curso.AppJAR/files/FOTO_ADF_20250922_123007

        // prueba 2 con ruta publica / privada VISIBLE en el explorador de archivos nativo de Android
        //val rutaFoto = "${getExternalFilesDir(null)?.path}/$nombreFichero"
        ///storage/emulated/0/Android/data/com.curso.AppJAR/files/FOTO_ADF_20250922_124618

        // Esta opcion actualmente no funcionaria
        // prueba 3 con ruta publica raiz VISIBLE en el explorador de archivos nativo de Android
        // con este directamente no se puede porque tira una excepcion
        //val rutaFoto = "${Environment.getExternalStorageDirectory()?.path}/$nombreFichero"

        // prueba 4 con ruta de DESCARGAS se podria pero con permisos especiales
        //val rutaFoto = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)?.path}/$nombreFichero"
        val rutaFoto = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)?.path}/$nombreFichero"
        Log.d(Constantes.ETIQUETA_LOG, "Ruta completa fichero = $rutaFoto")

        // creamos el fichero
        val ficheroFoto = File(rutaFoto)
        try{
            ficheroFoto.createNewFile()//este método tira una excepción, pero para KOTLIN todas las excepciones son de tipo RUNTIME o UnCHECKED - NO ME OBLIGA A GESTIONARLAS CON TRY/CATCH -
            rutaUriFoto = ficheroFoto.toUri()
            Log.d(Constantes.ETIQUETA_LOG, "Fichero destino creado OK")

            // aqui es donde obtenemos la ruta publica de la ruta que pasamos
            rutaUriFoto = FileProvider.getUriForFile(this, "com.curso.AppJAR",ficheroFoto)

            Log.d(Constantes.ETIQUETA_LOG, "ruta logica o ficticia solo existente para el intercambio $rutaUriFoto")
        } catch (e:Exception)
        {
            Log.e(Constantes.ETIQUETA_LOG, "Error al crear el fichero destino de la foto", e)
        }

        //TODO CREAR RUTA PÚBLICA
        return rutaUriFoto
    }


}