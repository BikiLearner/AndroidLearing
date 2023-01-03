package com.example.dobcalculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.time.Year
import java.util.*

class MainActivity : AppCompatActivity() {
    private var date_text : TextView?=null
    private var age_in_minute : TextView?=null
    private var age_in_hour : TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn=findViewById<Button>(R.id.select_date)
         date_text = findViewById(R.id.date_in_normal)
        age_in_minute=findViewById(R.id.age_in_minute)
        age_in_hour=findViewById(R.id.age_in_hour)

        btn.setOnClickListener{
            clickDatePicker()
        }

    }

    private fun clickDatePicker(){

        val myCalender=Calendar.getInstance()
        var dateintext:String;
        val year = myCalender.get(Calendar.YEAR)
        val month = myCalender.get(Calendar.MONTH)
        val date = myCalender.get(Calendar.DAY_OF_MONTH)
        val dpd= DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener{ _, year, month, dayOfMonth ->
                if(dayOfMonth<=9 && month+1>=9)
                    dateintext="0$dayOfMonth/${month+1}/$year"
                else if(month+1<=9 && dayOfMonth>=9)
                    dateintext="$dayOfMonth/0${month+1}/$year"
                else if(month+1<=9 && dayOfMonth<=9)
                    dateintext="0$dayOfMonth/0${month+1}/$year"
                else
                    dateintext="$dayOfMonth/${month+1}/$year"

                date_text?.text = dateintext

                val sdf=SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

                val theDate=sdf.parse(dateintext)
                theDate?.let {
                    val dateInMinutes=theDate.time/60000
                    val dateInHour=dateInMinutes/60

                    val currentDate= sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let {
                        val currentDateInMinute=currentDate.time/60000
                        val currentDateInHour=currentDateInMinute/60

                        val differenceInMinute=currentDateInMinute-dateInMinutes
                        val differenceInHour=currentDateInHour-dateInHour

                        age_in_minute?.text=differenceInMinute.toString()
                        age_in_hour?.text=differenceInHour.toString()
                    }
                }


            }
            ,year,month,date)

        dpd.datePicker.maxDate=System.currentTimeMillis()

        dpd.show()

    }
}