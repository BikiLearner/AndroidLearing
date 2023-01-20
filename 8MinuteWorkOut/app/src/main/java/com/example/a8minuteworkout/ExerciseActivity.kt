package com.example.a8minuteworkout

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.Toast
import androidx.core.os.HandlerCompat.postDelayed
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a8minuteworkout.databinding.ActivityExerciseBinding
import com.example.a8minuteworkout.databinding.CustomBackDialogBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding:ActivityExerciseBinding?=null
   private var countDownTimer: CountDownTimer?=null
   private var restProgress =0
   private var exerciseProgress =0
    private var exerciseList:ArrayList<ExerciseModel>?=null
    private var currentExercisePositon=-1
    private var textToSpeech:TextToSpeech?=null
    private var mediaPlayer:MediaPlayer?=null
    private var exerciseAdapter:RecyclarAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        textToSpeech= TextToSpeech(this,this@ExerciseActivity)

        setSupportActionBar(binding?.customToolbarExcercise)
        exerciseList=Constants.defaultExerciseList()
        //this hole line use to get the back button for the toolbar fk**
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.customToolbarExcercise?.setNavigationOnClickListener{
            customWantToQuitDialog()
        }
        resetTimerRest()
        recycleViewsetup()
    }
    private fun recycleViewsetup(){
        exerciseAdapter= exerciseList?.let { RecyclarAdapter(it) }
        binding?.rvExerciseStatus?.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding?.rvExerciseStatus?.adapter=exerciseAdapter
    }

    @SuppressLint("SetTextI18n")
    private fun resetTimerRest(){
        try {
            val sound = Uri.parse(
                "android.resource://com.example.a8minuteworkout/"+R.raw.app_src_main_res_raw_press_start)
            mediaPlayer=MediaPlayer.create(applicationContext,sound)
            mediaPlayer?.isLooping=false
            mediaPlayer?.start()
        }catch (e: Exception){
            e.printStackTrace()
        }
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
            restProgress = 0
        }
        if(currentExercisePositon<exerciseList!!.size-1) {
            binding?.upcommingExerciseLl?.visibility=View.VISIBLE
            binding?.nextExerciseName?.text=" ${exerciseList?.get(currentExercisePositon + 1)?.getExerciseName()}"
            binding?.flprogressBar?.visibility = View.VISIBLE
            binding?.tvTitle?.visibility=View.VISIBLE
            binding?.flprogressBarExersise?.visibility = View.INVISIBLE
            binding?.exerciseImg?.visibility=View.INVISIBLE
            binding?.tvExercise?.visibility=View.INVISIBLE

            startTimer()
        }else{
            val intent=Intent(this@ExerciseActivity,Finish_activity::class.java)
            startActivity(intent)
            finish()
        }
    }



    private fun resetTimerExcersise(){
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
            exerciseProgress = 0
        }
        if(currentExercisePositon<exerciseList!!.size) {
            binding?.flprogressBar?.visibility = View.INVISIBLE
            binding?.tvTitle?.visibility=View.INVISIBLE
            binding?.flprogressBarExersise?.visibility = View.VISIBLE
            binding?.tvExercise?.text = "${exerciseList?.get(currentExercisePositon)?.getExerciseName()}"
            binding?.tvExercise?.visibility = View.VISIBLE
            binding?.exerciseImg?.visibility = View.VISIBLE
            exerciseList?.get(currentExercisePositon)?.getExerciseImg()
                ?.let { binding?.exerciseImg?.setImageResource(it) }
            binding?.upcommingExerciseLl?.visibility=View.INVISIBLE

            speakOut("Start ${exerciseList!![currentExercisePositon].getExerciseName()}")
            startTimerForExercise()
        }
    }

    private fun startTimerForExercise() {
        binding?.progressBarExersise?.progress= exerciseProgress
        countDownTimer=object : CountDownTimer(30000,1000) {
            override fun onTick(millisUntilFinished: Long) {
               exerciseProgress++
                binding?.progressBarExersise?.progress=30-exerciseProgress
                binding?.tvTimerExersise?.text=(30-exerciseProgress).toString()

            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity,"finish timer", Toast.LENGTH_LONG).show()
                exerciseList!![currentExercisePositon].setIsSelected(false)
                exerciseList!![currentExercisePositon].setIsComplete(true)

                exerciseAdapter?.notifyDataSetChanged()
                resetTimerRest()
            }

        }.start()
    }

    override fun onBackPressed() {
        customWantToQuitDialog()
    }

    private fun startTimer(){
        binding?.progressBar?.progress= restProgress
         countDownTimer=object : CountDownTimer(10000,1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress=10-restProgress
                binding?.tvTimer?.text=(10-restProgress).toString()
                speakOut("${10-restProgress}")

            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity,"finish timer", Toast.LENGTH_LONG).show()
                currentExercisePositon++
                exerciseList!![currentExercisePositon].setIsSelected(true)
                exerciseAdapter?.notifyDataSetChanged()


                resetTimerExcersise()

            }

        }.start()
    }
    override fun onInit(status: Int) {
        if(status==TextToSpeech.SUCCESS){
            val result=textToSpeech!!.setLanguage(Locale.ENGLISH)
            if(result == TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","The language is not supported")
            }
        }else
            Log.e("TTS","Init failed")
    }

    private fun speakOut(text:String){
        textToSpeech?.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    private fun customWantToQuitDialog(){
        val customDialog=Dialog(this@ExerciseActivity)
        val dialogBinding=CustomBackDialogBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.btnDialogYes.setOnClickListener {
           this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.btnDialogNo.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
            restProgress = 0
        }
        if(textToSpeech!=null){
            textToSpeech?.stop()
            textToSpeech?.shutdown()
        }
        if(mediaPlayer!=null){
            mediaPlayer?.stop()
            mediaPlayer=null
        }
        binding=null
    }




}
