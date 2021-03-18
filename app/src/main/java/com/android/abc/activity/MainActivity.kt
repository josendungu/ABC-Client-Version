package com.android.abc.activity

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.android.abc.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        application.setTheme(Resources.Theme.MaterialComponents.Light.NoActionBar)
        setContentView(R.layout.activity_main)


    }
}