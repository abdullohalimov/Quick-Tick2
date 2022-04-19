package com.example.quick_tick.fragments

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_tick.DBHelper
import com.example.quick_tick.R
import com.example.quick_tick.Task_adding
import com.example.quick_tick.recycler_view.schedule_adapter
import com.example.quick_tick.recycler_view.scheduled_items
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.collections.ArrayList


class Schedule_fragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_schedule, container, false)

        //setting current data to data field
        val calendar  = Calendar.getInstance()
        val date_field = v.findViewById<TextView>(R.id.date_field)
        date_field.text = calendar.get(Calendar.DAY_OF_MONTH).toString().plus(", ".plus(calendar.get(Calendar.MONTH).plus(1).toString().plus(", ".plus(calendar.get(Calendar.YEAR)))))

        //Recycler start
        //assigning recyclerview to value
        val recyclerView = v.findViewById<RecyclerView>(R.id.schedule_recycle)
        //assigning layoutmanager for recyclerview
        recyclerView.layoutManager = LinearLayoutManager(v.context)
        //recyclerview's list for data
        val data = ArrayList<scheduled_items>()

        // creating a DBHelper class
        // and passing context to it
        val db = DBHelper(v.context, null)
        // below is the variable for cursor
        // we have called method to get
        // all tasks from our database
        // and add to recycler view
        val a = date_field.text.split(", ")
        val cursor = db.getSpecData(a[2], a[1], a[0])
        Toast.makeText(v.context, date_field.text, Toast.LENGTH_SHORT).show()
        // moving the cursor to first position and
        // appending value in the text view

        refresh_recycle(cursor!!, data)




        //assigning adapter for recyclerview
        val adapter = schedule_adapter(data)
        recyclerView.adapter = adapter
        //recycler end




        //floating action button realization
        val schedule_float_button = v.findViewById<FloatingActionButton>(R.id.schedule_float_button)
        schedule_float_button.setOnClickListener {
            val intent = Intent(v.context, Task_adding::class.java)
            startActivity(intent)
        }
        return v
    }

    private fun refresh_recycle(cursor: Cursor, data: ArrayList<scheduled_items>) {
        if (cursor.moveToFirst()){
            do{
                val task_name = cursor.getString(1)
                val task_descr = cursor.getString(8)
                val task_start = cursor.getString(5)
                val task_end = cursor.getString(6)
                val task_tag = cursor.getString(9)
                val task_date = cursor.getString(4) + "." + cursor.getString(3) + "." + cursor.getString(2)

                data.add(scheduled_items(R.drawable.baseline_navigate_next_black_48, task_tag, task_name, task_descr, "0", task_date, task_start, task_end))
            }while (cursor.moveToNext())
        }    }


}