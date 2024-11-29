package hr.java.restaurant.sort;

import hr.java.restaurant.model.Chef;
import hr.java.restaurant.model.Deliverer;
import hr.java.restaurant.model.Waiter;

import java.math.BigDecimal;
import java.util.Comparator;

public class EmployeesBySalarySorter implements Comparator<Object> {

    @Override
    public int compare(Object object1, Object object2) {
        if (object1 == null || object2 == null)
            throw new IllegalArgumentException("Cannot compare null objects");

        BigDecimal salary1 = extractSalary(object1);
        BigDecimal salary2 = extractSalary(object2);

        return salary1.compareTo(salary2);
    }

    private BigDecimal extractSalary(Object object) {
        return switch (object) {
            case Chef chef -> chef.getContract().getSalary();
            case Waiter waiter -> waiter.getContract().getSalary();
            case Deliverer deliverer -> deliverer.getContract().getSalary();
            case null, default -> {
                assert object != null;
                throw new IllegalArgumentException("Unknown employee type: " + object.getClass());
            }
        };
    }
}
