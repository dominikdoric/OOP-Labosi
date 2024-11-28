package hr.java.restaurant.model;

public class Waiter extends Person {
    private Contract contract;
    private Bonus bonus;

    public Waiter(String firstName, String lastName, Contract contract, Bonus bonus) {
        super(firstName, lastName);
        this.contract = contract;
        this.bonus = bonus;
    }
}
