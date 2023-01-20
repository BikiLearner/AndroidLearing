package com.example.demotimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import android.widget.Toast
import com.example.demotimer.databinding.ActivityMainBinding
import java.util.Objects

class MainActivity : AppCompatActivity() {
    var binding:ActivityMainBinding?=null
    var countDownTimer:CountDownTimer?=null
    var timeDuration:Long=60000
    var pauseOffset:Long=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.textView?.text= (timeDuration/1000).toString()
        binding?.startBtn?.setOnClickListener {
            startTimmer(pauseOffset)
        }
        binding?.buttonPauz?.setOnClickListener {
            pauseTimmer()
        }
        binding?.buttonReset?.setOnClickListener {
            resetTimmer()
        }


    }

    private fun startTimmer(pauseOffset: Long){

        countDownTimer=object:CountDownTimer(timeDuration-pauseOffset,1000){
            override fun onTick(millisUntilFinished: Long) {
               this@MainActivity.pauseOffset =timeDuration-millisUntilFinished
                binding?.textView?.text=(millisUntilFinished/1000).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@MainActivity,"finish timer",Toast.LENGTH_LONG).show()
            }
        }.start()
    }

    private fun pauseTimmer() {
       if(countDownTimer!=null)
           countDownTimer!!.cancel()
    }

    private fun resetTimmer(){
        if(countDownTimer!=null) {
            countDownTimer!!.cancel()
            binding?.textView?.text=(timeDuration/1000).toString()
            countDownTimer=null
            this.pauseOffset = 0
        }
    }
}