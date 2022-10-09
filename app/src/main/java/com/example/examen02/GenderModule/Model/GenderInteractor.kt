package com.example.examen02.GenderModule.Model

import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.examen02.SongApplication
import com.example.examen02.common.entities.GenderEntity
import com.example.examen02.common.entities.GenderWithSongs
import com.example.examen02.common.entities.ResponseMessage
import com.example.examen02.common.entities.SongEntity
import com.example.examen02.common.utils.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject

class GenderInteractor{

    var url = Constants.BASE_URL+Constants.GENDER_PATH

    fun getGenderxSong(genderId: Long, callback: (GenderWithSongs) -> Unit){

        var requestget = JsonObjectRequest(Request.Method.GET, "$url/genderxsong/$genderId", null,{
                rest ->
            var data = rest.getJSONObject("data").toString()
            var dataType = object:TypeToken<GenderWithSongs>(){}.type
            var genderList = Gson().fromJson<GenderWithSongs>(data,dataType)
            callback(genderList)
        },{
                error ->
            error.printStackTrace()
        })

        SongApplication.apiSatellite.addToRequestQueue(requestget)


        /*
        doAsync {

            var genderWithSongs = SongApplication.database.GenderDao().findGenderWithSongs(genderId)

            uiThread {
                callback(genderWithSongs)
            }
        }
         */

    }


    fun getGender(callback: (MutableList<GenderEntity>) -> Unit){

        var requestget = JsonObjectRequest(Request.Method.GET, url, null,{
            rest ->
            var data = rest.getJSONArray("data").toString()
            var dataType = object:TypeToken<MutableList<GenderEntity>>(){}.type
            var genderList = Gson().fromJson<MutableList<GenderEntity>>(data,dataType)
            callback(genderList)
        },{
            error ->
            error.printStackTrace()
        })

        SongApplication.apiSatellite.addToRequestQueue(requestget)

        /*
        doAsync {

            var genderlistdb = SongApplication.database.GenderDao().findAllDB()

            uiThread {

                callback(genderlistdb)
            }
        }
         */

    }


    fun saveGender(genderEntity: GenderEntity, callback: (ResponseMessage) -> Unit) {

        var genderjson = JSONObject(Gson().toJson(genderEntity))
        var requestGenderSong = JsonObjectRequest(Request.Method.POST, url, genderjson, { rest ->
            var data = rest.toString()
            var dataType = object:TypeToken<ResponseMessage>(){}.type
            var response = Gson().fromJson<ResponseMessage>(data, dataType)
            callback(response)
        },{
                error ->
            error.printStackTrace()
        })

        SongApplication.apiSatellite.addToRequestQueue(requestGenderSong)

        /*
        doAsync {
            var newId = SongApplication.database.GenderDao().insertDB(genderEntity)

            uiThread {
                callback(newId)
            }
        }
         */
    }


    fun updateGender(genderEntity: GenderEntity, callback: (ResponseMessage) -> Unit){

        var genderjson = JSONObject(Gson().toJson(genderEntity))
        var requestGenderSong = JsonObjectRequest(Request.Method.PUT, url, genderjson, { rest ->
            var data = rest.toString()
            var dataType = object:TypeToken<ResponseMessage>(){}.type
            var response = Gson().fromJson<ResponseMessage>(data, dataType)
            callback(response)
        },{
                error ->
            error.printStackTrace()
        })

        SongApplication.apiSatellite.addToRequestQueue(requestGenderSong)

        /*
        doAsync {
            SongApplication.database.GenderDao().updateDB(genderEntity)

            uiThread {
                callback(genderEntity)
            }
        }
         */
    }

    fun deleteGender(genderEntity: GenderEntity, callback: (ResponseMessage) -> Unit){

        var request = JsonObjectRequest(Request.Method.DELETE, "$url/${genderEntity.idGender}", null, {
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
            SongApplication.database.GenderDao().deleteDB(genderEntity)

            uiThread {
                callback(genderEntity)
            }
        }
         */
    }

    fun getgenderbyId(genderId: Long,callback: (GenderEntity) -> Unit){

        var requestget = JsonObjectRequest(Request.Method.GET, "$url/$genderId", null,{
                rest ->
            var data = rest.getJSONObject("data").toString()
            var dataType = object:TypeToken<GenderEntity>(){}.type
            var genderList = Gson().fromJson<GenderEntity>(data,dataType)
            callback(genderList)
        },{
                error ->
            error.printStackTrace()
        })

        SongApplication.apiSatellite.addToRequestQueue(requestget)


        /*
        doAsync {


           var genderEntity = SongApplication.database.GenderDao().findByIdDB(genderId)

            uiThread {
                callback(genderEntity)
            }
        }
         */
    }


    fun getgenderbytype(type:String, callback: (GenderEntity) -> Unit){

        var request = JsonObjectRequest(Request.Method.GET, "$url/getbyType/$type", null,{
                rest ->
            var data = rest.getJSONObject("data").toString()
            var dataType = object:TypeToken<GenderEntity>(){}.type
            var genderList = Gson().fromJson<GenderEntity>(data,dataType)
            callback(genderList)

        },{
            error ->
            error.printStackTrace()
        })

        SongApplication.apiSatellite.addToRequestQueue(request)


        /*
        doAsync {

            var genderEntity = SongApplication.database.GenderDao().findByTypeDB(type)

            uiThread {

                callback(genderEntity)
            }
        }
         */
    }
}