package com.saldivar.certifgood.utils.permissionsAndConexion

import android.app.Activity
import com.saldivar.certifgood.R
import com.saldivar.certifgood.utils.ShowDialog
import com.saldivar.certifgood.utils.permissionsAndConexion.CheckPermissions.statusPermissions
import com.saldivar.certifgood.utils.permissionsAndConexion.CheckPermissions.validatePermissions
import com.saldivar.zkflol.utils.permissionsAndConexion.CheckInternetConnection.internetConnectionStatus
import com.saldivar.zkflol.utils.permissionsAndConexion.CheckInternetConnection.validateInternetConnection

interface CheckConnectionPermissionsToPerformFunctionality {

    fun <T: Activity> checkConnectionAndPermission(context: T){
        statusPermissions=validatePermissions(context)
        internetConnectionStatus = validateInternetConnection(context)
        if(statusPermissions){
            if(!internetConnectionStatus){
                ShowDialog.dialogShow(
                    context.resources.getString(R.string.text_error_conexion_internet),
                    context
                )
            }
        }
    }
}
