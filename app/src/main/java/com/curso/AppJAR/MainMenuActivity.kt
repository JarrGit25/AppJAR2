package com.curso.AppJAR

import android.annotation.TargetApi
import android.content.ComponentName
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.edit
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.curso.AppJAR.biometrico.HuellaActivity
import com.curso.AppJAR.canciones.BusquedaCancionesActivity
import com.curso.AppJAR.contactos.SeleccionContactoActivity
import com.curso.AppJAR.contactos.SeleccionContactoPermisosActivity
import com.curso.AppJAR.fechayhora.SeleccionFechaYHoraActivity
import com.curso.AppJAR.foto.FotoActivity
import com.curso.AppJAR.lista.ListaUsuariosActivity
import com.curso.AppJAR.mapa.MapsActivity
import com.curso.AppJAR.perros.PerrosActivity
import com.curso.AppJAR.productos.ListaProductosActivity
import com.curso.AppJAR.servicios.PlayActivity
import com.curso.AppJAR.tabs.TabsActivity
import com.curso.AppJAR.worker.MiTareaProgramada
import com.google.android.material.navigation.NavigationView
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

/**
 * ESTA ES LA ACTIVIDAD DE INICIO
 * DESDE AQUI , LANZAMOS EL RESTO DE ACTIVIDADES
 * EN UN FUTURO PONDREMOS UN MENU HAMBURGUESA / LATERAL
 * DE MOMENTO , LO HACEMOS CON INTENTS
 */
class MainMenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    //declaro 2 variables para poder trabajar luego con los menus
    lateinit var drawerLayout : DrawerLayout
    lateinit var navigationView: NavigationView
    // para trabajar con los estados de visibilidad del menu
    var menuVisible: Boolean = false
    var launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        Log.d(Constantes.ETIQUETA_LOG, "Volviendo de Ajustes Autonicio")
        val ficherop = getSharedPreferences("ajustes", MODE_PRIVATE)
        //ficherop.edit().putBoolean("INICIO_AUTO", true).commit()
        //ponemos alarma a true la primera vez
        ficherop.edit(true){
            putBoolean("INICIO_AUTO", true)
            putBoolean("ALARMA", false)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_menu)

        val ficherop = getSharedPreferences("ajustes", MODE_PRIVATE)
        val inicio_auto = ficherop.getBoolean("INICIO_AUTO", false)
        if (!inicio_auto) {
            //PRIMERA VEZ
            solicitarInicioAutomatico()
        }

        // Inicializo las dos vistas
        this.drawerLayout = findViewById<DrawerLayout>(R.id.drawer)
        this.navigationView = findViewById<NavigationView>(R.id.navigationView)

        // Ponemos Listener cuando se selecciona una opcion del menu para ello en la
        //clase principal hay que implementar la interfaz NavigationView.OnNavigationItemSelectedListener
        // y sobreescribir el metodo onNavigationItemSelected
        this.navigationView.setNavigationItemSelectedListener (this)

        // nos referimos a la barra y con el metodo dibuja el icono de menú
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //dice el icono que queremos mostrar en este caso el del menu hamburguesa
        this.supportActionBar?.setHomeAsUpIndicator(R.drawable.outline_menu_24)

        // LANZA LA ACTIVIDAD IMC navega de una pantalla a otra
        //val intent = Intent(this,ImcActivity::class.java)
        //startActivity(intent)

        //mostrarAPPSinstaladas()
        gestionarPermisosNotis()
        // lanzarAlarma()
        lanzarWorkManager()
    }

//    // creo Intent para enviar texto
//    fun intentCompartir() {
//        val intentEnviartexto = Intent(Intent.ACTION_SEND) // ENVIAR
//        intentEnviartexto.type = "text/plain"  // TIPO MIME de que tipo es la información  para que lo abra la extension oportuna
//        intentEnviartexto.putExtra(Intent.EXTRA_TEXT, "Hola desde Android")
//        startActivity(Intent.createChooser(intentEnviartexto,"Enviar mensake con ..."))
//    }

    // este metodo se invoca al tocar el menu de la hamburguesa
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        //android.R.id.home (referencia internamente a recursos de android
        when(item.itemId){
            android.R.id.home -> {
                Log.d(Constantes.TAG_LOG, "Botón Hamburguesa tocado")
                if (this.menuVisible){
                    //cerrar menu
                    this.drawerLayout.closeDrawers()
                    this.menuVisible=false
                }
                else{
                    this.drawerLayout.openDrawer(GravityCompat.START)
                    this.menuVisible=true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d(Constantes.TAG_LOG, "Opción ${item.order} seleccionada")
        this.drawerLayout.closeDrawers()
        this.menuVisible = false
        // TODO completar el menú lateral y su funcionamiento
        // 1 item por cada activity y cada opcion transite a su pantalla

        // establece una imagen por defecto de la carpeta drawable de Resources
        when (item.order) {
            1 -> {
                Log.d(Constantes.TAG_LOG,"Navegar hacia item $item.order")
                // intent nos sirve para navegar a otra actividad
                val intent = Intent(this,VersionesActivity::class.java)
                // navega hacia la actividad
                startActivity(intent)
            }
            2 -> {
                Log.d(Constantes.TAG_LOG,"Navegar hacia item $item.order")
                val intent = Intent(this,AdivinaNumeroActivity::class.java)
                startActivity(intent)
            }
            3 -> {
                Log.d(Constantes.TAG_LOG,"Navegar hacia item $item.order")
                val intent = Intent(this,ImcActivity::class.java)
                startActivity(intent)
            }
            4 -> {
                Log.d(Constantes.TAG_LOG,"Navegar hacia item $item.order")
                val intent = Intent(this,CuadrosActivity::class.java)
                startActivity(intent)
            }
            5 -> {
                Log.d(Constantes.TAG_LOG,"Navegar hacia item $item.order")
                val intent = Intent(this,SumaActivity::class.java)
                startActivity(intent)
            }
            6 -> {
                Log.d(Constantes.TAG_LOG,"Navegar hacia item $item.order")
                val intent = Intent(this, BusquedaActivity::class.java)
                startActivity(intent)
            }
            7 -> {
                Log.d(Constantes.TAG_LOG,"Navegar hacia item $item.order")
                //val intenImplicito = Intent(Intent.ACTION_VIEW, "https://adf-formacion.es/".toUri()) // Intent Implicito

                val intent = Intent(this, WebViewActivity::class.java)  // Intent Explicito
                //startActivity(Intent.createChooser(intenImplicito, "ELIGE la APP para ver la web de ADF"))
                startActivity(intent)
            }

            8 -> {
                Log.d(Constantes.TAG_LOG,"Navegar hacia item $item.order")
                val intent = Intent(this, CompartirTextoActivity::class.java)
                startActivity(intent)
            }
            9 -> {
                Log.d(Constantes.TAG_LOG,"Navegar hacia item $item.order")
                val intent = Intent(this, SpinnerActivity::class.java)
                startActivity(intent)
            }
            10 -> {
                Log.d(Constantes.TAG_LOG,"Navegar hacia item $item.order")
                val intent = Intent(this, FormularioActivity::class.java)
                startActivity(intent)
            }
            11 -> {
                Log.d(Constantes.TAG_LOG,"Navegar hacia item $item.order")
                val intent = Intent(this, VideoActivity::class.java)
                startActivity(intent)
            }
            12 -> {
                Log.d(Constantes.TAG_LOG,"Navegar hacia item $item.order")
                val intent = Intent(this, BlackGameActivity::class.java)
                startActivity(intent)
            }
            13 -> {
                Log.d(Constantes.TAG_LOG,"Navegar hacia item $item.order")
                val intent = Intent(this, SportsNewsActivity::class.java)
                startActivity(intent)
            }
            14 -> {
                Log.d(Constantes.TAG_LOG,"Navegar hacia item $item.order")
                val intent = Intent(this, InflarActivity::class.java)
                startActivity(intent)
            }
            15 -> {
                Log.d(Constantes.TAG_LOG,"Navegar hacia item $item.order")
                val intent = Intent(this, ListaUsuariosActivity::class.java)
                startActivity(intent)
            }
            16 -> {
                Log.d(Constantes.TAG_LOG,"Navegar hacia item $item.order")
                val intent = Intent(this, ListaProductosActivity::class.java)
                startActivity(intent)
            }
            17 -> {
                Log.d(Constantes.TAG_LOG,"Navegar hacia item $item.order")
                val intent = Intent(this, PerrosActivity::class.java)
                startActivity(intent)
            }
            18 -> {
                Log.d(Constantes.TAG_LOG,"Navegar hacia item $item.order")
                val intent = Intent(this, TabsActivity::class.java)
                startActivity(intent)
            }
            19 -> {
                Log.d(Constantes.TAG_LOG,"Navegar hacia item $item.order")
                val intent = Intent(this, BusquedaCancionesActivity::class.java)
                startActivity(intent)
            }
            20 -> {
                Log.d(Constantes.TAG_LOG,"Navegar hacia item $item.order")
                val intent = Intent(this, SeleccionContactoActivity::class.java)
                startActivity(intent)
            }
            21 -> {
                Log.d(Constantes.TAG_LOG,"Navegar hacia item $item.order")
                val intent = Intent(this, SeleccionContactoPermisosActivity::class.java)
                startActivity(intent)
            }
            22 -> {
                Log.d(Constantes.TAG_LOG,"Navegar hacia item $item.order")
                val intent = Intent(this, FotoActivity::class.java)
                startActivity(intent)
            }
            23 -> {
                Log.d(Constantes.TAG_LOG,"Navegar hacia item $item.order")
                val intent = Intent(this, HuellaActivity::class.java)
                startActivity(intent)
            }
            24 -> {
                Log.d(Constantes.TAG_LOG,"Navegar hacia item $item.order")
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
            }
            25 -> {
                Log.d(Constantes.TAG_LOG,"Navegar hacia item $item.order")
                val intent = Intent(this, PlayActivity::class.java)
                startActivity(intent)
            }
            26 -> {
                Log.d(Constantes.TAG_LOG,"Navegar hacia item $item.order")
                val intent = Intent(this, SeleccionFechaYHoraActivity::class.java)
                startActivity(intent)
            }

        }


        return true
    }

    fun lanzarWorkManager ()
    {
        //definimos restricciones
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED) // solo Wi-Fi
            .setRequiresBatteryNotLow(true)               // no ejecutar con batería baja
            //.setRequiresCharging(true)                    // solo cuando esté cargando
            .build()

        //pasamos datos de entrada
        val inputData = workDataOf("USER_ID" to "12345")

        //creamos el trabajo periódico (la petición) con los datos y restricciones anteriores
        val periodicWorkRequest = PeriodicWorkRequestBuilder<MiTareaProgramada>(
            15, TimeUnit.MINUTES // Periodicidad mínima: 15 minutos
        )
            .setConstraints(constraints)
            .setInputData(inputData)
            .build()

        //encolamos la petición
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "MiTareaProgramada",                       // Nombre único
                ExistingPeriodicWorkPolicy.KEEP,        // No reemplazar si ya existe
                periodicWorkRequest
            )

        val tiempo = System.currentTimeMillis()+(60*1000*15) //(30*1000)//15 minutos
        //val tiempo = System.currentTimeMillis()+(1*1000) //(30*1000)//15 minutos
        val dateformatter = SimpleDateFormat("E dd/MM/yyyy ' a las ' hh:mm:ss")
        val mensaje = dateformatter.format(tiempo)
        Log.d(Constantes.ETIQUETA_LOG, "ALARMA PROGRAMADA PARA $mensaje")
        Toast.makeText(this, "Alarma programada para $mensaje", Toast.LENGTH_LONG).show()

    }

    private fun solicitarInicioAutomatico() {
        val manufacturer = Build.MANUFACTURER
        try {
            val intent = Intent()
            if ("xiaomi".equals(manufacturer, ignoreCase = true)) {
                intent.setComponent(
                    ComponentName(
                        "com.miui.securitycenter",
                        "com.miui.permcenter.autostart.AutoStartManagementActivity"
                    )
                )
            }

            launcher.launch(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun mostrarAPPSinstaladas ()
    {
        val packageManager = packageManager
        val apps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

        Log.d("AppInfo","Total APPS ${apps.size}")
        for (app in apps) {
            Log.d("AppInfo", "Package: ${app.packageName}, Label: ${packageManager.getApplicationLabel(app)}")
        }

    }

    fun gestionarPermisosNotis ()
    {
        val areNotificationsEnabled = NotificationManagerCompat.from(this).areNotificationsEnabled()

        if (!areNotificationsEnabled) {
            // Mostrar un diálogo al usuario explicando por qué necesita habilitar las notificaciones
            mostrarDialogoActivarNotis()
        }
        else {
            Log.d(Constantes.ETIQUETA_LOG, "Notis desactivadas")
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun mostrarDialogoActivarNotis() {
        var dialogo = AlertDialog.Builder(this)
            .setTitle("AVISO NOTIFICACIONES") //i18n
            //.setTitle("AVISO")
            .setMessage("Para que la app funcione, debe ir a ajustes y activar las notificaciones")
            //.setMessage("¿Desea Salir?")
            .setIcon(R.drawable.outline_notifications_active_24)
            .setPositiveButton("IR"){ dialogo, opcion ->
                Log.d(Constantes.ETIQUETA_LOG, "Opción positiva salir =  $opcion")
                val intent = Intent().apply {
                    action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                    putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                }
                startActivity(intent)

            }
            .setNegativeButton("CANCELAR"){ dialogo: DialogInterface, opcion: Int ->
                Log.d(Constantes.ETIQUETA_LOG, "Opción negativa  =  $opcion")
                dialogo.dismiss()
            }


        dialogo.show()//lo muestro
    }

}