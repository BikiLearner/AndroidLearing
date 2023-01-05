package com.example.quizapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.parseColor
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.quizapp.Result

class QuestionModel : AppCompatActivity() , View.OnClickListener{
    private var currentPostion=1
    private var questionsList:ArrayList<Questions>?=null
    private var mySelectedOption :Int =0
    private var processBar:ProgressBar?=null
    private var textProcessBar:TextView?=null
    private var imageFlag:ImageView?=null
    private var questionView:TextView?=null

    private var optionView1:TextView?=null
    private var optionView2:TextView?=null
    private var optionView3:TextView?=null
    private var optionView4:TextView?=null

    private var buttonSubmit:Button?=null

    private var userName:String?=null

    private var noOfCorrectAnswer=0
    private var totalNoOfquestion=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_model)
        findId()
        userName=intent.getStringExtra(Constant.USER_NAME)
        optionView1?.setOnClickListener(this)
        optionView2?.setOnClickListener(this)
        optionView3?.setOnClickListener(this)
        optionView4?.setOnClickListener(this)
        buttonSubmit?.setOnClickListener(this)


        questionsList = Constant.getQuestion()
        totalNoOfquestion=questionsList!!.size

        setQuestion()


    }

    private fun setQuestion() {


        for (i in questionsList!!) {
            Log.e("Question", i.question)
        }

        val question: Questions = questionsList!![currentPostion - 1]

        processBar?.progress = currentPostion
        textProcessBar?.text = "$currentPostion/${processBar?.max}"
        questionView?.text = question.question
        imageFlag?.setImageResource(question.image)
        optionView1?.text = question.optionOne
        optionView2?.text = question.optionTwo
        optionView3?.text = question.optionThree
        optionView4?.text = question.optionFour

        defaultOptionView()
        if(currentPostion == questionsList!!.size){
            buttonSubmit?.text="Result"

        }
    }
   fun defaultOptionView(){
       val option=ArrayList<TextView>()

       optionView1?.let {
           option.add(0,it)
       }
       optionView2?.let {
           option.add(1,it)
       }
       optionView3?.let {
           option.add(2,it)
       }
       optionView4?.let {
           option.add(3,it)
       }

       for(i in option){
           i.setTextColor(Color.parseColor("#7A8089"))
           i.typeface= Typeface.DEFAULT
           i.background=ContextCompat.getDrawable(this,R.drawable.text_view_bodder)
       }
       buttonSubmit?.text="Submit"
   }

    fun selectOptionView(tv:TextView,selectedOptionNum:Int){
        defaultOptionView()

        mySelectedOption=selectedOptionNum

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface,Typeface.BOLD)
        tv.background=ContextCompat.getDrawable(this,R.drawable.text_view_border_change_on_click)
    }
    fun findId(){
        processBar=findViewById(R.id.progressBar)
        textProcessBar=findViewById(R.id.text_progressBar)
        imageFlag=findViewById(R.id.flag_imageView)
        questionView=findViewById(R.id.question_textView)

        optionView1=findViewById(R.id.tv_option1)
        optionView2=findViewById(R.id.tv_option2)
        optionView3=findViewById(R.id.tv_option3)
        optionView4=findViewById(R.id.tv_option4)

        buttonSubmit=findViewById(R.id.submit_button)
    }

    override fun onClick(v: View?) {

        when(v?.id) {
            R.id.tv_option1 -> {
                optionView1?.let {
                    selectOptionView(it, 1)
                }
            }
            R.id.tv_option2 -> {
                optionView2?.let {
                    selectOptionView(it, 2)
                }
            }
            R.id.tv_option3 -> {
                optionView3?.let {
                    selectOptionView(it, 3)
                }
            }
            R.id.tv_option4 -> {
                optionView4?.let {
                    selectOptionView(it, 4)
                }
            }
            R.id.submit_button -> {
                buttonSubmit?.let {
                    if (mySelectedOption == 0) {
                        currentPostion++
                        when {
                            currentPostion <= questionsList!!.size -> {
                                setQuestion()
                            }
                            else ->{
                                Toast.makeText(this,"good Job biki",Toast.LENGTH_LONG).show()
                                val intent =Intent(this,com.example.quizapp.Result::class.java)
                                intent.putExtra(Constant.CORRECT_ANSWER,noOfCorrectAnswer)
                                intent.putExtra(Constant.USER_NAME,userName)
                                intent.putExtra(Constant.TOTAL_QUESTION,totalNoOfquestion)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }else{
                        val question=questionsList?.get(currentPostion-1)

                            if(question!!.correctAnswer!=mySelectedOption){
                                answerView(mySelectedOption, R.drawable.wrong_answer_colour)
                            }else{noOfCorrectAnswer++}

                        answerView(question.correctAnswer,R.drawable.correct_answer_colour)

                        if(currentPostion==questionsList?.size)
                            buttonSubmit?.text="Result"
                        else{
                            buttonSubmit?.text="Next Question"
                        }
                        mySelectedOption=0
                    }
                }
            }
        }

    }
    private fun answerView(answer:Int,drawable:Int){
        when(answer){
            1->{
                optionView1?.background=ContextCompat.getDrawable(this,drawable)
            }
            2->{
                optionView2?.background=ContextCompat.getDrawable(this,drawable)
            }
            3->{
                optionView3?.background=ContextCompat.getDrawable(this,drawable)
            }
            4->{
                optionView4?.background=ContextCompat.getDrawable(this,drawable)
            }
        }
    }
}