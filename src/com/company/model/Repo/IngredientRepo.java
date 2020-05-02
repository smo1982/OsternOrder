package com.company.model.Repo;

import com.company.model.DatabaseConnector;
import com.company.model.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngredientRepo implements Repository<Ingredient> {
    private DatabaseConnector connector;

    public IngredientRepo() {
        this.connector = DatabaseConnector.getInstance();
    }


    @Override
    public List<Ingredient> findAll() {
        List<Ingredient> ingredients = new ArrayList<>();
        ResultSet result = connector.fetchData("SELECT * FROM ingredient");
        if (result == null) {
            System.out.println("could not fetch ingredients");
            return null;
        }
        try {
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("ingredient_name");
                ingredients.add(new Ingredient(id, name));
            }
        } catch (SQLException e) {
            System.out.println("error while parsing ingredient");
            System.out.println(e.getMessage());
        } finally {
            connector.closeConnection();
            return ingredients;
        }
    }

    @Override
    public Ingredient findOne(int id) {
        Ingredient resultIngredient = null;
        ResultSet result = connector.fetchData("SELECT * FROM ingredient WHERE id = " + id);
        if (result == null) {
            System.out.println("could not fetch ingredient");
            return null;
        }
        try {
            result.next();
            String name = result.getString("ingredient_name");
            resultIngredient = new Ingredient(id, name);
        } catch (SQLException e) {
            System.out.println("error while parsing ingredient");
            System.out.println(e.getMessage());
        } finally {
            connector.closeConnection();
            return resultIngredient;
        }
    }

    @Override
    public void create(Ingredient entity) {
        String sql = "INSERT INTO ingredient (ingredient_name) Values ('" + entity.getNameIngredient() + "')";
        connector.deleteUpdateInsert(sql);
    }
}
