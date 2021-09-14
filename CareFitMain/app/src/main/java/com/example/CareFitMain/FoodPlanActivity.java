package com.example.CareFitMain;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.CareFitMain.adapter.FoodPlanAdapter;
import com.example.CareFitMain.model.FoodPlan;
import com.example.CareFitMain.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FoodPlanActivity extends AppCompatActivity{
    private SharedPreferences preferences;
    private RecyclerView recyclerView5;
    private FoodPlanAdapter foodPlanAdapter;
    private ArrayList<FoodPlan> foodPlans;
    private static final String KEY_AGE = "AGE";
    String age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_plan);
        preferences = getSharedPreferences("mypref", MODE_PRIVATE);
        recyclerView5 = findViewById(R.id.recyclerView5);
        recyclerView5.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false));
        recyclerView5.setHasFixedSize(true);
        foodPlans  = new ArrayList<>();
        age = preferences.getString("User_Age","");
        FoodPlan();
    }

    private void FoodPlan() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.FOOD_PLAN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("INFO", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for(int i=0;i<jsonArray.length();i++ )
                    {
                        JSONObject jOBJ = jsonArray.getJSONObject(i);
                        FoodPlan mFoodPlan = new FoodPlan(jOBJ.getString("morning"),
                                jOBJ.getString("breakfast"),
                                jOBJ.getString("midday"),
                                jOBJ.getString("lunch"),
                                jOBJ.getString("snacks"),
                                jOBJ.getString("dinner"));
                        foodPlans.add(mFoodPlan);
                    }
                    foodPlanAdapter = new FoodPlanAdapter(FoodPlanActivity.this,foodPlans);
                    recyclerView5.setAdapter(foodPlanAdapter);
                }catch (JSONException ex){
                    Log.e("JSON",ex.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(KEY_AGE, age);
                return hashMap;
            }
        };
        VolleySingleton singleton = VolleySingleton.getInstance(this);
        singleton.addToRequestQueue(stringRequest);
    }
}