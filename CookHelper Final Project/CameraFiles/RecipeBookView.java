package com.example.elias.cookhelper;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;


public class RecipeBookView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActionBarDrawerToggle toggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if (getIntent().getExtras() != null) {

            //Recipe test = (Recipe) getIntent().getSerializableExtra("Test");
            //DavidBook.getRecipes().add(new Recipe(test.getName(), null, null, null, 5));

        //}
            //Testbook.loadSave(this);
            // System.out.println(Testbook.getRecipes().size());



            setContentView(R.layout.drawer_recipebook);
            ImageView IView = (ImageView) findViewById(R.id.imageView);
            ListView lView = (ListView) findViewById(R.id.sampleListView);
            ArrayList<String> values = new ArrayList<String>();
            ArrayList<Bitmap> Images = new ArrayList<Bitmap>();






            for (int j = 0; j < Book.getInstance().getRecipes().size(); j++) {
                values.add(Book.getInstance().getRecipes().get(j).getName());
            }
             for (int k = 0; k < Book.getInstance().getRecipes().size(); k++) {
            Images.add(Book.getInstance().getRecipes().get(k).getBitmap());
             }




        CustomAdapter adapter = new CustomAdapter(this, values,Images);
            lView.setAdapter(adapter);
            lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                    final String item = (String) parent.getItemAtPosition(position);
                    Context context = getApplicationContext();
                    Intent i = new Intent(RecipeBookView.this, ViewRecipe.class);
                    i.putExtra("Index", position);
                    startActivity(i);


                }
            });
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            final ActionBar actionBar = getSupportActionBar();

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            //  toggle.syncState();
            toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();
            actionBar.setDisplayHomeAsUpEnabled(true);
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
        }





    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipe_book, menu);
        return true;

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            String X="Here is where all your recipe data is stored. Each card stores one of your recipes, and shows the recipe picture, name, and a brief description. Click on the card to pull up the full recipe view.";
            Intent a = new Intent(this, Help.class);
            a.putExtra("MyClass", X);
            startActivity(a);
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
        //toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    public void addNewNoteFunction(View view){
        Intent i = new Intent(this, CreateRecipe.class);
        startActivity(i);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.menu_search) {
            Intent i = new Intent(this, SearchRecipe.class);
            startActivity(i);
        } else if (id == R.id.menu_supriseMe) {
            Intent a = new Intent(this, ViewRecipe.class);
            startActivity(a);

        }else if (id == R.id.menu_recipeBook) {
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

}
