package com.example.myapplicat

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat

class SettingsActivity : AppCompatActivity() {
    private lateinit var themeSwitch: SwitchCompat
    private lateinit var notificationsSwitch: SwitchCompat
    private lateinit var aboutButton: Button
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        title = getString(R.string.settings_title)

        themeSwitch = findViewById(R.id.themeSwitch)
        notificationsSwitch = findViewById(R.id.notificationsSwitch)
        aboutButton = findViewById(R.id.aboutButton)
        prefs = getSharedPreferences("settings", MODE_PRIVATE)

        // Тема
        themeSwitch.isChecked = prefs.getBoolean("dark_theme", false)
        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("dark_theme", isChecked).apply()
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        // Уведомления (заглушка)
        notificationsSwitch.isChecked = prefs.getBoolean("notifications", true)
        notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("notifications", isChecked).apply()
            Toast.makeText(this, if (isChecked) "Уведомления включены" else "Уведомления выключены", Toast.LENGTH_SHORT).show()
        }

        // О приложении
        aboutButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("О приложении")
                .setMessage("PetCareApp\nВерсия 1.0\nАвтор: Вы\n\nПриложение для заботы о питомцах.")
                .setPositiveButton("OK", null)
                .show()
        }
    }
} 