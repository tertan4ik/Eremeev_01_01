package com.example.eremeevpr_01_01

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast

class CalculateActivity : AppCompatActivity() {
    private val units = listOf("Байт", "Килобайт", "Мегабайт", "Гигабайт")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate)
        val etInput = findViewById<EditText>(R.id.etInput)
        val spinnerSource = findViewById<Spinner>(R.id.spinnerSource)
        val spinnerTarget = findViewById<Spinner>(R.id.spinnerTarget)
        val btnCalculate = findViewById<Button>(R.id.btnCalculate)

        // Настройка Spinner'ов
        val units = resources.getStringArray(R.array.units_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, units)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSource.adapter = adapter
        spinnerTarget.adapter = adapter

        // Установка значений по умолчанию
        spinnerSource.setSelection(0) // Байт
        spinnerTarget.setSelection(1) // Килобайт

        // Обработчик кнопки "Вычислить"
        btnCalculate.setOnClickListener {
            convertUnits()
        }
    }

    private fun convertUnits() {
        val input = findViewById<EditText>(R.id.etInput).text.toString()

        // Проверка пустого ввода
        if (input.isEmpty()) {
            Toast.makeText(this, "Введите число", Toast.LENGTH_SHORT).show()
            return
        }

        // Проверка корректности числа
        val value = input.toDoubleOrNull() ?: run {
            Toast.makeText(this, "Некорректное число", Toast.LENGTH_SHORT).show()
            return
        }

        // Проверка выбора единиц измерения
        val sourceUnit = findViewById<Spinner>(R.id.spinnerSource).selectedItem?.toString()
        val targetUnit = findViewById<Spinner>(R.id.spinnerTarget).selectedItem?.toString()
        if (sourceUnit == null || targetUnit == null) {
            Toast.makeText(this, "Выберите единицы измерения", Toast.LENGTH_SHORT).show()
            return
        }

        // Расчет результата
        val result = calculate(value, sourceUnit, targetUnit)

        // Переход на ResultActivity
        val intent = Intent(this, RezultActiviy::class.java).apply {
            putExtra("INPUT_VALUE", input)
            putExtra("SOURCE_UNIT", sourceUnit)
            putExtra("TARGET_UNIT", targetUnit)
            putExtra("RESULT", result)
        }
        startActivity(intent)
    }

    private fun calculate(value: Double, from: String, to: String): Double {
        // Конвертация через промежуточное значение в байтах
        val valueInBytes = when (from) {
            "Килобайт" -> value * 1024
            "Мегабайт" -> value * 1024 * 1024
            "Гигабайт" -> value * 1024 * 1024 * 1024
            else -> value // Байты
        }

        return when (to) {
            "Килобайт" -> valueInBytes / 1024
            "Мегабайт" -> valueInBytes / (1024 * 1024)
            "Гигабайт" -> valueInBytes / (1024 * 1024 * 1024)
            else -> valueInBytes // Байты
        }
    }
}