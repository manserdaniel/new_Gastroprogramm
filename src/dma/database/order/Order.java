package dma.database.order;

import dma.database.customer.Customer;
import dma.location.Location;

import java.util.LinkedList;
import java.util.List;

public class Order {
    int id = 0;
    int customerId = 0;
    double price = 0.0;
    int plz = 0;

    LinkedList<OrderPart> orderDetails = new LinkedList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void changePrice(Customer customer, List<Location> locations) {
        for (OrderPart meal : orderDetails) {
            this.price = price + (meal.getPrice()-(meal.getPrice()/100*customer.getDiscount().getPercentage()));
        }
        for (Location location : locations) {
            if (location.getPlz() == customer.getPlz()) {
                this.price = this.price + location.getPrice();
            }
        }
    }

    public int getPlz() {
        return plz;
    }

    public void setPlz(int plz) {
        this.plz = plz;
    }

    public LinkedList<OrderPart> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderPart orderDetails) {
        this.orderDetails.add(orderDetails);
    }
}
