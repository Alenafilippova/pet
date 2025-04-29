package com.example.myapplicat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация кнопок
        val addPetButton = findViewById<Button>(R.id.addPetButton)
        val scheduleButton = findViewById<Button>(R.id.scheduleButton)
        val vetButton = findViewById<Button>(R.id.vetButton)
        val foodButton = findViewById<Button>(R.id.foodButton)
        val groomingButton = findViewById<Button>(R.id.groomingButton)
        val notesButton = findViewById<Button>(R.id.notesButton)
        val settingsButton = findViewById<Button>(R.id.settingsButton)

        // Обработчики нажатий
        addPetButton.setOnClickListener {
            startActivity(Intent(this, AddPetActivity::class.java))
        }

        scheduleButton.setOnClickListener {
            startActivity(Intent(this, ScheduleActivity::class.java))
        }

        vetButton.setOnClickListener {
            startActivity(Intent(this, VetActivity::class.java))
        }

        foodButton.setOnClickListener {
            startActivity(Intent(this, FoodActivity::class.java))
        }

        groomingButton.setOnClickListener {
            startActivity(Intent(this, GroomingActivity::class.java))
        }

        notesButton.setOnClickListener {
            startActivity(Intent(this, NotesActivity::class.java))
        }

        settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}