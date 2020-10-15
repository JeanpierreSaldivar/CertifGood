package com.saldivar.certifgood.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.saldivar.certifgood.CredentialesLogin

class Repo {
    val dbFirestore = FirebaseFirestore.getInstance()
    fun getCredenciales():LiveData<Boolean>{
        val mutableResponse = MutableLiveData<Boolean>()
        dbFirestore.collection("USUARIOS").whereEqualTo("user", CredentialesLogin.usuario)
            .whereEqualTo("password", CredentialesLogin.password).get().addOnSuccessListener {
                for (document in it){
                    val idDocumento=document.id
                    updateActividadUser(idDocumento)
                }

                mutableResponse.value = it.size() != 0
            }
        return mutableResponse
    }

    fun updateActividadUser(idDocumento: String):LiveData<Boolean>{
        val mutableResponse = MutableLiveData<Boolean>()
        dbFirestore.collection("USUARIOS").document(idDocumento).update("actividad_usuario",true)
            .addOnSuccessListener {

            }.addOnFailureListener {

            }
    }
}