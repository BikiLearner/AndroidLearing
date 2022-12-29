package com.example.calculatorapp;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class curremcy_c extends AppCompatActivity implements View.OnClickListener {
    TextView result,inputs,currency_name;
    int from_rate=0,to_rate=0,toggle_It=0;
    String rate;
    String textset1,textset2;
    MaterialButton button_ac,button_c,button_dots,button_equals,button_module;
    MaterialButton button_9,button_8,button_7,button_6,button_5,button_4,button_3,button_2,button_1,button_0;
    MaterialButton button_addition,button_minus,button_divide,button_multiply;
    MaterialButton from_change,to_change ,back_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curremcy_c);
        Intent theflagvalue=getIntent();
        toggle_It=theflagvalue.getIntExtra("toggle",-9);
        textset1="Select Your currency Input";
        textset2="Select curreny to change";
        if(toggle_It==0){
            from_rate=0;
            to_rate=0;
        }
        else if(toggle_It==1) {
            from_rate = theflagvalue.getIntExtra("From", -99);
            textset1 = theflagvalue.getStringExtra("currency_name_change");
            to_rate = from_rate;
            textset2 = textset1;
        }
        else if(toggle_It==2){
            from_rate = theflagvalue.getIntExtra("From", -99);
            textset1 = theflagvalue.getStringExtra("currency_name_change");
            to_rate = theflagvalue.getIntExtra("to",-99);
            textset2 = theflagvalue.getStringExtra("to_the_currency");
        }

        result=findViewById(R.id.result);
        inputs=findViewById(R.id.input);
        from_change=findViewById(R.id.from_currency);
        to_change=findViewById(R.id.to_currency);
        back_button=findViewById(R.id.button_back);


//       onclick for rate change button
        Intent next;
        next=new Intent(curremcy_c.this,ListOfcurrencyrate.class);
        from_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next.putExtra("toggle_1",1);
                next.putExtra("text_1","Biki");
                startActivity(next);
                finish();
            }
        });

        to_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next.putExtra("toggle_1",2);
                next.putExtra("text_1",textset1);
                next.putExtra("form_value",from_rate);
                startActivity(next);
                finish();
            }
        });

        from_change.setText(textset1);
        to_change.setText(textset2);
        rate=Double.toString(list_for_currency(from_rate,to_rate));

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent previous;
                previous=new Intent(curremcy_c.this,MainActivity.class);
                startActivity(previous);
                finish();
            }
        });



        call();
    }
    //setting button click to all
    public void assignId(MaterialButton btn, int button_name){
        btn=findViewById(button_name);
        btn.setOnClickListener(this);


    }
//    calling the assignId function
    public void call() {
        assignId(button_ac, R.id.button_ac);
        assignId(button_c, R.id.button_c);
        assignId(button_dots, R.id.button_dot);
        assignId(button_equals, R.id.button_equal);
        assignId(button_addition, R.id.button_addition);
        assignId(button_minus, R.id.button_minus);
        assignId(button_multiply, R.id.button_multiply);
        assignId(button_divide, R.id.button_divide);
        assignId(button_module,R.id.button_module);
        assignId(button_9, R.id.button_9);
        assignId(button_8, R.id.button_8);
        assignId(button_7, R.id.button_7);
        assignId(button_6, R.id.button_6);
        assignId(button_5, R.id.button_5);
        assignId(button_4, R.id.button_4);
        assignId(button_3, R.id.button_3);
        assignId(button_2, R.id.button_2);
        assignId(button_1, R.id.button_1);
        assignId(button_0, R.id.button_0);
    }
    @Override
    public void onClick(View v) {
        MaterialButton current_button=(MaterialButton) v;


        String buttonText=current_button.getText().toString();
        String data_to_calculate="";
        data_to_calculate = inputs.getText().toString();
        if(buttonText.equals("AC")){
            inputs.setText("");
            result.setText("0");
            return;

        }
        if(buttonText.equals("=")){
            inputs.setText(result.getText());
            //calculate
            return;

        }
        if(buttonText.equals("c") && !data_to_calculate.equals("")) {
            data_to_calculate = data_to_calculate.substring(0, data_to_calculate.length() - 1);
        }
        else {

            data_to_calculate = data_to_calculate + buttonText;
        }
        if(!data_to_calculate.equals("c")) {
            inputs.setText(data_to_calculate);
            if(data_to_calculate.length()>5)
                inputs.setTextSize(25.0F);

            String finalresult = getresult(data_to_calculate);
            if (!finalresult.equals("Syntax Error")) {
                String newresult;
               newresult = finalresult + "*" + rate;
               newresult = getresult(newresult);
                if(!finalresult.equals("Syntax Error"))
                    result.setText(newresult);
                else
                    result.setText("");
            }
            if (finalresult.equals("Syntax Error") && !finalresult.startsWith("*")) {
                result.setText("Syntax Error");
            }
            if (finalresult.equals("org.mozilla.javascript.Undefined@0")) {
                inputs.setText("");
                result.setText("0");
            }
        }

    }
    String getresult(String data) {
        try{
            Context context=Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable=context.initStandardObjects();
            String finalresult=context.evaluateString(scriptable,data,"Javascript",1,null).toString();
            if(finalresult.endsWith(".0")){
                finalresult=finalresult.replace(".0","");
            }
            return finalresult;
        }catch (Exception e) {
            return "Syntax Error";
        }
    }
    public double list_for_currency(int from,int to){
        //India =0,USD=1,Euro=2,British=3,Australia=4,Canadian=5,Singapore Dollar=6,Swiss Franc=7,Malaysian Ringgit=8,Japanese Yen=9,Chinese Yen=10,
        double[][] currency_arr = {{1.00, 82.697002,87.894256,99.775596,55.627931,60.817204,61.196476,88.685350,18.689033,0.623196,11.874232},
                { 1.062907,1.00,1.206424,0.012094,0.672679,0.735410,0.739961,1.071971,0.225982,0.007538,0.143510},
                {0.940741,1.134957,1.00,0.011376,0.632744,0.691827,0.696146,1.008673,0.212598,0.007086,0.135052},
                {0.828711,0.881009,0.010022,1.00,0.556834,0.609271,0.613222,0.888220,0.187276,0.006247,0.119018},
                {1.488229,1.582174,1.795598,0.017994,1.00,1.094414,1.101144,1.595471,0.336317,0.011219,0.213667},
                {1.359737,1.445525,1.640681,0.016441,0.913754,1.00,1.006244,1.458153,0.307283,0.010249,0.195219},
                {1.351076,1.436302,1.630104,0.016337,0.908647,0.993659,1.00,1.448598,0.305328,0.010186,0.194010},
                {0.932661,0.991560,1.125196,0.011280,0.627403,0.685921,0.690369,1.00,0.210767,0.007028,0.133832},
                {4.425143,4.704459,5.338656,0.053506,2.976548,3.254503,3.274708,4.743630,1.00,0.033348,0.635267},
                {132.675599,141.045778,160.066705,1.604167,89.234264,97.573501,98.181156,142.225494,29.983140,1.00,19.051813},
                {6.963724,7.402874,8.401480,0.084194,4.684107,5.120747,5.152891,7.465246,1.573721,0.052482,1.00}
        };

            return currency_arr[from][to];
        }
    }