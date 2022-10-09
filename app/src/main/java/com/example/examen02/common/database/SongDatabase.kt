package com.example.examen02.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.examen02.common.entities.GenderEntity
import com.example.examen02.common.entities.SongEntity

@Database(entities = [SongEntity::class, GenderEntity::class], version = 1)
abstract class SongDatabase:RoomDatabase() {
    abstract fun SongDao(): SongDao
    abstract fun GenderDao(): GenderDao
}