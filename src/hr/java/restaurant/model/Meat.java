package hr.java.restaurant.model;

/**
 * Sealed interface which restricts only MeatMeal class to implement it.
 */
public sealed interface Meat permits MeatMeal {
    String getPreparationMethod();

    Long getMinutesToPrepare();
}
