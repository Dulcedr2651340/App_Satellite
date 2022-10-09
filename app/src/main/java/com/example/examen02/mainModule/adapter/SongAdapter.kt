package com.example.examen02.mainModule.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.examen02.R
import com.example.examen02.databinding.ItemSongBinding
import com.example.examen02.common.entities.SongEntity

class SongAdapter(
    private var songsList: MutableList<SongEntity>,
    private var listener: OnClickListener,
    private var mode:String,
    private var recyclerView: RecyclerView?=null,
    private var recyclerGenderView: RecyclerView?=null
): RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    lateinit var mContext:Context
    var listOriginal = mutableListOf<SongEntity>()

    inner class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemBinding = ItemSongBinding.bind(itemView)

        fun setListener(songEntity: SongEntity){

            itemBinding.root.setOnClickListener {
                listener.onClick(songEntity)
            }

            itemBinding.btnWebSite.setOnClickListener {
                listener.onClickWebSite(songEntity)
            }

            itemBinding.cbFavorite.setOnClickListener {
                listener.onClickFavorite(songEntity)
            }

            itemBinding.root.setOnLongClickListener {
                listener.onLongClickListener(songEntity, itemBinding)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        mContext = parent.context
        var view = LayoutInflater.from(mContext).inflate(R.layout.item_song, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        var song = songsList[position]
        with(holder){

            itemBinding.tvNameSong.text = song.name
            itemBinding.tvDurationSong.text = song.duration
            if(mode == "favorite"){
                itemBinding.cbFavorite.visibility = View.GONE
            }
            itemBinding.cbFavorite.isChecked = song.isFavorite
            Glide.with(mContext).load(song.imageUrl).into(itemBinding.imgSong)
            setListener(song)
        }
    }

    override fun getItemCount(): Int {
        return songsList.size
    }

    fun setCollection(list: MutableList<SongEntity>){
        songsList.clear()
        listOriginal.clear()
        songsList.addAll(list)
        listOriginal.addAll(list)
        notifyDataSetChanged()
    }

    fun setCollectionFilterFavorite(list: MutableList<SongEntity>){
        var listFavorite: MutableList<SongEntity> = mutableListOf()
        for(i in list.indices){
            var songEntity = list[i]
            if(songEntity.isFavorite){
                listFavorite.add(songEntity)
            }
        }
        songsList.addAll(listFavorite)
        notifyDataSetChanged()
    }

    fun songDelete(songEntity: SongEntity) {
        val index = songsList.indexOf(songEntity)
        songsList.removeAt(index)
        notifyItemRemoved(index)
    }

    fun filter(text:String){
        if(text.isEmpty()){
            songsList.clear()
            songsList.addAll(listOriginal)
            recyclerView?.visibility = View.INVISIBLE
            recyclerGenderView?.visibility = View.VISIBLE
        }else{
            var list = songsList.filter { song -> song.name.contains(text,true)}.toMutableList()
            songsList.clear()
            songsList.addAll(list)
            recyclerView?.visibility = View.VISIBLE
            recyclerGenderView?.visibility = View.GONE
        }
        notifyDataSetChanged()
    }
}