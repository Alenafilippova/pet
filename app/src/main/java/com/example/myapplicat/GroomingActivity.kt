package com.example.myapplicat

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class GroomingActivity : AppCompatActivity() {
    private lateinit var groomingListView: ListView
    private lateinit var inputGrooming: EditText
    private lateinit var addGroomingButton: Button
    private val groomingNotes = mutableListOf<String>()
    private lateinit var adapter: GroomingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grooming)
        title = "Уход"

        groomingListView = findViewById(R.id.groomingListView)
        inputGrooming = findViewById(R.id.inputGrooming)
        addGroomingButton = findViewById(R.id.addGroomingButton)

        adapter = GroomingAdapter(groomingNotes)
        groomingListView.adapter = adapter

        addGroomingButton.setOnClickListener {
            val note = inputGrooming.text.toString().trim()
            if (note.isNotEmpty()) {
                groomingNotes.add(note)
                adapter.notifyDataSetChanged()
                inputGrooming.text.clear()
            }
        }
    }

    inner class GroomingAdapter(private val items: MutableList<String>) : BaseAdapter() {
        override fun getCount(): Int = items.size
        override fun getItem(position: Int): Any = items[position]
        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView ?: LayoutInflater.from(this@GroomingActivity).inflate(R.layout.item_grooming, parent, false)
            val groomingText = view.findViewById<TextView>(R.id.groomingText)
            val editButton = view.findViewById<Button>(R.id.editButton)
            val deleteButton = view.findViewById<Button>(R.id.deleteButton)

            groomingText.text = items[position]

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
        editText.setText(groomingNotes[position])
        AlertDialog.Builder(this)
            .setTitle("Редактировать запись")
            .setView(editText)
            .setPositiveButton("Сохранить") { _, _ ->
                val newText = editText.text.toString().trim()
                if (newText.isNotEmpty()) {
                    groomingNotes[position] = newText
                    adapter.notifyDataSetChanged()
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }
} 