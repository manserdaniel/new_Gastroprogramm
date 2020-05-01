package dma.database.ingredient;

import dma.database.DatabaseConnector;
import dma.database.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngredientRepository implements Repository<Ingredient> {
    private DatabaseConnector connector;

    public IngredientRepository() {
        this.connector = DatabaseConnector.getInstance();
    }

    @Override
    public List<Ingredient> findAll() {
        List<Ingredient> ingredients = new ArrayList<>();
        ResultSet result = connector.fetchData("SELECT * FROM ingredient");

        if (result == null) {
            System.out.println("No Ingredients found!");
            return null;
        }

        try {
            while (result.next()) {
                Ingredient tempIngredient = new Ingredient(result.getString("name"));
                tempIngredient.setId(result.getInt("id"));
                ingredients.add(tempIngredient);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            connector.closeConnection();
            return ingredients;
        }
    }

    @Override
    public Ingredient findOne(int id) {
        Ingredient ingredient = null;
        ResultSet result = connector.fetchData("SELECT * FROM ingredient WHERE id = '" + id + "'");

        if (result == null) {
            System.out.println("No Meal found!");
            return null;
        }

        try {
            while (result.next()) {
                ingredient = new Ingredient(result.getString("name"));
                ingredient.setId(result.getInt("id"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            connector.closeConnection();
            return ingredient;
        }
    }

    @Override
    public int create(Ingredient newIngredient) {
        return connector.insert("INSERT INTO meal (name, price) VALUES ('" + newIngredient.getName() + "' )");
    }
}
