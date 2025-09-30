package com.curso.pruebaskotlin

fun main() {
    val morningNotification = 51
    val eveningNotification = 135

//    printNotificationSummary(morningNotification)
//    printNotificationSummary(eveningNotification)
//    printNotificationSummary(10)

    //valorarEdad(18)
//    valorarNota(5)
//    valorarNota(6)
//    valorarNota(8)
//    valorarNota(9)

//    numeroMayor(1,2,3)
//    numeroMayor(3,2,1)
//    numeroMayor(1,5,2)
//    numeroMayor(1,-1,2)
//    numeroMayor(0,0,0)

    val caso = -2
    val child = 5
    val adult = 28
    val senior = 87

    val isMonday = true

    println("The movie ticket price for a person aged $caso is ${ticketPrice(caso, isMonday)}.")
    println("The movie ticket price for a person aged $child is ${ticketPrice(child, isMonday)}$.")
    println("The movie ticket price for a person aged $adult is ${ticketPrice(adult, isMonday)}$.")
    println("The movie ticket price for a person aged $senior is ${ticketPrice(senior, isMonday)}$.")

}
/*
4) ENUNCIADO DE GOOGLE : PRECIO DE LAS ENTRADAS DE CINE
https://developer.android.com/codelabs/basic-android-kotlin-compose-kotlin-fundamentals-practice-problems?hl=es-419#2
Las entradas de cine suelen tener un precio diferente según la edad de los espectadores.

En el código inicial que se proporciona en el siguiente fragmento de código, escribe un programa que calcule los precios de estas entradas basados en la edad:

Un precio de entrada infantil de USD 15 para personas de 12 años o menos.
Un precio de entrada estándar de USD 30 para personas de entre 13 y 60 años. Los lunes, un precio estándar con descuento de USD 25 para el mismo grupo etario
Un precio para adultos mayores de USD 20 para personas de 61 años o más (asumimos que la edad máxima de un espectador es de 100 años)
Un valor de -1 para indicar que el precio no es válido cuando un usuario ingresa una edad fuera de las especificaciones
*/
//    val child = 5
//    val adult = 28
//    val senior = 87
//
//    val isMonday = true
//
/**
 * Calcula el precio de la entrada según la edad y el día de la semana
 * @param age la edad de la persona
 * @param isMonday indica si es lunes
 * @return el precio de la entrada calculado
 * @author JAR
 * @since version 1(09/07/2025)
*/
fun ticketPrice(age: Int, isMonday: Boolean): Int {
    val precioEntrada = when {
        (age>=0 && age <=12) -> 15
        (age>=13 && age <=60) -> {
            if(isMonday) { 25 }
            else { 30 }
        }
        (age>61 && age <=100) -> 20
        else -> -1
    }

    return precioEntrada
}

//3) HACED UNA FUNCIÓN QUE RECIBA 3 NÚMEROS Y DIGA CUÁL ES EL MAYOR
fun numeroMayor(n1: Int,n2: Int,n3: Int) {
    var numMayor: String = ""

    if (n1 > n2 && n1 > n3)
        numMayor = "El 1er número introducido $n1 es el Mayor"
    else if (n2 > n1 && n2 > n3)
        numMayor = "El 2º número introducido $n2 es el Mayor"
    else if (n3 > n2 && n3 > n1)
        numMayor = "El 3er número introducido $n3 es el Mayor"
    else
        numMayor = "Ningún número es mayor que otro"

    println(numMayor)
}

//2) HACED UNA FUNCIÓN, QUE RECIBA UNA NOTA NUMÉRICA Y DIGA LA NOMINAL CORRESPONDIENTE,
//BASÁNDOSE EN ESTE RANGO
//
//0-4 SUSPENSO
//5- APROBADO
//6 - BIEN
//7, 8 - NOTABLE
//9, 10 - SOBRESALIENTE

//fun valorarNota(nota: Int): String {

fun valorarNota(nota: Int) {

    val conversionNota = when {
        nota == 0 && nota <= 4 -> "SUSPENSO"
        nota == 5  -> "APROBADO"
        nota == 6 -> "BIEN"
        nota >= 7  && nota <=8 -> "NOTABLE"
        nota >= 9 && nota <= 10 -> "SOBRESALIENTE"
        else -> "NOTA INVALIDA"
    }

    println("SU NOTA ES $conversionNota")
    //return conversionNota
}

//HACED UNA FUNCIÓN QUE RECIBA UNA EDAD Y DIGA SI ES MAYOR DE EDAD O NO
fun valorarEdad(edad: Int) {
    if (edad < 18)
    {
        println("No es mayor de edad")
    }
    else
    {
        println("ES MAYOR DE EDAD")
    }
}

fun printNotificationSummary(numberOfMessages: Int) {
    if (numberOfMessages < 100)
    {
        println("You have $numberOfMessages notifications")
    }
    else
    {
        println("Your phone is blowing up! You have 99+ notifications.")
    }
}

class PruebasKotlin {
}