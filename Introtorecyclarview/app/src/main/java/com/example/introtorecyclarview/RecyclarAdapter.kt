package com.example.introtorecyclarview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.introtorecyclarview.databinding.ItemRecyclarViewBinding

class RecyclarAdapter(val taskList:List<task>) : RecyclerView.Adapter<RecyclarAdapter.MainViewHolder>(){
    inner class MainViewHolder(val itemBinding:ItemRecyclarViewBinding):RecyclerView.ViewHolder(itemBinding.root){
        fun bindItem(task: task){
            itemBinding.text1.text=task.title
            itemBinding.text2.text=task.timeStamp
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(ItemRecyclarViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val task= taskList[position]
        holder.bindItem(task)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }
}