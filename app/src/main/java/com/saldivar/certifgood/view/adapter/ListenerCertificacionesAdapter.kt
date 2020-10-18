package com.saldivar.certifgood.view.adapter

import com.saldivar.certifgood.repo.objetos.Certificacion

interface ListenerCertificacionesAdapter {
    fun onClick(flight: Certificacion, position :Int)
}