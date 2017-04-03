package com.example.elias.cookhelper;

import android.content.Context;

import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;


public class Recipe implements Serializable,Comparable<Recipe> {
    private String name;
    private String category;
    private double rating;
    private LinkedList<String> directions;
    private LinkedList<String> ingredients;
    private String type;
    private String prepTime;
    private String cookingTime;
    private String calories;
    private int searchScore;
    private Bitmap image;

    public Recipe(String name, String category, String type, String cookingTime, String prepTime, String calories){
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.type = type;
        this.prepTime = prepTime;
        this.calories = calories;
        this.cookingTime = cookingTime;
        this.ingredients = new LinkedList<String>();
        this.directions = new LinkedList<String>();
    }
    private void delegateData(String data){
        String[] toDelegate = data.split("/");
        int i = 0;
        while(!toDelegate[i].contains("--DIRECTIONS--") & i<toDelegate.length){
            addIngredient(toDelegate[i]);
            System.out.println(toDelegate[i]);
            i++;
        }
        i++;
        for(int j = i; j<toDelegate.length;j++){
            addDirection(toDelegate[j]);
            System.out.println(toDelegate[j]);
        }
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getCalories() {
        return calories;
    }
    public void setCalories(String calories) {
        this.calories = calories;
    }
    public double getRating() {
        return rating;
    }
    public void setRating(float rating) {
        this.rating = rating;
    }
    public LinkedList<String> getDirections() {
        return directions;
    }
    public void setDirections(LinkedList<String> directions) {
        this.directions = directions;
    }
    public LinkedList getIngredients() {
        return ingredients;
    }
    public void setIngredients(LinkedList<String> ingredients) {
        this.ingredients = ingredients;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getCookingTime() {
        return cookingTime;
    }
    public String getPrepTime() {
        return prepTime;
    }
    public void setPrepTime(String cookingTime) {
        this.prepTime = cookingTime;
    }
    public Bitmap getBitmap(){
        return image;
    }
    public void setBitmap(Bitmap a){
        this.image = a;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }
    public void addIngredient(String ingredientText){


        ingredients.add(ingredientText);
    }
    public void RemoveIngredient(){

        while(ingredients.isEmpty()!=true) {
            ingredients.removeLast();
        }
    }
    public void RemoveDirections(){
        int x=directions.size();
        if(!directions.isEmpty()) {
            while (directions.isEmpty()!=true) {
                directions.removeLast();
            }
        }
    }
    public void addDirection(String direction){
        directions.add(direction);
    }
    /*public void readData(Activity activity){
        String data = null;
        try {
            FileInputStream in = activity.openFileInput(name+".txt");
            InputStreamReader reader = new InputStreamReader(in);
            int c;
            while((c = reader.read()) != -1){
                data = data + Character.toString((char)c);
            }
            in.close();
        }catch(IOException e){

        }
        delegateData(data);
    }
    */


    public int getScore(){
        return searchScore;
    }
    public void setScore(int score){
        searchScore = score;
    }
    public int compareTo(Recipe arg0) {
        return Integer.compare(arg0.getScore(), searchScore);
    }

}