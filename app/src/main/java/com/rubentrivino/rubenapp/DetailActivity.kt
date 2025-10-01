package com.rubentrivino.rubenapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        val et = findViewById<EditText>(R.id.etTask)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnCancel = findViewById<Button>(R.id.btnCancel)

        val currentText = intent.getStringExtra("current_text")
        tvTitle.text = if (currentText != null) "Editar tarea" else "Nueva tarea"
        currentText?.let { et.setText(it) }

        btnSave.setOnClickListener {
            val text = et.text.toString().trim()
            if (text.isEmpty()) { et.error = "Escribe una tarea"; return@setOnClickListener }

            val data = Intent().putExtra("result_text", text)
            setResult(Activity.RESULT_OK, data)
            finish()
        }
        btnCancel.setOnClickListener { finish() }
    }
}
