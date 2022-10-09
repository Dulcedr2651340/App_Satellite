package com.example.examen02.editModule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examen02.mainModule.adapter.SongAdapter
import com.example.examen02.SongApplication
import com.example.examen02.common.entities.SongEntity
import com.example.examen02.mainModule.adapter.OnClickListener
import com.example.examen02.mainModule.MainActivity
import com.example.examen02.R
import com.example.examen02.databinding.FragmentFavoriteBinding
import com.example.examen02.databinding.ItemSongBinding
import com.example.examen02.mainModule.viewModel.MainViewModel
import com.example.examen02.songModule.ViewModel.SongViewModel
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class FavoriteFragment : Fragment(), OnClickListener {

    lateinit var mBinding: FragmentFavoriteBinding
    private lateinit var mAdapter: SongAdapter
    private lateinit var mGridLayoutManager: GridLayoutManager
    private var mActivity: MainActivity?= null
    lateinit var mSongViewModel: SongViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentFavoriteBinding.inflate(inflater, container, false)

        initViewModel()
        mAdapter = SongAdapter(mutableListOf(), this, "favorite")
        mGridLayoutManager = GridLayoutManager(context, 1, RecyclerView.VERTICAL, false)

        /*
        doAsync {
            val listSongDB = SongApplication.database.SongDao().findAllDB()
            uiThread {
                mAdapter.setCollectionFilterFavorite(listSongDB)
            }
        }
         */

        setData()

        mBinding.rvSongsFavorite.apply {
            setHasFixedSize(true) //que no cambie de tamaño
            adapter = mAdapter
            layoutManager = mGridLayoutManager
        }

        return mBinding.root
    }

    private fun setData() {
       mSongViewModel.getFavorite().observe(viewLifecycleOwner){
           mAdapter.setCollectionFilterFavorite(it)
       }
    }

    private fun initViewModel() {
        mSongViewModel = ViewModelProvider(requireActivity()).get(SongViewModel::class.java)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mActivity?.supportActionBar?.title = getString(R.string.title_favorite)

        setHasOptionsMenu(true)
    }

    override fun onDestroy() {
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mActivity?.supportActionBar?.title = getString(R.string.app_name)
        setHasOptionsMenu(false)
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                mActivity?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onClick(songEntity: SongEntity) {
    }

    override fun onClickWebSite(songEntity: SongEntity) {
        TODO("Not yet implemented")
    }

    override fun onClickFavorite(songEntity: SongEntity) {
    }

    override fun onLongClickListener(songEntity: SongEntity, itemBinding: ItemSongBinding) {
        TODO("Not yet implemented")
    }

}