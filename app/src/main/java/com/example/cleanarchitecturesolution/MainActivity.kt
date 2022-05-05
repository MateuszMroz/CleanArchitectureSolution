package com.example.cleanarchitecturesolution

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.cleanarchitecturesolution.features.character.presentation.CharacterFragment
import com.example.cleanarchitecturesolution.features.location.presentation.LocationFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.container, CharacterFragment())
            }
        }
    }
}
