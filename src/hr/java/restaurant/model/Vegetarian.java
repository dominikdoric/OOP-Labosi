package hr.java.restaurant.model;

/**
 * Sealed interface which restricts only VegetarianMeal class to implement it.
 */
public sealed interface Vegetarian permits VegetarianMeal {
    String getPreparationMethod();

    Long getMinutesToPrepare();
}
