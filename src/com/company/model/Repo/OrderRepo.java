package com.company.model.Repo;

import com.company.model.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderRepo implements Repository<MealOrder> {
    private DatabaseConnector connector;

    public OrderRepo() {
        this.connector = DatabaseConnector.getInstance();
    }

    @Override
    public List<MealOrder> findAll() {
        return null;
    }

    @Override
    public MealOrder findOne(int id) {
        return null;
    }

    @Override
    public void create(MealOrder entity) {
        String sql = "INSERT INTO menu_order (customer_id, order_finish_price, delivery_price_id) Values" +
                "('" + entity.getIdCustomer() + "','" + entity.getPriceFinish() + "', '" + entity.getIdDeliveryPrice() +
                "')";
        connector.deleteUpdateInsert(sql);
    }

    public void update(MealOrder order) {
        String sql;
        if (order.getReductionId() != 0) {
            sql = "UPDATE menu_order SET reduction_id = " + order.getReductionId() + "WHERE id = " + order.getId();
            connector.deleteUpdateInsert(sql);
        }
        sql = "UPDATE menu_order SET order_finish_price = " + order.getPriceFinish() + "WHERE id = " + order.getId();
        connector.deleteUpdateInsert(sql);
    }

    public int getOrderID(MealOrder order) {
        int id = 0;
        ResultSet result = connector.fetchData("SELECT * FROM menu_order WHERE customer_id = " +
                order.getIdCustomer() + " GROUP by id DESC");
        if (result == null) {
            System.out.println("could not find id from order");
        }
        try {
            result.next();
            id = result.getInt("id");

        } catch (SQLException e) {
            System.out.println("error search id order");
            System.out.println(e.getMessage());
        } finally {
            connector.closeConnection();
            return id;
        }
    }

    public int getReductionPercent(Customer customer, MealOrder order) {
        int count = getCount(customer);
        int reductionPercent = 0;
        int reductionId = 0;

        if (count >= 10 && count < 20) {
            reductionId = 1;
        }
        if (count >= 20) {
            reductionId = 2;
        }
        if (reductionId != 0) {
            order.setReductionId(reductionId);
            ResultSet result = connector.fetchData("SELECT reduction_percent FROM order_reduction WHERE id ='" + reductionId + "'");
            if (result == null) {
                System.out.println("could not select reductionPercent");
            }
            try {
                result.next();
                reductionPercent = result.getInt("reduction_percent");

            } catch (SQLException e) {
                System.out.println("error parsing reduction_percent");
                System.out.println(e.getMessage());
            } finally {
                connector.closeConnection();
            }
        }
        return reductionPercent;
    }

    private int getCount(Customer customer) {
        int count = 0;

        ResultSet result = connector.fetchData("SELECT count(*) FROM menu_order WHERE customer_id ='"
                + customer.getIdCustomer() + "'");
        if (result == null) {
            System.out.println("could not select count");
            return count;
        }
        try {
            result.next();
            count = result.getInt("count(*)");

        } catch (SQLException e) {
            System.out.println("error parsing count");
            System.out.println(e.getMessage());
        } finally {
            connector.closeConnection();
            return count;
        }
    }

    public double getChangeImpact(OrderChange orderChange) {
        double changeImpact = .0;

        ResultSet result = connector.fetchData("SELECT * FROM change_price");
        if (result == null) {
            System.out.println("could not select changeImpact");
            return changeImpact;
        }
        try {
            result.next();
            changeImpact = result.getDouble("price");
            orderChange.setChangePriceID(result.getInt("id"));

        } catch (SQLException e) {
            System.out.println("error while parsing changeImpact");
            System.out.println(e.getMessage());
        } finally {
            connector.closeConnection();
            return changeImpact;
        }
    }

    public int createChange(OrderChange orderChange) {
        int changeId = 0;
        String sql;

        if (orderChange.getChangePriceID() == 0) {
            sql = "INSERT INTO menu_order_change (menu_change, details_id) Values ('" + orderChange.getChange() +
                    "','" + orderChange.getDetailsID() + "')";
        } else {
            sql = "INSERT INTO menu_order_change (menu_change, change_price_id, details_id) Values " +
                    "('" + orderChange.getChange() + "','" + orderChange.getChangePriceID() + "','"
                    + orderChange.getDetailsID() + "')";
        }
        connector.deleteUpdateInsert(sql);
        ResultSet result = connector.fetchData("SELECT id FROM menu_order_change WHERE menu_change = '" +
                orderChange.getChange() + "' ORDER by id DESC");
        if (result == null) {
            System.out.println("could not select changeID");
        }
        try {
            result.next();
            changeId = result.getInt("id");

        } catch (SQLException e) {
            System.out.println("error parsing changeID");
            System.out.println(e.getMessage());
        } finally {
            connector.closeConnection();
        }
        return changeId;
    }

    public int createDetail(OrderDetail orderDetail) {
        int detailId = 0;
        String sql = "INSERT INTO menu_order_details (menu_id, order_id) Values " +
                "('" + orderDetail.getMenuId() + "','" + orderDetail.getOrderId() + "')";

        connector.deleteUpdateInsert(sql);
        ResultSet result = connector.fetchData("SELECT id FROM menu_order_details WHERE order_id = '" +
                orderDetail.getOrderId() + "' ORDER by id DESC");
        if (result == null) {
            System.out.println("could not select detailID");
        }
        try {
            result.next();
            detailId = result.getInt("id");

        } catch (SQLException e) {
            System.out.println("error parsing detailID");
            System.out.println(e.getMessage());
        } finally {
            connector.closeConnection();
        }
        return detailId;
    }
}
