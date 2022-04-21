package com.example.quick_tick.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_tick.R
import com.example.quick_tick.recycler_view.ItemsViewModel
import com.example.quick_tick.recycler_view.tags_adapter


class Tags_fragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.fragment_tags, container, false)

        // getting the recyclerview by its id
        val recyclerview = v.findViewById<RecyclerView>(R.id.tags_recycler)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = GridLayoutManager(v.context, 2)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        add_primary_tags(data)


        // This will pass the ArrayList to our Adapter
        val adapter = tags_adapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

        // Inflate the layout for this fragment
        return v
    }

    private fun add_primary_tags(data: ArrayList<ItemsViewModel>) {
        data.add(ItemsViewModel(R.drawable.next, "Break"))
        data.add(ItemsViewModel(R.drawable.next, "Commuting"))
        data.add(ItemsViewModel(R.drawable.next, "Eating"))
        data.add(ItemsViewModel(R.drawable.next, "Housework"))
        data.add(ItemsViewModel(R.drawable.next, "Internet"))
        data.add(ItemsViewModel(R.drawable.next, "Reading"))
        data.add(ItemsViewModel(R.drawable.next, "Relationship"))
        data.add(ItemsViewModel(R.drawable.next, "Sleep"))
        data.add(ItemsViewModel(R.drawable.next, "Studying"))
        data.add(ItemsViewModel(R.drawable.next, "Training"))
        data.add(ItemsViewModel(R.drawable.next, "TV"))
        data.add(ItemsViewModel(R.drawable.next, "Work"))
    }


}