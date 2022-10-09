package com.example.examen02

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examen02.GenderModule.ViewModel.GenderViewModel
import com.example.examen02.common.entities.GenderEntity
import com.example.examen02.common.entities.GenderWithSongs
import com.example.examen02.mainModule.adapter.SongAdapter
import com.example.examen02.common.entities.SongEntity
import com.example.examen02.mainModule.adapter.OnClickListener
import com.example.examen02.databinding.ActivitySongsXgenderBinding
import com.example.examen02.databinding.ItemSongBinding
import com.example.examen02.editModule.DetailFragment
import com.example.examen02.songModule.ViewModel.SongViewModel
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class SongsXGenderActivity : AppCompatActivity(), OnClickListener {

    private lateinit var mBinding: ActivitySongsXgenderBinding
    private lateinit var mAdapter: SongAdapter
    private lateinit var mGridLayoutManager: GridLayoutManager
    lateinit var mGenderViewModel: GenderViewModel
    lateinit var mSongViewModel: SongViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySongsXgenderBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initViewModel()

        var idGender = intent.getLongExtra("idGender", 0L)
        setData(idGender)

        mAdapter = SongAdapter(mutableListOf(), this, "home")
        mGridLayoutManager = GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)

        mBinding.rvSongsGender.apply {
            setHasFixedSize(true)
            adapter = mAdapter
            layoutManager = mGridLayoutManager
        }

        mBinding.appToolbar.setNavigationOnClickListener {
            onBackPressed()
            finish()
        }
    }

    private fun setData(idGender:Long) {
        mGenderViewModel.genderxSong(idGender).observe(this){
                res ->
            mAdapter.setCollection(res.songsList)
        }
    }

    private fun initViewModel() {
        mGenderViewModel = ViewModelProvider(this).get(GenderViewModel::class.java)
        mSongViewModel = ViewModelProvider(this).get(SongViewModel::class.java)
    }

    override fun onClick(songEntity: SongEntity) {
    }

    override fun onClickWebSite(songEntity: SongEntity) {
        var intent = Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(songEntity.videoUrl)
        }
        startActivity(intent)
    }

    override fun onClickFavorite(songEntity: SongEntity) {
        songEntity.isFavorite = !songEntity.isFavorite
        mSongViewModel.updateSong(songEntity).observe(this){
            if(songEntity.isFavorite){
                Snackbar.make(mBinding.root, "Canción agregada a favoritos", Snackbar.LENGTH_SHORT).show()
            }else{
                Snackbar.make(mBinding.root, "Canción removida de favoritos", Snackbar.LENGTH_SHORT).show()
            }
        }

    }

    override fun onLongClickListener(songEntity: SongEntity, itemBinding: ItemSongBinding) {
    }

}