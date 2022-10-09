package com.example.examen02.mainModule.adapter

import com.example.examen02.common.entities.SongEntity
import com.example.examen02.databinding.ItemSongBinding

interface OnClickListener {

    fun onClick(songEntity: SongEntity)
    fun onClickWebSite(songEntity: SongEntity)
    fun onClickFavorite(songEntity: SongEntity)
    fun onLongClickListener(songEntity: SongEntity, itemBinding: ItemSongBinding)


}