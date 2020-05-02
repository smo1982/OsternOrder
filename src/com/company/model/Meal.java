package com.company.model;

import java.util.ArrayList;

public class Meal {
    int idMeal;
    String nameMeal;
    double priceMeal;
    ArrayList<Ingredient> ingredients;

    public Meal(int idMeal, String nameMeal, double priceMeal) {
        this.idMeal = idMeal;
        this.nameMeal = nameMeal;
        this.priceMeal = priceMeal;
        this.ingredients = new ArrayList<>();
    }

    public int getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(int idMeal) {
        this.idMeal = idMeal;
    }

    public String getNameMeal() {
        return nameMeal;
    }

    public void setNameMeal(String nameMeal) {
        this.nameMeal = nameMeal;
    }

    public double getPriceMeal() {
        return priceMeal;
    }

    public void setPriceMeal(double priceMeal) {
        this.priceMeal = priceMeal;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

}
