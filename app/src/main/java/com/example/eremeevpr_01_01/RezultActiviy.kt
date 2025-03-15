package com.example.eremeevpr_01_01

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class RezultActiviy : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rezult_activiy)
        val inputValue = intent.getStringExtra("INPUT_VALUE") ?: ""
        val sourceUnit = intent.getStringExtra("SOURCE_UNIT") ?: ""
        val targetUnit = intent.getStringExtra("TARGET_UNIT") ?: ""
        val result = intent.getDoubleExtra("RESULT", 0.0)

        // Форматирование текста
        findViewById<TextView>(R.id.tvInputValue).text =
            "$inputValue $sourceUnit ="

        findViewById<TextView>(R.id.tvResult).text =
            String.format("%.2f %s", result, targetUnit)


        findViewById<Button>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }
}