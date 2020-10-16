package com.saldivar.certifgood.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.saldivar.certifgood.utils.CredentialesLogin

class Repo {
    val dbFirestore = FirebaseFirestore.getInstance()
    fun getCredenciales():LiveData<String>{
        val mutableResponse = MutableLiveData<String>()
        dbFirestore.collection("USUARIOS").whereEqualTo("user", CredentialesLogin.usuario)
            .whereEqualTo("password", CredentialesLogin.password).get().addOnSuccessListener {
                if(it.size() != 0){
                    mutableResponse.value ="Usuario existe"
                    CredentialesLogin.id_documento = it.documents[0].id
                }else{
                    mutableResponse.value = "No existe Usuario"
                }

            }
        return mutableResponse
    }

    fun consultaActividadUser():LiveData<String>{
        val mutableResponse = MutableLiveData<String>()
        dbFirestore.collection("USUARIOS").whereEqualTo("user", CredentialesLogin.usuario).
        whereEqualTo("password", CredentialesLogin.password)
            .whereEqualTo("actividad_usuario",false).get().addOnSuccessListener {
                mutableResponse.value = if(it.size() != 0){
                    "Usuario inactivo"
                }else{
                    "Usuario activo"
                }
        }
        return mutableResponse
    }
    fun updateActividadUser(idDocumento: String):LiveData<Boolean>{
        val mutableResponse = MutableLiveData<Boolean>()
        dbFirestore.collection("USUARIOS").document(idDocumento).update("actividad_usuario",true)
            .addOnSuccessListener {
                mutableResponse.value = true
            }.addOnFailureListener {
                mutableResponse.value= false
            }
        return mutableResponse
    }
}