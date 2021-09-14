package com.example.CareFitMain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.CareFitMain.model.User;
import com.example.CareFitMain.model.appointments;
import com.example.CareFitMain.util.Constants;
import com.google.android.material.button.MaterialButton;
import com.google.common.collect.Range;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class AppointmentActivity extends AppCompatActivity{
    String appointment_date[];
    String appointment_time[];
    AppCompatSpinner appointmentDate, appointmentTime;
    private static  AppCompatEditText appointeeName, appointeeEmail, appointeeNum,  concern;
    private MaterialButton bookNowBtn;
    private StringRequest stringRequest;
    private VolleySingleton singleton;

    String dateData, time_data;
    private static final String KEY_APP_NAME = "pname";
    private static final String KEY_APP_EMAIL = "email";
    private static final String KEY_NUM = "num";
    private static final String KEY_DATE = "slotdate";
    private static final String KEY_TIME = "time";
    private static final String KEY_CONCERN = "concern";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appointeeName = findViewById(R.id.appointeeName);
        appointeeEmail = findViewById(R.id.appointeeEmail);
        appointeeNum = findViewById(R.id.appointeeNum);
        appointmentDate = findViewById(R.id.spinnerAppointmentDate);
        appointmentTime = findViewById(R.id.spinnerAppointmentTime);
        bookNowBtn = findViewById(R.id.bookNowBtn);
        concern = findViewById(R.id.concern);

        appointment_date = getResources().getStringArray(R.array.AppointmentDate);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(AppointmentActivity.this,
                android.R.layout.simple_spinner_dropdown_item, appointment_date)
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
        appointmentDate.setAdapter(arrayAdapter1);
        appointmentDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dateData =(String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        appointment_time = getResources().getStringArray(R.array.AppointmentTime);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(AppointmentActivity.this,
                android.R.layout.simple_spinner_dropdown_item, appointment_time)
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

        appointmentTime.setAdapter(arrayAdapter2);
        appointmentTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                time_data =(String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        bookNowBtn.setOnClickListener(v -> {
            if (v == bookNowBtn)
                bookAppointment();
        });
    }

    private void bookAppointment() {
        appointments Appointments = new DAOClassA().setData();
        stringRequest = new StringRequest(Request.Method.POST, Constants.APPOINTMENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("INFO", response);
                        if(Appointments!=null) {
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
                hashMap.put(KEY_APP_NAME, String.valueOf(appointeeName.getText()));
                hashMap.put(KEY_APP_EMAIL, String.valueOf(appointeeEmail.getText()));
                hashMap.put(KEY_NUM, String.valueOf(appointeeNum.getText()));
                hashMap.put(KEY_DATE, dateData);
                hashMap.put(KEY_TIME, time_data);
                hashMap.put(KEY_CONCERN, String.valueOf(concern.getText()));
                return hashMap;
            }
        };
        singleton = VolleySingleton.getInstance(this);
        singleton.addToRequestQueue(stringRequest);
    }

    private static class DAOClassA {
        public appointments setData()
        {
            String uname = appointeeName.getText().toString().trim();
            String email = appointeeEmail.getText().toString().trim();
            String contact = appointeeNum.getText().toString().trim();
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
            if(TextUtils.isEmpty(uname))
                appointeeName.setError("Please Fill the Field");
            else if (!uname.matches(noWhiteSpace))
                appointeeName.setError("White spaces are not allowed");
            else if(TextUtils.isEmpty(email))
                appointeeEmail.setError("Please Fill the Field");
            else if(!email.matches(emailPattern))
                appointeeEmail.setError("Invalid email");
            else if(TextUtils.isEmpty(contact))
                appointeeNum.setError("Please Fill the Field");
            else if (contact.length()!=10)
                appointeeNum.setError("Contact Number is Incorrect");
            else{
                return new appointments(uname, email, contact);
            }
            return null;
        }
    }

}
