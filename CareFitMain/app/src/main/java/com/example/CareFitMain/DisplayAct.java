package com.example.CareFitMain;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.CareFitMain.adapter.FoodPlanAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.squareup.picasso.Picasso;

public class DisplayAct extends AppCompatActivity  {
    private AppCompatImageView imageView;
    private AppCompatTextView ThName, ThAge,ThBio,ThDesc,ThAddress,ThFee;
    MaterialButton bookbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.therapist_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ThName = findViewById(R.id.therapistName);
        imageView = findViewById(R.id.therapistImage);
        ThAge = findViewById(R.id.therapistAge);
        ThAddress = findViewById(R.id.therapistAdd);
        ThBio = findViewById(R.id.therapistBio);
        ThFee = findViewById(R.id.therapistFee);
        ThDesc = findViewById(R.id.therapistDesc);
        bookbtn = findViewById(R.id.bookBtn);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            ThName.setText(bundle.getCharSequence("name"));
            Picasso.get().load(String.valueOf(bundle.getCharSequence("image"))).into(imageView);
            ThAge.setText(bundle.getCharSequence("tage"));
            ThAddress.setText(bundle.getCharSequence("taddress"));
            ThBio.setText(bundle.getCharSequence("tbio"));
            ThFee.setText(bundle.getCharSequence("tratings"));
            ThDesc.setText(bundle.getCharSequence("tdesc"));
        }
        bookbtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AppointmentActivity.class);
            startActivityForResult(intent,1 );
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}