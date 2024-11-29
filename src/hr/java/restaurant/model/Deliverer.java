package hr.java.restaurant.model;

/**
 * Represents data about deliverer which will work in the restaurant.
 */
public class Deliverer extends Person {
    private Contract contract;
    private Bonus bonus;

    public Deliverer(String firstName, String lastName, Contract contract, Bonus bonus) {
        super(firstName, lastName);
        this.contract = contract;
        this.bonus = bonus;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Bonus getBonus() {
        return bonus;
    }

    public void setBonus(Bonus bonus) {
        this.bonus = bonus;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
