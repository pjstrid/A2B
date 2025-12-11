package com.example.runa2b

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frag_cont_main, RunsRecyclerFragment())
            commit()
        }

    }
}