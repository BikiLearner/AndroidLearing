package com.example.a8minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.a8minuteworkout.databinding.ActivityWorkoutHistoryBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class workoutHistory : AppCompatActivity() {
    private var binding:ActivityWorkoutHistoryBinding?=null
    private lateinit var historyData:AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityWorkoutHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.customHistoryToolbar)
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title="History"
        }
        binding?.customHistoryToolbar?.setNavigationOnClickListener {
            onBackPressed()
        }
        historyData= AppDatabase.getDataBase(this)
        listData()
    }

    private fun listData(){
    historyData.histroyDao().getAllDate().observe(this){
        val adapter=historyAdapter(it as ArrayList<historyModel>)
        binding?.historyList?.layoutManager=LinearLayoutManager(this@workoutHistory)
        binding?.historyList?.adapter=adapter
    }
    }

//    private fun addDate(){
//        lifecycleScope.launch {
//            historyData.histroyDao().insert(historyModel(0,"19/06/2002"))
//        }
//    }
    override fun onDestroy() {
        super.onDestroy()
    binding=null
    }
}