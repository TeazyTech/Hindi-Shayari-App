package com.teazytech.hindishayari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String URL = "https://www.googleapis.com/blogger/v3/blogs/3212958123231308699/posts?key=AIzaSyCN2mxG7C68tcsDYUXNvDgoduC3dMVA5rc";
    RecyclerView myRecyclerView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    LinearLayoutManager manager;
    MyAdapter adapter;
    ProgressBar progressBar;
    Boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;
    String token = "";
    List<Item> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        setUpToolBar();
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        Toast.makeText(MainActivity.this,"Home Button Clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_about:
                        Toast.makeText(MainActivity.this,"About Button Clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_more:t:
                        Toast.makeText(MainActivity.this,"More Apps Button Clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_Setting:
                        Toast.makeText(MainActivity.this,"Settings Button Clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_Shayari:
                        Toast.makeText(MainActivity.this,"Shayari Button Clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_resource:
                        Toast.makeText(MainActivity.this,"Recource Button Clicked", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

        myRecyclerView = findViewById(R.id.myRecyclerView);
        manager = new LinearLayoutManager(this);
        adapter = new MyAdapter(this, items);
        myRecyclerView.setLayoutManager(manager);
        myRecyclerView.setAdapter(adapter);
        myRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrollOutItems = manager.findFirstVisibleItemPosition();
                if(isScrolling && currentItems + scrollOutItems == totalItems){
                    isScrolling = false;
                    getData();


                }
            }
        });
        getData();


        }
    private void getData(){
        progressBar.setVisibility(View.VISIBLE);

        if(token != ""){
            URL = URL+"&pageToken="+token;
        }
        if(token==null){
            progressBar.setVisibility(View.GONE);
            return;
        }

        StringRequest request = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("CODE", response);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                PostList PostList = gson.fromJson(response,PostList.class);
                items.addAll(PostList.getItems());
                token = PostList.getNextPageToken();
                adapter.notifyDataSetChanged();
                myRecyclerView.setAdapter(new MyAdapter(MainActivity.this, PostList.getItems()));
                progressBar.setVisibility(View.GONE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void setUpToolBar(){
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}