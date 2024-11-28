package hr.java.restaurant.model;

/**
 * Represents data about waiter which is employed in the restaurant.
 */
public class Waiter extends Person {
    private Contract contract;
    private Bonus bonus;

    public Waiter(String firstName, String lastName, Contract contract, Bonus bonus) {
        super(firstName, lastName);
        this.contract = contract;
        this.bonus = bonus;
    }
}
