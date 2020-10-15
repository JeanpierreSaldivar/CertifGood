package com.saldivar.zkflol.utils.permissionsAndConexion

import android.content.Context
import com.saldivar.permisolibrary.isInternetAvailableSaldivar

object CheckInternetConnection {
    var internetConnectionStatus :Boolean = false

    fun validateInternetConnection(context: Context)= isInternetAvailableSaldivar(context)
}