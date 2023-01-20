package com.example.a8minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.a8minuteworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class Finish_activity : AppCompatActivity() {
    private var historyDate:AppDatabase?=null
    private var binding:ActivityFinishBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.FinishBtn?.setOnClickListener {

            finish()
        }
        setSupportActionBar(binding?.customToolbarFinish)
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.customToolbarFinish?.setNavigationOnClickListener{
            onBackPressed()
        }
        historyDate= AppDatabase.getDataBase(this@Finish_activity)
        addDate()
    }
private fun addDate(){
    val c=Calendar.getInstance()
    val dateTime=c.time
    Log.d("Date",""+dateTime)

    val sdf=SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
    val date=sdf.format(dateTime)
    Log.d("Date1",""+date)

    lifecycleScope.launch {
        historyDate?.histroyDao()?.insert(historyModel(0,date))
    }

}



    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }
}