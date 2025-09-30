package com.curso.pruebaskotlin
/*
27.0 degrees Celsius is 80.60 degrees Fahrenheit.
350.0 degrees Kelvin is 76.85 degrees Celsius.
10.0 degrees Fahrenheit is 260.93 degrees Kelvin.

De grados Celsius a Fahrenheit: °F = 9/5 (°C) + 32
Kelvin a Celsius: °C = K - 273.15
De Fahrenheit a Kelvin: K = 5/9 (°F - 32) + 273.15
* */

// FUNCIONES COMO PARAMETROS DE ENTRADA

fun main() {
    printFinalTemperature(27.0, "Celsius", "Fahrenheit", { t -> (t * 9 / 5) + 32 })
    printFinalTemperature(350.0, "Kelvin", "Celsius", { t -> (t - 273.15) })

    printFinalTemperature(10.0, "Fahrenheit", "Kelvin", { t -> ((t - 32.0) * 5.0 / 9.0) + 273.15 })

    //función anónima
    printFinalTemperature(27.0, "Celsius", "Fahrenheit", fun (temperaturaInicial: Double):Double {
        var temperaturaFinal: Double = 0.0

        temperaturaFinal = (temperaturaInicial *9)/5+32

        return  temperaturaFinal
    } )


    printFinalTemperature(27.0, "Celsius", "Fahrenheit", ::convertirTemperatura )
}

fun convertirTemperatura (temperaturaInicial: Double):Double {
    var temperaturaFinal: Double = 0.0

    temperaturaFinal = (temperaturaInicial *9)/5+32

    return  temperaturaFinal
}

//esta funcion se traduciria como se ve arriba en el main a :
//{temperaturaInicial->temperaturaInicial*3}
// y seria lo mismo que crear una funcion convertirTemperatura

fun printFinalTemperature(
    initialMeasurement: Double,
    initialUnit: String,
    finalUnit: String,
    conversionFormula: (Double) -> Double
) {
    val finalMeasurement = String.format("%.2f", conversionFormula(initialMeasurement)) // two decimal places
    println("$initialMeasurement degrees $initialUnit is $finalMeasurement degrees $finalUnit.")
}