package dma.database;

import dma.database.ingredient.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MealIngredientsRepository {

    private DatabaseConnector connector;

    public MealIngredientsRepository() {
        this.connector = DatabaseConnector.getInstance();
    }

    public List<Ingredient> findIngredientsOfMeal(int mealId) {
        List<Ingredient> ingredients = new ArrayList<>();
        ResultSet result = connector.fetchData("SELECT ingredient.name\n" +
                "FROM meal_ingredients\n" +
                "INNER JOIN ingredient ON ingredient.id = meal_ingredients.ingredient_id\n" +
                "WHERE meal_ingredients.meal_id = '" + mealId + "';");

        while (true) {
            try {
                if (result.next()) {
                    ingredients.add(new Ingredient(result.getString("name")));
                } else {
                    break;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return ingredients;
    }

    public List<Ingredient> findAllMealIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        ResultSet result = connector.fetchData("SELECT ingredient.name\n" +
                "FROM meal_ingredients\n" +
                "INNER JOIN ingredient ON ingredient.id = meal_ingredients.ingredient_id;");

        while (true) {
            try {
                if (result.next()) {
                    ingredients.add(new Ingredient(result.getString("name")));
                } else {
                    break;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return ingredients;
    }

    public List<Ingredient> countIngredientsSold() {
        List<Ingredient> ingredients = new ArrayList<>();

        ResultSet result = connector.fetchData("SELECT ingredient.name, count(*) as counter FROM `orderdetails` \n" +
                "INNER JOIN meal ON meal.id = orderdetails.meal_id\n" +
                "INNER JOIN meal_ingredients ON meal_ingredients.meal_id = meal.id\n" +
                "INNER JOIN ingredient ON meal_ingredients.ingredient_id = ingredient.id\n" +
                "GROUP by meal_ingredients.ingredient_id");

        while (true) {
            try {
                if (result.next()) {
                    Ingredient ingredient = new Ingredient(result.getString("name"));
                    ingredient.setCounter(Integer.toString(result.getInt("counter")));
                    ingredients.add(ingredient);
                } else {
                    break;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return ingredients;
    }
}
