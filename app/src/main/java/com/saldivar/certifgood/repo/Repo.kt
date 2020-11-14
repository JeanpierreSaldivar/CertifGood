package com.saldivar.certifgood.repo

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.saldivar.certifgood.repo.objetos.Certification
import com.saldivar.certifgood.repo.objetos.Historial
import com.saldivar.certifgood.repo.objetos.Question
import com.saldivar.certifgood.utils.CertificationObject
import com.saldivar.certifgood.utils.CredentialsLogin
import com.saldivar.certifgood.utils.HistorialObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Repo {
    /*descomentar para ambiente de desarrollo*/
    /*val app = FirebaseApp.getInstance("databaseDesa")
    val dbFirestore = FirebaseFirestore.getInstance(app)*/

    val dbFirestore = FirebaseFirestore.getInstance()
   fun getCredenciales(user: String):LiveData<String>{
       val mutableResponse = MutableLiveData<String>()
       dbFirestore.collection("USUARIOS").whereEqualTo("user", user).get().addOnSuccessListener {
           if(it.size() != 0){
               mutableResponse.value ="Usuario existe"
               CredentialsLogin.id_documento = it.documents[0].id
           }else{
               mutableResponse.value = "No existe Usuario"
           }
       }
       return mutableResponse
    }

    fun saveDataUserGoogle(user:String,foto:String,nameUser:String):LiveData<Boolean>{
        val mutableResponse = MutableLiveData<Boolean>()
        val data = hashMapOf(
            "user" to user,
            "image_user_url" to foto,
            "nombre_user" to nameUser,
        )
        dbFirestore.collection("USUARIOS").add(data).addOnSuccessListener {
            mutableResponse.value = true
        }.addOnFailureListener {
            mutableResponse.value = false
        }
        return mutableResponse
    }

    fun  sizeHistorial(user:String):LiveData<Int>{
        val mutableResponse = MutableLiveData<Int>()
        dbFirestore.collection("HISTORIAL").whereEqualTo("usuario", user).get().addOnSuccessListener {
            mutableResponse.value = it.size()
        }
        return mutableResponse
    }
    fun saveHistorial(size:Int):LiveData<Boolean>{
        val mutableResponse = MutableLiveData<Boolean>()
        val data = hashMapOf(
            "estado_examen" to HistorialObject.estado_examen,
            "nombre_examen" to HistorialObject.nombre_examen,
            "nota_examen" to HistorialObject.nota_examen,
            "usuario" to HistorialObject.usuario,
            "porcentaje_examen" to HistorialObject.porcentaje_examen,
            "orden_historial_prueba" to size.toString(),
            "orden_historial_int" to size,
        )
        dbFirestore.collection("HISTORIAL").add(data).addOnSuccessListener {
            mutableResponse.value = true
        }.addOnFailureListener {
            mutableResponse.value = false
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
                val porcentajeAprobar = document.getString("porcentaje_minimo_aprobar")
                val certificacion = Certification(cantidadPreguntas!!.toInt(),
                    cantidadPreguntasEvaluar!!.toInt(),nombre!!,niveles!!.toInt(),pruebaHoras!!.toInt(),
                pruebaMinutos!!.toInt(),pruebaSegundos!!.toInt(),porcentajeAprobar!!.toInt())
                listaCertificaciones.add(certificacion)
            }
            mutableResponse.value = listaCertificaciones
        }
        return mutableResponse
    }

    fun getListQuestions(idPregunta :String):LiveData<MutableList<Question>>{
        val mutableResponse = MutableLiveData<MutableList<Question>>()
        dbFirestore.collection("PREGUNTAS").
        whereEqualTo("nombre",CertificationObject.nombreCertificacion).
            whereEqualTo("nivel",CertificationObject.nivelElegido).
        whereEqualTo("id_pregunta",idPregunta).
        get().addOnSuccessListener {
            val questionList = mutableListOf<Question>()
            for(document in it){
                val id_pregunta = document.getString("id_pregunta")!!
                val nivel =document.getString("nivel")!!
                val nombre = document.getString("nombre")!!
                val pregunta = document.getString("pregunta")!!
                val respuesta1 = document.getString("respuesta1")!!
                val respuesta2 = document.getString("respuesta2")!!
                val respuesta3 = document.getString("respuesta3")!!
                val respuesta4 = document.getString("respuesta4")!!
                val respuesta_correcta = document.getString("respuesta_correcta")!!
                val question =Question(id_pregunta,nivel.toInt(),nombre, pregunta, respuesta1,
                    respuesta2, respuesta3, respuesta4, respuesta_correcta)
                questionList.add(question)
            }
            mutableResponse.value = questionList
        }
        return mutableResponse
    }

    fun getListHistorial(user:String):LiveData<MutableList<Historial>>{
        val mutableResponse = MutableLiveData<MutableList<Historial>>()
        dbFirestore.collection("HISTORIAL").whereEqualTo("usuario",user).
        get().addOnSuccessListener {
            val historialList = mutableListOf<Historial>()
            val historialListOrdenado = mutableListOf<Historial>()
            for (document in it){
                val estado_examen = document.getBoolean("estado_examen")!!
                val nombre_examen = document.getString("nombre_examen")!!
                val nota_examen = document.getString("nota_examen")!!
                val orden_historial_prueba = document.getString("orden_historial_prueba")!!
                val porcentaje_examen = document.getString("porcentaje_examen")!!
                val historial = Historial(estado_examen,nombre_examen,nota_examen, orden_historial_prueba, porcentaje_examen)
                historialList.add(historial)
            }

            for (j in (historialList.size-1) downTo 0){
                for(i in (historialList.size-1) downTo 0){
                    if(historialList[j].orden_historial_prueba.toInt()==i){
                        historialListOrdenado.add(historialList[i])
                    }
                }
            }

            mutableResponse.value = historialListOrdenado
        }
        return mutableResponse
    }
}