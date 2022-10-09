package com.example.examen02.editModule

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.examen02.GenderModule.ViewModel.GenderViewModel
import com.example.examen02.SongApplication
import com.example.examen02.common.entities.SongEntity
import com.example.examen02.mainModule.MainActivity
import com.example.examen02.R
import com.example.examen02.ValidatorUtil
import com.example.examen02.databinding.FragmentSongBinding
import com.example.examen02.songModule.ViewModel.SongViewModel
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SongFragment : Fragment() {

    lateinit var mBinding: FragmentSongBinding
    private var mActivity: MainActivity? = null
    private var isEdit = false
    private var id: Long? = 0L
    private var genderList: MutableList<String> = mutableListOf()
    lateinit var mSongViewModel: SongViewModel
    lateinit var mGenderViewModel: GenderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = FragmentSongBinding.inflate(inflater, container, false)

        initViewModel()
        setDataGender()
        setData()

        mBinding.btnSaveSong.setOnClickListener {
            if (ValidatorUtil.validateFields(
                    mBinding.tilSongImage,
                    mBinding.tilSongDuration,
                    mBinding.tilSongAlbum,
                    mBinding.tilSongArtist,
                    mBinding.tilSongName
                )
            ) {
                saveSong()
            }

        }

        return mBinding.root
    }

    private fun initViewModel() {
        mGenderViewModel = ViewModelProvider(requireActivity()).get(GenderViewModel::class.java)
        mSongViewModel = ViewModelProvider(requireActivity()).get(SongViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = activity as? MainActivity
        //mostrar fecha de retroceso
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //mostrar titulo
        mActivity?.supportActionBar?.title = getString(R.string.title_new_song)
        //acceso al menu
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                //code flecha retroceso
                mActivity?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setDataGender() {

        mGenderViewModel.getdataGender().observe(viewLifecycleOwner) {
            for (gender in it) {
                genderList.add(gender.type)
            }
            val adapter = ArrayAdapter(
                requireContext(), R.layout.list_item, genderList
            )
            mBinding.actvGender.setAdapter(adapter)
        }


        /*
        doAsync {
            var list = SongApplication.database.GenderDao().findAllDB()
            uiThread {
                for(gender in list){
                    genderList.add(gender.type)
                }
                val adapter = ArrayAdapter(
                    requireContext(), R.layout.list_item, genderList)
                mBinding.actvGender.setAdapter(adapter)
            }
        }
         */
    }

    override fun onDestroy() {
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mActivity?.supportActionBar?.title = getString(R.string.app_name)
        setHasOptionsMenu(false)
        mSongViewModel.setSongSelected(SongEntity())
        super.onDestroy()
    }

    private fun saveSong() {
        var name = mBinding.edtSongName.text.toString()
        var video = mBinding.edtSongVideo.text.toString()
        var images = mBinding.edtSongImage.text.toString()
        var duration = mBinding.edtSongDuration.text.toString()
        var artist = mBinding.edtSongArtist.text.toString()
        var album = mBinding.edtSongAlbum.text.toString()


        mGenderViewModel.getbytype(mBinding.actvGender.text.toString())
            .observe(viewLifecycleOwner) {
                var idGender = it.idGender

                var song: SongEntity
                if (id == 0L) {
                    song = SongEntity(
                        idGender = idGender,
                        name = name,
                        artist = artist,
                        album = album,
                        duration = duration,
                        imageUrl = images,
                        videoUrl = video
                    )
                    mSongViewModel.saveSong(song).observe(viewLifecycleOwner) {
                        Snackbar.make(mBinding.root, "Cancion guardada", Snackbar.LENGTH_SHORT)
                            .show()
                        clear()
                    }


                    /*
                    doAsync {
                        SongApplication.database.SongDao().insertDB(song)
                        uiThread {
                            Snackbar.make(mBinding.root, "Cancion guardada", Snackbar.LENGTH_SHORT).show()
                            clear()
                        }


                    }

                     */
                } else {
                    song = SongEntity(
                        songId = id!!,
                        idGender = idGender,
                        name = name,
                        artist = artist,
                        album = album,
                        duration = duration,
                        imageUrl = images,
                        videoUrl = video
                    )
                    mSongViewModel.updateSong(song).observe(viewLifecycleOwner) {
                        Snackbar.make(mBinding.root, "Cancion actualizada", Snackbar.LENGTH_SHORT)
                            .show()
                        clear()
                    }
                    /*
                    doAsync {
                        SongApplication.database.SongDao().updateDB(song)
                        uiThread {
                            Snackbar.make(mBinding.root, "Cancion actualizada", Snackbar.LENGTH_SHORT).show()
                            clear()
                        }
                    }

                     */
                }
            }

        /*
        doAsync {
            var genderEntity = SongApplication.database.GenderDao().findByTypeDB(mBinding.actvGender.text.toString())
            uiThread {
                var idGender = genderEntity.idGender

                var song: SongEntity
                if(id==null){
                    song = SongEntity(idGender = idGender,name = name, artist = artist, album = album, duration = duration, imageUrl = images, videoUrl = video)
                    doAsync {
                        SongApplication.database.SongDao().insertDB(song)
                        uiThread {
                            Snackbar.make(mBinding.root, "Cancion guardada", Snackbar.LENGTH_SHORT).show()
                            clear()
                        }
                    }
                }else{
                    song = SongEntity(songId = id!!,idGender = idGender,name = name, artist = artist, album = album, duration = duration, imageUrl = images, videoUrl = video)
                    doAsync {
                        SongApplication.database.SongDao().updateDB(song)
                        uiThread {
                            Snackbar.make(mBinding.root, "Cancion actualizada", Snackbar.LENGTH_SHORT).show()
                            clear()
                        }
                    }
                }

            }
         */
    }


private fun setData() {


    mSongViewModel.getSongSelected().observe(viewLifecycleOwner) {

            songEntity ->
        if (songEntity.songId != 0L) {

            mGenderViewModel.getbyId(songEntity.idGender).observe(viewLifecycleOwner) {
                with(mBinding) {
                    id = songEntity.songId
                    edtSongName.setText(songEntity.name)
                    edtSongArtist.setText(songEntity.artist)
                    edtSongAlbum.setText(songEntity.album)
                    edtSongDuration.setText(songEntity.duration)
                    actvGender.setText(it.type)
                    edtSongImage.setText(songEntity.imageUrl)
                    edtSongVideo.setText(songEntity.videoUrl)
                    setDataGender()
                }

            }

            /*
            doAsync {
                var gender =
                    SongApplication.database.GenderDao().findByIdDB(songEntity.idGender)
                uiThread {
                    with(mBinding) {
                        edtSongName.setText(songEntity.name)
                        edtSongArtist.setText(songEntity.artist)
                        edtSongAlbum.setText(songEntity.album)
                        edtSongDuration.setText(songEntity.duration)
                        actvGender.setText(gender.type)
                        edtSongImage.setText(songEntity.imageUrl)
                        edtSongVideo.setText(songEntity.videoUrl)
                        setDataGender()
                    }
                }

             */
        }
    }
}


private fun clear() {
    var handler = Handler()
    handler.postDelayed(Runnable {
        mActivity?.onBackPressed()
    }, 500)
}

}