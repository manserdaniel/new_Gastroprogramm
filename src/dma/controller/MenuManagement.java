package dma.controller;

import dma.database.DatabaseConnector;
import dma.database.ingredient.Ingredient;
import dma.database.ingredient.IngredientRepository;
import dma.database.meal.Meal;
import dma.database.meal.MealRepository;
import dma.database.order.OrderRepository;
import dma.view.MealView;
import dma.view.OrderView;

import java.util.List;
import java.util.Scanner;

public class MenuManagement {

    private static final DatabaseConnector dbConnector = DatabaseConnector.getInstance();
    private static List<Ingredient> ingredientList = null;

    public static void start() {
        MealRepository mealRepository = new MealRepository();
        MealView mealView = new MealView();
        List<Meal> menu =  mealRepository.findAll();

        IngredientRepository ingredientRepository = new IngredientRepository();
        ingredientList = ingredientRepository.findAll();
        mealView.insertNewMeals();
        mealView.printTable(menu);
    }

    public static int insertMenuIntoDB(Meal newMeal) {
        return dbConnector.insert("INSERT INTO meal (name, price) VALUES ('" + newMeal.getName() + "', '" + newMeal.getPrice() + "')");
    }

    public static void insertIngredientIntoDB(int mealId, Ingredient newIngredient) {
        boolean ingredientExists = false;
        int existingIngredientId = 0;

        for (Ingredient tempIngredient : ingredientList) {
            if (tempIngredient.equals(newIngredient)) {
                ingredientExists = true;
                existingIngredientId = tempIngredient.getId();
                break;
            }
        }
        if (!ingredientExists) {
            dbConnector.insert("INSERT INTO ingredient (name) VALUES ('" + newIngredient.getName() + "')");
        } else {
            insertMealIngredientIntoDB(mealId, existingIngredientId);
        }
    }

    public static void insertMealIngredientIntoDB(int mealId, int ingredientId) {
        dbConnector.insert("INSERT INTO meal_ingredients (meal_id, ingredient_id) VALUES ('" + mealId + "', '" + ingredientId + "')");
    }
}
