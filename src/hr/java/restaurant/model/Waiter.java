package hr.java.restaurant.model;

import java.io.Serializable;

/**
 * Represents data about waiter which is employed in the restaurant.
 */
public class Waiter extends Person implements Serializable {
    private Contract contract;
    private Bonus bonus;

    public Waiter(Integer id, String firstName, String lastName, Contract contract, Bonus bonus) {
        super(id, firstName, lastName);
        this.contract = contract;
        this.bonus = bonus;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
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
}
