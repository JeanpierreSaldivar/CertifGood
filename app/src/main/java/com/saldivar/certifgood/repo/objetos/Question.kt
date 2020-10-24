package com.saldivar.certifgood.repo.objetos

data class Question(
    val id_pregunta: String ="",
    val nivel :Int=0,
    val nombre : String = "",
    val pregunta: String = "",
    val respuesta1: String = "",
    val respuesta2: String = "",
    val respuesta3: String = "",
    val respuesta4: String = "",
    val respuesta_correcta: String = "",
)