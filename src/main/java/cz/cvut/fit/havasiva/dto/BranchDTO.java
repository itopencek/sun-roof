package cz.cvut.fit.havasiva.dto;

import java.util.List;

public class BranchDTO {

    private final int id;
    private final String country;
    private final boolean isWebStore;
    private final int yearlyProfit;
    private List<Integer> employeeIds;

    public BranchDTO(int id, String country, boolean isWebStore, int yearlyProfit, List<Integer> employeeIds ) {
        this.id = id;
        this.country = country;
        this.isWebStore = isWebStore;
        this.yearlyProfit = yearlyProfit;
        this.employeeIds = employeeIds;
    }

    public String getCountry() {
        return country;
    }

    public int getYearlyProfit() {
        return yearlyProfit;
    }

    public int getId() { return id; }

    public boolean isWebStore() {
        return isWebStore;
    }

    public List<Integer> getEmployeeIds() {
        return employeeIds;
    }

}
