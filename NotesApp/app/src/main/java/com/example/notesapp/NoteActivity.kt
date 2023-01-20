package com.example.notesapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.example.notesapp.databinding.ActivityNoteBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

open class NoteActivity : AppCompatActivity() {
    private var binding:ActivityNoteBinding?=null
    private var datebase:NoteDataBase?=null
    private var doIt = false

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.customToolBarNoteActivity)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = ""
        }
        binding?.customToolBarNoteActivity?.setNavigationOnClickListener {
            onBackPressed()
        }
        datebase = (NoteDataBase.getInstance(this@NoteActivity))
        var id=0;
        binding?.dateTimeNoteActivity?.text=date()
        editOnChange()
        id= intent.getIntExtra("id",0)
        if(id!=0){
            getDataFromDataBaseAndsetOnNoteBackground(id)
            binding?.doneBtn?.setOnClickListener {
                val title = binding?.edTitle?.text.toString()
                val note = binding?.theNoteEdText?.text.toString()
                val date = date()
                val len=note.length
                val intent = Intent(this@NoteActivity, MainActivity::class.java)
                Toast.makeText(this@NoteActivity, "" + title, Toast.LENGTH_LONG).show()
                updateDatabase(title,note,date,id,len)


                startActivity(intent)
            }
        }
        else {
            Toast.makeText(this@NoteActivity, "date$id", Toast.LENGTH_LONG).show()
            binding?.doneBtn?.setOnClickListener {
                val title = binding?.edTitle?.text.toString()
                val note = binding?.theNoteEdText?.text.toString()
                val date = date()
                val len=note.length
                val intent = Intent(this@NoteActivity, MainActivity::class.java)
                Toast.makeText(this@NoteActivity, "" + title, Toast.LENGTH_LONG).show()
                addToDatabase(title, note, date,len)


                startActivity(intent)
            }
        }
    }
    private fun addToDatabase(title: String, note: String, date: String,numberofCharacter:Int){
        if (title.isNotEmpty() && note.isNotEmpty()) {
            lifecycleScope.launch {
                datebase?.noteDao()?.insertNote(NoteModel(0, title, note, date,numberofCharacter))
            }
        } else {
            Toast.makeText(this@NoteActivity, "Please enter the text", Toast.LENGTH_LONG)
                .show()
        }
    }
    private fun updateDatabase(title:String,note:String,date:String,id:Int,numberofCharacter:Int){
        if (title.isNotEmpty() && note.isNotEmpty()) {
            lifecycleScope.launch {
                datebase?.noteDao()?.updateNote(NoteModel(id, title, note, date,numberofCharacter))
            }
        } else {
            Toast.makeText(this@NoteActivity, "Please enter the text", Toast.LENGTH_LONG)
                .show()
        }
    }
    private fun getDataFromDataBaseAndsetOnNoteBackground(id:Int){
        datebase?.noteDao()?.fetchContentById(id)?.observe(this){
            binding?.edTitle?.setText(it.title)
            binding?.theNoteEdText?.setText(it.theNote)
            binding?.dateTimeNoteActivity?.text=it.date
            binding?.noOfCharacterNoteActivity?.text=it.NoOfCharacter.toString()
        }
    }
    //change no of character lively op🔥
    private fun editOnChange(){
        binding?.theNoteEdText?.addTextChangedListener {
            binding?.noOfCharacterNoteActivity?.text=it?.length.toString()
        }
    }
    private fun date(): String {
        val c = Calendar.getInstance()
        val time = c.time
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        return sdf.format(time)
    }
}