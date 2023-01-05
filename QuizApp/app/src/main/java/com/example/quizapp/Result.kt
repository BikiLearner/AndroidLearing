package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Result : AppCompatActivity() {
    private var nameText:TextView?=null
    private var nameprogress:TextView?=null
    private var restartBtn:Button?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        nameText=findViewById(R.id.name_set)
        nameprogress=findViewById(R.id.score_teller)
        restartBtn=findViewById(R.id.restart_button)

        val noOfCorrectAnswer=intent.getIntExtra(Constant.CORRECT_ANSWER,-99)
        val totalNoOFquestion=intent.getIntExtra(Constant.TOTAL_QUESTION,-99)
        nameText?.text=intent.getStringExtra(Constant.USER_NAME)
        nameprogress?.text="Your Score is $noOfCorrectAnswer out of $totalNoOFquestion"
        restartBtn?.setOnClickListener{
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}