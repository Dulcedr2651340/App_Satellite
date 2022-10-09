package com.example.examen02.mainModule.adapter

import com.example.examen02.common.entities.GenderEntity
import com.example.examen02.databinding.ItemGenderBinding

interface OnClickGenderListener {

    fun onClickGender(genderEntity: GenderEntity)
    fun onLongClickGender(genderEntity: GenderEntity, itemBinding: ItemGenderBinding)
}
