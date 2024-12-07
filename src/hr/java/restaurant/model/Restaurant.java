package hr.java.restaurant.model;

import java.io.Serializable;
import java.util.List;

/**
 * Represents data about every restaurant in the organisation.
 */
public class Restaurant extends Entity implements Serializable {
    private String name;
    private Address address;
    private List<Meal> meals;
    private List<Chef> chefs;
    private List<Waiter> waiters;
    private List<Deliverer> deliverers;

    public Restaurant(Long id,
                      String name,
                      Address address,
                      List<Meal> meals,
                      List<Chef> chefs,
                      List<Waiter> waiters,
                      List<Deliverer> deliverers) {
        super(id);
        this.name = name;
        this.address = address;
        this.meals = meals;
        this.chefs = chefs;
        this.waiters = waiters;
        this.deliverers = deliverers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public List<Chef> getChefs() {
        return chefs;
    }

    public void setChefs(List<Chef> chefs) {
        this.chefs = chefs;
    }

    public List<Waiter> getWaiters() {
        return waiters;
    }

    public void setWaiters(List<Waiter> waiters) {
        this.waiters = waiters;
    }

    public List<Deliverer> getDeliverers() {
        return deliverers;
    }

    public void setDeliverers(List<Deliverer> deliverers) {
        this.deliverers = deliverers;
    }

    public Integer getEmployeeCount() {
        return chefs.size() + waiters.size() + deliverers.size();
    }

    @Override
    public String toString() {
        return name;
    }
}
