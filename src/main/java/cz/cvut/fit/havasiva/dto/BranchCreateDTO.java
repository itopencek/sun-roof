package cz.cvut.fit.havasiva.dto;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BranchCreateDTO that = (BranchCreateDTO) o;
        return isWebStore == that.isWebStore &&
                yearlyProfit == that.yearlyProfit &&
                Objects.equals(country, that.country) &&
                Objects.equals(employeeIds, that.employeeIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, isWebStore, yearlyProfit, employeeIds);
    }
}
