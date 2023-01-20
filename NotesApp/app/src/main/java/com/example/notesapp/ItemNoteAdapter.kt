package com.example.notesapp

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.ItemNoteLayoutBinding

class ItemNoteAdapter(val item: ArrayList<NoteModel>,private val onNoteclickdelete: (Int) -> Unit):RecyclerView.Adapter<ItemNoteAdapter.viewHolder>() {
    inner class viewHolder(val binding:ItemNoteLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun itemBinding(title:String,description:String,dateTime:String){
            binding.itemNoteTvTitle.text=title
            binding.itemNoteTvDescription.text=description
            binding.dateTime.text=dateTime
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
       return viewHolder(binding = ItemNoteLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val title=item[position].title
        val note=item[position].theNote
        val date=item[position].date
        holder.itemBinding(title,note,date)
        holder.binding.root.setOnClickListener {
            val id =item[position].id
            val intent=Intent(it.context,NoteActivity::class.java)
            intent.putExtra("id",id)
            it.context.startActivity(intent)
        }
        //opening the popup dialog and deleting encrypting pinning and moving to folder
        holder.binding.itemNoteCardView.setOnLongClickListener {view ->
            val popupMenu=PopupMenu(view.context,view)
            val id =item[position].id
            popupMenu.inflate(R.menu.pop_up_menu)
            popupMenu.setOnMenuItemClickListener {
                menuItemClick(it,holder,id)

              true
            }
            popupMenu.show()
            true
        }
    }
private fun menuItemClick(v:MenuItem,holder: viewHolder,id:Int){
    when(v.itemId){
        R.id.delete->{
            Toast.makeText(holder.itemView.context,"done",Toast.LENGTH_LONG).show()
            onNoteclickdelete(id)
        }
        R.id.encrypt->{
            Toast.makeText(holder.itemView.context,"Encryption not implemented yet",Toast.LENGTH_LONG).show()
        }
        R.id.pin->{
            Toast.makeText(holder.itemView.context,"Pin top not implemented yet",Toast.LENGTH_LONG).show()
        }
        R.id.movetofolder->{
            Toast.makeText(holder.itemView.context,"do not know how to implement implemented yet",Toast.LENGTH_LONG).show()
        }
    }
}
    override fun getItemCount(): Int {
        return item.size
    }
}

