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

class NotesActivity : AppCompatActivity() {
    private lateinit var notesListView: ListView
    private lateinit var inputNote: EditText
    private lateinit var addNoteButton: Button
    private lateinit var addNoteReminderButton: Button
    private val notes = mutableListOf<String>()
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        title = "Заметки"

        notesListView = findViewById(R.id.notesListView)
        inputNote = findViewById(R.id.inputNote)
        addNoteButton = findViewById(R.id.addNoteButton)
        addNoteReminderButton = findViewById(R.id.addNoteReminderButton)

        adapter = NoteAdapter(notes)
        notesListView.adapter = adapter

        addNoteButton.setOnClickListener {
            val note = inputNote.text.toString().trim()
            if (note.isNotEmpty()) {
                notes.add(note)
                adapter.notifyDataSetChanged()
                inputNote.text.clear()
            }
        }

        addNoteReminderButton.setOnClickListener {
            showTimePickerAndSetReminder()
        }
    }

    private fun showTimePickerAndSetReminder() {
        val calendar = Calendar.getInstance()
        val timePicker = TimePickerDialog(this, { _, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
            setNoteReminder(calendar.timeInMillis)
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)
        timePicker.show()
    }

    private fun setNoteReminder(timeInMillis: Long) {
        val intent = Intent(this, ReminderReceiver::class.java)
        intent.putExtra("reminder_text", "Проверьте ваши заметки!")
        val pendingIntent = PendingIntent.getBroadcast(this, 1002, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
        Toast.makeText(this, "Напоминание установлено!", Toast.LENGTH_SHORT).show()
    }

    inner class NoteAdapter(private val items: MutableList<String>) : BaseAdapter() {
        override fun getCount(): Int = items.size
        override fun getItem(position: Int): Any = items[position]
        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView ?: LayoutInflater.from(this@NotesActivity).inflate(R.layout.item_note, parent, false)
            val noteText = view.findViewById<TextView>(R.id.noteText)
            val editButton = view.findViewById<Button>(R.id.editButton)
            val deleteButton = view.findViewById<Button>(R.id.deleteButton)

            noteText.text = items[position]

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
        editText.setText(notes[position])
        AlertDialog.Builder(this)
            .setTitle("Редактировать заметку")
            .setView(editText)
            .setPositiveButton("Сохранить") { _, _ ->
                val newText = editText.text.toString().trim()
                if (newText.isNotEmpty()) {
                    notes[position] = newText
                    adapter.notifyDataSetChanged()
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }
} 