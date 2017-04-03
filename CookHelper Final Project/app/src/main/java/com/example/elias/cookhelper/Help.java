package com.example.elias.cookhelper;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Help extends AppCompatActivity
         {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        String x= (String) getIntent().getSerializableExtra("MyClass");
        System.out.println(x);
        ((TextView) findViewById(R.id.HelpText)).setText(x);
    }





}
