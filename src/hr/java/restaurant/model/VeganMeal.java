package hr.java.restaurant.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * Represents data about vegan meal available in the restaurant.
 */
public final class VeganMeal extends Meal implements Vegan, Serializable {
    private int numberOfSalads;

    public VeganMeal(Long id,
                     String name,
                     Category category,
                     List<Ingredient> ingredients,
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
