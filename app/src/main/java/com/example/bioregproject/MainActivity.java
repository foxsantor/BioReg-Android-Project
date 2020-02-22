package com.example.bioregproject;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.bioregproject.Services.TaskManger;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.Account;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private static  Context conx;
    private static RequestQueue requestQueue;
    private static boolean hasConnection = false;
    private ImageButton image;
    private TextView name;
    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent stickyService = new Intent(this, TaskManger.class);
        startService(stickyService);

        setContentView(R.layout.activity_main);
        conx = this;
        layout = findViewById(R.id.connetcd);
        name = findViewById(R.id.name);
        image = findViewById(R.id.image);



        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        layout.setVisibility(View.GONE);

        if(StaticUse.loggedInInternal(conx))
        {
            layout.setVisibility(View.VISIBLE);
            Account currentAccount = StaticUse.loadSession(conx);
            name.setText(currentAccount.getFirstName());
            Glide.with(conx).asBitmap().load(currentAccount.getProfileImage()).into(image);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        if (item.getTitle().equals("Settings")) {
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.settingsFragment);
        }
        if (item.getTitle().equals("Logout")) {
            StaticUse.removeData(StaticUse.SHARED_NAME_USER, "email", this);
            StaticUse.clearShared(StaticUse.SHARED_NAME_USER_LOG,this);
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.signIn);
        }
        return super.onOptionsItemSelected(item);
    }

    public static boolean getConnection()
    {

        requestQueue = Volley.newRequestQueue(conx);
        requestQueue.start();
        JsonObjectRequest request = new JsonObjectRequest (Request.Method.GET,"http://192.168.1.8:5000/", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                hasConnection =true;

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                hasConnection = false;
                return;

            }
        });
        requestQueue.add(request);
        return hasConnection;
    }

    @Override
    protected void onResume() {
        super.onResume();
        conx = this;
        layout = findViewById(R.id.connetcd);
        name = findViewById(R.id.name);
        image = findViewById(R.id.image);
        if(StaticUse.loggedInInternal(conx))
        {
            layout.setVisibility(View.VISIBLE);
            Account currentAccount = StaticUse.loadSession(conx);
            name.setText(currentAccount.getFirstName());
            Glide.with(conx).asBitmap().load(currentAccount.getProfileImage()).into(image);
        }
    }
}
