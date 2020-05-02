package com.company.model.Repo;

import com.company.model.Customer;
import com.company.model.DatabaseConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepo implements Repository<Customer> {
    private DatabaseConnector connector;

    public CustomerRepo() {
        this.connector = DatabaseConnector.getInstance();
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        ResultSet result = connector.fetchData("SELECT * FROM customer");
        if (result == null) {
            System.out.println("could not fetch customers");
            return null;
        }
        try {
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("customer_name");
                String city = result.getString("customer_city");
                customers.add(new Customer(id, name, city));

            }
        } catch (SQLException e) {
            System.out.println("error while parsing customer");
            System.out.println(e.getMessage());
        } finally {
            connector.closeConnection();
            return customers;
        }
    }

    @Override
    public Customer findOne(int id) {
        Customer resultCustomer = null;
        ResultSet result = connector.fetchData("SELECT * FROM customer WHERE id = " + id );
        if (result == null) {
            System.out.println("could not fetch customer");
            return null;
        }
        try {
            result.next();
            String name = result.getString("customer_name");
            String city = result.getString("customer_city");
            resultCustomer = new Customer(id, name, city);
        } catch (SQLException e) {
            System.out.println("error while parsing customer");
            System.out.println(e.getMessage());
        } finally {
            connector.closeConnection();
            return resultCustomer;
        }
    }

    @Override
    public void create(Customer customer) {
        String sql = "INSERT INTO customer (customer_name, customer_city) Values ('" + customer.getNameCustomer() + "', '"
                + customer.getCityCustomer() + "') ";
        connector.deleteUpdateInsert(sql);

    }
}
