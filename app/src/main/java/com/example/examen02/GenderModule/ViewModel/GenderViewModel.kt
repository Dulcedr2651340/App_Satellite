package com.example.examen02.GenderModule.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.examen02.GenderModule.Model.GenderInteractor
import com.example.examen02.common.entities.GenderEntity
import com.example.examen02.common.entities.GenderWithSongs
import com.example.examen02.common.entities.ResponseMessage

class GenderViewModel: ViewModel() {

    private val genderSelected = MutableLiveData<GenderEntity>()
    private val interactor: GenderInteractor

    init {
        interactor = GenderInteractor()
    }

    fun getGenderSelected(): LiveData<GenderEntity>{
        return genderSelected
    }

    fun setGenderSelected(genderEntity: GenderEntity){
        genderSelected.value = genderEntity
    }


    fun saveGender(genderEntity: GenderEntity): LiveData<ResponseMessage>{
        var rest = MutableLiveData<ResponseMessage>()
        interactor.saveGender(genderEntity){
            newId -> rest.value = newId
        }

        return rest
    }

    fun updateGender(genderEntity: GenderEntity): LiveData<ResponseMessage>{
        var rest = MutableLiveData<ResponseMessage>()

        interactor.updateGender(genderEntity){
            genderupdate -> rest.value = genderupdate
        }
            return rest
    }

    fun deteleteGender(genderEntity: GenderEntity):LiveData<ResponseMessage>{
        var rest = MutableLiveData<ResponseMessage>()
        interactor.deleteGender(genderEntity){
            genderdelete -> rest.value = genderdelete
        }
        return rest
    }

    fun genderxSong(genderId: Long):LiveData<GenderWithSongs>{

        var rest = MutableLiveData<GenderWithSongs>()
        interactor.getGenderxSong(genderId){
            rest.value = it
        }

        return rest
    }

    //Funcion para que se carge la data en el adapter(liste ahi mismo, refrescar)
    fun getdataGender(): LiveData<MutableList<GenderEntity>>{

        var result = MutableLiveData<MutableList<GenderEntity>>()
        interactor.getGender {
            result.value = it
        }
        return result
    }


    fun getbyId(id:Long): LiveData<GenderEntity>{
        var result = MutableLiveData<GenderEntity>()

        interactor.getgenderbyId(id){
            result.value = it
        }
        return result
    }

    fun getbytype(type:String): LiveData<GenderEntity>{
        var result = MutableLiveData<GenderEntity>()

        interactor.getgenderbytype(type){
            result.value = it
        }
        return result
    }
}