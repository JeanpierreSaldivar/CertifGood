package com.saldivar.certifgood.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saldivar.certifgood.repo.Repo
import com.saldivar.certifgood.repo.objetos.Certification
import com.saldivar.certifgood.repo.objetos.Historial
import com.saldivar.certifgood.repo.objetos.Question

class MainViewModel: ViewModel() {
    private val repo= Repo()

    fun getResultadoBusquedaUsuario(user: String):LiveData<String>{
        val mutableResponse =MutableLiveData<String>()
        repo.getCredenciales(user).observeForever{
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


    fun sizeHistorial(user:String):LiveData<Int>{
        val mutableResponse = MutableLiveData<Int>()
        repo.sizeHistorial(user).observeForever{
            mutableResponse.value = it
        }
        return  mutableResponse
    }
    fun saveHistorial(size:Int):LiveData<Boolean>{
        val mutableResponse = MutableLiveData<Boolean>()
        repo.saveHistorial(size).observeForever{
            mutableResponse.value = it
        }
        return mutableResponse
    }

    fun saveGoogleUser(user:String,foto:String,nameUser:String):LiveData<Boolean>{
        val mutableResponse = MutableLiveData<Boolean>()
        repo.saveDataUserGoogle(user,foto,nameUser).observeForever{
            mutableResponse.value = it
        }
        return mutableResponse
    }
    fun getHistorial(user:String):LiveData<MutableList<Historial>>{
        val mutableResponse = MutableLiveData<MutableList<Historial>>()
        repo.getListHistorial(user).observeForever{
            mutableResponse.value = it
        }
        return mutableResponse
    }
}