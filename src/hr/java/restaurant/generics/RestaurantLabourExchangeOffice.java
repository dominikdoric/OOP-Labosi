package hr.java.restaurant.generics;

import hr.java.restaurant.model.Restaurant;

import java.util.Set;

/**
 * Generic class which represents Labour Exchange Office for all the workers
 * which are working in restaurants.
 *
 * @param <T> Generic type of Restaurant or its subclasses.
 */
public class RestaurantLabourExchangeOffice<T extends Restaurant> {
    private Set<T> restaurants;

    public RestaurantLabourExchangeOffice(Set<T> restaurants) {
        this.restaurants = restaurants;
    }

    public Set<T> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(Set<T> restaurants) {
        this.restaurants = restaurants;
    }
}
