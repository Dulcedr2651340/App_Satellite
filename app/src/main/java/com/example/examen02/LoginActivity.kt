package com.example.examen02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.examen02.databinding.ActivityLoginBinding
import com.example.examen02.fragments.RegisterUserActivity
import com.example.examen02.fragments.SessionActivity
import com.example.examen02.mainModule.MainActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginBinding.inflate(layoutInflater)

        mAuth = FirebaseAuth.getInstance()
        setContentView(mBinding.root)

        mBinding.btnLogin.setOnClickListener {
            startActivity(Intent(this, SessionActivity::class.java))
            finish()
        }

        mBinding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterUserActivity::class.java))
            finish()
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