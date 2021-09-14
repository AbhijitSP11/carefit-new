package com.example.CareFitMain;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.CareFitMain.model.User;
import com.example.CareFitMain.util.Constants;

import java.util.HashMap;
import java.util.Map;

public class LoginAct extends AppCompatActivity implements View.OnClickListener {
    private static EditText emailEdit, passwordEdit;
    private static Button signInBtn;
    private TextView registerText;
    private SharedPreferences preferences;
    private StringRequest stringRequest;
    private VolleySingleton singleton;
    private static final String KEY_EMAIL = "email";
    private static final String KEY_USERNAME = "uname";
    private static final String KEY_PASSWORD = "password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences = getSharedPreferences("mypref", MODE_PRIVATE);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        signInBtn.setOnClickListener(this);
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterAct.class));

            }
        });
    }

    private void init() {
        emailEdit = findViewById(R.id.editTextTextEmailAddress);
        passwordEdit = findViewById(R.id.editTextTextPassword);
        signInBtn = findViewById(R.id.signInBtn);
        registerText = findViewById(R.id.registerText);
    }

    @Override
    public void onClick(View v) {
        if(v == signInBtn)
            loginUser();
    }
    @SuppressLint("CommitPrefEdits")
    private void loginUser() {
        User user = new DAOClass().setData();
        stringRequest = new StringRequest(Request.Method.POST, Constants.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("INFO",response);
                        if(user!=null)
                        {
                            if(response.equals("success")){
                                Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("uname",user.getUname());
                                editor.putString("email", user.getEmail());
                                editor.putString("password", user.getPasswd());
                                editor.apply();
                                editor.commit();
                                startActivity(new Intent(getApplicationContext(), User_data.class));
                                finish();
                            }
                            else if (response.equals("success yes")){
                                Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("uname",user.getUname());
                                editor.putString("email", user.getEmail());
                                editor.putString("password", user.getPasswd());
                                editor.apply();
                                editor.commit();
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                finish();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR",error.toString());
                //Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(KEY_EMAIL, user.getEmail());
                hashMap.put(KEY_PASSWORD, user.getPasswd());
                return hashMap;
            }
        };
        singleton = VolleySingleton.getInstance(this);
        singleton.addToRequestQueue(stringRequest);
    }

    public static class DAOClass
    {
        public User setData()
        {
            String email = emailEdit.getText().toString().trim();
            String password = passwordEdit.getText().toString().trim();
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
            if(TextUtils.isEmpty(email))
                emailEdit.setError("Please enter email");
            else if(!email.matches(emailPattern))
                emailEdit.setError("Invalid email");
            else if(TextUtils.isEmpty(password))
                passwordEdit.setError("Please enter password");
            else if(password.length()<8)
                passwordEdit.setError("Password is too short");
            else{
                 return new User(email,password);
            }
            return null;
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("email");
        editor.remove("password");
        editor.clear();
        editor.apply();
        startActivity(new Intent(getApplicationContext(), LoginAct.class));
        finish();
    }
}