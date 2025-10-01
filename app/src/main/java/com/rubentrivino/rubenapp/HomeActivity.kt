package com.rubentrivino.rubenapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val btn = findViewById<MaterialButton>(R.id.btnVerTareas)
        btn.setOnClickListener {
            startActivity(Intent(this@HomeActivity, MainActivity::class.java))
        }
    }
}
