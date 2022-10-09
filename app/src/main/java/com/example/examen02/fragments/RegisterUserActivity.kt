package com.example.examen02.fragments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.examen02.ValidatorUtil
import com.example.examen02.databinding.ActivityRegisterUserBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterUserActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mBinding: ActivityRegisterUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterUserBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mAuth = FirebaseAuth.getInstance()


        mBinding.btnRegister.setOnClickListener {
            if(ValidatorUtil.validateFields(mBinding.tilUserEmail, mBinding.tilUserPassword)){
                var email = mBinding.edtUserEmail.text.toString().trim()
                var password = mBinding.edtUserPassword.text.toString().trim()
                if(ValidatorUtil.validEmail(email)){
                    signIn(email, password)
                }
            }
        }
    }

    private fun signIn(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Usuario Registrado", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, SessionActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Error al registrar el Usuario", Toast.LENGTH_SHORT).show()
                }
            }
    }

}


