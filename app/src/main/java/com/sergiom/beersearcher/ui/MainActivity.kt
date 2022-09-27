package com.sergiom.beersearcher.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sergiom.beersearcher.R
import com.sergiom.beersearcher.ui.beersearchview.BeerSearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, BeerSearchFragment())
                .commitNow()
        }
    }
}