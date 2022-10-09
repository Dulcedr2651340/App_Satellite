package com.example.examen02.common.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "GenderTable")
data class GenderEntity(
    @PrimaryKey(autoGenerate = true)
    var idGender:Long = 0,
    var type:String,
    var description:String,
    var color_code:Int
)
{
    constructor():this(type="", description="", color_code=0)


}