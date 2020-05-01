package dma.view;

import au.com.bytecode.opencsv.CSVWriter;
import dma.database.ingredient.Ingredient;
import dma.database.order.Order;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Exporter {
    private static String ordersFile = "C:\\Users\\daniel\\Desktop\\CodingCampus\\Datenbanken\\NewGastroprogramm\\src\\dma\\orders.csv";
    private static String ingredientsFile = "C:\\Users\\daniel\\Desktop\\CodingCampus\\Datenbanken\\NewGastroprogramm\\src\\dma\\ingredients.csv";
    private static FileWriter OutputFile;

    public void writeOrderFile(String[] header, List<Order> orders) {
        try {
            OutputFile = new FileWriter(ordersFile);
            CSVWriter csvWriter = new CSVWriter(OutputFile);
            csvWriter.writeNext(header);


            for (Order order : orders) {
                String[] ordersString = new String[]{Integer.toString(order.getId()), Integer.toString(order.getCustomerId()), Double.toString(order.getPrice())};
                csvWriter.writeNext(ordersString);
            }
            csvWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeIngredientFile(String[] header, List<Ingredient> ingredients) {
        try {
            OutputFile = new FileWriter(ingredientsFile);
            CSVWriter csvWriter = new CSVWriter(OutputFile);
            csvWriter.writeNext(header);

            for (Ingredient ingredient : ingredients) {
                String[] ordersString = new String[]{
                        ingredient.getName(),
                        ingredient.getCounter()};

                csvWriter.writeNext(ordersString);
            }
            csvWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
