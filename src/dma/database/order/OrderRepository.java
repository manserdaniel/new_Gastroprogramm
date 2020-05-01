package dma.database.order;

import dma.database.DatabaseConnector;
import dma.database.Repository;
import dma.database.customer.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository implements Repository<Order> {
    private DatabaseConnector connector;

    public OrderRepository() {
        this.connector = DatabaseConnector.getInstance();
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        ResultSet result = connector.fetchData("SELECT * FROM `order`");

        if (result == null) {
            System.out.println("No Orders found!");
            return null;
        }

        try {
            while (result.next()) {
                Order tempOrder = new Order();
                tempOrder.setId(result.getInt("id"));
                tempOrder.setCustomerId(result.getInt("customer_id"));
                tempOrder.setPrice(Double.parseDouble(result.getString("price")));
                tempOrder.setPlz(result.getInt("plz"));
                orders.add(tempOrder);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            connector.closeConnection();
            return orders;
        }
    }

    @Override
    public Order findOne(int id) {
        return null;
    }

    @Override
    public int create(Order newOrder) {
        return connector.insert("INSERT INTO `order` (`customer_id`, `price`, `plz`) VALUES ('"
                + newOrder.getCustomerId() + "', '"
                + newOrder.getPrice() + "', '"
                + newOrder.getPlz() + "')");
    }

    public int countOrdersOfCustomer(Customer customer) {
        int overallOrders = 0;
        ResultSet result = connector.fetchData("SELECT COUNT(*) as orders FROM `order` WHERE customer_id = '"
                + customer.getId() + "'");

        if (result == null) {
            System.out.println("No Orders found!");
            return 0;
        }

        try {
            while (result.next()) {
                overallOrders = result.getInt("orders");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            connector.closeConnection();
            return overallOrders;
        }
    }

    public void updatePrice(Order order) {
        connector.update("UPDATE `order` SET price = '"
                + order.getPrice() + "' WHERE id = '"
                + order.getId() + "';");
    }

    public void createOrderdetails(Order order, Discount discount) {
        for (OrderPart meal : order.getOrderDetails()) {
            connector.insert("INSERT INTO orderdetails (order_id, price, meal_id) VALUES ('"
                    + order.getId() + "', '"
                    + (meal.getPrice() - (meal.getPrice() / 100 * discount.getPercentage())) + "', '"
                    + meal.getMeal().getId() + "')");
        }
    }

    public String printOrdersAll() {
        ResultSet result = connector.fetchData("SELECT COUNT(*) as orders FROM `order`");
        String output = "";

        if (result == null) {
            System.out.println("No Orders found!");

        }

        try {
            while (result.next()) {
                output = ("Bestellungen gesamt: " + result.getInt("orders"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            connector.closeConnection();
            return output;
        }
    }

    public String printOrdersPerCustomer() {
        ResultSet result = connector.fetchData("SELECT customer_id, COUNT(*) as orders FROM `order` GROUP BY customer_id");
        String output = "";
        if (result == null) {
            System.out.println("No Orders found!");
        } else {
            output = "Kunden ID - Bestellungen\n";
        }

        try {
            while (result.next()) {
                output = output + (result.getInt("customer_id") + " - " + result.getInt("orders") + "\n");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            connector.closeConnection();
            return output;
        }
    }

    public String printOrdersPerLocation() {
        ResultSet result = connector.fetchData("SELECT plz, COUNT(*) as orders FROM `order` GROUP BY plz");
        String output = "";
        if (result == null) {
            System.out.println("No Orders found!");
        } else {
            output = "Postleitzahl - Bestellungen\n";
        }

        try {
            while (result.next()) {
                output = output + (result.getInt("plz") + " - " + result.getInt("orders") + "\n");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            connector.closeConnection();
            return output;
        }
    }

    public String printTurnoverAll() {
        ResultSet result = connector.fetchData("SELECT SUM(price) as turnover FROM `order`");
        String output = "";
        if (result == null) {
            System.out.println("No Orders found!");
        }

        try {
            while (result.next()) {
                output = ("Gesamt Einnahmen: " + result.getInt("turnover") + " €");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            connector.closeConnection();
            return output;
        }
    }

    public String printTurnoverPerCustomer() {
        ResultSet result = connector.fetchData("SELECT customer_id, SUM(price) as turnover FROM `order` GROUP BY customer_id");
        String output = "";
        if (result == null) {
            System.out.println("No Orders found!");
        } else {
            output = "Kunden ID - Einnahmen\n";
        }

        try {
            while (result.next()) {
                output = output + result.getInt("customer_id") + " - " + result.getInt("turnover") + " €\n";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            connector.closeConnection();
            return output;
        }
    }

    public String printTurnoverPerLocation() {
        ResultSet result = connector.fetchData("SELECT plz, SUM(price) as turnover FROM `order` GROUP BY plz");
        String output = "";
        if (result == null) {
            System.out.println("No Orders found!");
        } else {
            output = "Postleitzahl - Einnahmen\n";
        }

        try {
            while (result.next()) {
                output = output + result.getInt("plz") + " - " + result.getInt("turnover") + " €\n";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            connector.closeConnection();
            return output;
        }
    }

    public String printTheMostPopularMeal() {
        ResultSet result = connector.fetchData("SELECT meal_id, meal.name, COUNT(*) as counter FROM `orderdetails`" +
                " INNER JOIN meal ON meal.id = orderdetails.meal_id");

        String output = "";

        if (result == null) {
            System.out.println("No Orders found!");
        } else {
            output = "ID - Speise - Anzahl\n";
        }

        try {
            while (result.next()) {
                output = output + result.getInt("meal_id") + " - " + result.getString("name") + " - " + result.getInt("counter") + "\n";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            connector.closeConnection();
            return output;
        }
    }
}
