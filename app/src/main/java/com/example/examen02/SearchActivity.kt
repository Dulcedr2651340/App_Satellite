package com.example.examen02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examen02.GenderModule.ViewModel.GenderViewModel
import com.example.examen02.mainModule.adapter.GenderAdapter
import com.example.examen02.mainModule.adapter.SongAdapter
import com.example.examen02.common.entities.GenderEntity
import com.example.examen02.common.entities.SongEntity
import com.example.examen02.mainModule.adapter.OnClickGenderListener
import com.example.examen02.mainModule.adapter.OnClickListener
import com.example.examen02.databinding.ActivitySearchBinding
import com.example.examen02.databinding.ItemGenderBinding
import com.example.examen02.databinding.ItemSongBinding
import com.example.examen02.mainModule.viewModel.MainViewModel
import com.example.examen02.songModule.ViewModel.SongViewModel
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SearchActivity : AppCompatActivity(), OnClickListener, OnClickGenderListener {

    private lateinit var mBinding: ActivitySearchBinding
    private lateinit var mAdapterSong: SongAdapter
    private lateinit var mAdapterGender: GenderAdapter
    private lateinit var mGridLayoutManagerSong: GridLayoutManager
    private lateinit var mGridLayoutManagerGender: GridLayoutManager
    lateinit var mMainViewModel: MainViewModel
    lateinit var mGenderViewModel: GenderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.svSearch.setQueryHint(Html.fromHtml("<font color = #FF000000><b>" + "Buscar generos y canciones" + "</b></font>"))

        mAdapterSong = SongAdapter(mutableListOf(), this, "home", mBinding.rvSongs, mBinding.rvGenders)
        mGridLayoutManagerSong = GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)
        mAdapterGender = GenderAdapter(mutableListOf(), this)
        mGridLayoutManagerGender = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)

        initgetdata()
        setdataSong()
        setdataGender()

        mBinding.rvSongs.apply {
            setHasFixedSize(true) //que no cambie de tamaño
            adapter = mAdapterSong
            layoutManager = mGridLayoutManagerSong
        }
        mBinding.rvGenders.apply {
            setHasFixedSize(true) //que no cambie de tamaño
            adapter = mAdapterGender
            layoutManager = mGridLayoutManagerGender
        }

        mBinding.appToolbar.setNavigationOnClickListener {
            onBackPressed()
            finish()
        }

        mBinding.svSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(text: String?): Boolean {
                mAdapterSong.filter(text!!)
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }
        })
    }

    private fun setdataGender() {
        mGenderViewModel.getdataGender().observe(this){
            mAdapterGender.setCollection(it)
        }
    }

    private fun setdataSong() {
        mMainViewModel.getdatasong().observe(this){
            mAdapterSong.setCollection(it)
        }
    }

    private fun initgetdata() {
        mGenderViewModel = ViewModelProvider(this).get(GenderViewModel::class.java)
        mMainViewModel= ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onClick(songEntity: SongEntity) {
    }

    override fun onClickWebSite(songEntity: SongEntity) {
        TODO("Not yet implemented")
    }

    override fun onClickFavorite(songEntity: SongEntity) {
    }

    override fun onLongClickListener(songEntity: SongEntity, itemBinding: ItemSongBinding) {
    }

    override fun onClickGender(genderEntity: GenderEntity) {
        var intent = Intent(this,SongsXGenderActivity::class.java)
        intent.putExtra("idGender", genderEntity.idGender)
        startActivity(intent)
    }

    override fun onLongClickGender(genderEntity: GenderEntity, itemBinding: ItemGenderBinding) {
        Toast.makeText(this, "${genderEntity.type}", Toast.LENGTH_SHORT).show()
    }
}