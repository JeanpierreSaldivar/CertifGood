package com.saldivar.certifgood.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saldivar.certifgood.repo.Repo

class MainViewModel: ViewModel() {
    private val repo= Repo()
    fun getResultadoBusquedaUsuario():LiveData<Boolean>{
        val mutableResponse =MutableLiveData<Boolean>()
        repo.getCredenciales().observeForever{
            mutableResponse.value=it
        }
        return mutableResponse
    }
}