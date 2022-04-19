package com.example.quick_tick.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_tick.DBHelper
import com.example.quick_tick.R
import com.example.quick_tick.recycler_view.schedule_adapter
import com.example.quick_tick.recycler_view.scheduled_items


class Blocks_fragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_tasks, container, false)

        val recyclerView = v.findViewById<RecyclerView>(R.id.alltasks_fragment)

        recyclerView.layoutManager = LinearLayoutManager(v.context)

        val data = ArrayList<scheduled_items>()
        // creating a DBHelper class
        // and passing context to it
        val db = DBHelper(v.context, null)
        // below is the variable for cursor
        // we have called method to get
        // all tasks from our database
        // and add to recycler view
        val cursor = db.getData()
        // moving the cursor to first position and
        // appending value in the text view
        if (cursor!!.moveToFirst()){
            do{
                val task_name = cursor.getString(1)
                val task_descr = cursor.getString(8)
                val task_start = cursor.getString(5)
                val task_end = cursor.getString(6)
                val task_tag = cursor.getString(9)
                val task_date = cursor.getString(4) + "." + cursor.getString(3) + "." + cursor.getString(2)

                data.add(scheduled_items(R.drawable.baseline_navigate_next_black_48, task_tag, task_name, task_descr, "0", task_date, task_start, task_end))


            }while (cursor.moveToNext())
        }



        //assigning adapter for recyclerview
        val adapter = schedule_adapter(data)
        recyclerView.adapter = adapter
        //recycler end

        return v
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }


}