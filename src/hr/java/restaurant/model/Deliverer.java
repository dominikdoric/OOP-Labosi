package hr.java.restaurant.model;

public class Deliverer extends Person {
    private Contract contract;
    private Bonus bonus;

    public Deliverer(String firstName, String lastName, Contract contract, Bonus bonus) {
        super(firstName, lastName);
        this.contract = contract;
        this.bonus = bonus;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
