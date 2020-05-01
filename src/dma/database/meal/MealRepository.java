package dma.database.meal;

import dma.database.DatabaseConnector;
import dma.database.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MealRepository implements Repository<Meal> {

    private DatabaseConnector connector;

    public MealRepository() {
        this.connector = DatabaseConnector.getInstance();
    }

    @Override
    public List<Meal> findAll() {
        List<Meal> meals = new ArrayList<>();
        ResultSet result = connector.fetchData("SELECT * FROM meal");

        if (result == null) {
            System.out.println("No Meals found!");
            return null;
        }

        try {
            while (result.next()) {
                Meal tempMeal = new Meal(result.getString("name"),
                        result.getString("price"));
                tempMeal.setId(result.getInt("id"));

                meals.add(tempMeal);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            connector.closeConnection();
            return meals;
        }
    }

    @Override
    public Meal findOne(int id) {
        Meal meal = null;
        ResultSet result = connector.fetchData("SELECT * FROM meal WHERE id = '" + id + "'");

        if (result == null) {
            System.out.println("No Meal found!");
            return null;
        }

        try {
            while (result.next()) {
                meal = new Meal(result.getString("name"),
                        result.getString("price"));
                meal.setId(result.getInt("id"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            connector.closeConnection();
            return meal;
        }
    }

    @Override
    public int create(Meal newMeal) {
        return connector.insert("INSERT INTO meal (name, price) VALUES ('" + newMeal.getName() + "', '" + newMeal.getPrice() + "' )");
    }
}
