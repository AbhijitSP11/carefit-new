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
import com.example.CareFitMain.adapter.MyAdapter;
import com.example.CareFitMain.model.Therapists;
import com.example.CareFitMain.util.Constants;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TherapistActivity extends AppCompatActivity implements  MyAdapter.OnItemClickListener{
    private SharedPreferences preferences;
    private NavigationView navigationView;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private ArrayList<Therapists> therapists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapist_main);
        preferences = getSharedPreferences("mypref", MODE_PRIVATE);
        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));
        recyclerView.setHasFixedSize(true);
        therapists  = new ArrayList<>();
        loadData();
    }

    private void loadData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Constants.URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("INFO", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for(int i=0;i<jsonArray.length();i++ )
                    {
                        JSONObject jOBJ = jsonArray.getJSONObject(i);
                        Therapists mTherapists = new Therapists(
                                jOBJ.getString("name"),
                                jOBJ.getString("image"),
                                jOBJ.getString("tage"),
                                jOBJ.getString("taddress"),
                                jOBJ.getString("tbio"),
                                jOBJ.getString("tratings"),
                                jOBJ.getString("tdesc"));
                        therapists.add(mTherapists);

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("Therapist_Name", jOBJ.getString("name"));
                        editor.putString("Therapist_Fee",jOBJ.getString("tratings"));
                        editor.apply();
                        editor.commit();

                    }
                    adapter = new MyAdapter(TherapistActivity.this, therapists);
                    recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener(TherapistActivity.this);

                }catch (JSONException ex){
                    Log.e("JSON",ex.getMessage());
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

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        Therapists mTherapsits = therapists.get(position);
        Intent intent = new Intent(TherapistActivity.this, DisplayAct.class);
        bundle.putString("name",mTherapsits.getName());
        bundle.putString("image", mTherapsits.getImage());
        bundle.putString("tage", mTherapsits.getTage());
        bundle.putString("taddress", mTherapsits.getTaddress());
        bundle.putString("tbio", mTherapsits.getTbio());
        bundle.putString("tdesc",mTherapsits.getTdesc());
        bundle.putString("tratings", mTherapsits.getTratings());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}