package com.saldivar.zkflol.utils.permissionsAndConexion

import android.content.Context
import com.saldivar.certifgood.utils.isInternetAvailableSaldivar

object CheckInternetConnection {
    var internetConnectionStatus :Boolean = false

    fun validateInternetConnection(context: Context)= isInternetAvailableSaldivar(context)
}