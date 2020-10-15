package com.saldivar.certifgood.repo

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.FirebaseFirestore

class Repo {
    val dbFirebase =FirebaseFirestore.getInstance()
    fun getCredenciales():LiveData<Boolean>{
        dbFirebase.collection("USUARIOS").whereEqualTo("user",)

    }
}