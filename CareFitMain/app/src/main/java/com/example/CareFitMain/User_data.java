package com.example.CareFitMain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.CareFitMain.model.User;
import com.example.CareFitMain.model.UserDetails;
import com.example.CareFitMain.model.appointments;
import com.example.CareFitMain.util.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class User_data extends AppCompatActivity {
    String user_data[];
    private static  EditText age, weight, height;
    Button submitBtn;
    AppCompatSpinner profession;
    RadioGroup gender;
    RadioButton male, female;
    String professionData, gender_data;
    String Age, Weight, Height, userId;
    private static final String KEY_ID = "udid";
    private static final String KEY_AGE = "age";
    private static final String KEY_HEIGHT = "height";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_PROFESSION = "profession";

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        age = findViewById(R.id.uAge);
        preferences = getSharedPreferences("mypref", MODE_PRIVATE);
        userId = preferences.getString("email","");
        weight = findViewById(R.id.current_weight);
        height = findViewById(R.id.height);
        profession = findViewById(R.id.spinner);
        gender = findViewById(R.id.radioGroup);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        submitBtn = findViewById(R.id.userSubmit);

        user_data = getResources().getStringArray(R.array.Profession);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(User_data.this,
                android.R.layout.simple_spinner_dropdown_item, user_data)
        {
            @Override
            public boolean isEnabled(int position) {
                if(position>=0){
                    return true;

                }else{
                    return false;
                }
            }
        };

        profession.setAdapter(arrayAdapter);
        profession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                professionData =(String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.male)
                {
                    gender_data = "male";
                }
                else if (checkedId == R.id.female)
                {
                    gender_data = "female";
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

    }

    private void saveData()  {
        UserDetails userDetails = new User_data.DAOClassC().setData();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.USER_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("INFO",response);
                            if(response.equals("success")){
                                Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", error.getMessage());
                //Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @NotNull
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(KEY_ID,userId);
                hashMap.put(KEY_AGE,age.getText().toString().trim());
                hashMap.put(KEY_HEIGHT, height.getText().toString().trim());
                hashMap.put(KEY_WEIGHT, weight.getText().toString().trim());
                hashMap.put(KEY_GENDER, gender_data);
                hashMap.put(KEY_PROFESSION,professionData);
                return hashMap;
            }
        };
        VolleySingleton singleton = VolleySingleton.getInstance(this);
        singleton.addToRequestQueue(stringRequest);
    }
    private static class DAOClassC {
        public UserDetails setData()
        {
            String ageVal = age.getText().toString().trim();
            String weightVal = weight.getText().toString().trim();
            String heightVal = height.getText().toString().trim();
            String noWhiteSpace = "\\A\\w{4,20}\\z";
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            String passwordVal = "^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$";
            if(TextUtils.isEmpty(ageVal))
                age.setError("Please enter age");
            else if(TextUtils.isEmpty(weightVal))
                weight.setError("Please enter weight");
            else if(TextUtils.isEmpty(heightVal))
                height.setError("Please enter height");
            else{
                return new UserDetails(ageVal, heightVal, weightVal);
            }
            return null;
        }
    }
}