package com.curso.AppJAR.tabs

import android.os.Bundle
import android.util.Log
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.curso.AppJAR.Constantes
import com.curso.AppJAR.databinding.ActivityTabsBinding
import com.google.android.material.tabs.TabLayoutMediator

class TabsActivity : AppCompatActivity() {

    lateinit var binding: ActivityTabsBinding
    lateinit var adapterTabs: AdpterTabs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityTabsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // el viewPager  tiene asociado este adapter
        this.adapterTabs = AdpterTabs(this)
        this.binding.vpt.adapter = this.adapterTabs

        // asocio al tabLayout el view pager
        // lo vemos con tres implementaciones distintas
        TabLayoutMediator(this.binding.tablayout, this.binding.vpt){
            tl,n-> tl.text = "VISTA ${n+1}"
        }.attach()

        onBackPressedDispatcher.addCallback{
            Log.d(Constantes.ETIQUETA_LOG, "Ha tocado el botón hacia atrás 1")
            haciaAtras()
        }

        // otra implementacion
        /*
        TabLayoutMediator(this.binding.tablayout, this.binding.vpt, fun(t,n){
            t.text = "VISTA ${n+1}"
        }).attach()
        */

        /*
        val mtconfg = MiTabConfigStrategyImp()
        TabLayoutMediator(this.binding.tablayout, this.binding.vpt, mtconfg).attach()
         */
    }

    fun haciaAtras ()
    {
        if (this.binding.vpt.currentItem == 0)//si estoy en el tab 1
        {
            finish() //salgo
        } else { //si no
            this.binding.vpt.currentItem = this.binding.vpt.currentItem-1 //me voy al tab anterior
        }
    }

    //metodo para la funcionalidad de cuando se pulsa el boton hacia atras
    // hay que sobreescribir el metodo onBackPressed
    /*
    override fun onBackPressed() {
        Log.d(Constantes.ETIQUETA_LOG, "Ha pulsado el boton ir hacia atrás")

        //si estoy en el primer item
        if(this.binding.vpt.currentItem==0)
        {
            super.onBackPressed() // salgo porque hace el finish
        }else{
            // echamos uno hacia atras voy al tab anterior
            this.binding.vpt.currentItem = this.binding.vpt.currentItem-1
        }
        // si comento la siguiente , como he sobreescrito el metodo ,
        // el boton atras no funcionara ya que no hace el finish del padre
        //super.onBackPressed()

        // hace las veces de onBackPressed ya deprecado
        onBackPressedDispatcher.addCallback() {
            Log.d(Constantes.ETIQUETA_LOG, "Ha tocado el boton hacia atrás")
        }

        onBackPressedDispatcher.addCallback(this) {
            Log.d(Constantes.ETIQUETA_LOG, "Ha tocado el boton hacia atrás")
            super.onBackPressed()
        }

    }
     */

    // necesita de crear un adapter que sera AdapterTabs
}