package cz.cvut.fit.havasiva.entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Branch {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String country;

    @NotNull
    private boolean isWebStore;

    @NotNull
    private int numOfEmployees;

    @ManyToMany
    @JoinTable(
            name = "sunroof_employee",
            joinColumns = @JoinColumn(name = "branch_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Employee> employees;


    public Branch() {
    }

    public Branch(@NotNull String country, @NotNull int numOfEmployees, @NotNull boolean isWebStore, List<Employee> employees) {
        this.country = country;
        this.numOfEmployees = numOfEmployees;
        this.isWebStore = isWebStore;
        this.employees = employees;
    }

    public boolean isWebStore() {
        return isWebStore;
    }

    public void setWebStore(boolean webStore) {
        isWebStore = webStore;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getNumOfEmployees() {
        return numOfEmployees;
    }

    public void setNumOfEmployees(int numOfEmployees) {
        this.numOfEmployees = numOfEmployees;
    }

    public int getId() {
        return id;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}
