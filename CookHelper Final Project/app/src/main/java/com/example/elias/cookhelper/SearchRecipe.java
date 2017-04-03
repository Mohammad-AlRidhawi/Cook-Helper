package com.example.elias.cookhelper;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.Random;


public class SearchRecipe extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private ActionBarDrawerToggle toggle;

    private Toolbar toolbar;
    String[] Categories = {"Main Dish", "Starter", "Dessert","Appetizer","Drink","Sauce","None"};
    String[] Type = {"Asian","American","European","African","None"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_searchrecipe);

        getSupportActionBar().setTitle("Search Recipe");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_dropdown_item, Categories);

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_dropdown_item,Type);
        MaterialBetterSpinner CategorySpinner = (MaterialBetterSpinner)findViewById(R.id.search_form_category);
        CategorySpinner.setAdapter(arrayAdapter);
        MaterialBetterSpinner TypeSpinner = (MaterialBetterSpinner)findViewById(R.id.search_form_type);
        TypeSpinner.setAdapter(arrayAdapter1);
        ((Button)findViewById(R.id.search_form_searchbtn)).setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        final ActionBar actionBar = getSupportActionBar();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        actionBar.setDisplayHomeAsUpEnabled(true);
        toggle.syncState();

        //actionBar.SetDisplayHomeAsUpEnabled(true);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.menu_search) {

        } else if (id == R.id.menu_supriseMe) {
            Random r = new Random();
            if (Book.getInstance().getRecipes().size() == 0) {
                Toast.makeText(getApplicationContext(), "Why don't you try adding some recipes first!", Toast.LENGTH_SHORT).show();
            }
             else {
                int b = r.nextInt(Book.getInstance().getRecipes().size());
                Intent intent = new Intent(this, ViewRecipe.class);
                intent.putExtra("Index", b);
                startActivity(intent);
            }
        }
        else if (id == R.id.menu_recipeBook) {
            Intent r = new Intent(this, RecipeBookView.class);
            startActivity(r);
        } else if (id == R.id.menu_createRecipe) {
            Intent i = new Intent(this, CreateRecipe.class);
            startActivity(i);
        }
        else if (id == R.id.home_page) {
            Intent i = new Intent(this, HomePage.class);
            startActivity(i);
            finish();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        String category = ((MaterialBetterSpinner)findViewById(R.id.search_form_category)).getText().toString();
        String type = ((MaterialBetterSpinner)findViewById(R.id.search_form_type)).getText().toString();
        String condition = ((android.widget.SearchView) findViewById(R.id.search_form_searchview)).getQuery().toString();


        if(TextUtils.isEmpty(category)|| TextUtils.isEmpty(type)) {
            int length = Toast.LENGTH_LONG;
            Toast blankFiled = Toast.makeText(this, "Please fill in all fields", length);
            blankFiled.show();

        }else{
            Intent intent = new Intent(this, RecipeBookView.class);
            intent.putExtra("search", "yup");
            intent.putExtra("category", category);
            intent.putExtra("type", type);
            intent.putExtra("condition", condition);
            startActivity(intent);
        }
    }
}

