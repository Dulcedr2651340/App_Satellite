package com.example.examen02.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.examen02.common.entities.SongEntity
import com.example.examen02.mainModule.model.MainInteractor

class MainViewModel: ViewModel(){

    //Declarar propiedades para reflejar datos dentro de la vista
    private var interactor:MainInteractor


    //Bloque de inicializacion
    init {
        interactor = MainInteractor()
    }

    fun getdatasong(): LiveData<MutableList<SongEntity>>{
            var rest = MutableLiveData<MutableList<SongEntity>>()
            interactor.getSong {
                rest.value = it
            }
        return rest
    }
}