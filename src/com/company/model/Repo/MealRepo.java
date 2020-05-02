package com.company.model.Repo;

import com.company.model.DatabaseConnector;
import com.company.model.Ingredient;
import com.company.model.Meal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MealRepo implements Repository<Meal> {
    private DatabaseConnector connector;

    public MealRepo() {
        this.connector = DatabaseConnector.getInstance();
    }

    @Override
    public List<Meal> findAll() {
        List<Meal> meals = new ArrayList<>();
        ResultSet result = connector.fetchData("SELECT * FROM menu");
        if (result == null) {
            System.out.println("could not fetch menus");
            return null;
        }
        try {
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                double price = result.getDouble("menu_price");
                Meal meal = null;
                meals.add(meal = new Meal(id, name, price));
                addIngredients(meal);
            }
        } catch (SQLException e) {
            System.out.println("error while parsing all menu");
            System.out.println(e.getMessage());
        } finally {
            connector.closeConnection();
            return meals;
        }
    }

    @Override
    public Meal findOne(int id) {
        Meal resultMeal = null;
        ResultSet result = connector.fetchData("SELECT * FROM menu WHERE id = " + id);
        if (result == null) {
            System.out.println("could not fetch menu");
            return null;
        }
        try {
            result.next();
            String name = result.getString("name");
            double price = result.getDouble("menu_price");
            resultMeal = new Meal(id, name, price);
            addIngredients(resultMeal);
        } catch (SQLException e) {
            System.out.println("error while parsing one menu");
            System.out.println(e.getMessage());
        } finally {
            connector.closeConnection();
            return resultMeal;
        }
    }

    @Override
    public void create(Meal entity) {
        String sql = "INSERT INTO menu (name, menu_price) Values ('" + entity.getNameMeal() + "', '"
                + entity.getPriceMeal() + "') ";
        connector.deleteUpdateInsert(sql);
    }

    private void addIngredients(Meal meal) {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ResultSet result = connector.fetchData("SELECT ingredient.id as ingredientID, ingredient_name FROM `ingredient` " +
                "inner JOIN menu_ingredient ON menu_ingredient.ingredient_id = ingredient.id inner JOIN menu ON " +
                "menu.id = menu_ingredient.menu_id where menu.id = " + meal.getIdMeal());
        try {
            while (result.next()) {
                int id = result.getInt("ingredientID");
                String name = result.getString("ingredient_name");
                ingredients.add(new Ingredient(id, name));
            }
        } catch (SQLException e) {
            System.out.println("error while parsing all menu");
            System.out.println(e.getMessage());
        } finally {
            connector.closeConnection();
            meal.setIngredients(ingredients);
        }


    }
}
