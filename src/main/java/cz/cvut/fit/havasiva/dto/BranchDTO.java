package cz.cvut.fit.havasiva.dto;

import java.util.List;

public class BranchDTO {

    private final int id;
    private final String country;
    private final boolean isWebStore;
    private final int numOfEmployees;
    private List<Integer> employeeIds;

    public BranchDTO(int id, String country, boolean isWebStore, int numOfEmployees, List<Integer> employeeIds ) {
        this.id = id;
        this.country = country;
        this.isWebStore = isWebStore;
        this.numOfEmployees = numOfEmployees;
        this.employeeIds = employeeIds;
    }

    public String getCountry() {
        return country;
    }

    public int getNumOfEmployees() {
        return numOfEmployees;
    }

    public int getId() { return id; }

    public boolean isWebStore() {
        return isWebStore;
    }

    public List<Integer> getEmployeeIds() {
        return employeeIds;
    }

}
