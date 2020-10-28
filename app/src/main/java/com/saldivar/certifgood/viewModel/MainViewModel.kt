package com.saldivar.certifgood.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saldivar.certifgood.repo.Repo
import com.saldivar.certifgood.repo.objetos.Certification
import com.saldivar.certifgood.repo.objetos.Question

class MainViewModel: ViewModel() {
    private val repo= Repo()
    fun getActividadUsuarioEncontrado():LiveData<String>{
        val mutableResponse = MutableLiveData<String>()
        repo.consultaActividadUser().observeForever{
            mutableResponse.value = it
        }
        return mutableResponse
    }

    fun updateActividadUsuario(Document:String,activacion:Boolean):LiveData<Boolean>{
        val mutableResponse = MutableLiveData<Boolean>()
        repo.updateActividadUser(Document,activacion).observeForever{
            mutableResponse.value = it
        }
        return mutableResponse
    }
    fun getResultadoBusquedaUsuario():LiveData<String>{
        val mutableResponse =MutableLiveData<String>()
        repo.getCredenciales().observeForever{
            mutableResponse.value=it
        }
        return mutableResponse
    }

    fun getListCertificaciones():LiveData<MutableList<Certification>>{
        val  mutableResponse = MutableLiveData<MutableList<Certification>>()
        repo.getCertificacionesList().observeForever{
            mutableResponse.value= it
        }
        return mutableResponse
    }

    fun getQuestionsList(id:String):LiveData<MutableList<Question>>{
        val mutableResponse = MutableLiveData<MutableList<Question>>()
        repo.getListQuestions(id).observeForever{
            mutableResponse.value = it
        }
        return  mutableResponse
    }

    fun queryImageNameUser(user:String):LiveData<String>{
        val mutableResponse = MutableLiveData<String>()
        repo.consultarNombreImagenUser(user).observeForever{
            mutableResponse.value = it
        }
        return  mutableResponse
    }

    fun sizeHistorial(user:String):LiveData<Int>{
        val mutableResponse = MutableLiveData<Int>()
        repo.sizeHistorial(user).observeForever{
            mutableResponse.value = it
        }
        return  mutableResponse
    }
    fun saveHistorial(imageUser:String,size:Int):LiveData<Boolean>{
        val mutableResponse = MutableLiveData<Boolean>()
        repo.saveHistorial(imageUser,size).observeForever{
            mutableResponse.value = it
        }
        return mutableResponse
    }
}