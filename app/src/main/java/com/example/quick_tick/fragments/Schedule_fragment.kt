package com.example.quick_tick.fragments

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_tick.DBHelper
import com.example.quick_tick.R
import com.example.quick_tick.Task_adding
import com.example.quick_tick.recycler_view.schedule_adapter
import com.example.quick_tick.recycler_view.scheduled_items
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.collections.ArrayList


class Schedule_fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_schedule, container, false)

        // creating a DBHelper class
        // and passing context to it
        val db = DBHelper(v.context, null)

        //recyclerview's list for data
        val data = ArrayList<scheduled_items>()

        //assigning adapter for recyclerview
        val adapter = schedule_adapter(data)

        //setting current data to variable
        val calendar  = Calendar.getInstance()
        val date_field = v.findViewById<TextView>(R.id.date_field)
        date_field.text = calendar.get(Calendar.DAY_OF_MONTH).toString().plus(", ".plus(calendar.get(Calendar.MONTH).plus(1).toString().plus(", ".plus(calendar.get(Calendar.YEAR)))))

        // split data for search in db
        var date_splitted = date_field.text.split(", ")
        // handling clicks on data field
        date_field.setOnClickListener{
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val dpd = DatePickerDialog(v.context, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                var montha = monthOfYear + 1
                // Display Selected date in textbox
                date_field.text = "$dayOfMonth, $montha, $year"
                date_splitted = date_field.text.split(", ")
                refresh_recycle(v, db, data, adapter, date_splitted)

            }, year, month, day)

            dpd.show()

        }

        //initializing recyclerview
        refresh_recycle(v, db, data, adapter, date_splitted)
        //on clicking calendar next button and refreshing recyclerview
        val calendar_next = v.findViewById<ImageView>(R.id.calendar_next)
        calendar_next.setOnClickListener{
            val date = date_field.text.split(", ")
            var date_day = date[0].toInt() + 1
            var date_month = date[1]
            val date_year =date[2]
            if (date_day == 32){
                date_month = date[1] + 1
            }
            date_field.text = "$date_day, $date_month, $date_year"
            date_splitted = date_field.text.split(", ")
            refresh_recycle(v, db, data, adapter, date_splitted)
        }
        //on clicking calendar prev. button and refreshing recyclerview
        val calendar_back = v.findViewById<ImageView>(R.id.calendar_back)
        calendar_back.setOnClickListener{
            val date = date_field.text.split(", ")
            var date_day = date[0].toInt() - 1
            var datemonth: Int = date[1].toInt()
            val date_year =date[2]
            if (date_day == 0){
                date_day = 31
                datemonth -= 1
                if (datemonth == 0)
                    datemonth = 12
            }
            date_field.text = "$date_day, $datemonth, $date_year"
            date_splitted = date_field.text.split(", ")
            refresh_recycle(v, db, data, adapter, date_splitted)
        }

        //clicking on floating action button and starting taskAdd activity for adding new tasks to database
        val schedule_float_button = v.findViewById<ImageView>(R.id.schedule_float_button)
        schedule_float_button.setOnClickListener {
            val intent = Intent(v.context, Task_adding::class.java)
            startActivity(intent)
        }

        //return view after all changes
        return v
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        //inflating menu to our toolbar
        val m = view.findViewById<MaterialToolbar>(R.id.toolbar)
        m.inflateMenu(R.menu.schedule_toolbar_menu)

        //define what to do when clicking on action today button
        m.setOnMenuItemClickListener{
            when(it.itemId){
                // on clicking change date fields date to today's and refresh recyclerview
                R.id.action_today -> {
                    val db = DBHelper(view.context, null)
                    val data = ArrayList<scheduled_items>()
                    val adapter = schedule_adapter(data)
                    val date_field = view.findViewById<TextView>(R.id.date_field)
                    val calendar  = Calendar.getInstance()
                    date_field.text = calendar.get(Calendar.DAY_OF_MONTH).toString().plus(", ".plus(calendar.get(Calendar.MONTH).plus(1).toString().plus(", ".plus(calendar.get(Calendar.YEAR)))))
                    var date_splitted = date_field.text.split(", ")
                    Toast.makeText(view.context, date_field.text, Toast.LENGTH_SHORT).show()
                    refresh_recycle(view, db, data, adapter, date_splitted)
                }
            }
            true
        }
    }


}

    private fun refresh_recycle(
        v: View,
        db: DBHelper,
        data: ArrayList<scheduled_items>,
        adapter: schedule_adapter,
        date_splitted: List<String>
    ) {
        //Recycler start
        //assigning recyclerview to value
        val recyclerView = v.findViewById<RecyclerView>(R.id.schedule_recycle)

        //assigning layoutmanager for recyclerview
        recyclerView.layoutManager = LinearLayoutManager(v.context)
        data.clear()
        // below is the variable for cursor
        // we have called method to get
        // all tasks from our database
        // and add to recycler view
        val cursor = db.getSpecData(date_splitted[2], date_splitted[1], date_splitted[0])

        // moving the cursor to first position and
        // appending value in the text view
        if (cursor!!.moveToFirst()){
            do{
                //assigning to variables our data from database
                val task_name = cursor.getString(1)
                val task_descr = cursor.getString(8)
                val task_start = cursor.getString(5)
                val task_end = cursor.getString(6)
                val task_tag = cursor.getString(9)
                val task_date = cursor.getString(4) + "." + cursor.getString(3) + "." + cursor.getString(2)
                //adding new item to recyclerview
                data.add(scheduled_items(R.drawable.next, task_tag, task_name, task_descr, "0", task_date, task_start, task_end))
            }while (cursor.moveToNext())
            cursor.close()
            //update recyclerview
            adapter.update(data)
        }else{
            //show empty recyclerview
            data.clear()
            //update recyclerview
            adapter.update(data)}

        recyclerView.adapter = adapter
        //recycler end

    }


