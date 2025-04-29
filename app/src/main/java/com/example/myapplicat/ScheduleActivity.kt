package com.example.myapplicat

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ScheduleActivity : AppCompatActivity() {
    private lateinit var eventsListView: ListView
    private lateinit var inputEvent: EditText
    private lateinit var addEventButton: Button
    private val events = mutableListOf<String>()
    private lateinit var adapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)
        title = "Расписание"

        eventsListView = findViewById(R.id.eventsListView)
        inputEvent = findViewById(R.id.inputEvent)
        addEventButton = findViewById(R.id.addEventButton)

        adapter = EventAdapter(events)
        eventsListView.adapter = adapter

        addEventButton.setOnClickListener {
            val eventText = inputEvent.text.toString().trim()
            if (eventText.isNotEmpty()) {
                events.add(eventText)
                adapter.notifyDataSetChanged()
                inputEvent.text.clear()
            }
        }
    }

    inner class EventAdapter(private val items: MutableList<String>) : BaseAdapter() {
        override fun getCount(): Int = items.size
        override fun getItem(position: Int): Any = items[position]
        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView ?: LayoutInflater.from(this@ScheduleActivity).inflate(R.layout.item_event, parent, false)
            val eventText = view.findViewById<TextView>(R.id.eventText)
            val editButton = view.findViewById<Button>(R.id.editButton)
            val deleteButton = view.findViewById<Button>(R.id.deleteButton)

            eventText.text = items[position]

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
        editText.setText(events[position])
        AlertDialog.Builder(this)
            .setTitle("Редактировать событие")
            .setView(editText)
            .setPositiveButton("Сохранить") { _, _ ->
                val newText = editText.text.toString().trim()
                if (newText.isNotEmpty()) {
                    events[position] = newText
                    adapter.notifyDataSetChanged()
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }
} 