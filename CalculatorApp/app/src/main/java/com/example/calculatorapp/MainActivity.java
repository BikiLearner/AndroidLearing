package com.example.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
TextView result,inputs;
MaterialButton button_ac,button_c,button_dots,button_equals,button_module;
MaterialButton button_9,button_8,button_7,button_6,button_5,button_4,button_3,button_2,button_1,button_0;
MaterialButton button_addition,button_minus,button_divide,button_multiply,button_plus_minus;
ImageButton current_change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result=findViewById(R.id.result);
        inputs=findViewById(R.id.input);

        current_change=findViewById(R.id.currency_change);

        current_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next;
                next=new Intent(MainActivity.this,curremcy_c.class);
                startActivity(next);
            }
        });


        call();
    }

    public void assignId(MaterialButton btn,int button_name){
        btn=findViewById(button_name);
        btn.setOnClickListener(this);


    }
    public void call() {
        assignId(button_ac, R.id.button_ac);
        assignId(button_c, R.id.button_c);
        assignId(button_dots, R.id.button_dot);
        assignId(button_equals, R.id.button_equal);
        assignId(button_addition, R.id.button_addition);
        assignId(button_minus, R.id.button_minus);
        assignId(button_multiply, R.id.button_multiply);
        assignId(button_divide, R.id.button_divide);
        assignId(button_plus_minus, R.id.button_plus_minus);
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
                result.setText(finalresult);
            }
            if (finalresult.equals("Syntax Error")) {
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
}