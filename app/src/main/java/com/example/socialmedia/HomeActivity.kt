package com.example.socialmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.socialmedia.fragments.EventsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setFragment(HomeFragment())
        val bottomNa = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNa.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_news -> {
                    setFragment(HomeFragment())
                }
                R.id.search_person -> {
                    setFragment(SearchFragment())
                }
                R.id.events -> {
                   setFragment(EventsFragment())
                }
                R.id.profile_bottom -> {
                    setFragment(AccountFragment())
                }
            }
            true
        }
    }
    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frames, fragment)
            .commit()
    }
}