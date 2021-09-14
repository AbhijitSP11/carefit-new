package com.example.CareFitMain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;

import com.squareup.picasso.Picasso;

public class ExerciseDisplayActivity extends AppCompatActivity {
    private AppCompatImageView imageView4;
    private AppCompatTextView ExerciseDispName, ExerciseDispDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_display);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageView4 = findViewById(R.id.ExerciseDispImage);
        ExerciseDispName =  findViewById(R.id.ExerciseDispName);
        ExerciseDispDesc = findViewById(R.id.exerciseDesc);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            Picasso.get().load(String.valueOf(bundle.getCharSequence("eimage"))).into(imageView4);
            ExerciseDispName.setText(bundle.getCharSequence("ename"));
            ExerciseDispDesc.setText(bundle.getCharSequence("edescription"));
        }
    }
}