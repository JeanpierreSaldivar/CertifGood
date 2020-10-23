package com.saldivar.certifgood.view.adapter

import com.saldivar.certifgood.repo.objetos.Certification

interface ListenerCertificationsAdapter {
    fun onClick(flight: Certification, position :Int)
}