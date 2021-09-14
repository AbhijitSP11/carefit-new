package com.example.CareFitMain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;

import com.example.CareFitMain.model.User;

public class FirstOnBoardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_on_boarding);
        Intent intent = new Intent(getApplicationContext(), User_data.class);
        startActivity(intent);

    }
}