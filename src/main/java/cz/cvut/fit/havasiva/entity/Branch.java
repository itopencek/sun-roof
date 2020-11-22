package cz.cvut.fit.havasiva.entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

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
    private int yearlyProfit;

    @ManyToMany
    @JoinTable(
            name = "branch_employee",
            joinColumns = @JoinColumn(name = "branch_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Employee> employees;


    public Branch() {
    }

    public Branch(@NotNull String country, @NotNull int yearlyProfit, @NotNull boolean isWebStore, List<Employee> employees) {
        this.country = country;
        this.yearlyProfit = yearlyProfit;
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

    public int getYearlyProfit() {
        return yearlyProfit;
    }

    public void setYearlyProfit(int yearlyProfit) {
        this.yearlyProfit = yearlyProfit;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Branch branch = (Branch) o;
        return id == branch.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
