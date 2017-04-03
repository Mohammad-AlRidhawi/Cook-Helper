package com.example.elias.cookhelper;

/**
 * Created by Ahns on 2016-11-20.
 */


public class Ingredient{
    private String name;
    private int quantity;
    private int unit;
    public Ingredient(String name, int quantity, int unit){
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }
    public Ingredient(String Name){
        this.name = Name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getUnit() {
        return unit;
    }
    public void setUnit(int unit) {
        this.unit = unit;
    }

}