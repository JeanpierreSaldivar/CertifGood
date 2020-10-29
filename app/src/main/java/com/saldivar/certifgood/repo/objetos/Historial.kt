package com.saldivar.certifgood.repo.objetos

data class Historial (
    var estado_examen:Boolean = false,
    var nombre_examen:String = "",
    var nota_examen:String = "",
    var orden_historial_prueba:String ="",
    var porcentaje_examen:String = "",
)
