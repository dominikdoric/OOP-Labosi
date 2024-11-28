package hr.java.restaurant.model;

public sealed interface Vegan permits VeganMeal {
    String getPreparationMethod();

    Long getMinutesToPrepare();
}
