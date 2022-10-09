package com.example.examen02.songModule.Model

import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.examen02.SongApplication
import com.example.examen02.common.entities.GenderWithSongs
import com.example.examen02.common.entities.ResponseMessage
import com.example.examen02.common.entities.SongEntity
import com.example.examen02.common.utils.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import javax.security.auth.callback.Callback

class SongInteractor {

    val URL = Constants.BASE_URL + Constants.SONG_PATH

    fun saveSong(songEntity: SongEntity, callback: (ResponseMessage) -> Unit) {

        var songjson = JSONObject(Gson().toJson(songEntity))
        var requestSaveSong = JsonObjectRequest(Request.Method.POST, URL, songjson, { rest ->
            var data = rest.toString()
            var dataType = object:TypeToken<ResponseMessage>(){}.type
            var response = Gson().fromJson<ResponseMessage>(data, dataType)
            callback(response)
        },{
            error ->
            error.printStackTrace()
        })

        SongApplication.apiSatellite.addToRequestQueue(requestSaveSong)
        /*
        doAsync {

               var newId = SongApplication.database.SongDao().insertDB(songEntity)

            uiThread {
                callback(newId)
            }
        }
         */
    }


    fun updateSong(songEntity: SongEntity, callback: (ResponseMessage) -> Unit) {

        //Preparando el json
        var songJson = JSONObject(Gson().toJson(songEntity))
        var request = JsonObjectRequest(Request.Method.PUT, URL, songJson, {
            rest ->
            var data = rest.toString()
            var dataType = object:TypeToken<ResponseMessage>(){}.type
            var response = Gson().fromJson<ResponseMessage>(data, dataType)
            callback(response)
        }, {
            error ->
            error.printStackTrace()
        })
        SongApplication.apiSatellite.addToRequestQueue(request)
        /*
        doAsync {

            SongApplication.database.SongDao().updateDB(songEntity)

            uiThread {

                callback(songEntity)
            }
        }
         */
    }

    fun deleteSong(songEntity: SongEntity, callback: (ResponseMessage) -> Unit) {

        var request = JsonObjectRequest(Request.Method.DELETE, "$URL/${songEntity.songId}", null, {
            var data = it.toString()
            var dataType = object:TypeToken<ResponseMessage>(){}.type
            var response = Gson().fromJson<ResponseMessage>(data, dataType)
            callback(response)
        },{
            error ->
            error.printStackTrace()
        })
        SongApplication.apiSatellite.addToRequestQueue(request)
        /*
        doAsync {
            SongApplication.database.SongDao().deleteDB(songEntity)

            uiThread {
                callback(songEntity)
            }
        }
         */
    }

    fun getSong(callback: (MutableList<SongEntity>) -> Unit) {

        var resquestGetAllSong =
            JsonObjectRequest(Request.Method.GET, "$URL/favorite",
                null,
                { response ->

                    //La reepsuesta
                    var data = response.getJSONArray("data").toString()
                    Log.i(
                        "Lista---------------------------------------------------------------------->",
                        "" + data
                    )
                    var typeData = object : TypeToken<MutableList<SongEntity>>() {}.type
                    var songList = Gson().fromJson<MutableList<SongEntity>>(data, typeData)

                    callback(songList)
                },
                { error ->
                    Log.i(
                        "Error ---------------------------------------------------------------------->",
                        "" + error.message
                    )
                    error.printStackTrace()
                })

        SongApplication.apiSatellite.addToRequestQueue(resquestGetAllSong)
    }

    }