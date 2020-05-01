package dma.controller;

import dma.database.DatabaseConnector;
import dma.database.MealIngredientsRepository;
import dma.database.customer.Customer;
import dma.database.customer.CustomerRepository;
import dma.database.ingredient.Ingredient;
import dma.database.ingredient.IngredientRepository;
import dma.database.meal.Meal;
import dma.database.meal.MealRepository;
import dma.database.order.*;
import dma.location.Location;
import dma.location.LocationRepository;
import dma.view.Exporter;
import dma.view.MealView;
import dma.view.OrderView;

import java.util.List;

public class OrderManagement {

    private static final DatabaseConnector dbConnector = DatabaseConnector.getInstance();

    private static CustomerRepository customerRepository = new CustomerRepository();

    public static void start() {
        OrderView orderView = new OrderView();
        Customer actualCustomer = getCustomer(orderView.selectCustomer());

        Order actualOrder = new Order();
        actualOrder.setCustomerId(actualCustomer.getId());
        actualOrder.setPlz(actualCustomer.getPlz());

        OrderRepository orderRepository = new OrderRepository();
        actualOrder.setId(orderRepository.create(actualOrder));

        MealRepository mealRepository = new MealRepository();
        MealView mealView = new MealView();
        List<Meal> menu = mealRepository.findAll();

        MealIngredientsRepository mealIngredientsRepository = new MealIngredientsRepository();

        IngredientRepository ingredientRepository = new IngredientRepository();
        List<Ingredient> ingredients = ingredientRepository.findAll();
        mealView.printFullMenu(menu);

        LocationRepository locationRepository = new LocationRepository();
        List<Location> locations = locationRepository.findAll();

        DiscountRepository discountRepository = new DiscountRepository();
        List<Discount> discountsList = discountRepository.findAll();


        for (int i = 0; i < menu.size(); i++) {
            menu.get(i).setIngredients(mealIngredientsRepository.findIngredientsOfMeal(menu.get(i).getId()));
        }

        actualOrder = orderView.selectOrder(actualOrder, menu);

        // get the discount for actual customer
        for (Discount discount : discountsList) {
            if (discount.getOrders() <= orderRepository.countOrdersOfCustomer(actualCustomer)) {
                actualCustomer.setDiscount(discount);
            }
        }

        actualOrder.changePrice(actualCustomer, locations);

        orderRepository.countOrdersOfCustomer(actualCustomer);
        orderRepository.updatePrice(actualOrder);
        orderRepository.createOrderdetails(actualOrder, actualCustomer.getDiscount());

        for (OrderPart orderPart : actualOrder.getOrderDetails()) {

            System.out.println(orderPart.getMeal().getName() + "   "
                    + orderPart.getPrice() + " €\n\t Zusätzliche Zutaten "
                    + orderPart.getCountAddedIngredients());
        }

        for (Location location : locations) {
            if (location.getPlz() == actualCustomer.getPlz()) {
                System.out.println("\nLieferkosten " + location.getPrice() + ".0 €");
            }
        }
    }

    public static Customer getCustomer(Customer actualCustomer) {
        if (actualCustomer.getId() == 0) {
            int newCustomerId = customerRepository.create(actualCustomer);
            System.out.println("Deine Kundennummer ist: " + newCustomerId);
            actualCustomer.setId(newCustomerId);
        } else {
            actualCustomer = customerRepository.findOne(actualCustomer.getId());
        }
        return actualCustomer;
    }

    public static void startEvaluation() {
        OrderView orderView = new OrderView();
        OrderRepository orderRepository = new OrderRepository();

        orderView.printEvaluation(orderRepository);
    }

    public static void exportData() {
        OrderRepository orderRepository = new OrderRepository();
        MealIngredientsRepository mealIngredientsRepository = new MealIngredientsRepository();
        Exporter exporter = new Exporter();

        String[] headerOrders = {"Order ID", "Customer ID", "Price"};
        exporter.writeOrderFile(headerOrders, orderRepository.findAll());

        String[] headerIngredients = {"Zutat", "Anzahl"};
        exporter.writeIngredientFile(headerIngredients, mealIngredientsRepository.countIngredientsSold());
    }
}
