package com.example.a8minuteworkout

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.example.a8minuteworkout.databinding.ActivityBmiCalculatorBinding
import kotlin.math.floor

class BmiCalculator : AppCompatActivity() {
    private var binding:ActivityBmiCalculatorBinding?=null
    private var measurementUnit:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBmiCalculatorBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.bmiToolbar)
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title="BMI Calculator"
        }
        binding?.bmiToolbar?.setNavigationOnClickListener{
            onBackPressed()
        }
        defaultVlaue()
        binding?.MetricBtn?.setOnClickListener {
            binding?.frameHeightCm?.visibility= View.VISIBLE
            binding?.frameHeightFeetInches?.visibility=View.GONE
            measurementUnit=binding?.measureUnitOfHeightCm?.text.toString()

        }
        binding?.usUnitBtn?.setOnClickListener {
            binding?.frameHeightCm?.visibility= View.GONE
            binding?.frameHeightFeetInches?.visibility=View.VISIBLE
            measurementUnit=binding?.measureUnitOfHeightInches?.text.toString()
        }

       binding?.calculatorBtn?.setOnClickListener {
           getNumbers()
       }
    }

    private fun getNumbers(){
        if(measurementUnit.equals("cm")){
           val height=(((binding?.edHeight?.text).toString()).toDouble())/100
           val weight=((binding?.edWeight?.text).toString()).toDouble()
            binding?.resultData?.text=calculateBmi(height,weight)
        }
        else if (measurementUnit.equals("inches")){
            val heightIn=((binding?.edHeightInches?.text).toString()).toDouble()
            val heightfeet=((binding?.edHeightFeet?.text).toString()).toDouble()
            val weight=((binding?.edWeight?.text).toString()).toDouble()
            val height1=(heightIn/12)+heightfeet
            val height=height1/3.281
            binding?.resultData?.text=calculateBmi(height,weight)
        }

    }
    private fun calculateBmi(height:Double,weight:Double):String{


       val result=weight/(height*height)

        bmiCatagory(result)

        return  floor(result).toString()

    }
    @SuppressLint("SetTextI18n")
    private fun bmiCatagory(result: Double){

        if(result<16){
            binding?.bodyWeightName?.text="Severe Thinness"
            binding?.root?.setBackgroundColor(Color.parseColor("#bc2020"))

        }
        else if(result in 16.0..17.0){

            binding?.bodyWeightName?.text="Moderate Thinness"
            binding?.root?.setBackgroundColor(Color.parseColor("#d38888"))
        }
        else if(result in 17.0..18.5){

            binding?.bodyWeightName?.text="Mild Thinness"
            binding?.root?.setBackgroundColor(Color.parseColor("#ffe400"))
        }
        else if(result in 18.5..25.0){

            binding?.bodyWeightName?.text="Normal"
            binding?.root?.setBackgroundColor(Color.parseColor("#008137"))
        }
        else if(result in 25.0..30.0){

            binding?.bodyWeightName?.text="Overweight"
            binding?.root?.setBackgroundColor(Color.parseColor("#ffe400"))
        }
        else if(result in 30.0..35.0){

            binding?.bodyWeightName?.text="Obese Class I"
            binding?.root?.setBackgroundColor(Color.parseColor("#d38888"))
        }
        else if(result in 35.0..40.0){

            binding?.bodyWeightName?.text="Obese Class II"
            binding?.root?.setBackgroundColor(Color.parseColor("#bc2020"))
        }
        else{
            binding?.bodyWeightName?.text="Obese Class III"
            binding?.root?.setBackgroundColor(Color.parseColor("#8a0101"))
        }
    }
private fun defaultVlaue(){
    binding?.frameHeightCm?.visibility= View.VISIBLE
    binding?.frameHeightFeetInches?.visibility=View.GONE
    measurementUnit=binding?.measureUnitOfHeightCm?.text.toString()
}
    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }
}