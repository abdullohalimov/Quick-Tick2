package com.example.quick_tick

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.util.*

class Task_adding : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.task_adding_activity)

        //buttons
        val task_add_button = findViewById<Button>(R.id.add_task_button)
        //text views
        val date_text_view = findViewById<TextView>(R.id.date_text_view)
        val start_time_text = findViewById<TextView>(R.id.start_time_text)
        val end_time_text = findViewById<TextView>(R.id.end_time_text)
        //edit texts
        val name_edit_text = findViewById<EditText>(R.id.name_edit_text)
        val descr_edit_text = findViewById<EditText>(R.id.descr_edit_text)
        //Spinners
        //for Repeat block
        val repeat = resources.getStringArray(R.array.Repeat)
        val repeat_spinner = findViewById<Spinner>(R.id.repeat_spinner)
        if (repeat_spinner != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, repeat)
            repeat_spinner.adapter = adapter
        }
        //for Tags block
        val tags = resources.getStringArray(R.array.Tags)
        val tags_spinner = findViewById<Spinner>(R.id.tag_spinner)
        if (tags_spinner != null){
            val  adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tags)
            tags_spinner.adapter = adapter

        }
        //

        //Listeners
        //date picker dialog
        date_text_view.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                var montha = monthOfYear + 1
                // Display Selected date in textbox
                date_text_view.text = "$dayOfMonth, $montha, $year"

            }, year, month, day)

            dpd.show()

        }
        //time picker dialog for start and end time
        start_time_text.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)
            val timePicker: TimePickerDialog
            timePicker = TimePickerDialog(this, object :TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
                    start_time_text.setText(String.format("%d : %d", p1, p2))
                }
            }, hour, minute, false)
            timePicker.show()
        }
        end_time_text.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)
            val timePicker: TimePickerDialog
            timePicker = TimePickerDialog(this, object :TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
                    end_time_text.setText(String.format("%d : %d", p1, p2))
                }
            }, hour, minute, false)
            timePicker.show()
        }
        // Add button on click listener
        task_add_button.setOnClickListener {
            // below we have created
            // a new DBHelper class,
            // and passed context to it
            val db = DBHelper(this, null)

            // creating variables for values
            // in name and other edit texts
            val name_field = name_edit_text.text.toString()
            val descr_field = descr_edit_text.text.toString()
            val date_field = date_text_view.text.split(", ")
            val start_time_field = start_time_text.text.toString()
            val end_time_field = end_time_text.text.toString()
            val repeat_spinner_val = repeat_spinner.selectedItem.toString()
            val tags_spinner_val = tags_spinner.selectedItem.toString()

            // calling method to add
            // task to our database
            db.addData(name_field, date_field[2], date_field[1], date_field[0], start_time_field, end_time_field, repeat_spinner_val, descr_field, tags_spinner_val)

            // Toast to message on the screen
            Toast.makeText(this, "Added successfully", Toast.LENGTH_LONG).show()
        }


    }
}