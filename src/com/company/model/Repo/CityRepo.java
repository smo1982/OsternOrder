package com.company.model.Repo;

import com.company.model.City;
import com.company.model.DatabaseConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CityRepo implements Repository<City> {
    private DatabaseConnector connector;

    public CityRepo() {
        this.connector = DatabaseConnector.getInstance();
    }

    @Override
    public List<City> findAll() {
        List<City> city = new ArrayList<>();
        ResultSet result = connector.fetchData("SELECT city.id, city_name, delivery_price, delivery_id FROM city INNER JOIN " +
                "delivery_price ON city.delivery_id = delivery_price.id");
        if (result == null) {
            System.out.println("could not fetch city");
            return null;
        }
        try {
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("city_name");
                double price = result.getDouble("delivery_price");
                int deliveryId = result.getInt("delivery_id");
                city.add(new City(id, name, price, deliveryId));

            }
        } catch (SQLException e) {
            System.out.println("error while parsing customer");
            System.out.println(e.getMessage());
        } finally {
            connector.closeConnection();
            return city;
        }
    }

    @Override
    public City findOne(int id) {
        return null;
    }

    @Override
    public void create(City entity) {

    }
}
