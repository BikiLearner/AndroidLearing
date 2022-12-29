package com.example.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class ListOfcurrencyrate extends AppCompatActivity {
ListView currency_list;
MaterialButton back_button,search_button;
ArrayAdapter<String> arrayAdapter;
    int toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ofcurrencyrate);


        currency_list = findViewById(R.id.list);
        List<String> list = new ArrayList<>();
        list.add("India INR");
        list.add("US Dollar USD");
        list.add("EURO");
        list.add("British Pound");
        list.add("Australian Dollar");
        list.add("Canadian Dollar");
        list.add("Singapore Dollar");
        list.add("Swiss Franc");
        list.add("Malaysian Ringgit");
        list.add("Japanese Yen");
        list.add("Chinese Yen");
        Intent toggle_value = getIntent();
        toggle = toggle_value.getIntExtra("toggle_1", 99);
        arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,list);
        currency_list.setAdapter(arrayAdapter);

        search_button=findViewById(R.id.search_next);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i=new Intent(ListOfcurrencyrate.this,searchActivity.class);
                startActivity(i);
            }
        });

        back_button=findViewById(R.id.list_back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
        if (toggle == 1) {
            currency_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent inext;
                    inext = new Intent(ListOfcurrencyrate.this, curremcy_c.class);
                     inext.putExtra("toggle",1);
                    if (position == 0) {
                            inext.putExtra("From", 0);
                            inext.putExtra("currency_name_change", "India INR");
                    }
                    if (position == 1) {
                            inext.putExtra("From", 1);
                            inext.putExtra("currency_name_change", "US Dollar USD");
                    }
                    if (position == 2) {
                        inext.putExtra("From", 2);
                        inext.putExtra("currency_name_change", "EURO");

                    }
                    if (position == 3) {
                        inext.putExtra("From", 3);
                        inext.putExtra("currency_name_change", "British Pound");

                    }
                    if (position == 4) {
                        inext.putExtra("From", 4);
                        inext.putExtra("currency_name_change", "Australian Dollar");
                    }
                    if (position == 5) {
                        inext.putExtra("From", 5);
                        inext.putExtra("currency_name_change", "Canadian Dollar");
                    }
                    if (position == 6) {
                        inext.putExtra("From", 6);
                        inext.putExtra("currency_name_change", "Singapore Dollar");
                    }
                    if (position == 7) {
                        inext.putExtra("From", 7);
                        inext.putExtra("currency_name_change", "Swiss Franc");
                    }
                    if (position == 8) {
                        inext.putExtra("From", 8);
                        inext.putExtra("currency_name_change", "Malaysian Ringgit");
                    }
                    if (position == 9) {
                        inext.putExtra("From", 9);
                        inext.putExtra("currency_name_change", "Japanese Yen");
                    }
                    if (position == 10) {
                        inext.putExtra("From", 10);
                        inext.putExtra("currency_name_change", "Chinese Yen");;
                    }
                    startActivity(inext);
                    finish();
                }
            });
        }
        else{
            currency_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent inext;
                    inext = new Intent(ListOfcurrencyrate.this, curremcy_c.class);
                    String value_name=toggle_value.getStringExtra("text_1");
                    inext.putExtra("toggle",2);
                    int rate_value=toggle_value.getIntExtra("form_value",-1);
                    if (position == 0) {
                            inext.putExtra("From",rate_value);
                            inext.putExtra("currency_name_change", value_name);
                            inext.putExtra("to", 0);
                            inext.putExtra("to_the_currency", "India INR");
                    }
                    if (position == 1) {
                        inext.putExtra("From",rate_value);
                        inext.putExtra("currency_name_change", value_name);
                        inext.putExtra("to", 1);
                        inext.putExtra("to_the_currency", "US Dollar USD");
                    }
                    if (position == 2) {
                        inext.putExtra("From",rate_value);
                        inext.putExtra("currency_name_change", value_name);
                        inext.putExtra("to", 2);
                        inext.putExtra("to_the_currency", "EURO");
                    }
                    if (position == 3) {
                        inext.putExtra("From",rate_value);
                        inext.putExtra("currency_name_change", value_name);
                        inext.putExtra("to", 3);
                        inext.putExtra("to_the_currency", "British Pound");
                    }
                    if (position == 4) {
                        inext.putExtra("From",rate_value);
                        inext.putExtra("currency_name_change", value_name);
                        inext.putExtra("to", 4);
                        inext.putExtra("to_the_currency", "Australian Dollar");
                    }
                    if (position == 5) {
                        inext.putExtra("From",rate_value);
                        inext.putExtra("currency_name_change", value_name);
                        inext.putExtra("to", 5);
                        inext.putExtra("to_the_currency", "Canadian Dollar");
                    }
                    if (position == 6) {
                        inext.putExtra("From",rate_value);
                        inext.putExtra("currency_name_change", value_name);
                        inext.putExtra("to", 6);
                        inext.putExtra("to_the_currency", "Singapore Dollar");
                    }
                    if (position == 7) {
                        inext.putExtra("From",rate_value);
                        inext.putExtra("currency_name_change", value_name);
                        inext.putExtra("to", 7);
                        inext.putExtra("to_the_currency", "Swiss Franc");
                    }
                    if (position == 8) {
                        inext.putExtra("From",rate_value);
                        inext.putExtra("currency_name_change", value_name);
                        inext.putExtra("to", 8);
                        inext.putExtra("to_the_currency", "Malaysian Ringgit");
                    }
                    if (position == 9) {
                        inext.putExtra("From",rate_value);
                        inext.putExtra("currency_name_change", value_name);
                        inext.putExtra("to", 9);
                        inext.putExtra("to_the_currency", "Japanese Yen");
                    }
                    if (position == 10) {
                        inext.putExtra("From",rate_value);
                        inext.putExtra("currency_name_change", value_name);
                        inext.putExtra("to", 10);
                        inext.putExtra("to_the_currency", "Chinese Yen");
                    }
                    startActivity(inext);
                    finish();
                }
            });

        }
    }
}