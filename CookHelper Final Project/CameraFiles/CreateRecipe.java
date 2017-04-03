package com.example.elias.cookhelper;

        import android.app.Activity;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.content.res.Configuration;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.drawable.BitmapDrawable;
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
        import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
        import java.io.ByteArrayOutputStream;
        import java.io.File;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.Serializable;
        import java.util.ArrayList;
        import android.content.Context;
        import android.widget.Toast;


public class CreateRecipe extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, Serializable {
    private Toolbar toolbar;
    String[] Categories = {"Chinese","Italian","Indian","None"};
    String[] Type = {"Breakfast","Lunch","Dinner","None"};
    private TextView mainTextView;
    private int i=0;
    private EditText recipeNameEditText;
    private EditText mainEditText;
    private EditText cookingTime;
    private EditText directionsListEditText;
    private ArrayAdapter mArrayAdapter;
    private ArrayAdapter instructionArrayAdapter;
    private ListView mainListView;
    private ListView directionsListView;
    private ArrayList<String> ingredientsList;
    private ArrayList<String> directionsList;
    private ActionBarDrawerToggle toggle;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Button btnSelect;
    private Button saveBtn;
    private boolean cat= false;
    private ImageView ivImage;
    private String userChoosenTask;
    private MaterialBetterSpinner typeSpinner;
    private MaterialBetterSpinner categorySpinner;
    private Bitmap thumbnail;
    private Recipe newRecipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_activitycreaterecipe);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        final ActionBar actionBar = getSupportActionBar();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_dropdown_item, Categories);

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_dropdown_item,Type);
        categorySpinner = (MaterialBetterSpinner)findViewById(R.id.recipe_form_category);
        categorySpinner.setAdapter(arrayAdapter);
        typeSpinner = (MaterialBetterSpinner)findViewById(R.id.recipe_form_type);
        typeSpinner.setAdapter(arrayAdapter1);
        recipeNameEditText = (EditText)findViewById(R.id.recipe_form_name);
        cookingTime = (EditText)findViewById(R.id.recipe_edit_cookTime);
        ImageView mainButton;
        mainButton = (ImageView) findViewById(R.id.ingredientButton);
        mainButton.setOnClickListener(this);
        ImageView instructionButton;
        instructionButton = (ImageView) findViewById(R.id.instructionButton);
        instructionButton.setOnClickListener(this);
        ImageView deleteingredientButton;
        deleteingredientButton = (ImageView) findViewById(R.id.deleteingredientButton);
        deleteingredientButton.setOnClickListener(this);
        ImageView deleteinstrucationButton;
        deleteinstrucationButton=(ImageView) findViewById(R.id.deleteinstrucationButton);
        deleteinstrucationButton.setOnClickListener(this);
        saveBtn=(Button)findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(this);
        ImageView ImageClick;
        ImageClick=(ImageView) findViewById(R.id.ImageClick);
        ImageClick.setOnClickListener(this);
        ingredientsList = new ArrayList<String>();
        directionsList=new ArrayList<String>();
        // 4. Access the ListView
        mainListView = (ListView) findViewById(R.id.listView2);
        directionsListView=(ListView) findViewById(R.id.listView);
// Create an ArrayAdapter for the ListView
        mArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                ingredientsList);
        mainEditText = (EditText) findViewById(R.id.recipe_form_ingredients);
        instructionArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,directionsList
        );
        directionsListEditText = (EditText) findViewById(R.id.recipe_form_directions);

// Set the ListView to use the ArrayAdapter
        mainListView.setAdapter(mArrayAdapter);
        directionsListView.setAdapter(instructionArrayAdapter);
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
        getMenuInflater().inflate(R.menu.menu_main_save, menu);
        return true;

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }int id =item.getItemId();
        if (id==R.id.action_user) {
            save();
            Intent y = new Intent(this, RecipeBookView.class);
            startActivity(y);
        }
        if (id == R.id.action_settings) {
            String X="Here is where you create a new recipe. Tap any of the fields to get started. You can select the picture to change it, or simply fill out the ingredients and directions. You can also give your recipe a rating out of 5.";
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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ingredientButton:
                if(mainEditText.getText().length()>0) {
                    ingredientsList.add(((EditText)findViewById(R.id.recipe_form_ingredients)).getText().toString());
                    mainEditText.setText("");
                    mArrayAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.saveBtn:
                save();
                //cheer.show();
              //  Book newBook = new Book("Pablo");
                //newBook.loadSave(this);
                //newRecipe=newBook.getRecipes().remove;
                //newRecipe.readData(this);
                //cheer = Toast.makeText(context, newRecipe.getIngredients().remove().getName(), duration);
                //cheer.show();
                Intent y = new Intent(this, RecipeBookView.class);
                startActivity(y);


                break;
            case R.id.instructionButton:
                if(directionsListEditText.getText().length()>0){
                i=i+1;
                directionsList.add(i+". "+((EditText)findViewById(R.id.recipe_form_directions)).getText().toString());
                directionsListEditText.setText("");
                instructionArrayAdapter.notifyDataSetChanged();}
                break;
            case R.id.deleteingredientButton:
                int j=ingredientsList.size();
                if(j>0) {
                    ingredientsList.remove(j - 1);
                    mArrayAdapter.notifyDataSetChanged();
                }else{
                    //Do Nothing
                }
                break;
            case R.id.deleteinstrucationButton:
                int k=directionsList.size();
                if(k>0) {
                    directionsList.remove(k - 1);
                    instructionArrayAdapter.notifyDataSetChanged();
                    i=i-1;
                }else{
                    //Do Nothing
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
        EditText xd =(EditText)findViewById(R.id.recipe_form_name);
        String RecipeNameInput=xd.getText().toString();
        Bitmap icon = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.spaghetti);
        //Recipe newRecipe = new Recipe(recipeNameEditText.getText().toString(), categorySpinner.getText().toString(), typeSpinner.getText().toString(), cookingTime.getText().toString(),1);
        if(cat==true) {
             newRecipe = new Recipe(((EditText) findViewById(R.id.recipe_form_name)).getText().toString(), ((MaterialBetterSpinner) findViewById(R.id.recipe_form_category)).getText().toString(),
                    ((MaterialBetterSpinner) findViewById(R.id.recipe_form_type)).getText().toString(), ((EditText) findViewById(R.id.recipe_form_cookTime)).getText().toString(), ((EditText) findViewById(R.id.recipe_form_prepTime)).getText().toString(), ((EditText) findViewById(R.id.recipe_form_calories)).getText().toString(), ((RatingBar) findViewById(R.id.recipe_form_rating)).getRating(), thumbnail);
        }else{
             newRecipe = new Recipe(((EditText) findViewById(R.id.recipe_form_name)).getText().toString(), ((MaterialBetterSpinner) findViewById(R.id.recipe_form_category)).getText().toString(),
                    ((MaterialBetterSpinner) findViewById(R.id.recipe_form_type)).getText().toString(), ((EditText) findViewById(R.id.recipe_form_cookTime)).getText().toString(), ((EditText) findViewById(R.id.recipe_form_prepTime)).getText().toString(), ((EditText) findViewById(R.id.recipe_form_calories)).getText().toString(), ((RatingBar) findViewById(R.id.recipe_form_rating)).getRating(), icon);

        }
        Book.getInstance().addRecipe(newRecipe);
        int a = Book.getInstance().getRecipes().size()-1;
        for(int i = 0; i<ingredientsList.size(); i++){
            Book.getInstance().getRecipes().get(a).addIngredient(ingredientsList.get(i));
        }
        for(int i=0; i<directionsList.size(); i++){
            Book.getInstance().getRecipes().get(a).addDirection(directionsList.get(i));
        }

        Context context = getApplicationContext();
        Book.getInstance().save(this);


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

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CreateRecipe.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(CreateRecipe.this);

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
        cat=true;
         thumbnail = (Bitmap) data.getExtras().get("data");
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

        ivImage.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            cat=true;
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ivImage.setImageBitmap(bm);
        thumbnail=bm;

    }
}




