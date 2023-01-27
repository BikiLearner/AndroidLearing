package com.example.notesapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.ItemNoteLayoutBinding
import kotlin.reflect.KFunction2

class ItemNoteAdapter(
    private val item: ArrayList<NoteModel>,
    private val onNoteclickdelete: (Int) -> Unit,
    private val encryptFun: KFunction2<Int, Boolean, Unit>,
    private val selectBtnAction: Boolean,
    private val idListToDelete: ArrayList<Int> = ArrayList()
):RecyclerView.Adapter<ItemNoteAdapter.viewHolder>() {
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

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("NotifyDataSetChanged", "RtlHardcoded")
    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val popupMenu = PopupMenu(holder.itemView.context,holder.itemView)
        popupMenu.inflate(R.menu.pop_up_menu)
        val title=item[position].title
        val note=item[position].theNote
        val date=item[position].date
        val visibilityOnOfToggle=item[position].encryption
        if(visibilityOnOfToggle){
            holder.binding.lockEncrypt.visibility =View.VISIBLE
            popupMenu.menu.getItem(1).title = "Decrypt"
        }

        if(title==" "){
            holder.itemBinding(getStringToPresentInitem(note),title,date)
        }else{
            holder.itemBinding(getStringToPresentInitem(title),getStringToPresentInitem(note),date)
        }
        holder.binding.itemNoteCardView.setOnClickListener {
            val id =item[position].id
            val intent=Intent(it.context,NoteActivity::class.java)
            intent.putExtra("id",id)
            it.context.startActivity(intent)

        }
        //opening the popup dialog and deleting encrypting pinning and moving to folder
        holder.binding.itemNoteCardView.setOnLongClickListener {view ->
//            popupMenu=PopupMenu(view.context,view)
            val id =item[position].id

            popupMenu.setOnMenuItemClickListener {
                if(it.itemId == R.id.encrypt && visibilityOnOfToggle){
                    it.title = "UnEncrypted"
                    menuItemClick(it,holder,id,false)
                }else {
                    menuItemClick(it, holder, id, true)
                }

              true
            }
            popupMenu.gravity=Gravity.RIGHT
            popupMenu.show()
            true
        }
        //for radio btn
        selectFuntion(selectBtnAction, holder)
    }
private fun menuItemClick(v:MenuItem,holder: viewHolder,id:Int,encryptionToggle:Boolean){
    when(v.itemId){
        R.id.delete->{
            Toast.makeText(holder.itemView.context,"done",Toast.LENGTH_LONG).show()
            deleteDialog(holder.itemView.context,id)
        }
        R.id.encrypt->{
                encryptFun(id, encryptionToggle)
                Toast.makeText(
                    holder.itemView.context,
                    "Encryption not implemented yet",
                    Toast.LENGTH_LONG
                ).show()
        }
        R.id.pin->{
            Toast.makeText(holder.itemView.context,"Pin top not implemented yet",Toast.LENGTH_LONG).show()
        }
        R.id.movetofolder->{
            Toast.makeText(holder.itemView.context,"do not know how to implement implemented yet",Toast.LENGTH_LONG).show()
        }
    }
}
    private fun deleteDialog(context:Context,id: Int){
       val dialog=AlertDialog.Builder(context)
        dialog.setMessage("Are you sure you want to Delete?")
            .setPositiveButton("OK"){ dialog, _ ->
                onNoteclickdelete(id)
            }
            .setNegativeButton("Cancel"){dialog,_->
                dialog.dismiss()
            }
            .create().show()
    }
    private fun getStringToPresentInitem(note:String):String{
        return if(note.length <= 30 && note.lines().size<=1)
            note
        else if(note.lines().size>1){
            note.lines()[0]+"....."
        }
        else
            note.substring(0,30)+"....."
    }
    override fun getItemCount(): Int {
        return item.size
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun selectFuntion(selection: Boolean, holder: viewHolder){
        if(selection){
            holder.binding.selectButton.visibility=View.VISIBLE
            holder.binding.selectButton.setOnClickListener {

                if (it.isEnabled){
                    clickedItem(holder)
                }
            }
        }

    }

    private fun clickedItem(holder: viewHolder){
        val id =item[holder.adapterPosition].id
        idListToDelete.add(id)
    }
    fun deleteAllItem(){

    }
    fun deleteItem(isDone:Boolean){
        if(!isDone) {
            if (idListToDelete.isNotEmpty()) {
                for (i in idListToDelete) {
                    onNoteclickdelete(i)
                }
                idListToDelete.clear()
            }
        }else{
            idListToDelete.clear()
        }
    }

}


