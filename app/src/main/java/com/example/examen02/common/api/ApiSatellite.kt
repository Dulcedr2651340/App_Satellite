package com.example.examen02.common.api

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class ApiSatellite constructor(context: Context){
    //Patron singleton(La clase se instancia una sola vez)
    //Para acceder desde cualquier clase
    companion object{
        private var INSTANCE:ApiSatellite?=null
        //Crear funcion para Instanciar despues de la primera vez
        fun getInstance(context: Context) = INSTANCE?: synchronized(this){
            INSTANCE?: ApiSatellite(context).also {
                INSTANCE = it
            }
        }
    }

    //variable que administra las operaciones de red
    //By lazy para inicializar un object o una clase
    //RequestQueue cola de solicitudes
    val requestQueue:RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    //funcion generica
    //Agregar las solicitudes a la cola de solitudes
    fun <t> addToRequestQueue(req:Request<t>){
        requestQueue.add(req)
    }
}