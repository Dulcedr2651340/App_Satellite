package com.example.examen02.fragments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.examen02.mainModule.MainActivity
import com.example.examen02.ValidatorUtil
import com.example.examen02.databinding.ActivitySessionBinding
import com.google.firebase.auth.FirebaseAuth

class SessionActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mBinding: ActivitySessionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivitySessionBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mAuth = FirebaseAuth.getInstance()

        mBinding.btnLogin.setOnClickListener{
            if(ValidatorUtil.validateFields(mBinding.tilUserEmail, mBinding.tilUserPassword)){
                val mEmail = mBinding.edtUserEmail.text.toString()
                val mPassword = mBinding.edtUserPassword.text.toString()
                if(!ValidatorUtil.validEmail(mEmail)){
                    mBinding.tilUserEmail.error = "Invalid Email"
                }
                if(ValidatorUtil.validEmail(mEmail)){
                    login(mEmail, mPassword)
                }
            }
        }

    }

    private fun login(mEmail: String, mPassword: String) {
        mAuth.signInWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                Toast.makeText(this, "Email o password invalidos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        if(currentUser != null){
           startActivity(Intent(this, MainActivity::class.java))
        }
    }


}