package com.example.examen02.mainModule.model

import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.examen02.SongApplication
import com.example.examen02.common.entities.SongEntity
import com.example.examen02.common.utils.Constants
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.reflect.Type
import javax.security.auth.callback.Callback

//Para traer la data
class MainInteractor {

    val URL = Constants.BASE_URL+Constants.SONG_PATH

    fun getSong(callback: (MutableList<SongEntity>) -> Unit){

        var resquestGetAllSong =
            JsonObjectRequest(Request.Method.GET, URL,
                null,
                {
                    response ->

                    //La reepsuesta
                    var data = response.getJSONArray("data").toString()
                    Log.i("Lista---------------------------------------------------------------------->","" + data)
                    var typeData = object:TypeToken<MutableList<SongEntity>>(){}.type
                    var songList = Gson().fromJson<MutableList<SongEntity>>(data, typeData)

                    callback(songList)
                },
                {
                    error ->
                    Log.i("Error ---------------------------------------------------------------------->","" + error.message)
                    error.printStackTrace()
                })

        SongApplication.apiSatellite.addToRequestQueue(resquestGetAllSong)

        /*
        doAsync {

            var songlistdb = SongApplication.database.SongDao().findAllDB()

            uiThread {

                callback(songlistdb)
            }
        }
         */

    }
}