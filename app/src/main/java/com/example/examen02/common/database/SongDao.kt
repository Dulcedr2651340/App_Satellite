package com.example.examen02.common.database
import androidx.room.*
import com.example.examen02.common.entities.SongEntity

@Dao
interface SongDao {

    @Insert
    fun insertDB(songEntityEntity: SongEntity): Long

    @Update
    fun updateDB(songEntityEntityEntity: SongEntity)

    @Delete
    fun deleteDB(songEntityEntityEntity: SongEntity)

    @Query(value = "SELECT * FROM SongTable WHERE songId IN (:songId)")
    fun findByIdDB(songId: Long): SongEntity

    @Query(value = "SELECT * FROM SongTable")
    fun findAllDB(): MutableList<SongEntity>

    @Query(value = "SELECT * FROM SongTable where idGender IN (:genderId)")
    fun getSongbyGenderId(genderId: Long):MutableList<SongEntity>

}