package com.saldivar.certifgood.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.saldivar.certifgood.repo.objetos.Certification
import com.saldivar.certifgood.utils.CredentialsLogin

class Repo {
    val dbFirestore = FirebaseFirestore.getInstance()
    fun getCredenciales():LiveData<String>{
        val mutableResponse = MutableLiveData<String>()
        dbFirestore.collection("USUARIOS").whereEqualTo("user", CredentialsLogin.usuario)
            .whereEqualTo("password", CredentialsLogin.password).get().addOnSuccessListener {
                if(it.size() != 0){
                    mutableResponse.value ="Usuario existe"
                    CredentialsLogin.id_documento = it.documents[0].id
                }else{
                    mutableResponse.value = "No existe Usuario"
                }

            }
        return mutableResponse
    }

    fun consultaActividadUser():LiveData<String>{
        val mutableResponse = MutableLiveData<String>()
        dbFirestore.collection("USUARIOS").whereEqualTo("user", CredentialsLogin.usuario).
        whereEqualTo("password", CredentialsLogin.password)
            .whereEqualTo("actividad_usuario",false).get().addOnSuccessListener {
                mutableResponse.value = if(it.size() != 0){
                    "Usuario inactivo"
                }else{
                    "Usuario activo"
                }
        }
        return mutableResponse
    }
    fun updateActividadUser(idDocumento: String,activacion:Boolean):LiveData<Boolean>{
        val mutableResponse = MutableLiveData<Boolean>()
        dbFirestore.collection("USUARIOS").document(idDocumento).update("actividad_usuario",activacion)
            .addOnSuccessListener {
                mutableResponse.value = true
            }.addOnFailureListener {
                mutableResponse.value= false
            }
        return mutableResponse
    }

    fun getCertificacionesList():LiveData<MutableList<Certification>>{
        val mutableResponse = MutableLiveData<MutableList<Certification>>()
        dbFirestore.collection("CERTIFICACIONES").get().addOnSuccessListener {
            val listaCertificaciones = mutableListOf<Certification>()
            for (document in it){
                val cantidadPreguntas = document.getString("cantidad_preguntas")
                val cantidadPreguntasEvaluar = document.getString("cantidad_preguntas_evaluar")
                val nombre = document.getString("nombre")
                val niveles = document.getString("cantidad_niveles")
                val pruebaHoras = document.getString("tiempo_prueba_horas")
                val pruebaMinutos = document.getString("tiempo_prueba_minutos")
                val pruebaSegundos = document.getString("tiempo_prueba_segundos")
                val certificacion = Certification(cantidadPreguntas!!.toInt(),
                    cantidadPreguntasEvaluar!!.toInt(),nombre!!,niveles!!.toInt(),pruebaHoras!!.toInt(),
                pruebaMinutos!!.toInt(),pruebaSegundos!!.toInt())
                listaCertificaciones.add(certificacion)
            }
            mutableResponse.value = listaCertificaciones
        }
        return mutableResponse
    }
}