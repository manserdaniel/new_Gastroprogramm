package dma.database.order;

import dma.database.DatabaseConnector;
import dma.database.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiscountRepository implements Repository<Discount> {

    private DatabaseConnector connector;

    public DiscountRepository() {
        this.connector = DatabaseConnector.getInstance();
    }

    @Override
    public List<Discount> findAll() {
        List<Discount> discounts = new ArrayList<>();
        ResultSet result = connector.fetchData("SELECT * FROM discount");

        if (result == null) {
            System.out.println("No discount found!");
            return null;
        }

        try {
            while (result.next()) {
                Discount tempDiscount = new Discount();
                tempDiscount.setId(result.getInt("id"));
                tempDiscount.setOrders(result.getInt("orders"));
                tempDiscount.setPercentage(result.getInt("percent"));
                discounts.add(tempDiscount);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            connector.closeConnection();
            return discounts;
        }
    }

    @Override
    public Discount findOne(int id) {
        return null;
    }

    @Override
    public int create(Discount entity) {
        return 0;
    }
}
