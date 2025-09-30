package com.curso.pruebaskotlin

import kotlin.math.pow

fun main() {
    // declaro un alumno
    val alumno1:Alumno = Alumno("Vale",41, 5)
    val alumno2:Alumno = Alumno("Antonio",53, 5)
    val alumno3:Alumno = Alumno("Miguel",19, 8)

    println("EDAD de Fran ${alumno1.edad}")

    // implementacion de listas
    // llena la lista con 3 alumnos
    val listaAlumnos:List<Alumno> = listOf(alumno1,alumno2,alumno3)

    //recorrer la lista
    for (a in listaAlumnos)
    {
        println("Nombre ${a.nombre}, Edad ${a.edad}, Nota ${a.nota}")
    }

    listaAlumnos.forEach {
        // el parametro es una variable predefinidad que se llama it (iterador)
        println("La edad es : ${it.edad}")
    }

    mediaEdadAlumnos(listaAlumnos)

    // tambien hubiesemos podido calcular la media directamente
    // con una funcion del propio Kotlin transformando la lista a numeros

    println("Media de Edades usando map y average " +
            "${listaAlumnos.map { a -> a.edad}.average()}")
}

// redondear a N decimales que queramos
fun redondear(numero: Float, decimales: Int): Float {
    val factor = 10.0.pow(decimales).toFloat()
    return kotlin.math.round(numero * factor) / factor
}

// calcula la media de Edad de toda la lista de alumnos
fun mediaEdadAlumnos(lista : List<Alumno>) : Float {
    var mediaEdades:Float = 0f
    var sumaEdades:Float = 0f

    for (item in lista) {
        sumaEdades = sumaEdades + item.edad
    }
    mediaEdades = sumaEdades  / lista.size

    println("La media de Edad es: ${redondear(mediaEdades,2)}")

    return redondear(mediaEdades,2)
}