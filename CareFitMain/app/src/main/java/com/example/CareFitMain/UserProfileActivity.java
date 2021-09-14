package com.example.CareFitMain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.CareFitMain.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserProfileActivity extends AppCompatActivity {
private Button logout;
private SharedPreferences preferences ;
private GoogleSignInClient mGoogleSignInClient;
private String Age, Profession, ProfileName, ProfileWeight, ProfileHeight, ProfileEmail;
TextView profileAge, profilePro, proName, proWeight, proHeight, proEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        preferences = getSharedPreferences("mypref",MODE_PRIVATE);

        Age = preferences.getString("User_Age","");
        Profession = preferences.getString("User_Profession","");
        ProfileName = preferences.getString("uname","");
        ProfileWeight =  preferences.getString("User_Weight","");
        ProfileHeight = preferences.getString("User_Height","");
        ProfileEmail = preferences.getString("email","");





        logout = findViewById(R.id.logout);

        profileAge = findViewById(R.id.profileAge);
        profilePro = findViewById(R.id.proProfession);
        proName = findViewById(R.id.profileName);
        proWeight = findViewById(R.id.profileWeight);
        proHeight = findViewById(R.id.profileHeight);
        proEmail = findViewById(R.id.ProfileEmail);


        profileAge.setText(Age);
        profilePro.setText(Profession);
        proName.setText(ProfileName);
        proWeight.setText(ProfileWeight);
        proHeight.setText(ProfileHeight);
        proEmail.setText(ProfileEmail);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),
                                HomeActivity.class));
                        overridePendingTransition(3, 3);
                        return true;
                    case R.id.advice:
                        startActivity(new Intent(getApplicationContext(),
                                AdviceActivity.class));
                        overridePendingTransition(3, 3);
                        return true;
                    case R.id.profile:
                        overridePendingTransition(3, 3);
                        return true;
                }
                return false;
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("email");
                editor.remove("password");
                editor.clear();
                editor.apply();
                mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                       startActivity(new Intent(getApplicationContext(), LoginAct.class));
                                       finish();
                                }
                            });
                startActivity(new Intent(getApplicationContext(), LoginAct.class));
                finish();
            }
        });
    }


}