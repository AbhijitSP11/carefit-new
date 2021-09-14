package com.example.CareFitMain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.CareFitMain.adapter.ExerciseAdapter;
import com.example.CareFitMain.adapter.MyAdapter;
import com.example.CareFitMain.adapter.TrainerAdapter;
import com.example.CareFitMain.model.Exercises;
import com.example.CareFitMain.model.Trainers;
import com.example.CareFitMain.util.Constants;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ExerciseActivity extends AppCompatActivity implements  ExerciseAdapter.OnItemClickListener {
    ImageSlider ImageSlider;
    private SharedPreferences preferences;
    private RecyclerView recyclerView4;
    private ExerciseAdapter exerciseAdapter;
    private ArrayList<Exercises> exercisesArrayList;
    private static final String KEY_AGE = "Age";
    private static final String KEY_Profession = "Profession";
    private static final String KEY_UNAME = "uname";
    TextView age2, profession2, name2;
    String age, profession, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_exercise);
        preferences = getSharedPreferences("mypref", MODE_PRIVATE);
        ImageSlider = findViewById(R.id.image_slider);
        ArrayList<SlideModel> images = new ArrayList<>();
        images.add(new SlideModel(R.drawable.excarousel1, null));
        images.add(new SlideModel(R.drawable.exercise1, null));
        ImageSlider.setImageList(images, ScaleTypes.CENTER_CROP);
        recyclerView4 = findViewById(R.id.recyclerView4);
        recyclerView4.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        recyclerView4.setHasFixedSize(true);
        exercisesArrayList = new ArrayList<>();

        //experimental to set text and check fetched data
        age = preferences.getString("User_Age","");
        name =  preferences.getString("uname","");
        profession = preferences.getString("User_Profession","");
//        age2 = findViewById(R.id.userAgeExercise);
//        profession2 = findViewById(R.id.userProfession);
//        age2.setText(age);
//        profession2.setText(profession);

        loadExerciseData();
    }

    private void loadExerciseData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.EXERCISES_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("INFO", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jOBJ = jsonArray.getJSONObject(i);
                        Exercises mExercises = new Exercises(
                                jOBJ.getString("ename"),
                                jOBJ.getString("eimage"),
                                jOBJ.getString("edescription"));
                        exercisesArrayList.add(mExercises);
                    }
                    exerciseAdapter = new ExerciseAdapter(ExerciseActivity.this, exercisesArrayList);
                    recyclerView4.setAdapter(exerciseAdapter);
                    exerciseAdapter.setOnItemClickListener(ExerciseActivity.this);

                } catch (JSONException ex) {
                    Log.e("JSON", ex.getMessage());
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
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(KEY_AGE, age);
                hashMap.put(KEY_Profession, profession);
                return hashMap;
            }
        };

        VolleySingleton singleton = VolleySingleton.getInstance(this);
        singleton.addToRequestQueue(stringRequest);
    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        Exercises mExercises = exercisesArrayList.get(position);
        Intent intent = new Intent(ExerciseActivity.this, ExerciseDisplayActivity.class);
        bundle.putString("eimage", mExercises.getExImage());
        bundle.putString("ename", mExercises.getExName());
        bundle.putString("edescription", mExercises.getExDescription());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
