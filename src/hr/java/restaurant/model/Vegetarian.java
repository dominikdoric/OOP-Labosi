package hr.java.restaurant.model;

public sealed interface Vegetarian permits VegetarianMeal {
    String getPreparationMethod();

    Long getMinutesToPrepare();
}
