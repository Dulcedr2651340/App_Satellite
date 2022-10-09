package com.example.examen02.mainModule

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.examen02.LoginActivity
import com.example.examen02.R
import com.example.examen02.SearchActivity
import com.example.examen02.databinding.ActivityMainBinding
import com.example.examen02.editModule.FavoriteFragment
import com.example.examen02.editModule.HomeFragment
import com.example.examen02.editModule.ListGenderFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavView: BottomNavigationView
    lateinit var mBinding: ActivityMainBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mAuth = FirebaseAuth.getInstance()

        supportActionBar?.hide()
        bottomNavView = mBinding.bottomNavView

        setSupportActionBar(mBinding.appToolbar)

        val homeFragment = HomeFragment()
        val favoriteFragment = FavoriteFragment()

        setThatFragment(homeFragment)

        bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    setThatFragment(homeFragment)
                }

                R.id.favorite -> {
                    setThatFragment(favoriteFragment)
                }

                R.id.search -> {
                    startActivity(Intent(this, SearchActivity::class.java))
                }

                R.id.gender -> {
                    setThatFragment(ListGenderFragment())
                }
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.navLogout -> {
                logout()
                true
            }
            R.id.profile -> {
                callSupport()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun callSupport() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.title_help))
            .setMessage(getString(R.string.message_help))
            .setPositiveButton(getString(R.string.positive)){ dialogInterface, i ->
                //CODIGO PARA ABRIR EL TELEFONO
                val phone = "+51993222246"
                var callIntent = Intent().apply {
                    action = Intent.ACTION_DIAL
                    data = Uri.parse("tel: $phone")
                }
                startActivity(callIntent)
            }
            .setNegativeButton(getString(R.string.negative)) { dialogInterface, i ->
                dialogInterface.dismiss()
            }.show()
    }

    private fun logout() {
        mAuth.signOut()
        Toast.makeText(this, "Sesión Cerrada", Toast.LENGTH_SHORT).show()
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun setThatFragment(fragment: Fragment, bundle: Bundle? = null) =
        supportFragmentManager.beginTransaction().apply {
            if (bundle != null) {
                fragment.arguments = bundle
            }
            replace(R.id.frame, fragment)
            addToBackStack(null)
            commit()
        }

}