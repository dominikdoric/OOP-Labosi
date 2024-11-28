package hr.java.restaurant.model;

import java.math.BigDecimal;

public final class MeatMeal extends Meal implements Meat {
    private String freezingMethod;

    public MeatMeal(Long id,
                    String name,
                    Category category,
                    Ingredient[] ingredients,
                    BigDecimal price,
                    String freezingMethod) {
        super(id, name, category, ingredients, price);
        this.freezingMethod = freezingMethod;
    }

    public String getFreezingMethod() {
        return freezingMethod;
    }

    public void setFreezingMethod(String freezingMethod) {
        this.freezingMethod = freezingMethod;
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
