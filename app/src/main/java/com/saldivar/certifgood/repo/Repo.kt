package com.saldivar.certifgood.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.saldivar.certifgood.CredentialesLogin

class Repo {
    fun getCredenciales():LiveData<Boolean>{
        val mutableResponse = MutableLiveData<Boolean>()
        FirebaseFirestore.getInstance().collection("USUARIOS").whereEqualTo("user", CredentialesLogin.usuario)
            .whereEqualTo("password", CredentialesLogin.password).get().addOnSuccessListener {
                mutableResponse.value = it.size() != 0
            }
        return mutableResponse
    }
}