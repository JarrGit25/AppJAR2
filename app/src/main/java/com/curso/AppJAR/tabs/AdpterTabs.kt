package com.curso.AppJAR.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

//usa rl parametro fragmentActivity que es la actividad padre
// y hereda de FragmentStateAdapter que recibe fragmentActivity
class AdpterTabs(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    val array_datos = intArrayOf(1,2,3)

    //sobreescribimos los metodos  getItemCount y createFragment
    override fun getItemCount(): Int {
        //devolvemos el tamalo del array
        return array_datos.size
    }

    override fun createFragment(position: Int): Fragment {
        var fragment:Fragment

        // creamos el fragment
        fragment = FragmentoTabs()
        // la informacion que guardamos en el saco
        val bundle = Bundle() // aqui guardo datos qiue se usan para rellenar el fragment al inflarlo
        // guardo una variable en el saco
        bundle.putInt("VALOR", array_datos[position])
        fragment.arguments = bundle // se hace la asignacion

        return fragment
    }
}