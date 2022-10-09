package com.example.examen02

import com.google.android.material.textfield.TextInputLayout

class ValidatorUtil {

    companion object {
        fun validateFields(vararg tilArray:TextInputLayout):Boolean{
            var isValid = true
            for(til in tilArray){
                if(til.editText?.text.toString().isEmpty()){
                    til.error = "Required"
                    til.requestFocus()
                    isValid = false
                }else{
                    til.error = null
                }
            }
            return isValid
        }

        fun validUrl(text: TextInputLayout):Boolean{
            var isValid = true
            var url = text.editText?.text.toString()
            var regex = Regex("^[a-z]+:[^:]+\$")
            if(regex.matches(url)){
                text.error = null
                isValid = true
            }else{
                text.error = "Invalid Url"
                isValid = false
            }
            return isValid
        }

        fun validEmail(email: String):Boolean{
            var format = Regex("^[^@]+@[^@]+\\.[a-zA-Z]{2,}\$")
            return format.matches(email)
        }
    }
}