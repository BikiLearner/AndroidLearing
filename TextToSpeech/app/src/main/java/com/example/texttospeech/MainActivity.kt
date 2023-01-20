package com.example.texttospeech

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.example.texttospeech.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var tts:TextToSpeech?=null
    private var binding: ActivityMainBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        tts=TextToSpeech(this,this@MainActivity)

        binding?.buttonSpeech?.setOnClickListener{view->
            if(binding?.edConvert?.text!!.isEmpty()){
                Toast.makeText(this@MainActivity,"Please Enter text",Toast.LENGTH_LONG).show()
            }else{
                speakOut(binding?.edConvert?.text.toString())
            }

        }
    }

    override fun onInit(status: Int) {
        if(status==TextToSpeech.SUCCESS){
            val result=tts!!.setLanguage(Locale.ENGLISH)

            if(result == TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","The language is not supported")
            }
        } else{
            Log.e("TTS","initilisationfalil")
        }
    }

    private fun speakOut(text:String){
        tts?.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    override fun onDestroy() {
        super.onDestroy()
        if(tts!=null){
            tts?.stop()
            tts?.shutdown()
        }
        binding=null
    }

}