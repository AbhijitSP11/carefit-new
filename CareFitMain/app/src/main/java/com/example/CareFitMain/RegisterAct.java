package com.example.CareFitMain;

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

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterAct extends AppCompatActivity implements View.OnClickListener {
    @SuppressLint("StaticFieldLeak")
    private static EditText nameEdit, emailEdit, passwordEdit, confirmPasswordEdit, contactEdit;
    @SuppressLint("StaticFieldLeak")
    private static Button registerBtn;
    private StringRequest stringRequest;
    private VolleySingleton singleton;
    private static final String KEY_UNAME = "uname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_CONTACT = "contact";
    private SharedPreferences preferences;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        preferences = getSharedPreferences("mypref", MODE_PRIVATE);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerBtn.setOnClickListener(this);
    }

    private void init() {
        nameEdit = findViewById(R.id.editTextTextPersonName);
        emailEdit = findViewById(R.id.editTextTextEmailAddress);
        passwordEdit = findViewById(R.id.editTextTextPassword);
        confirmPasswordEdit = findViewById(R.id.editTextConfirmPassword);
        contactEdit = findViewById(R.id.editTextPhone);
        registerBtn = findViewById(R.id.registerBtn);
    }

    @Override
    public void onClick(View v) {
        if(v==registerBtn)
          registerUser();  
    }

    private void registerUser()
    {
        User user = new DAOClass().setData();
        stringRequest = new StringRequest(Request.Method.POST, Constants.REG_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("INFO",response);
                        if(user!=null)
                        {
                            if(response.equals("success")){
                                Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("username",user.getUname());
                                editor.apply();
                                Intent intent = new Intent(getApplicationContext(), LoginAct.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }else{
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
        })
        {
            @NotNull
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(KEY_UNAME, user.getUname());
                hashMap.put(KEY_EMAIL, user.getEmail());
                hashMap.put(KEY_PASSWORD, user.getPasswd());
                hashMap.put(KEY_CONTACT, user.getContact());
                return hashMap;
            }
        };
        singleton = VolleySingleton.getInstance(this);
        singleton.addToRequestQueue(stringRequest);
    }
    private static class DAOClass {
        public User setData()
        {
            String uname = nameEdit.getText().toString().trim();
            String email = emailEdit.getText().toString().trim();
            String passwd = passwordEdit.getText().toString().trim();
            String cpasswd = confirmPasswordEdit.getText().toString().trim();
            String contact = contactEdit.getText().toString().trim();
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
                nameEdit.setError("Please fill the Field");
            else if (!uname.matches(noWhiteSpace))
                nameEdit.setError("White spaces are not allowed");
            else if(TextUtils.isEmpty(email))
                emailEdit.setError("Please fill the Field");
            else if(!email.matches(emailPattern))
                emailEdit.setError("Invalid email");
            else if (TextUtils.isEmpty(passwd))
                passwordEdit.setError("Please fill the Field");
            else if (passwd.length()<8)
                passwordEdit.setError("Password is too Short");
            else if (!passwd.matches(passwordVal))
                passwordEdit.setError("Password includes uppercase, lowercase, special character and 8 character length");
            else if(!passwd.matches(cpasswd))
                confirmPasswordEdit.setError("Password does not match");
            else if(TextUtils.isEmpty(contact))
                contactEdit.setError("Please fill the Field");
            else if (contact.length()!=10)
                contactEdit.setError("Contact Number is Incorrect");
            else{
                return new User(uname, email, passwd, cpasswd, contact);
            }
            return null;
        }
    }
}