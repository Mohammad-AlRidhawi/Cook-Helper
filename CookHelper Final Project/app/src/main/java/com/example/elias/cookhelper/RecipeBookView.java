package com.example.elias.cookhelper;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Random;
import android.widget.TextView;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;


public class RecipeBookView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActionBarDrawerToggle toggle;
    String[] Categories = {"Main Dish", "Starter", "Dessert","Appetizer","Drink","Sauce","None"};
    String[] Type = {"Asian","American","European","African","None"};

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
        ListView lView = (ListView) findViewById(R.id.book_form_listView);
        ArrayList<Recipe> values = new ArrayList<Recipe>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_dropdown_item, Categories);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_dropdown_item,Type);
        MaterialBetterSpinner CategorySpinner = (MaterialBetterSpinner)findViewById(R.id.book_form_category);
        CategorySpinner.setAdapter(arrayAdapter);
        MaterialBetterSpinner TypeSpinner = (MaterialBetterSpinner)findViewById(R.id.book_form_type);
        TypeSpinner.setAdapter(arrayAdapter1);
        if(getIntent().getStringExtra("search")!=null) {
            ((android.widget.SearchView)findViewById(R.id.book_form_search)).setIconifiedByDefault(false);
            String condition = getIntent().getStringExtra("condition");
            String category = getIntent().getStringExtra("category");
            String type = getIntent().getStringExtra("type");
            ((MaterialBetterSpinner)findViewById(R.id.book_form_category)).setText(category);
            ((MaterialBetterSpinner)findViewById(R.id.book_form_type)).setText(type);
            ((android.widget.SearchView)findViewById(R.id.book_form_search)).setQuery(condition, false);
            PriorityQueue<Recipe> results = Book.getInstance().search(category, type, condition);

            while (!results.isEmpty()) {
                values.add(results.remove());

            }
        }else { //Thinking we might not even need to make them invisible, just adds to the usability of the app, if they want to search from
            //there they can.

            for (int j = 0; j < Book.getInstance().getRecipes().size(); j++) {
                values.add(Book.getInstance().getRecipes().get(j));
            }
        }




        CustomAdapter adapter = new CustomAdapter(this, values);
        lView.setAdapter(adapter);
        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Context context = getApplicationContext();
                Intent i = new Intent(RecipeBookView.this, ViewRecipe.class);
                TextView listViewName = (TextView) view.findViewById(R.id.recipe_listview_name);
                String name = listViewName.getText().toString();
                int index = Book.getInstance().find(name);
                i.putExtra("Index", index );
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


        Intent a = new Intent(this, CreateRecipe.class);
        startActivity(a);
    }


    public void searchRecipe(View v) {
        String category = ((MaterialBetterSpinner)findViewById(R.id.book_form_category)).getText().toString();
        String type = ((MaterialBetterSpinner)findViewById(R.id.book_form_type)).getText().toString();
        String condition = ((android.widget.SearchView) findViewById(R.id.book_form_search)).getQuery().toString();


        if(TextUtils.isEmpty(category)|| TextUtils.isEmpty(type)){
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




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.menu_search) {
            Intent i = new Intent(this, SearchRecipe.class);
            startActivity(i);
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
