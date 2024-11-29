package hr.java.restaurant.model;

import java.util.Set;

/**
 * Represents data about every restaurant in the organisation.
 */
public class Restaurant extends Entity {
    private String name;
    private Address address;
    private Set<Meal> meals;
    private Set<Chef> chefs;
    private Set<Waiter> waiters;
    private Set<Deliverer> deliverers;

    public Restaurant(Long id,
                      String name,
                      Address address,
                      Set<Meal> meals,
                      Set<Chef> chefs,
                      Set<Waiter> waiters,
                      Set<Deliverer> deliverers) {
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

    public Set<Meal> getMeals() {
        return meals;
    }

    public void setMeals(Set<Meal> meals) {
        this.meals = meals;
    }

    public Set<Chef> getChefs() {
        return chefs;
    }

    public void setChefs(Set<Chef> chefs) {
        this.chefs = chefs;
    }

    public Set<Waiter> getWaiters() {
        return waiters;
    }

    public void setWaiters(Set<Waiter> waiters) {
        this.waiters = waiters;
    }

    public Set<Deliverer> getDeliverers() {
        return deliverers;
    }

    public void setDeliverers(Set<Deliverer> deliverers) {
        this.deliverers = deliverers;
    }

    @Override
    public String toString() {
        return name;
    }
}
