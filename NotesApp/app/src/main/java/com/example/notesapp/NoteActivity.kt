package com.example.notesapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.example.notesapp.databinding.ActivityNoteBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

open class NoteActivity : AppCompatActivity() {
    private var binding:ActivityNoteBinding?=null
    private var datebase:NoteDataBase?=null
    private lateinit var biometricPrompt:BiometricPrompt
    private lateinit var promptInfo:BiometricPrompt.PromptInfo
    private var id=0
    private var fingerPrintToggle:Boolean=false
    private val undoArray=ArrayList<String>()

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

        datebase = (NoteDataBase.getInstance(this@NoteActivity))
         id= intent.getIntExtra("id",0)
        Toast.makeText(this, "id = $id",Toast.LENGTH_LONG).show()
        if(id!=0){
            fingerPrintToggle=true
            checkAndExcuteEncryption(id)

        }

        binding?.customToolBarNoteActivity?.setNavigationOnClickListener {
            buttonDoneOnNoclickAddToDatabase()
            fingerPrintToggle=false

        }
        binding?.dateTimeNoteActivity?.text=date()
        editOnChange()

//            Toast.makeText(this@NoteActivity, "date$id", Toast.LENGTH_LONG).show()
            binding?.doneBtn?.setOnClickListener {
                buttonDoneOnNoclickAddToDatabase()
                fingerPrintToggle=false


            }
        binding?.undoBtn?.setOnClickListener {
        }
        }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        fingerPrintToggle=false
        buttonDoneOnNoclickAddToDatabase()


    }
    private fun buttonDoneOnNoclickAddToDatabase(){
        val title = binding?.edTitle?.text.toString()
        val note = binding?.theNoteEdText?.text.toString()
        val date = date()
        val len=note.length
//        val intent = Intent(this@NoteActivity, MainActivity::class.java)
//        Toast.makeText(this@NoteActivity, "" + title, Toast.LENGTH_LONG).show()
        if(id!=0){
            datebase?.noteDao()?.fetchContentById(id)?.observe(this@NoteActivity){
                val encryptToggle=it.encryption
                updateDatabase(title,note,date,id,len,encryptToggle)
            }
        }
        else{
            addToDatabase(title, note, date,len)
        }
        finish()
//        startActivity(intent)
    }
    private fun addToDatabase(title: String, note: String, date: String,numberofCharacter:Int){
        if (title.isNotEmpty() && note.isNotEmpty()) {
            lifecycleScope.launch {
                datebase?.noteDao()?.insertNote(NoteModel(0, title, note, date,numberofCharacter,false))
            }
        }
        else if(title.isNotEmpty() && note.isEmpty()){
            lifecycleScope.launch {
                datebase?.noteDao()?.insertNote(NoteModel(0, title, " ", date,numberofCharacter,false))
            }
        }  else if(title.isEmpty() && note.isNotEmpty()) {
            lifecycleScope.launch {
                datebase?.noteDao()
                    ?.insertNote(NoteModel(0, " ", note, date, numberofCharacter, false))
            }
        }
            else {
            Toast.makeText(this@NoteActivity, "Please enter the text", Toast.LENGTH_LONG)
                .show()
        }
    }
    private fun updateDatabase(
        title: String,
        note: String,
        date: String,
        id: Int,
        numberofCharacter: Int,
        encryptToggle: Boolean
    ){
        if (title.isNotEmpty() && note.isNotEmpty()) {
            lifecycleScope.launch {
                datebase?.noteDao()?.updateNote(NoteModel(id, title, note, date,numberofCharacter,encryptToggle))
            }
        }   else if(title.isNotEmpty() && note.isEmpty()){
            lifecycleScope.launch {
                datebase?.noteDao()?.updateNote(NoteModel(id, title, "", date,numberofCharacter,encryptToggle))
            }
        }  else if(title.isEmpty() && note.isNotEmpty()) {
            lifecycleScope.launch {
                datebase?.noteDao()?.updateNote(NoteModel(id, "", note, date,numberofCharacter,encryptToggle))
            }
        }else {
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
    //total bio-matrix is here form line 118 to 174 and the biometric dependency is
//    implementation("androidx.biometric:biometric:1.2.0-alpha05") got in android studio official webpage
    private fun checkAndExcuteEncryption(id: Int){
        datebase?.noteDao()?.fetchContentById(id)?.observe(this){
            if(it.encryption &&fingerPrintToggle){
                Toast.makeText(this@NoteActivity,"Encryption id"+it.id,Toast.LENGTH_LONG).show()
                encyrptionDialog(this)
            }
            else{
                getDataFromDataBaseAndsetOnNoteBackground(id)
//                Toast.makeText(this,"No encryption",Toast.LENGTH_SHORT).show()
            }
        }
    }

    open fun encyrptionDialog(context:Context){
        val executor=ContextCompat.getMainExecutor(context)

        biometricPrompt=
            BiometricPrompt(this@NoteActivity,executor,object : BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    getDataFromDataBaseAndsetOnNoteBackground(id)
                    Toast.makeText(this@NoteActivity,"Setup fingerPrint",Toast.LENGTH_LONG).show()
                }

            })
        promptInfo= BiometricPrompt.PromptInfo.Builder().setTitle("To access")
            .setDescription("Practicing the biometric in app")
            .setNegativeButtonText("Use account password")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK)
            .setSubtitle("Use your fingers print")
            .build()

        if(biometricAvailable()) {
            biometricPrompt.authenticate(promptInfo)
        }
    }

    private fun biometricAvailable():Boolean{
        val biometricManager= BiometricManager.from(this)//to check if i had biomatirc or not

        return when(biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK)){
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE->{
                Toast.makeText(this,"Device do not have fingerprint",Toast.LENGTH_LONG).show()
                false
            }

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE->{
                Toast.makeText(this,"Device Not working",Toast.LENGTH_LONG).show()
                false
            }
            BiometricManager.BIOMETRIC_SUCCESS->{
//                Toast.makeText(this,"Setup fingerPrint",Toast.LENGTH_LONG).show()
                true
            }
            else -> {false}
        }


    }
}