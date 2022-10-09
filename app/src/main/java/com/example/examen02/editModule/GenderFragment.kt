
package com.example.examen02.editModule

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.lifecycle.ViewModelProvider
import com.example.examen02.GenderModule.ViewModel.GenderViewModel
import com.example.examen02.SongApplication
import com.example.examen02.common.entities.GenderEntity
import com.example.examen02.mainModule.MainActivity
import com.example.examen02.R
import com.example.examen02.ValidatorUtil
import com.example.examen02.databinding.FragmentGenderBinding
import com.google.android.material.snackbar.Snackbar
import com.pes.androidmaterialcolorpickerdialog.ColorPicker
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class GenderFragment : Fragment() {

    private lateinit var mBinding: FragmentGenderBinding
    private var mActivity: MainActivity?= null
    private var isEdit = false
    private var idG:Long?=0L
    private var colorCode:Int?=null
    private var colorDefault:List<Int> = arrayListOf(0,0,0)

    lateinit var mViewModel: GenderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        initSaveData()

        mBinding = FragmentGenderBinding.inflate(inflater, container, false)
        setData()
        /*
        var idGender = arguments?.getLong("idGenderKey",0L)

        if(idGender == 0L || idGender != null){
            mBinding.tvTitleNewPhone.text = "Edit Gender"
            idG = idGender
            isEdit = true
            doAsync {
                var genderEntity = SongApplication.database.GenderDao().findByIdDB(idGender)
                uiThread {
                    setData(genderEntity)
                }
            }
        }else{
            mBinding.tvTitleNewPhone.text = "New Gender"
        }
         */


        mBinding.btnSaveGender.setOnClickListener {
            with(mBinding){
                if(ValidatorUtil.validateFields(tilGenderDescription, tilGenderType)){
                        saveGender()
                }
            }
        }

        mBinding.btnPickColor.setOnClickListener {
            val colorPicker = ColorPicker(requireActivity(),colorDefault[0], colorDefault[1], colorDefault[2])
            colorPicker.show()
            colorPicker.enableAutoClose()
            colorPicker.setCallback { color ->
                colorCode = color
                colorDefault = arrayListOf(color.red, color.green, color.blue)
                mBinding.btnPickColor.setBackgroundColor(color)
            }
        }

        return mBinding.root
    }

    private fun initSaveData() {

        mViewModel = ViewModelProvider(requireActivity()).get(GenderViewModel::class.java)
    }

    private fun setData() {

        mViewModel.getGenderSelected().observe(viewLifecycleOwner) {

            if (it.idGender != 0L) {
                with(mBinding) {
                    idG = it.idGender
                    edtGenderType.setText(it.type)
                    edtGenderDescription.setText(it.description)
                    btnPickColor.setBackgroundColor(it.color_code)
                    colorCode = it.color_code
                    colorDefault = arrayListOf(
                        Color.red(it.color_code),
                        Color.green(it.color_code),
                        Color.blue(it.color_code)
                    )
                }
            }
        }
    }

    private fun saveGender() {
            var typeG = mBinding.edtGenderType.text.toString().trim()
            var descriptionG = mBinding.edtGenderDescription.text.toString().trim()


            if(idG == 0L){
                var genderEntity = GenderEntity(type = typeG, description = descriptionG, color_code = colorCode!!.toInt())
                mViewModel.saveGender(genderEntity).observe(viewLifecycleOwner){
                    rest -> Snackbar.make(mBinding.root, rest.message , Snackbar.LENGTH_SHORT).show()
                    clear()
                }
                /*
                doAsync {
                    SongApplication.database.GenderDao().insertDB(genderEntity)
                    uiThread {
                        Snackbar.make(mBinding.root, "Genero Registrado", Snackbar.LENGTH_SHORT).show()
                        clear()
                    }
                }
                 */


            }else{
                var genderEntity = GenderEntity(idGender = idG!!,type = typeG, description = descriptionG, color_code = colorCode!!.toInt())

                mViewModel.updateGender(genderEntity).observe(viewLifecycleOwner){
                    Snackbar.make(mBinding.root, it.message, Snackbar.LENGTH_SHORT).show()
                    clear()
                }
                /*
                doAsync {
                    SongApplication.database.GenderDao().updateDB(genderEntity)
                    uiThread {
                        Snackbar.make(mBinding.root, "Genero Actualizado", Snackbar.LENGTH_SHORT).show()
                        clear()
                    }
                }
                 */
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mActivity?.supportActionBar?.title = getString(R.string.title_new_song)
        setHasOptionsMenu(true)
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
        mActivity?.supportActionBar?.title=getString(R.string.app_name)
        setHasOptionsMenu(false)
        super.onDestroy()
        mViewModel.setGenderSelected(GenderEntity())
    }

    private fun clear() {
        var handler = Handler()
        handler.postDelayed( Runnable {
            mActivity?.onBackPressed()
        }, 500)
    }
}