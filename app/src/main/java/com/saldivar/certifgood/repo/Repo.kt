package com.saldivar.certifgood.repo

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.saldivar.certifgood.CredentialesLogin

class Repo {
    fun getCredenciales():Boolean{
          FirebaseFirestore.getInstance().collection("USUARIOS").whereEqualTo("user", CredentialesLogin.usuario)
            .whereEqualTo("password", CredentialesLogin.password).get().addOnSuccessListener {
                for(document in it){
                    CredentialesLogin.usuario_activo = true
                }
            }
        return CredentialesLogin.usuario_activo
    }
}