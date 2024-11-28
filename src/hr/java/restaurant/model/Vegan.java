package hr.java.restaurant.model;

/**
 * Sealed interface which restricts only VeganMeal class to implement it.
 */
public sealed interface Vegan permits VeganMeal {
    String getPreparationMethod();

    Long getMinutesToPrepare();
}
