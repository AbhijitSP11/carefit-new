package com.example.CareFitMain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class TrainerDisplayActivity extends AppCompatActivity implements PaymentResultListener {
    private AppCompatImageView imageView;
    private AppCompatTextView TrainerName, TrainerAge, TrainerExperience, TrainerRating, TrainerDescription, TrainerFee ;

    Button payBtn;
    TextView payText;
    String Name, THNAME,ProfileEmail;
    int Fee;
    private SharedPreferences preferences;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainer_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageView = findViewById(R.id.TrainerImage);
        TrainerName=  findViewById(R.id.TrainerName);
        TrainerAge = findViewById(R.id.TrainerAge);
        TrainerFee = findViewById(R.id.TrainerFee);
        TrainerRating = findViewById(R.id.TrainerRatings);
        TrainerExperience = findViewById(R.id.TrainerExperience);
        TrainerDescription = findViewById(R.id.trainerDesc);


        Checkout.preload(getApplicationContext());
        payBtn = findViewById(R.id.bookBtnTrainer);
        payText = findViewById(R.id.payText);

        preferences = getSharedPreferences("mypref",MODE_PRIVATE);

        Name = preferences.getString("Therapist_Name","");
        ProfileEmail = preferences.getString("email","");




        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePayment();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            Picasso.get().load(String.valueOf(bundle.getCharSequence("image"))).into(imageView);
            TrainerName.setText(bundle.getCharSequence("name"));
            TrainerAge.setText(bundle.getCharSequence("age"));
            TrainerExperience.setText(bundle.getCharSequence("experience"));
            TrainerRating.setText(bundle.getCharSequence("rating"));
            TrainerFee.setText(bundle.getCharSequence("fee"));
            TrainerDescription.setText(bundle.getCharSequence("description"));

        }
    }


    private void makePayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_caAdcqxWcfin2x");

        checkout.setImage(R.drawable.logo1);

        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();
            options.put("name", "CareFit Trainer");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
          //  options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "10000");//pass amount in currency subunits
            options.put("email", ProfileEmail);
//            options.put("prefill.contact","7694050633");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);
            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        payText.setText("Successfull Payment ID: " +s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        payText.setText("Failed Payment Id :" +s);
    }
}