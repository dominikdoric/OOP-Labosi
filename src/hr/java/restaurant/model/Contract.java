package hr.java.restaurant.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Contract {
    BigDecimal salary;
    LocalDate startDate;
    LocalDate endDate;
    ContractType contractType;

    public Contract(BigDecimal salary, LocalDate startDate, LocalDate endDate, ContractType contractType) {
        this.salary = salary;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contractType = contractType;
    }

    public enum ContractType {
        FULL_TIME,
        PART_TIME
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public ContractType getConcractType() {
        return contractType;
    }

    public void setConcractType(ContractType contractType) {
        this.contractType = contractType;
    }
}
