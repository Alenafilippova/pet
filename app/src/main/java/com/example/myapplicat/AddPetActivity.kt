package com.example.myapplicat

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddPetActivity : AppCompatActivity() {
    private lateinit var nameEditText: EditText
    private lateinit var typeSpinner: Spinner
    private lateinit var breedEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var weightEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pet)

        nameEditText = findViewById(R.id.nameEditText)
        typeSpinner = findViewById(R.id.typeSpinner)
        breedEditText = findViewById(R.id.breedEditText)
        ageEditText = findViewById(R.id.ageEditText)
        weightEditText = findViewById(R.id.weightEditText)
        saveButton = findViewById(R.id.saveButton)

        saveButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val type = typeSpinner.selectedItem.toString()
            val breed = breedEditText.text.toString()
            val age = ageEditText.text.toString()
            val weight = weightEditText.text.toString()

            if (name.isEmpty() || breed.isEmpty() || age.isEmpty() || weight.isEmpty()) {
                Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                // Преобразуем возраст в год рождения (пример: если возраст 3, то год = текущий - 3)
                val currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
                val birthYear = currentYear - age.toInt()
                val birthDate = "$birthYear-01-01" // Формируем строку даты (можно улучшить)
                val pet = Pet(
                    name = name,
                    type = type,
                    breed = breed,
                    birthDate = birthDate,
                    weight = weight.toDouble(),
                    ownerId = 1 // TODO: Получить ID текущего пользователя
                )

                // TODO: Сохранить питомца в базу данных
                Toast.makeText(this, "Питомец успешно добавлен", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Пожалуйста, введите корректные числовые значения", Toast.LENGTH_SHORT).show()
            }
        }
    }
} 