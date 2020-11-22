package cz.cvut.fit.havasiva.dto;

import java.util.List;

public class BranchCreateDTO {

    private final String country;
    private final boolean isWebStore;
    private final int yearlyProfit;
    private List<Integer> employeeIds;

    public BranchCreateDTO( String country, boolean isWebStore, int yearlyProfit, List<Integer> employeeIds ) {
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

    public boolean isWebStore() {
        return isWebStore;
    }

    public List<Integer> getEmployeeIds() {
        return employeeIds;
    }
}
