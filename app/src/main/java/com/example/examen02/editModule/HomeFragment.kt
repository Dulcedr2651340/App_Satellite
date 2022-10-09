package com.example.examen02.editModule

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examen02.mainModule.adapter.SongAdapter
import com.example.examen02.SongApplication
import com.example.examen02.common.entities.SongEntity
import com.example.examen02.mainModule.adapter.OnClickListener
import com.example.examen02.mainModule.MainActivity
import com.example.examen02.R
import com.example.examen02.SongApplication.Companion.song
import com.example.examen02.databinding.FragmentHomeBinding
import com.example.examen02.databinding.ItemSongBinding
import com.example.examen02.mainModule.viewModel.MainViewModel
import com.example.examen02.songModule.ViewModel.SongViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class HomeFragment : Fragment(), OnClickListener {

    lateinit var mBinding: FragmentHomeBinding
    private lateinit var mAdapter: SongAdapter
    private lateinit var mGridLayoutManager: GridLayoutManager
    private var mActivity: MainActivity?= null

    lateinit var mMainViewModel: MainViewModel
    lateinit var mSongViewModel: SongViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        mActivity = activity as? MainActivity

        initViewModel()

        mAdapter = SongAdapter(mutableListOf(), this, "home")
        mGridLayoutManager = GridLayoutManager(context, 1, RecyclerView.VERTICAL, false)

        //ejecutar hilo (cargar coleccion)
        /*
        doAsync {
            val listSongDB = SongApplication.database.SongDao().findAllDB()
            uiThread {
                mAdapter.setCollection(listSongDB)
            }
        }
         */

        setdataAdapter()

        mBinding.rvSongs.apply {
            setHasFixedSize(true) //que no cambie de tamaño
            adapter = mAdapter
            layoutManager = mGridLayoutManager
        }

        mBinding.btnAddSong.setOnClickListener{
           mActivity?.setThatFragment(SongFragment())
        }

        mBinding.btnAddGender.setOnClickListener {
            mActivity?.setThatFragment(GenderFragment())
        }

        return mBinding.root
    }


    private fun setdataAdapter() {

        mMainViewModel.getdatasong().observe(viewLifecycleOwner){

            songlist -> mAdapter.setCollection(songlist)
        }

    }

    //Inicializacion de viewModel
    private fun initViewModel() {
        mMainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        mSongViewModel = ViewModelProvider(requireActivity()).get(SongViewModel::class.java)
    }

    override fun onClick(songEntity: SongEntity) {
        /*
        var bundle = Bundle()
        bundle.putLong("songIdKey",songEntity.songId)
         */

        mSongViewModel.setSongSelected(songEntity)
        mActivity?.setThatFragment(DetailFragment())
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
        mSongViewModel.updateSong(songEntity).observe(viewLifecycleOwner){
            if(songEntity.isFavorite){
                Snackbar.make(mBinding.root, "Canción agregada a favoritos", Snackbar.LENGTH_SHORT).show()
            }else{
                Snackbar.make(mBinding.root, "Canción removida de favoritos", Snackbar.LENGTH_SHORT).show()
            }
        }


        /*
        mSongViewModel.getResult().observe(viewLifecycleOwner){

            song -> song as SongEntity

            if(song.isFavorite){
                Snackbar.make(mBinding.root, "Canción agregada a favoritos", Snackbar.LENGTH_SHORT).show()
            }else{
                Snackbar.make(mBinding.root, "Canción removida de favoritos", Snackbar.LENGTH_SHORT).show()
            }
        }
         */

    }

    override fun onLongClickListener(songEntity: SongEntity, itemBinding: ItemSongBinding) {
        registerForContextMenu(itemBinding.root)
        showMenu(itemBinding.root, R.menu.song_actions_menu, songEntity)

    }

    @SuppressLint("RestrictedApi")
    private fun showMenu(v: View, @MenuRes menuRes: Int, songEntity: SongEntity) {
        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.itemEditSong -> editSong(songEntity)
                R.id.itemDeleteSong -> deleteSong(songEntity)
            }
            true
        }

        try {
            val popupF = PopupMenu::class.java.getDeclaredField("mPopup")
            popupF.isAccessible = true
            val menus = popupF.get(popup)
            menus.javaClass
                .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(menus, true)

        }catch (e:Exception){
            e.printStackTrace()
        }finally {
            popup.show()
        }
    }

    private fun editSong(songEntity: SongEntity){
        mSongViewModel.setSongSelected(songEntity)
        mActivity?.setThatFragment(SongFragment())

    }

    private fun deleteSong(songEntity: SongEntity){

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Eliminar")
            .setMessage("¿Seguro de eliminar esta canción?")
            .setPositiveButton("Si"){dialog, i ->

                mSongViewModel.deleteSong(songEntity).observe(viewLifecycleOwner){
                    Snackbar.make(mBinding.root, it.message, Snackbar.LENGTH_SHORT).show()
                    setdataAdapter()
                    dialog.dismiss()
                    mAdapter.songDelete(songEntity)
                }
            }.setNegativeButton("No"){dialog, i ->
                dialog.dismiss()
            }.show()
    }

}