package hr.java.restaurant.model;

/**
 * Represents data about chef which will work in restaurant.
 */
public class Chef extends Person {
    private Contract contract;
    private Bonus bonus;

    public Chef(String firstName, String lastName, Contract contract, Bonus bonus) {
        super(firstName, lastName);
        this.contract = contract;
        this.bonus = bonus;
    }
}
