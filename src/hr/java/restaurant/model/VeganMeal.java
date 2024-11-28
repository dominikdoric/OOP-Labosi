package hr.java.restaurant.model;

import java.math.BigDecimal;

public final class VeganMeal extends Meal implements Vegan {
    private int numberOfSalads;

    public VeganMeal(Long id,
                     String name,
                     Category category,
                     Ingredient[] ingredients,
                     BigDecimal price,
                     int numberOfSalads
    ) {
        super(id, name, category, ingredients, price);
        this.numberOfSalads = numberOfSalads;
    }

    @Override
    public String getPreparationMethod() {
        return "";
    }

    @Override
    public Long getMinutesToPrepare() {
        return 0L;
    }

    public int getNumberOfSalads() {
        return numberOfSalads;
    }

    public void setNumberOfSalads(int numberOfSalads) {
        this.numberOfSalads = numberOfSalads;
    }
}
