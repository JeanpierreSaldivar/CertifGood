package com.saldivar.certifgood.repo.objetos

data class Certification (
    val cantidad_preguntas: Int = 0,
    val cantidad_preguntas_evaluar :Int=0,
    val nombre : String = "",
    val niveles: Int =0,
    val tiempo_prueba_horas: Int = 0,
    val tiempo_prueba_minutos: Int = 0,
    val tiempo_prueba_segundos: Int = 0,
)