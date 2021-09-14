package com.example.CareFitMain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.CareFitMain.model.Trainers;
import com.example.CareFitMain.util.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class InstructorActivity extends AppCompatActivity {

    private static EditText InstructorName, InstructorAge, InstructorSkills,  InstructorFees, InstructorAbout;
    private Button InstructorBtn;
    private StringRequest stringRequest;
    private VolleySingleton singleton;

    private static final String KEY_INS_NAME = "tname";
    private static final String KEY_INS_AGE = "age";
    private static final String KEY_INS_SKILLS = "experience";
    private static final String KEY_INS_FEES = "fee";
    private static final String KEY_INS_DESCRIPTION = "description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);
        InstructorName = findViewById(R.id.editTextInstructorName);
        InstructorAge = findViewById(R.id.editTextInstructorAge);
        InstructorSkills = findViewById(R.id.editTextInstructorSkills);
        InstructorFees = findViewById(R.id.editTextInstructorFees);
        InstructorAbout = findViewById(R.id.EditTextInstructorAbout);
        InstructorBtn = findViewById(R.id.registerTrainerBtn);
    }
    @Override
    protected void onResume() {
        super.onResume();
        InstructorBtn.setOnClickListener(v -> {
            if (v == InstructorBtn)
                RegisterInstructor();
        });
    }

    private void RegisterInstructor() {
        Trainers trainers = new DAOClassB().setData();
        stringRequest = new StringRequest(Request.Method.POST, Constants.INSTRUCTOR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("INFO", response);
                        if(trainers!=null) {
                            if (response.equals("success")) {
                                Toast.makeText(getApplicationContext(), "Booked Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", error.getMessage());
                //Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @NotNull
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(KEY_INS_NAME, String.valueOf(InstructorName.getText()));
                hashMap.put(KEY_INS_AGE, String.valueOf(InstructorAge.getText()));
                hashMap.put(KEY_INS_SKILLS, String.valueOf(InstructorSkills.getText()));
                hashMap.put(KEY_INS_FEES, String.valueOf(InstructorFees.getText()));
                hashMap.put(KEY_INS_DESCRIPTION, String.valueOf(InstructorAbout.getText()));
                return hashMap;
            }
        };
        singleton = VolleySingleton.getInstance(this);
        singleton.addToRequestQueue(stringRequest);
    }
    private static class DAOClassB {
        public Trainers setData()
        {
            String insName = InstructorName.getText().toString().trim();
            String insAge = InstructorAge.getText().toString().trim();
            String insSkills = InstructorSkills.getText().toString().trim();
            String insFees = InstructorFees.getText().toString().trim();
            String insAbout = InstructorAbout.getText().toString().trim();
            String noWhiteSpace = "\\A\\w{4,20}\\z";
            if(TextUtils.isEmpty(insName))
                InstructorName.setError("Please Fill the Field");
            else if (!insName.matches(noWhiteSpace))
                InstructorName.setError("White spaces are not allowed");
            else if(TextUtils.isEmpty(insAge))
                InstructorAge.setError("Please Fill the Field");
            else if(TextUtils.isEmpty(insSkills))
                InstructorSkills.setError("Please Fill the Field");
            else if(TextUtils.isEmpty(insFees))
                InstructorName.setError("Please Fill the Field");
            else if(TextUtils.isEmpty(insAbout))
                InstructorAbout.setError("Please Fill the Field");
            else if(TextUtils.isEmpty(insFees))
                InstructorFees.setError("Please Fill the Field");

            else{
                return new Trainers(insName, insAge, insSkills, insFees,insAbout);
            }
            return null;
        }
    }


}