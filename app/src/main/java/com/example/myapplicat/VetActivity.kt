package com.example.myapplicat

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

// Модель записи к врачу
data class VetRecord(
    var petName: String,
    var date: String,
    var time: String,
    var reason: String
)

class VetActivity : AppCompatActivity() {
    private lateinit var vetListView: ListView
    private lateinit var inputPetName: EditText
    private lateinit var inputDate: EditText
    private lateinit var inputTime: EditText
    private lateinit var inputReason: EditText
    private lateinit var bookVetButton: Button
    private val vetRecords = mutableListOf<VetRecord>()
    private lateinit var adapter: VetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vet)
        title = "Ветеринар"

        vetListView = findViewById(R.id.vetListView)
        inputPetName = findViewById(R.id.inputPetName)
        inputDate = findViewById(R.id.inputDate)
        inputTime = findViewById(R.id.inputTime)
        inputReason = findViewById(R.id.inputReason)
        bookVetButton = findViewById(R.id.bookVetButton)

        adapter = VetAdapter(vetRecords)
        vetListView.adapter = adapter

        bookVetButton.setOnClickListener {
            val petName = inputPetName.text.toString().trim()
            val date = inputDate.text.toString().trim()
            val time = inputTime.text.toString().trim()
            val reason = inputReason.text.toString().trim()
            if (petName.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty() && reason.isNotEmpty()) {
                vetRecords.add(VetRecord(petName, date, time, reason))
                adapter.notifyDataSetChanged()
                inputPetName.text.clear()
                inputDate.text.clear()
                inputTime.text.clear()
                inputReason.text.clear()
            } else {
                Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }
    }

    inner class VetAdapter(private val items: MutableList<VetRecord>) : BaseAdapter() {
        override fun getCount(): Int = items.size
        override fun getItem(position: Int): Any = items[position]
        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView ?: LayoutInflater.from(this@VetActivity).inflate(R.layout.item_vet, parent, false)
            val petNameView = view.findViewById<TextView>(R.id.vetPetName)
            val dateTimeView = view.findViewById<TextView>(R.id.vetDateTime)
            val reasonView = view.findViewById<TextView>(R.id.vetReason)
            val editButton = view.findViewById<Button>(R.id.editButton)
            val deleteButton = view.findViewById<Button>(R.id.deleteButton)

            val record = items[position]
            petNameView.text = record.petName
            dateTimeView.text = "${record.date}  ${record.time}"
            reasonView.text = record.reason

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
        val record = vetRecords[position]
        val dialogView = LayoutInflater.from(this).inflate(R.layout.activity_vet, null)
        val petNameEdit = EditText(this)
        val dateEdit = EditText(this)
        val timeEdit = EditText(this)
        val reasonEdit = EditText(this)
        petNameEdit.setText(record.petName)
        dateEdit.setText(record.date)
        timeEdit.setText(record.time)
        reasonEdit.setText(record.reason)
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.addView(petNameEdit)
        layout.addView(dateEdit)
        layout.addView(timeEdit)
        layout.addView(reasonEdit)
        AlertDialog.Builder(this)
            .setTitle("Редактировать запись")
            .setView(layout)
            .setPositiveButton("Сохранить") { _, _ ->
                val newPetName = petNameEdit.text.toString().trim()
                val newDate = dateEdit.text.toString().trim()
                val newTime = timeEdit.text.toString().trim()
                val newReason = reasonEdit.text.toString().trim()
                if (newPetName.isNotEmpty() && newDate.isNotEmpty() && newTime.isNotEmpty() && newReason.isNotEmpty()) {
                    vetRecords[position] = VetRecord(newPetName, newDate, newTime, newReason)
                    adapter.notifyDataSetChanged()
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }
} 