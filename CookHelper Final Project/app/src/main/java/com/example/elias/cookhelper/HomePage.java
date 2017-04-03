package com.example.elias.cookhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Random;
public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_activityhomepage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            String X="Tap the menu icon in the top left to open the main navigation of the app. Here you can access all the information you will need. Down below, near the bottom of the screen, you will see options to search for a recipe, view your recipe book, and search for a random recipe. Tap the view recipe button to get started.";
            Intent a = new Intent(this, Help.class);
            a.putExtra("MyClass",X);
            startActivity(a);


// To retrieve object in second Activity
            getIntent().getSerializableExtra("MyClass");
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.menu_search) {
            Intent a = new Intent(this, SearchRecipe.class);
            startActivity(a);

        } else if (id == R.id.menu_supriseMe) {
            Random r = new Random();
            if (Book.getInstance().getRecipes().size()==0){
                Toast.makeText(getApplicationContext(), "Why don't you try adding some recipes first!", Toast.LENGTH_SHORT).show();
            }
            else {
                int b = r.nextInt(Book.getInstance().getRecipes().size());
                Intent intent = new Intent(this, ViewRecipe.class);
                intent.putExtra("Index", b);
                startActivity(intent);
            }

        } else if (id == R.id.menu_recipeBook) {
            Intent r = new Intent(this, RecipeBookView.class);
            startActivity(r);

        } else if (id == R.id.menu_createRecipe) {
            Intent i = new Intent(this, CreateRecipe.class);
            startActivity(i);
        }
        else if (id == R.id.home_page) {
            System.out.println("Hell0");
            Intent i = new Intent(this, HomePage.class);
            startActivity(i);
            finish();

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void searchbutton(View view){
        Intent intent = new Intent(HomePage.this, SearchRecipe.class);
        startActivity(intent);
    }
    public void Soupbutton(View view){
        Random r = new Random();
        if (Book.getInstance().getRecipes().size()==0){
            Toast.makeText(getApplicationContext(), "Why don't you try adding some recipes first!", Toast.LENGTH_SHORT).show();
        }
        else {
            int b = r.nextInt(Book.getInstance().getRecipes().size());
            Intent intent = new Intent(this, ViewRecipe.class);
            intent.putExtra("Index", b);
            startActivity(intent);
        }
    }
    public void bookbutton(View view){

        Intent intent = new Intent(HomePage.this, RecipeBookView.class);
        startActivity(intent);
    }

}
