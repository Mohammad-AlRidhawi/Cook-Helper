package com.example.elias.cookhelper;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class EditRecipe extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Toolbar toolbar;
    String[] Categories = {"Main Dish", "Starter", "Dessert","Appetizer","Drink","Sauce","None"};
    String[] Type = {"Asian","American","European","African","None"};
    private TextView mainTextView;
    private int i=0;
    private EditText mainEditText;
    private EditText DirectionsEditText;
    private ArrayAdapter mArrayAdapter;
    private ArrayAdapter instructionArrayAdapter;
    private ListView ingredientListView;
    private ListView instructionlistView;
    private ArrayList ingredientList;
    private ArrayList instructionList;
    private ActionBarDrawerToggle toggle;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private ImageView ivImage;
    private Button button;
    private String userChoosenTask;
    private  int index;
    private LinkedList<String> IngredientsLinkedList;
    private LinkedList<String> DirectionLinkedList;
    private Editable Reccategory;
    private Bitmap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_activityeditrecipe);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        final ActionBar actionBar = getSupportActionBar();
        index = (int) getIntent().getSerializableExtra("Index");

        if(Book.getInstance().getRecipes().get(index).getBitmap()==null){
            ((ImageView) findViewById(R.id.ImageClick)).setImageDrawable(this.getDrawable(R.drawable.placeholder));
        }
        else {
            ((ImageView) findViewById(R.id.ImageClick)).setImageBitmap(Book.getInstance().getRecipes().get(index).getBitmap());
        }
        ((EditText) findViewById(R.id.recipe_edit_name)).setText(Book.getInstance().getRecipes().get(index).getName());
       ((EditText) findViewById(R.id.recipe_edit_prepTime)).setText( Book.getInstance().getRecipes().get(index).getPrepTime());
       ((EditText) findViewById(R.id.recipe_edit_cookTime)).setText( Book.getInstance().getRecipes().get(index).getCookingTime());
        ((TextView) findViewById(R.id.recipe_edit_calories)).setText( Book.getInstance().getRecipes().get(index).getCalories());
        ((MaterialBetterSpinner) findViewById(R.id.recipe_edit_category)).setText(Book.getInstance().getRecipes().get(index).getCategory());
        ((MaterialBetterSpinner) findViewById(R.id.recipe_edit_type)).setText(Book.getInstance().getRecipes().get(index).getType());



        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_dropdown_item, Categories);

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_dropdown_item,Type);
        MaterialBetterSpinner CategorySpinner = (MaterialBetterSpinner)findViewById(R.id.recipe_edit_category);
        CategorySpinner.setAdapter(arrayAdapter);
        MaterialBetterSpinner TypeSpinner = (MaterialBetterSpinner)findViewById(R.id.recipe_edit_type);
        TypeSpinner.setAdapter(arrayAdapter1);

        ImageView mainButton;
        mainButton = (ImageView) findViewById(R.id.ingredientButton);
        mainButton.setOnClickListener(this);
        ImageView instructionButton;
        instructionButton = (ImageView) findViewById(R.id.instructionButton);
        instructionButton.setOnClickListener(this);
        ImageView deleteingredientButton;
        deleteingredientButton = (ImageView) findViewById(R.id.deleteingredientButton);
        deleteingredientButton.setOnClickListener(this);
        ImageView deleteinstructionButton;
        deleteinstructionButton=(ImageView) findViewById(R.id.deleteinstructionButton);
        deleteinstructionButton.setOnClickListener(this);
        button=(Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        ImageView ImageClick;
        ImageClick=(ImageView) findViewById(R.id.ImageClick);


        IngredientsLinkedList =Book.getInstance().getRecipes().get(index).getIngredients();
        DirectionLinkedList=Book.getInstance().getRecipes().get(index).getDirections();

        ImageClick.setOnClickListener(this);
        ingredientList = new ArrayList();
        
        instructionList=new ArrayList();
        // 4. Access the ListView
        ingredientListView = (ListView) findViewById(R.id.listView2);
        instructionlistView=(ListView) findViewById(R.id.listView);
// Create an ArrayAdapter for the ListView
        mArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                ingredientList);
        mainEditText = (EditText) findViewById(R.id.recipe_edit_ingredients);
        instructionArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,instructionList
        );
        DirectionsEditText = (EditText) findViewById(R.id.recipe_edit_directions);

// Set the ListView to use the ArrayAdapter
        ingredientListView.setAdapter(mArrayAdapter);
        instructionlistView.setAdapter(instructionArrayAdapter);
        for(int i=0;i<IngredientsLinkedList.size();i++){
            ingredientList.add(IngredientsLinkedList.get(i).toString());
            mArrayAdapter.notifyDataSetChanged();
        }
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
        getMenuInflater().inflate(R.menu.menu_main_edit, menu);
        return true;

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();
        if (id == R.id.action_about) {
            String X = "This is where you can make changes to one of your recipes. Simply tap any of the parameters to pull up the keyboard and make any adjustments as you see fit.";
            Intent a = new Intent(this, Help.class);
            a.putExtra("MyClass", X);
            startActivity(a);
        }
        if (id == R.id.action_user) {
            if (findViewById(R.id.recipe_edit_name) == null || ingredientList.size() == 0 || TextUtils.isEmpty(((MaterialBetterSpinner) findViewById(R.id.recipe_edit_type)).getText().toString()) || TextUtils.isEmpty(((MaterialBetterSpinner) findViewById(R.id.recipe_edit_category)).getText().toString())) {
                Toast.makeText(getApplicationContext(), "Please make sure you specified the name, type, category and at least 1 ingredient", Toast.LENGTH_SHORT).show();
            } else {
                save();
                Intent r = new Intent(this, RecipeBookView.class);
                startActivity(r);
            }
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
            Intent i = new Intent(this, HomePage.class);
            startActivity(i);
            finish();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ingredientButton:
                if(mainEditText.getText().length()>0) {
                    ingredientList.add(mainEditText.getText().toString());
                    mainEditText.setText("");
                    mArrayAdapter.notifyDataSetChanged();
                }
                break;

            case R.id.instructionButton:
                if(DirectionsEditText.getText().length()>0){
                    int lol=instructionList.size()+1;
                    instructionList.add(lol+". "+DirectionsEditText.getText().toString());
                    DirectionsEditText.setText("");
                    instructionArrayAdapter.notifyDataSetChanged();}
                break;
            case R.id.deleteingredientButton:
                int j=ingredientList.size();
                if(j>0) {
                    ingredientList.remove(j - 1);
                    mArrayAdapter.notifyDataSetChanged();
                }else{
                    //Do Nothing
                }
                break;
            case R.id.deleteinstructionButton:
                int k=instructionList.size();
                if(k>0) {
                    instructionList.remove(k - 1);
                    instructionArrayAdapter.notifyDataSetChanged();
                    i=i-1;
                }else{
                    //Do Nothing
                }
                break;
            case R.id.button:
                if(findViewById(R.id.recipe_edit_name) == null || ingredientList.size() == 0 || TextUtils.isEmpty(((MaterialBetterSpinner)findViewById(R.id.recipe_edit_type)).getText().toString()) || TextUtils.isEmpty(((MaterialBetterSpinner)findViewById(R.id.recipe_edit_category)).getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please make sure you specified the name, type, category and at least 1 ingredient", Toast.LENGTH_SHORT).show();
                }
                else {
                    save();
                    Intent r = new Intent(this, RecipeBookView.class);
                    startActivity(r);
                }
                break;
            case R.id.ImageClick:
                selectImage();
                ivImage = (ImageView) findViewById(R.id.ImageClick);
            default:
                break;
        }
    }
    private void save(){



        EditText name = (EditText) findViewById(R.id.recipe_edit_name);
        Reccategory=((MaterialBetterSpinner)findViewById(R.id.recipe_edit_category)).getText();
        String  Rectype=((MaterialBetterSpinner)findViewById(R.id.recipe_edit_type)).getText().toString();
        String  cooktime=((EditText)findViewById(R.id.recipe_edit_cookTime)).getText().toString();
        String preptime=((EditText)findViewById(R.id.recipe_edit_prepTime)).getText().toString();
        String calories = ((EditText) findViewById(R.id.recipe_edit_calories)).getText().toString();


        Book.getInstance().getRecipes().get(index).setName(name.getText().toString() );
        Book.getInstance().getRecipes().get(index).setCategory(Reccategory.toString());
        Book.getInstance().getRecipes().get(index).setPrepTime(preptime);
        Book.getInstance().getRecipes().get(index).setType(Rectype);
        Book.getInstance().getRecipes().get(index).setCookingTime(cooktime);
        Book.getInstance().getRecipes().get(index).setCalories(calories);




        Book.getInstance().getRecipes().get(index).RemoveIngredient();
        Book.getInstance().getRecipes().get(index).RemoveDirections();
        for(int i = 0; i<ingredientList.size(); i++){
            Book.getInstance().getRecipes().get(index).addIngredient(ingredientList.get(i).toString());
        }
        for(int i=0; i<instructionList.size(); i++){
            String x=instructionList.get(i).toString().substring(3,instructionList.get(i).toString().length());
            Book.getInstance().getRecipes().get(index).addDirection(i+1+". "+x);
        }
            if (map == null) {
            }
                else {
                    Book.getInstance().getRecipes().get(index).setBitmap(map);
                    }
                }





    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(EditRecipe.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(EditRecipe.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        map =thumbnail;
        ivImage.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        map =bm;
        ivImage.setImageBitmap(bm);
    }
}




