package com.example.examen02.editModule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.examen02.common.entities.SongEntity
import com.example.examen02.mainModule.MainActivity
import com.example.examen02.R
import com.example.examen02.databinding.FragmentDetailBinding
import com.example.examen02.songModule.ViewModel.SongViewModel
import com.google.android.material.snackbar.Snackbar


class DetailFragment : Fragment() {

    private lateinit var mBinding: FragmentDetailBinding
    private var mActivity: MainActivity? = null
    private lateinit var mSongViewModel: SongViewModel
    var songEntity: SongEntity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentDetailBinding.inflate(inflater, container, false)

        initViewModel()
        cargarData()

        mBinding.cdSongFavoriteDetail.setOnClickListener {
            setFavorite(songEntity!!)
        }

        return mBinding.root
    }


    private fun initViewModel() {
        mSongViewModel = ViewModelProvider(requireActivity()).get(SongViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mActivity = activity as? MainActivity

        //mostrar flecha de retroceso
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //mostrar titulo
        mActivity?.supportActionBar?.title = getString(R.string.title_song_detail)

        //acceso al menu
        setHasOptionsMenu(true)

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mActivity?.supportActionBar?.title = getString(R.string.app_name)
        setHasOptionsMenu(false)
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                mActivity?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun setFavorite(song: SongEntity) {

        song.isFavorite = !song.isFavorite
        mSongViewModel.updateSong(song).observe(viewLifecycleOwner) {
            if (song.isFavorite) {
                Snackbar.make(mBinding.root, "Canción agregada a favoritos", Snackbar.LENGTH_SHORT)
                    .show()
            } else {
                Snackbar.make(mBinding.root, "Canción removida de favoritos", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

        /*
        doAsync {
            SongApplication.database.SongDao().updateDB(song)
            uiThread {
                if(song.isFavorite){
                    Snackbar.make(mBinding.root, "Canción agregada a favoritos", Snackbar.LENGTH_SHORT).show()
                }else{
                    Snackbar.make(mBinding.root, "Canción removida de favoritos", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
         */
    }

    private fun cargarData() {

        mSongViewModel.getSongSelected().observe(viewLifecycleOwner) {

                song ->
            mBinding.tvSongNameDetail.text = song.name
            mBinding.tvSongAlbumDetail.text = song.album
            mBinding.tvSongArtistDetail.text = song.artist
            mBinding.tvSongDurationDetail.text = song.duration
            mBinding.cdSongFavoriteDetail.isChecked = song.isFavorite
            songEntity = song

            Glide.with(mBinding.root).load(song.imageUrl).into(mBinding.imgSongDetail)
        }

    }

}