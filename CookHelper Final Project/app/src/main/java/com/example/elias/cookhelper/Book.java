package com.example.elias.cookhelper;


import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.LinkedList;
import android.app.Activity;
import java.util.PriorityQueue;
import java.util.Queue;
//Singleton class

public class Book {
    private String name;
    private LinkedList<Recipe> recipes;
    private static Book instance = new Book("Chef", new LinkedList<Recipe>());

    private Book(String name, LinkedList<Recipe> recipes) {
        this.name = name;
        this.recipes = recipes;

    }

    public static Book getInstance() {
        return instance;
    }


    public void loadSave(Activity activity) {
        String data = "";
        try {
            FileInputStream in = activity.openFileInput(name + ".txt");
            InputStreamReader reader = new InputStreamReader(in);
            int c;
            while ((c = reader.read()) != -1) {
                data = data + Character.toString((char) c);
            }
            in.close();

        } catch (IOException e) {
        }
        String[] recipes = data.split("/");
        for (int i = 0; i < recipes.length; i++) {
            System.out.println(recipes[i]);
            addRecipe(recipes[i]);
        }
    }


    String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(LinkedList<Recipe> recipes) {
        this.recipes = recipes;
    }

    public void addRecipe(String recipe) {
        String[] recipeInfo = recipe.split(":");
        System.out.println(recipeInfo[0]);
        if (recipeInfo.length > 1) {
            recipes.add(new Recipe(recipeInfo[0], recipeInfo[1], recipeInfo[2], recipeInfo[3], recipeInfo[3], recipeInfo[3]));
        }
    }

    public void addRecipe(Recipe newRecipe) {
        recipes.add(new Recipe(newRecipe.getName(), newRecipe.getCategory(), newRecipe.getType(), newRecipe.getCookingTime(), newRecipe.getPrepTime(), newRecipe.getCalories()));
    }
    public int find(String name) {
        Iterator<Recipe> i = recipes.iterator();
        int j = 0;
        while(i.hasNext()){
            Recipe temp = i.next();
            if(temp.getName().equalsIgnoreCase(name)){
                System.out.println("working");
                return j;
            }
            j++;
        }
        return j;
    }
    public void save(Activity activity) {
        Iterator<Recipe> i = recipes.iterator();
        String info = "";
        while (i.hasNext()) {
            Recipe current = i.next();

            info = info + current.getName() + ":" + current.getCategory() + ":" + current.getType() + ":" + current.getCookingTime() + ":" + current.getRating() + "/";
        }
        try {
            FileOutputStream out = activity.openFileOutput(name + ".txt", Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(out);
            writer.write(info);
            writer.close();
        } catch (IOException e) {

        }

    }

    public Recipe getFirstRecipe() {
        return recipes.get(0);
    }


    public PriorityQueue<Recipe> search(String category, String type, String condition) {
        Queue<Recipe> matches = narrowSearch(category, type);
        Recipe current;
        PriorityQueue<Recipe> result = new PriorityQueue<Recipe>();
        while (!matches.isEmpty()) {
			current = matches.remove();
			if(condition.length()>1){
				checkIngredients(condition, current);
				if(current.getScore()>0){
					result.add(current);
				}
			}else{
				result.add(current);
			}
        }

        return result;
    }

    private void checkIngredients(String condition, Recipe current) {
        condition = condition.toUpperCase();
        String[] conditions = condition.split(" ");
        Boolean last = true;
        String operator = "NONE";
        Boolean negated = false;
        String or, and, not;
        or = "OR";
        and = "AND";
        not = "NOT";
        int score = 0;
        for (int j = 0; j < conditions.length; j++) {
            if (conditions[j].equalsIgnoreCase(not)) {
                //Here if someone puts two NOT's in a row then the logic comes out to just never having NOT
                if (negated) {
                    negated = false;
                } else {
                    negated = true;
                }
            } else if (conditions[j].equalsIgnoreCase(and)) {
                operator = and;
            } else if (conditions[j].equalsIgnoreCase(or)) {
                operator = or;
            } else {
                Iterator<String> i = current.getIngredients().iterator();
                String ing;
                Boolean found = false;
                while (i.hasNext() & !found) {
                    ing = i.next();
                    if (conditions[j].equalsIgnoreCase(ing)) {
                        found = true;
                    }
                }
                if (negated) {
                    if (found) {
                        found = false;
                        negated = false;
                    } else {
                        found = true;
                        negated = false;
                    }
                }
                if (found) {
                    if (operator.equalsIgnoreCase("NONE")) {
                        score++;
                        last = true;
                    } else if (operator == and) {
                        if (last) {
                            score = score + 2;
                        } else {
                            score++;
                        }
                    } else if (operator == or) {
                        if (!last) {
                            score++;
                            last = true;
                        }
                    }
                } else {
                    last = false;
                }
            }
        }
        current.setScore(score);
    }

    private Queue<Recipe> narrowSearch(String category, String type) {
        Recipe current;
        Queue<Recipe> matches = new LinkedList<Recipe>();
        Iterator<Recipe> i = recipes.iterator();
        if (category.equalsIgnoreCase("NONE") & type.equalsIgnoreCase("NONE")) {
            while (i.hasNext()) {
                current = i.next();
                matches.add(current);
            }
        } else if (category.equalsIgnoreCase("NONE")) {
            while (i.hasNext()) {
                current = i.next();
                if (current.getType().equalsIgnoreCase(type)) {
                    matches.add(current);
                }
            }
        } else if (type.equalsIgnoreCase("NONE")) {
            while (i.hasNext()) {
                current = i.next();
                if (current.getCategory().equalsIgnoreCase(category)) {
                    matches.add(current);
                }
            }
        } else {
            while (i.hasNext()) {
                current = i.next();
                if (current.getCategory().equalsIgnoreCase(category) & current.getType().equalsIgnoreCase(type)) {
                    matches.add(current);
                }
            }
        }
        return matches;
    }


}
