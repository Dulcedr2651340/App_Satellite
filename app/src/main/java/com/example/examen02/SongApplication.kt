package com.example.examen02

import android.app.Application
import androidx.room.Room
import com.example.examen02.common.api.ApiSatellite
import com.example.examen02.common.database.SongDatabase
import com.example.examen02.common.entities.SongEntity

class SongApplication:Application(){

    companion object {
        lateinit var database: SongDatabase
        //Declarando de forma global el ApiSatellite
        lateinit var apiSatellite: ApiSatellite
        var song :MutableList<SongEntity> = mutableListOf()
    }

    override fun onCreate() {
        super.onCreate()

        //cargar database
        database = Room
            .databaseBuilder(this, SongDatabase::class.java, "SongDatabase")
            .build()

        //Instanciando el
        apiSatellite = ApiSatellite.getInstance(this)
    }
}