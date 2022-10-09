package com.example.examen02.common.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.examen02.common.entities.GenderEntity

@Entity(tableName = "SongTable",
    foreignKeys = [ ForeignKey(
        entity = GenderEntity::class,
        parentColumns = ["idGender"],
        childColumns = ["idGender"]
    )])
data class SongEntity(

    @PrimaryKey(autoGenerate = true)
    var songId: Long = 0,
    var idGender: Long = 0,
    var name: String = "",
    var artist: String = "",
    var album: String = "",
    var duration: String = "00:00",
    var imageUrl: String = "",
    var videoUrl: String = "",
    var isFavorite: Boolean = false
)