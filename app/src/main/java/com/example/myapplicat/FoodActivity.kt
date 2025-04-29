package com.example.myapplicat

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import androidx.appcompat.app.AlertDialog

class FoodActivity : AppCompatActivity() {
    private lateinit var foodListView: ListView
    private lateinit var inputFood: EditText
    private lateinit var addFoodButton: Button
    private lateinit var addFoodReminderButton: Button
    private val foodNotes = mutableListOf<String>()
    private lateinit var adapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)
        title = "Кормление"

        foodListView = findViewById(R.id.foodListView)
        inputFood = findViewById(R.id.inputFood)
        addFoodButton = findViewById(R.id.addFoodButton)
        addFoodReminderButton = findViewById(R.id.addFoodReminderButton)

        adapter = FoodAdapter(foodNotes)
        foodListView.adapter = adapter

        addFoodButton.setOnClickListener {
            val note = inputFood.text.toString().trim()
            if (note.isNotEmpty()) {
                foodNotes.add(note)
                adapter.notifyDataSetChanged()
                inputFood.text.clear()
            }
        }

        addFoodReminderButton.setOnClickListener {
            showTimePickerAndSetReminder()
        }
    }

    private fun showTimePickerAndSetReminder() {
        val calendar = Calendar.getInstance()
        val timePicker = TimePickerDialog(this, { _, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
            setFoodReminder(calendar.timeInMillis)
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)
        timePicker.show()
    }

    private fun setFoodReminder(timeInMillis: Long) {
        val intent = Intent(this, ReminderReceiver::class.java)
        intent.putExtra("reminder_text", "Время покормить питомца!")
        val pendingIntent = PendingIntent.getBroadcast(this, 1001, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
        Toast.makeText(this, "Напоминание установлено!", Toast.LENGTH_SHORT).show()
    }

    inner class FoodAdapter(private val items: MutableList<String>) : BaseAdapter() {
        override fun getCount(): Int = items.size
        override fun getItem(position: Int): Any = items[position]
        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView ?: LayoutInflater.from(this@FoodActivity).inflate(R.layout.item_food, parent, false)
            val foodText = view.findViewById<TextView>(R.id.foodText)
            val editButton = view.findViewById<Button>(R.id.editButton)
            val deleteButton = view.findViewById<Button>(R.id.deleteButton)

            foodText.text = items[position]

            editButton.setOnClickListener {
                showEditDialog(position)
            }
            deleteButton.setOnClickListener {
                items.removeAt(position)
                notifyDataSetChanged()
            }
            return view
        }
    }

    private fun showEditDialog(position: Int) {
        val editText = EditText(this)
        editText.setText(foodNotes[position])
        AlertDialog.Builder(this@FoodActivity)
            .setTitle("Редактировать запись")
            .setView(editText)
            .setPositiveButton("Сохранить") { _, _ ->
                val newText = editText.text.toString().trim()
                if (newText.isNotEmpty()) {
                    foodNotes[position] = newText
                    adapter.notifyDataSetChanged()
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }
} 