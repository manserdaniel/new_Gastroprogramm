package dma.view;

import dma.database.customer.Customer;
import dma.database.meal.Meal;
import dma.database.order.Order;
import dma.database.order.OrderPart;
import dma.database.order.OrderRepository;

import java.util.List;
import java.util.Scanner;

public class OrderView {

    public Customer selectCustomer() {
        Scanner scannerInt = new Scanner(System.in);
        Scanner scanner = new Scanner(System.in);

        Customer actalCustomer = new Customer();

        System.out.println("Sind sie Neukunde? (Y/N)");
        String answer = scanner.nextLine();

        if (answer.equalsIgnoreCase("Y")) {
            System.out.println("Geben sie ihren Name ein: ");
            actalCustomer.setName(scanner.nextLine());
            System.out.println("Geben sie ihre Postleitzahl ein: ");
            actalCustomer.setPlz(scannerInt.nextInt());


        } else if (answer.equalsIgnoreCase("N")) {
            System.out.println("Geben sie ihre Kundennummer ein ");
            actalCustomer.setId(scannerInt.nextInt());

        } else {
            System.out.println("Eingabe falsch!");
        }
        return actalCustomer;
    }

    public Order selectOrder(Order newOrder, List<Meal> menu) {
        Scanner scannerInt = new Scanner(System.in);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Wieviel Mahlzeiten möchten sie bestellen? ");
        int amountOfMeals = scannerInt.nextInt();
        int order = 0;

        for (int i = 0; i < amountOfMeals; i++) {
            System.out.println("Geben sie die id der Mahlzeit ein die sie bestellen möchten: ");
            order = scannerInt.nextInt();
            OrderPart newOrderPart = new OrderPart();

            for (Meal meal : menu) {
                if (meal.getId() == order) {
                    newOrderPart.setMeal(meal);
                    newOrderPart.setIngredients(meal.getIngredients());
                    break;
                }
            }

            int deleteIngredient = 1;
            while (deleteIngredient > 0) {
                System.out.println(newOrderPart.getIngredients());
                System.out.println("Möchten sie von den Zutaten etwas entfernen? (Y/N)");
                String answer = scanner.nextLine();


                if (answer.equalsIgnoreCase("y")) {

                    System.out.println("Was möchten sie entfernen? Geben sie die fortlaufende zahl ein? 0 um abzubrechen.");
                    deleteIngredient = scannerInt.nextInt();

                    if (deleteIngredient > 0) {
                        newOrderPart.deleteIngredient(deleteIngredient - 1);
                    }

                } else if (answer.equalsIgnoreCase("n")) {
                    deleteIngredient = 0;
                }
            }

            boolean addIngredient = true;
            while (addIngredient) {
                System.out.println(newOrderPart.getIngredients());
                System.out.println("Möchten sie etwas zusätlich auf ihrem Gericht? (Y/N)");
                String answer = scanner.nextLine();

                if (answer.equalsIgnoreCase("y")) {

                    System.out.println("Was möchten sie hinzufügen?");
                    String newIngredient = scanner.nextLine();

                    newOrderPart.addIngredient(newIngredient);

                } else if (answer.equalsIgnoreCase("n")) {
                    addIngredient = false;
                }
            }
            newOrder.setOrderDetails(newOrderPart);
        }
        return newOrder;
    }

    public void printEvaluation(OrderRepository orderRepository) {
        Scanner scanner = new Scanner(System.in);
        int answer = 0;

        while (answer <= 5) {
            System.out.println("Was möchten sie gerne Auswerten?\n" +
                    "1. Wie viele Bestellungen gab es schon?\n" +
                    "2. Wie viele Bestellungen gab es je Kunde?\n" +
                    "3. Wie viele Bestellungen gab es je Ortschaft?\n" +
                    "4. Was sind all meine Umsätze nach den Kriterien 1 - 3 (Gesamt, je Kunde, je Ortschaft)\n" +
                    "5. Was wurde am häufigsten bestellt und wie oft?\n" +
                    "6. Beenden");

            answer = scanner.nextInt();

            switch (answer) {
                case 1:
                    System.out.println(orderRepository.printOrdersAll());
                    break;
                case 2:
                    System.out.println(orderRepository.printOrdersPerCustomer());
                    break;
                case 3:
                    System.out.println(orderRepository.printOrdersPerLocation());
                    break;
                case 4:
                    System.out.println(orderRepository.printTurnoverAll() + "\n");
                    System.out.println(orderRepository.printTurnoverPerCustomer());
                    System.out.println(orderRepository.printTurnoverPerLocation());
                    break;
                case 5:
                    System.out.println(orderRepository.printTheMostPopularMeal());
                    break;
                default:
                    break;
            }
        }
    }
}
