package com.curso.AppJAR.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.curso.AppJAR.databinding.FragmentoTabBinding

// hereda de Fragment y va ligado a un layout de fragmento que hay que crear en el layout
class FragmentoTabs:Fragment() {

    // sobreescribimos el metodo onCreate View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // una vez definido el layout ahora toca inflarlo
        // el padre es el viewPager2
        var fragmentoTab = FragmentoTabBinding.inflate(inflater, container, false)

        // podemos obtener directamente el valor ya que lo hereda
        var num = arguments?.getInt("VALOR") ?: 0
        fragmentoTab.numvista.text = "VISTA $num"  // agrega el texto al TextView

        return fragmentoTab.root
    }



}