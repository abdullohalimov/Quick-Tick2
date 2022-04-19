package com.example.quick_tick.recycler_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_tick.R

class schedule_adapter(private val mList: List<scheduled_items>): RecyclerView.Adapter<schedule_adapter.ViewHolder>() {

    //create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the schedule_item view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.schedule_item, parent, false)
        return ViewHolder(view)
    }
    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val scheduledItems = mList[position]
        // sets the image to the imageview from our scheduledItems class
        holder.tag_icon_schd.setImageResource(scheduledItems.tags_icon)
        // sets the text to the textview from our scheduledItems class
        holder.tag_name_schd.text = scheduledItems.tags_name

        holder.schd_descr.text = scheduledItems.schd_descr
        holder.schd_hours.text = scheduledItems.schd_duration
        holder.schd_name.text = scheduledItems.schd_name
        holder.schd_date.text = scheduledItems.schd_date
        holder.schd_start.text = scheduledItems.schd_start
        holder.schd_end.text = scheduledItems.schd_end

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }
    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView) {
        val tag_icon_schd: ImageView = itemView.findViewById(R.id.tag_icon_schd)
        val tag_name_schd: TextView = itemView.findViewById(R.id.tag_name_schd)

        val schd_name = itemView.findViewById<TextView>(R.id.schd_name)
        val schd_descr = itemView.findViewById<TextView>(R.id.schd_descr)
        val schd_hours = itemView.findViewById<TextView>(R.id.schd_hours)
        val schd_date = itemView.findViewById<TextView>(R.id.schd_date)
        val schd_start = itemView.findViewById<TextView>(R.id.schd_from)
        val schd_end = itemView.findViewById<TextView>(R.id.schd_to)


    }
}