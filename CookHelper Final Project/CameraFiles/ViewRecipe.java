package com.example.elias.cookhelper;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;


public class ViewRecipe extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String[] Ingredients = {"Pasta","The Red Stuff"};
    String[] Directions = {"Ask Mom to make pasta"};
    private int index;
    private TextView instructionsText;
    private TextView directionsText;
    private ActionBarDrawerToggle toggle;
    private ArrayAdapter mArrayAdapter;
    private ArrayAdapter instructionArrayAdapter;
    private ListView ingredientListView;
    private ListView instructionlistView;
    private ArrayList ingredientList =new ArrayList();
    private ArrayList instructionList=new ArrayList();
    private LinkedList IngredientsLinkedList;
    private LinkedList DirectionLinkedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_activityviewrecipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final ActionBar actionBar = getSupportActionBar();
        index = (int) getIntent().getSerializableExtra("Index");
        IngredientsLinkedList=Book.getInstance().getRecipes().get(index).getIngredients();
        DirectionLinkedList=Book.getInstance().getRecipes().get(index).getDirections();
        Bitmap thumbnal=Book.getInstance().getRecipes().get(index).getBitmap();
        ImageView x=((ImageView) findViewById(R.id.imageView));
        x.setImageBitmap(thumbnal);
        ((TextView) findViewById(R.id.recipe_view_name)).setText(Book.getInstance().getRecipes().get(index).getName() );
        ((TextView) findViewById(R.id.recipe_view_prepTime)).setText("Preparation Time: " + Book.getInstance().getRecipes().get(index).getPrepTime());
        ((TextView) findViewById(R.id.recipe_view_cookTime)).setText("Cooking Time " + Book.getInstance().getRecipes().get(index).getCookingTime());
        ((TextView) findViewById(R.id.recipe_view_calories)).setText("Calories: " + Book.getInstance().getRecipes().get(index).getCalories());
        ((TextView) findViewById(R.id.recipe_view_category)).setText("Category:"+ Book.getInstance().getRecipes().get(index).getCatergory());
        ((TextView) findViewById(R.id.recipe_view_type)).setText("Type: "+Book.getInstance().getRecipes().get(index).getType());



        // 4. Access the ListView
        ingredientListView = (ListView) findViewById(R.id.listView2);
        instructionlistView=(ListView) findViewById(R.id.listView);
        // Create an ArrayAdapter for the ListView
        mArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                ingredientList);
        instructionsText = (TextView) findViewById(R.id.recipe_form_ingredients);
        instructionArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,instructionList
        );
        directionsText = (TextView) findViewById(R.id.recipe_form_directions);
        // Set the ListView to use the ArrayAdapter
        ingredientListView.setAdapter(mArrayAdapter);
       for(int i=0;i<IngredientsLinkedList.size();i++){
           ingredientList.add(IngredientsLinkedList.get(i).toString());
            mArrayAdapter.notifyDataSetChanged();
        }

        instructionlistView.setAdapter(instructionArrayAdapter);

        for(int i=0;i<DirectionLinkedList.size();i++){
            instructionList.add(DirectionLinkedList.get(i).toString());
            instructionArrayAdapter.notifyDataSetChanged();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        actionBar.setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_view, menu);
        return true;

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id =item.getItemId();
        if (id==R.id.action_user) {
            Intent i = new Intent(this, EditRecipe.class);
            i.putExtra("Index", index);
            startActivity(i);
        }
        if (id == R.id.action_settings){
            Book.getInstance().getRecipes().remove(index);
            Intent i = new Intent(this, RecipeBookView.class);
            startActivity(i);
        }
        if (id == R.id.action_about) {
String X="This is where you can access all the information of one of your recipes. Notice the ingredients and the directions. You should also see your recipe picture, which you can set if you tap the edit button above." ;
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




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.menu_search) {
            Intent a = new Intent(this, SearchRecipe.class);
            startActivity(a);

        } else if (id == R.id.menu_supriseMe) {
            Intent a = new Intent(this, ViewRecipe.class);
            startActivity(a);

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



}
