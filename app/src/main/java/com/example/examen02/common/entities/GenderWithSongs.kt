package com.example.examen02.common.entities

import androidx.room.Embedded
import androidx.room.Relation

class GenderWithSongs(
    @Embedded
    var gender: GenderEntity,
    @Relation(parentColumn = "idGender", entityColumn =  "idGender")
    var songsList: MutableList<SongEntity>
)