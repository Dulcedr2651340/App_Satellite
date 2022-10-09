package com.example.examen02.common.database

import androidx.room.*
import com.example.examen02.common.entities.GenderEntity
import com.example.examen02.common.entities.GenderWithSongs

@Dao
interface GenderDao {

    @Insert
    fun insertDB(genderEntity: GenderEntity): Long

    @Update
    fun updateDB(genderEntity: GenderEntity)

    @Delete
    fun deleteDB(genderEntity: GenderEntity)

    @Query(value = "SELECT * FROM GenderTable WHERE idGender IN (:genderId)")
    fun findByIdDB(genderId: Long): GenderEntity

    @Query(value = "SELECT * FROM GenderTable WHERE type IN (:type)")
    fun findByTypeDB(type:String): GenderEntity

    @Query(value = "SELECT * FROM GenderTable")
    fun findAllDB(): MutableList<GenderEntity>

    @Transaction
    @Query(value = "SELECT * FROM GenderTable")
    fun findAllGenderWithSongs(): MutableList<GenderWithSongs>

    @Transaction
    @Query(value = "SELECT * FROM GenderTable WHERE idGender IN (:genderId)")
    fun findGenderWithSongs(genderId: Long): GenderWithSongs
}
