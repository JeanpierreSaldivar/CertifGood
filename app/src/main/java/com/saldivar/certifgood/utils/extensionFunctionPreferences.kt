package com.saldivar.certifgood.utils

import android.content.SharedPreferences

fun SharedPreferences.devolverDatoUser(nameKey:String):String=this.getString(nameKey,"vacio")!!
