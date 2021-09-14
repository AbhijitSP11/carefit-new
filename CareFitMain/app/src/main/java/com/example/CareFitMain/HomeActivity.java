package com.example.CareFitMain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.CareFitMain.util.Constants;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.khizar1556.mkvideoplayer.MKPlayer;
import com.potyvideo.library.AndExoPlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity  {
    ImageSlider imageSlider;
    TextView emailCheck;
    GoogleSignInClient mGoogleSignInClient;
    private CardView cardView, trainerCard, exerciseCard,dietCard, meditationCard, instructorCard, selfCareCard2 ;
    private SharedPreferences preferences;
    private static final String USERID = "udid";
    String userID, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        imageSlider = findViewById(R.id.image_slider);
        ArrayList<SlideModel> images = new ArrayList<>();
        images.add(new SlideModel(R.drawable.vegetarian_food, null));
        images.add(new SlideModel(R.drawable.surfingis, null));
        images.add(new SlideModel(R.drawable.food, null));
        images.add(new SlideModel(R.drawable.walk, null));
        images.add(new SlideModel(R.drawable.workout2jpg, null));

        imageSlider.setImageList(images, ScaleTypes.CENTER_CROP);
        preferences = getSharedPreferences("mypref",MODE_PRIVATE);
        userID = preferences.getString("email","");

//        username = preferences.getString("uname","");
//
//        emailCheck = findViewById(R.id.emailFetch);
//        emailCheck.setText(username);

        meditationCard = findViewById(R.id.playMeditation);
        meditationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MeditationActivity.class);
                startActivity(intent);
            }
        });
        cardView = findViewById(R.id.TherapistCard);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TherapistActivity.class);
                startActivity(intent);
            }
        });
        trainerCard = findViewById(R.id.trainerCard);
        trainerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TrainerActivity.class);
                startActivity(intent);
            }
        });
        exerciseCard = findViewById(R.id.exerciseCard);
        exerciseCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ExerciseActivity.class);
                startActivity(intent);
            }
        });
         dietCard= findViewById(R.id.dietCard);
         dietCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FoodPlanActivity.class);
                startActivity(intent);
            }
        });
        instructorCard = findViewById(R.id.instructorCard);
        instructorCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),InstructorActivity.class);
                startActivity(intent);
            }
        });
        selfCareCard2 = findViewById(R.id.selfCareCard2);
        selfCareCard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SelfCareAdviceActivity.class);
                startActivity(intent);
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.home:
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.advice:
                        startActivity(new Intent(getApplicationContext(),
                                AdviceActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),
                                UserProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        UserData();
    }
    private void UserData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.USER_DATA_FETCH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("INFO", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            JSONObject jOBJ = jsonArray.getJSONObject(0);

                            SharedPreferences.Editor editor = preferences.edit();

                            editor.putString("User_ID", jOBJ.getString("udid"));
                            editor.putString("User_Age",jOBJ.getString("age"));
                            editor.putString("User_Weight",jOBJ.getString("weight"));
                            editor.putString("User_Height",jOBJ.getString("height"));
                            editor.putString("User_Gender", jOBJ.getString("gender"));
                            editor.putString("User_Profession", jOBJ.getString("profession"));
                            editor.apply();
                            editor.commit();

                        }catch (JSONException ex){
                            Log.e("JSON",ex.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR",error.toString());
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(USERID, userID);
                return hashMap;
            }
        };
        VolleySingleton singleton = VolleySingleton.getInstance(this);
        singleton.addToRequestQueue(stringRequest);
    }
}