package com.example.notesapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var adapter: ItemNoteAdapter? = null
    private var dataBase:NoteDataBase?=null
    private var mainMenu:Menu?=null
    private var count=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.customToolBarHome)
         dataBase = NoteDataBase.getInstance(this@MainActivity)
        binding?.addNewNoteButton?.setOnClickListener {
            val intent = Intent(this@MainActivity, NoteActivity::class.java)
            startActivity(intent)
        }
        addNote(false)

        binding?.selectAllBtn?.setOnClickListener {
                selectSearchThreeDotVisibility(false)
                addNote(true)
        }
        binding?.trippleDotSyncSettingDialogOpener?.setOnClickListener {
            popupmenu(it)
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if(count>0){
            addNote(false)
            selectSearchThreeDotVisibility(true)

        }else{
            finish()
        }

//        super.onBackPressed()
    }
    @SuppressLint("SuspiciousIndentation")
    private fun selectSearchThreeDotVisibility(set:Boolean){
        if(set){
            binding?.searchBtn?.visibility=View.VISIBLE
            binding?.selectAllBtn?.visibility=View.VISIBLE
            binding?.trippleDotSyncSettingDialogOpener?.visibility=View.VISIBLE
            mainMenu?.findItem(R.id.menu_delete)?.isVisible=false
            mainMenu?.findItem(R.id.menu_select_all)?.isVisible=false
            mainMenu?.findItem(R.id.menu_close)?.isVisible=false
            count=0
        }else{
            count=1;
            mainMenu?.findItem(R.id.menu_delete)?.isVisible=true
            binding?.selectAllBtn?.visibility=View.GONE
            mainMenu?.findItem(R.id.menu_select_all)?.isVisible=true
            binding?.searchBtn?.visibility=View.GONE
            mainMenu?.findItem(R.id.menu_close)?.isVisible=true
            binding?.trippleDotSyncSettingDialogOpener?.visibility=View.GONE
        }

    }

    private fun addNote(select: Boolean) {

        dataBase?.noteDao()?.fetchAll()?.observe(this) {
            if(it.isEmpty()){
                binding?.noElementPresent?.visibility=View.VISIBLE
            }
            adapter = ItemNoteAdapter(
                it as ArrayList<NoteModel>,
                ::deleteItem,
                ::encryptItem,
                select
            )
            val linearLayout=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true)
            linearLayout.stackFromEnd=true
            linearLayout.isSmoothScrollbarEnabled=false
            binding?.listOfNote?.layoutManager=linearLayout
            binding?.listOfNote?.adapter = adapter
        }

    }

    private fun deleteItem(id: Int) {

        lifecycleScope.launch {
            dataBase?.noteDao()?.deleteNote(id)
        }
        Toast.makeText(this, "hello great ", Toast.LENGTH_LONG).show()
    }
    private fun popupmenu(view:View){
        val popupMenu=PopupMenu(this,view)
        popupMenu.inflate(R.menu.popup_menu_for_setting_sync)
        popupMenu.show()
    }
    private fun encryptItem(id:Int,encryption:Boolean){
        encyrptionDialog(this)
        lifecycleScope.launch{
            dataBase?.noteDao()?.update(encryption,id)
        }

        Toast.makeText(this,"Encryption is not getting setting up fuck",Toast.LENGTH_SHORT).show()
    }
    //total bio-matrix is here form line 118 to 174 and the biometric dependency is
//    implementation("androidx.biometric:biometric:1.2.0-alpha05") got in android studio official webpage


    private fun encyrptionDialog(context: Context){
        val biometricPrompt:BiometricPrompt
        val executor=ContextCompat.getMainExecutor(context)

        biometricPrompt=
            BiometricPrompt(this@MainActivity,executor,object : BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(this@MainActivity,"Great Encryption Done",Toast.LENGTH_LONG).show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(this@MainActivity,"Wrong finger print",Toast.LENGTH_LONG).show()
                }

            })
        val promptInfo:PromptInfo = PromptInfo.Builder().setTitle("To access")
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
        val biometricManager= BiometricManager.from(this)//to check if i had biomaterial or not

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        mainMenu=menu
        menuInflater.inflate(R.menu.delete_select_all_menu,mainMenu)
        selectSearchThreeDotVisibility(true)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_delete->{
                deleteTheSelected()
                Toast.makeText(this,"The menu_delete is selected",Toast.LENGTH_SHORT).show()
            }
            R.id.menu_select_all->{
                //TODO : Please implement it to select all as soon as possible

                Toast.makeText(this,"Not yet Implemented",Toast.LENGTH_SHORT).show()
            }
            R.id.menu_close-> {
                selectSearchThreeDotVisibility(true)
                addNote(false)
            }


        }
        return super.onOptionsItemSelected(item)
    }
    private fun deleteTheSelected(){
        val alertDialog=AlertDialog.Builder(this@MainActivity)
        alertDialog.setTitle("Delete")
            .setPositiveButton("Yes"){_,_->
                adapter?.deleteItem(false)
                Handler().postDelayed({
                    selectSearchThreeDotVisibility(true)
                    addNote(false)
                },500)

            }
            .setNegativeButton("Cancel"){_,_->
                adapter?.deleteItem(true)
            }
            .show()
    }
    private fun searchOption(){

    }
}

/*    TODO : add delete option and dialog box setup database and setup recycleView and also add long
  press dialog*/

