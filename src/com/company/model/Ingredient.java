package com.company.model;

public class Ingredient {
    int idIngredient;
    String nameIngredient;

    public Ingredient(int idIngredient, String nameIngredient) {
        this.idIngredient = idIngredient;
        this.nameIngredient = nameIngredient;
    }

    public String getNameIngredient() {
        return nameIngredient;
    }

    public void setNameIngredient(String nameIngredient) {
        this.nameIngredient = nameIngredient;
    }

}
