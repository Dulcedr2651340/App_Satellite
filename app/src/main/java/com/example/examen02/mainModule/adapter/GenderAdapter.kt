package com.example.examen02.mainModule.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.examen02.common.entities.GenderEntity
import com.example.examen02.R
import com.example.examen02.databinding.ItemGenderBinding

class GenderAdapter(
    private var genderList: MutableList<GenderEntity>,
    private var listener: OnClickGenderListener,
): RecyclerView.Adapter<GenderAdapter.GenderViewHolder>() {

    lateinit var mContext:Context

    inner class GenderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemBinding = ItemGenderBinding.bind(itemView)

        fun setListener(genderEntity: GenderEntity){
            itemBinding.root.setOnClickListener {
                listener.onClickGender(genderEntity)
            }

            itemBinding.root.setOnLongClickListener {
                listener.onLongClickGender(genderEntity, itemBinding)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenderViewHolder {
        mContext = parent.context
        var view = LayoutInflater.from(mContext).inflate(R.layout.item_gender, parent, false)
        return GenderViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenderViewHolder, position: Int) {
        var gender = genderList[position]
        with(holder){
            itemBinding.tvTypeGender.text = gender.type
            //Glide.with(mContext).load(gender.image_url).into(itemBinding.imgGender)
            itemBinding.cvImg.setCardBackgroundColor(gender.color_code)
            setListener(gender)
        }
    }

    override fun getItemCount(): Int {
        return genderList.size
    }

    fun setCollection(list: MutableList<GenderEntity>){
        genderList.clear()
        genderList.addAll(list)
        notifyDataSetChanged()
    }

    fun genderDelete(genderEntity: GenderEntity) {
        val index = genderList.indexOf(genderEntity)
        genderList.removeAt(index)
        notifyItemRemoved(index)
    }

}