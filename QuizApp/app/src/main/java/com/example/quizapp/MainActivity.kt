package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton=findViewById<Button>(R.id.start_button)
        val name=findViewById<EditText>(R.id.name_Enter)

        startButton.setOnClickListener{
            if(name.text.isEmpty()){
                Toast.makeText(this,"Please enter Your name to start",Toast.LENGTH_LONG).show()
            }else{
                val intent =Intent(this,QuestionModel::class.java)
                intent.putExtra(Constant.USER_NAME,name.text.toString())
                startActivity(intent)
            }
        }
    }
}