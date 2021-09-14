package com.example.CareFitMain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.CareFitMain.adapter.TrainerAdapter;
import com.example.CareFitMain.model.Trainers;
import com.example.CareFitMain.util.Constants;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TrainerActivity extends AppCompatActivity implements TrainerAdapter.OnItemClickListener {
    private SharedPreferences preferences;
    private NavigationView navigationView;
    private RecyclerView recyclerView;
    private TrainerAdapter trainerAdapter;
    private ArrayList<Trainers> trainers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_main);
        preferences = getSharedPreferences("mypref", MODE_PRIVATE);
        recyclerView = findViewById(R.id.recyclerView3);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        trainers = new ArrayList<>();
        loadTrainerData();
    }

    private void loadTrainerData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Constants.TRAINER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("INFO", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jOBJ = jsonArray.getJSONObject(i);
                        Trainers mTrainers = new Trainers(jOBJ.getString("tname"),
                                jOBJ.getString("age"),
                                jOBJ.getString("image"),
                                jOBJ.getString("experience"),
                                jOBJ.getString("fee"),
                                jOBJ.getString("rating"),
                                jOBJ.getString("description"));
                        trainers.add(mTrainers);
                    }
                    trainerAdapter = new TrainerAdapter(TrainerActivity.this, trainers);
                    recyclerView.setAdapter(trainerAdapter);
                    trainerAdapter.setOnItemClickListener(TrainerActivity.this);

                } catch (JSONException ex) {
                    Log.e("JSON", ex.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleySingleton singleton = VolleySingleton.getInstance(this);
        singleton.addToRequestQueue(stringRequest);
    }

    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        Trainers mTrainers = trainers.get(position);
        Intent intent = new Intent(TrainerActivity.this, TrainerDisplayActivity.class);
        bundle.putString("image", mTrainers.getTImage());
        bundle.putString("name", mTrainers.getTName());
        bundle.putString("age", mTrainers.getTAge());
        bundle.putString("experience", mTrainers.getTExperience());
        bundle.putString("fee", mTrainers.getTFee());
        bundle.putString("ratings", mTrainers.getTRating());
        bundle.putString("description", mTrainers.getTDescription());
        intent.putExtras(bundle);
        startActivity(intent);
    }

}