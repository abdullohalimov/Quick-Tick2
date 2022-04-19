package com.example.quick_tick

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quick_tick.fragments.*
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val bottom_navbar = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val schedule_frag = Schedule_fragment()
        val calendar_frag = Calendar_fragment()
        val blocks_frag = Blocks_fragment()
        val tags_frag = Tags_fragment()

        setCurrentFragment(schedule_frag)

        bottom_navbar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.schedule_bot->setCurrentFragment(schedule_frag)
                R.id.calendar_bot->setCurrentFragment(calendar_frag)
                R.id.dialer_bot->setCurrentFragment(blocks_frag)
                R.id.tags_bot->setCurrentFragment(tags_frag)
            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_view, fragment)
            commit()
        }

    }
}