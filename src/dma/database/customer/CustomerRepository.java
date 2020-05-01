package dma.database.customer;

import dma.database.DatabaseConnector;
import dma.database.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository implements Repository<Customer> {

    private DatabaseConnector connector;

    public CustomerRepository() {
        this.connector = DatabaseConnector.getInstance();
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        ResultSet result = connector.fetchData("SELECT * FROM customer");

        if (result == null) {
            System.out.println("No Customers found!");
            return null;
        }

        try {
            while (result.next()) {
                    Customer tempCustomer = new Customer();
                    tempCustomer.setId(result.getInt("id"));
                    tempCustomer.setName(result.getString("name"));
                    tempCustomer.setPlz(result.getInt("plz"));
                    customers.add(tempCustomer);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            connector.closeConnection();
            return customers;
        }
    }

    @Override
    public Customer findOne(int id) {
        Customer customer = null;
        ResultSet result = connector.fetchData("SELECT * FROM customer WHERE id = '" + id + "'");

        if (result == null) {
            System.out.println("No Customer found!");
            return null;
        }

        try {
            while (result.next()) {
                customer = new Customer();
                customer.setId(result.getInt("id"));
                customer.setName(result.getString("name"));
                customer.setPlz(result.getInt("plz"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            connector.closeConnection();
            return customer;
        }
    }

    @Override
    public int create(Customer newCustomer) {
        return connector.insert("INSERT INTO customer (name, plz) VALUES ('" + newCustomer.getName() + "', '" + newCustomer.getPlz() + "' )");
    }
}
