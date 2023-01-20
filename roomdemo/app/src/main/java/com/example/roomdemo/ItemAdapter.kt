package com.example.roomdemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdemo.databinding.ItemRowBinding

class ItemAdapter(
    private val items: ArrayList<EmployeeEntity>,
//    private val updateListener: (id: Int) -> Unit,
//    private val deleteListener: (id: Int) -> Unit
):RecyclerView.Adapter<ItemAdapter.viewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(
            ItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val context = holder.itemView.context
        val item = items[position]

        holder.tvName.text = item.name
        holder.tvEmail.text = item.email

        // Updating the background color according to the odd/even positions in list.
        if (position % 2 == 0) {
            holder.llMain.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    androidx.appcompat.R.color.material_blue_grey_800
                )
            )
        } else {
            holder.llMain.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }
// Todo 3 set onclick listem on the icon and invoke update and delete listeners
        //start
        holder.ivEdit.setOnClickListener {
//            updateListener(item.id)
        }

        holder.ivDelete.setOnClickListener {
//            deleteListener(item.id)
        }
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
    class viewHolder(binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        // Holds the TextView that will add each item to
        val llMain = binding.llMain
        val tvName = binding.tvName
        val tvEmail = binding.tvEmail
        val ivEdit = binding.ivEdit
        val ivDelete = binding.ivDelete
    }
}