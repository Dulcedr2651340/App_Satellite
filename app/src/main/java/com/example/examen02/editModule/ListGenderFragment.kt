package com.example.examen02.editModule

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examen02.GenderModule.ViewModel.GenderViewModel
import com.example.examen02.mainModule.adapter.GenderAdapter
import com.example.examen02.common.entities.GenderEntity
import com.example.examen02.mainModule.adapter.OnClickGenderListener
import com.example.examen02.mainModule.MainActivity
import com.example.examen02.R
import com.example.examen02.SongsXGenderActivity
import com.example.examen02.databinding.FragmentListGenderBinding
import com.example.examen02.databinding.ItemGenderBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class ListGenderFragment : Fragment(), OnClickGenderListener {

    lateinit var mBinding: FragmentListGenderBinding
    private lateinit var mAdapter: GenderAdapter
    private lateinit var mGridLayoutManager: GridLayoutManager

    lateinit var mGenderViewModel: GenderViewModel

    private var mActivity: MainActivity?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        mBinding = FragmentListGenderBinding.inflate(layoutInflater)
        mAdapter = GenderAdapter(mutableListOf(), this)

        initViewModel()

        mGridLayoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)

        /*
        doAsync {
            val listGenderDB = SongApplication.database.GenderDao().findAllDB()
            uiThread {
                mAdapter.setCollection(listGenderDB)
            }
        }
         */

        setdataAdapter()

        mBinding.rvGender.apply {
            setHasFixedSize(true)
            adapter = mAdapter
            layoutManager = mGridLayoutManager
        }

        return mBinding.root
    }

    private fun setdataAdapter(){

        mGenderViewModel.getdataGender().observe(viewLifecycleOwner){

            genderlist -> mAdapter.setCollection(genderlist)
        }
    }


    //Inicializacion del ViewModel
    private fun initViewModel() {
        mGenderViewModel = ViewModelProvider(requireActivity()).get(GenderViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mActivity = activity as MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mActivity?.supportActionBar?.title = "Genders"
        setHasOptionsMenu(true)

        super.onViewCreated(view, savedInstanceState)
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

    override fun onDestroy() {
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mActivity?.supportActionBar?.title = getString(R.string.app_name)
        setHasOptionsMenu(false)
        super.onDestroy()
    }

    override fun onClickGender(genderEntity: GenderEntity) {

        var intent = Intent(mActivity,SongsXGenderActivity::class.java)
        intent.putExtra("idGender", genderEntity.idGender)
        startActivity(intent)
    }

    override fun onLongClickGender(genderEntity: GenderEntity, itemBinding: ItemGenderBinding) {
        showMenu(itemBinding.root, R.menu.song_actions_menu, genderEntity)
    }

    @SuppressLint("RestrictedApi")
    private fun showMenu(v: View, @MenuRes menuRes: Int, genderEntity: GenderEntity) {
        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.itemEditSong -> editGender(genderEntity)
                R.id.itemDeleteSong -> deleteGender(genderEntity)
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


    private fun editGender(genderEntity: GenderEntity) {
        mGenderViewModel.setGenderSelected(genderEntity)
        mActivity?.setThatFragment(GenderFragment())
    }

    private fun deleteGender(genderEntity: GenderEntity){

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Eliminar")
            .setMessage("¿Seguro de eliminar este genero?")
            .setPositiveButton("Si"){dialog, i ->

                mGenderViewModel.deteleteGender(genderEntity)
                mAdapter.genderDelete(genderEntity)
                Snackbar.make(mBinding.root, "Genero eliminada", Snackbar.LENGTH_SHORT).show()
                setdataAdapter()
                dialog.dismiss()

            }.setNegativeButton("No"){dialog, i ->
                dialog.dismiss()
            }.show()
    }

}
