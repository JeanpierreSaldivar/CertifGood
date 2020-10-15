package com.saldivar.certifgood.viewModel

import androidx.lifecycle.ViewModel
import com.saldivar.certifgood.repo.Repo

class MainViewModel: ViewModel() {
    private val repo= Repo()
    fun getResultadoBusquedaUsuario():Boolean{
        return repo.getCredenciales()
    }
}