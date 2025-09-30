package com.curso.AppJAR.tabs

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy

//Esto es una interfaz funcional porque tiene un único metodo abstracto
class MiTabConfigStrategyImp:TabConfigurationStrategy {
    override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
        tab.text = "VISTA ${position+1}"
    }
}