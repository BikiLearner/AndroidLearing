package com.example.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;
import java.util.Collections;

public class searchActivity extends AppCompatActivity {
    AutoCompleteTextView search;
    ArrayList<String> id = new ArrayList<>();
    String [] arr={"A", "Afghani – Afghanistan", "Akşa – Tuva", "Angolar – Angola", "Apsar - Abkhazia",
            "Argentino – Argentina", "Ariary – Madagascar", "Austral – Argentina", "Auksinas – Lithuania", "B",
            "Baht (บาท) – Thailand", "Balboa – Panama", "Birr – Ethiopia", "Bitcoin – El Salvador", "Bolívar – Venezuela",
            "Boliviano – Bolivia", "Budju – Algeria", "C", "Cedi – Ghana", "Chervonets – Russia", "Colón", "Costa Rican colón – Costa Rica",
            "Salvadoran colón – El Salvador", "Continental currency – United States of America", "Conventionsthaler – Holy Roman Empire",
            "Córdoba – Nicaragua", "Crown", "Austrian crown - Austria", "Austro-Hungarian crown - Austria-Hungary",
            "Bohemian and Moravian crown - Bohemia and Moravia", "British crown - United Kingdom", "Czech crown - Czech Republic",
            "Czechoslovak crown - Czechoslovakia", "Danish crown - Denmark"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        search=findViewById(R.id.search_currency);
        Collections.addAll(id, arr);

        ArrayAdapter<String> autoAdapter= new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,id);
        search.setAdapter(autoAdapter);
        search.setThreshold(1);
    }
}