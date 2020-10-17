package com.saldivar.certifgood.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saldivar.certifgood.repo.Repo

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
}