package com.saldivar.certifgood.utils.permissionsAndConexion

import android.Manifest
import android.app.Activity
import com.saldivar.permisolibrary.multiplePermissionsSaldivar

object CheckPermissions {
    var statusPermissions:Boolean = false

    fun validatePermissions(activity: Activity):Boolean{
        return multiplePermissionsSaldivar(context = activity, requestedPermissions = permissionsList())
    }

     private fun permissionsList() = ArrayList<String>().apply {
        add(Manifest.permission.INTERNET)
         add(Manifest.permission.ACCESS_NETWORK_STATE)

    }
}