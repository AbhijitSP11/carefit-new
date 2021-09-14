//package com.example.CareFitMain;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.Menu;
//import android.widget.TextView;
//
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.example.CareFitMain.adapter.MyAdapter;
//import com.example.CareFitMain.model.Therapists;
//import com.example.CareFitMain.util.Constants;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.snackbar.Snackbar;
//import com.google.android.material.navigation.NavigationView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.ActionBarDrawerToggle;
//import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//
//public class NavAct extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
//        MyAdapter.OnItemClickListener
//{
//    private SharedPreferences preferences;
//    private TextView unameText, emailText;
//    private NavigationView navigationView;
//    private RecyclerView recyclerView;
//    private MyAdapter adapter;
//    private ArrayList<Therapists> actors;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        preferences = getSharedPreferences("mypref", MODE_PRIVATE);
//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
//        recyclerView.setHasFixedSize(true);
//        actors  = new ArrayList<>();
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        navigationView = findViewById(R.id.nav_view);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer, toolbar,
//                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        toggle.syncState();
//        navigationView.setNavigationItemSelectedListener(this);
//        loadData();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        unameText = navigationView.getHeaderView(0).findViewById(R.id.usernameText);
//        emailText = navigationView.getHeaderView(0).findViewById(R.id.emailText);
//        unameText.setText(preferences.getString("username", ""));
//        emailText.setText(preferences.getString("email", ""));
//
//    }
//
//    private void loadData() {
//        StringRequest stringRequest = new StringRequest(Request.Method.GET,
//                Constants.URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.i("INFO", response);
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONArray jsonArray = jsonObject.getJSONArray("data");
//                    for(int i=0;i<jsonArray.length();i++ )
//                    {
//                        JSONObject jOBJ = jsonArray.getJSONObject(i);
//                        Therapists mTherapists = new Therapists(jOBJ.getString("name"),
//                                jOBJ.getString("image"),
//                                jOBJ.getString("latest_movie"),
//                                jOBJ.getString("hit_movie"));
//                        actors.add(mTherapists);
//                    }
//                    adapter = new MyAdapter(NavAct.this, actors);
//                    recyclerView.setAdapter(adapter);
//                    adapter.setOnItemClickListener(NavAct.this);
//
//                }catch (JSONException ex){
//                    Log.e("JSON",ex.getMessage());
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        VolleySingleton singleton = VolleySingleton.getInstance(this);
//        singleton.addToRequestQueue(stringRequest);
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if(id == R.id.action_logout)
//        {
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.remove("email");
//            editor.remove("password");
//            editor.clear();
//            editor.apply();;
//            startActivity(new Intent(getApplicationContext(), LoginAct.class));
//            finish();
//        }
//        return true;
//    }
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        Intent intent = null;
//        if(id == R.id.nav_home){
//            intent = new Intent();
//        }else if(id == R.id.nav_profile){
//            intent = new Intent(getApplicationContext(), ProfileAct.class);
//        }else if(id == R.id.nav_share){
//            intent = new Intent();
//        }
//        startActivity(intent);
//
//        return true;
//    }
//
//    @Override
//    public void onItemClick(int position) {
//        Bundle bundle = new Bundle();
//        Therapists mactors = actors.get(position);
//        Intent intent = new Intent(NavAct.this, DisplayAct.class);
//        bundle.putString("image", mactors.getImage());
//        intent.putExtras(bundle);
//        startActivity(intent);
//    }
//}