package com.example.examen02.songModule.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.examen02.GenderModule.ViewModel.GenderViewModel
import com.example.examen02.common.entities.GenderWithSongs
import com.example.examen02.common.entities.ResponseMessage
import com.example.examen02.common.entities.SongEntity
import com.example.examen02.songModule.Model.SongInteractor

class SongViewModel:ViewModel() {

    private val songSelected = MutableLiveData<SongEntity>()
    private val interactor: SongInteractor


    //Bloque de inicializacion
    init {
        interactor = SongInteractor()
    }

    //Get y set de sonselected
    fun getSongSelected(): LiveData<SongEntity>{
        return songSelected
    }

    //
    fun setSongSelected(songEntity: SongEntity){
        songSelected.value = songEntity
    }

    //Metodos del interactor
    fun saveSong(songEntity: SongEntity): LiveData<ResponseMessage>{
       var rest = MutableLiveData<ResponseMessage>()
        interactor.saveSong(songEntity){
            newId -> rest.value = newId
        }

        return rest
    }

    fun updateSong(songEntity: SongEntity):LiveData<ResponseMessage>{
        var rest = MutableLiveData<ResponseMessage>()
        interactor.updateSong(songEntity){
            songupdate -> rest.value = songupdate
        }

        return rest
    }

    fun deleteSong(songEntity: SongEntity):LiveData<ResponseMessage>{
        var rest = MutableLiveData<ResponseMessage>()
        interactor.deleteSong(songEntity){
            sondelete -> rest.value = sondelete
        }
        return rest
    }

    fun getFavorite():LiveData<MutableList<SongEntity>>{
        var rest = MutableLiveData<MutableList<SongEntity>>()
        interactor.getSong {
            rest.value = it
        }

        return rest
    }

}