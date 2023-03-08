package com.example.testdata.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testdata.MainActivity2
import com.example.testdata.R
import com.example.testdata.model.Task

class TaskAdapter(var dataset: List<Task>)
    : RecyclerView.Adapter<TaskAdapter.ItemViewHolder>(){


    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val contentTextView: TextView = view.findViewById(R.id.contentTask);
        val titleTextView: TextView = view.findViewById(R.id.titleTask);

    }
    //contentTextView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_task_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.titleTextView.text = item.name;
        holder.contentTextView.text = item.sdt;
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, MainActivity2::class.java)
            intent.putExtra("position", position)
            intent.putExtra("name", holder.titleTextView.text.toString())
            intent.putExtra("sdt",  holder.contentTextView.text.toString())
            context.startActivity(intent)
        }
    }

    override fun getItemCount() : Int{
        return dataset.size
    };

    fun setFilteredList(ds : List<Task>){
        this.dataset = ds
        notifyDataSetChanged()
    }

}