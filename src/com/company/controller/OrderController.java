package com.company.controller;

import com.company.model.*;
import com.company.model.Repo.CityRepo;
import com.company.model.Repo.CustomerRepo;
import com.company.model.Repo.MealRepo;
import com.company.model.Repo.OrderRepo;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderController {
    private DatabaseConnector connector;

    public OrderController(DatabaseConnector connector) {
        this.connector = DatabaseConnector.getInstance();
    }

    public static void order() {
        MealOrder order = new MealOrder();
        OrderRepo orderRepo = new OrderRepo();
//CUSTOMER
        Customer orderCustomer = addCustomer(order);
//LIEFERPREIS
        order.setPriceDelivery(deliveryPrice(orderCustomer.getCityCustomer(), order));
        orderRepo.create(order);
        order.setId(orderRepo.getOrderID(order));
//MENÜAUSWAHL
        MenuSelection(order, orderCustomer);
        order.setPriceFinish(order.getPriceDelivery());
        orderRepo.update(order);
//Rechnung
        printBill(order, orderCustomer);
    }

    private static void MenuSelection(MealOrder order, Customer orderCustomer) {
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        boolean go = true;
        while (go) {
//RABATT-Stammkunde
            OrderRepo orderRepo = new OrderRepo();
            int reductionPercent = orderRepo.getReductionPercent(orderCustomer, order);
            Scanner myScanner = new Scanner(System.in);
            Scanner myNumberScanner = new Scanner(System.in);
            MealRepo mealRepo = new MealRepo();
            List<Meal> meals = mealRepo.findAll();
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(order.getId());
            System.out.println("Bitte wählen sie aus folgenden Gerichten: ");
            printMeals(meals);
            System.out.println("Geben sie bitte die Bestellzahl ein: ");
            int orderMeal = myNumberScanner.nextInt();
            orderDetail.setMenuId(orderMeal);
            orderDetail.setId(orderRepo.createDetail(orderDetail));
            Meal mealOrder = mealRepo.findOne(orderMeal);
            order.setPriceFinish((mealOrder.getPriceMeal() / 100 * (100 - reductionPercent)));
//ZUTATEN AUFFÜHREN
            System.out.println(mealOrder.getNameMeal() + " enthält folgende Zutaten:");
            printIngredients(mealOrder);
//ÄNDERUNGEN
            ArrayList<OrderChange> changes = new ArrayList<>();
            System.out.println("Wünschen sie eine Zutatenänderung?(J/N)");
            String yes = myScanner.nextLine();
            if (yes.equalsIgnoreCase("J")) {
                boolean run = true;
                while (run) {
                    OrderChange orderChange = change(order, orderDetail);
                    orderChange.setId(orderRepo.createChange(orderChange));
                    changes.add(orderChange);
                    System.out.println("Noch eine Änderung?(J/N)");
                    String answer = myScanner.next();
                    if (answer.equalsIgnoreCase("N")) {
                        run = false;
                    }
                }
            }
            orderDetail.setOrderChanges(changes);
            orderDetails.add(orderDetail);
            System.out.println("Noch ein weiteres Gericht?(J/N)");
            String answer = myScanner.next();
            if (answer.equalsIgnoreCase("N")) {
                go = false;
            }
        }
        order.setOrderDetails(orderDetails);
    }

    private static Customer addCustomer(MealOrder order) {
        Scanner myScanner = new Scanner(System.in);
        Scanner myNumberScanner = new Scanner(System.in);
        CustomerRepo customerRepo = new CustomerRepo();
        List<Customer> customers = customerRepo.findAll();
        Customer orderCustomer = null;

        System.out.println("Herzlich Willkommen. Haben sie bereits ein Kundenkonto?(J/N)");
        String yesOrNo = myScanner.nextLine();
        if (yesOrNo.equalsIgnoreCase("N")) {
            System.out.println("Bitte geben Sie ihren Namen ein:");
            String name = myScanner.nextLine();
            System.out.println("Bitte geben Sie ihren Wohnort ein:");
            String city = myScanner.nextLine();
            customerRepo.create(orderCustomer = new Customer(0, name, city));
            customers.clear();
            customers = customerRepo.findAll();
            int customerId = 0;
            for (Customer customer : customers) {
                if (customer.getNameCustomer().equalsIgnoreCase(name)) {
                    customerId = customer.getIdCustomer();
                    break;
                }
            }
            if (customerId != 0) {
                order.setIdCustomer(customerId);
                System.out.println("Ihre Kundennummer wurde erstellt und lautet: " + customerId);
            }
//kundennummer eingeben + abfrage ob kunde existiert
        } else {
            boolean customerRun = true;
            while (customerRun) {
                System.out.println("Bitte geben sie Ihre Kundennummer ein:");
                int customerId = myNumberScanner.nextInt();
                orderCustomer = customerRepo.findOne(customerId);
                if (orderCustomer != null) {
                    customerRun = false;
                    order.setIdCustomer(customerId);
                    System.out.println("Herzlich Willkommen " + orderCustomer.getNameCustomer());
                }
            }
        }
        return orderCustomer;
    }

    private static OrderChange change(MealOrder order, OrderDetail orderDetail) {
        Scanner myScanner = new Scanner(System.in);
        OrderRepo orderRepo = new OrderRepo();
        OrderChange orderChange = new OrderChange();

        orderChange.setDetailsID(orderDetail.getId());
        System.out.println("Bitte geben sie ein was sie entfernen oder hinzufügen möchten(-.../+...)");
        String change = myScanner.next();
        String changeFinal = change.substring(0, 1);
        if (changeFinal.equals("+")) {
            double changeImpact = orderRepo.getChangeImpact(orderChange);
            change = change.substring(1);
            changeFinal = "mit " + change + " " + changeImpact + "€";
            order.setPriceFinish(changeImpact);
        } else {
            change = change.substring(1);
            changeFinal = "ohne " + change;
        }
        orderChange.setChange(changeFinal);
        System.out.println("'" + changeFinal + "'" + " wurde gespeichert!");
        return orderChange;
    }

    private static double deliveryPrice(String city, MealOrder order) {
        double price = 0;
        CityRepo cityRepo = new CityRepo();
        List<City> towns = cityRepo.findAll();
        for (City town : towns) {
            if (town.getCityName().equalsIgnoreCase(city)) {
                price = town.getDeliveryPrice();
                order.setIdDeliveryPrice(town.getDeliveryId());
            }
        }
        return price;
    }

    private static void printMeals(List<Meal> meals) {
        for (Meal meal : meals) {
            System.out.println(meal.getIdMeal() + ". " + meal.getNameMeal() + " " + meal.getPriceMeal() + "€");
        }
    }

    private static void printIngredients(Meal meal) {
        ArrayList<Ingredient> ingredients = meal.getIngredients();
        for (Ingredient ingredient : ingredients) {
            System.out.print("*" + ingredient.getNameIngredient() + "\t");
        }
        System.out.println();
    }

    private static void printBill(MealOrder order, Customer customer) {
        OrderRepo orderRepo = new OrderRepo();
        System.out.println("\t\t***RECHNUNG***");
        System.out.println("Kunde:\t\t" + customer.getNameCustomer());
        System.out.println("Lieferort:\t" + customer.getCityCustomer());
        System.out.println("----------------------------");
        if (order.getReductionId() != 0) {
            System.out.println("Stammkundenrabatt: " + orderRepo.getReductionPercent(customer, order) + "% auf Speisen");
        }
        ArrayList<OrderDetail> details = order.getOrderDetails();
        for (OrderDetail detail : details) {
            MealRepo mealRepo = new MealRepo();
            Meal mealOrder = mealRepo.findOne(detail.getMenuId());
            System.out.println(mealOrder.getNameMeal() + "\t" + String.format("%.2f", mealOrder.getPriceMeal()) + "€");
            ArrayList<OrderChange> changes = detail.getOrderChanges();
            for (OrderChange change : changes) {
                change.getChange();
                System.out.println("\t\t" + change.getChange());
            }
        }
        System.out.println("----------------------------");
        System.out.println("Lieferpreis: \t\t" + String.format("%.2f", order.getPriceDelivery()) + "€");
        System.out.println("Gesamt" + "\t\t\t\t" + String.format("%.2f", order.getPriceFinish()) + "€");

    }
}
