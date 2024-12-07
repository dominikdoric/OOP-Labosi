package hr.java.restaurant.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * Represents data about vegetarian meal which will be available in the restaurant.
 */
public final class VegetarianMeal extends Meal implements Vegetarian, Serializable {
    private boolean containsEggs;

    public VegetarianMeal(Long id,
                          String name,
                          Category category,
                          List<Ingredient> ingredients,
                          BigDecimal price,
                          boolean containsEggs
    ) {
        super(id, name, category, ingredients, price);
        this.containsEggs = containsEggs;
    }

    public boolean isContainsEggs() {
        return containsEggs;
    }

    public void setContainsEggs(boolean containsEggs) {
        this.containsEggs = containsEggs;
    }

    @Override
    public String getPreparationMethod() {
        return "";
    }

    @Override
    public Long getMinutesToPrepare() {
        return 0L;
    }
}
