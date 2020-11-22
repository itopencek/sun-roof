package cz.cvut.fit.havasiva.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Entity
public class CustomerOrder {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String productName;
    @NotNull
    private float price;
    @NotNull
    private String date;

    // who made the order - customer
    @NotNull
    private String madeBy;

    @ManyToOne
    @JoinColumn( name = "branch_id")
    private Branch orderedFrom;

    public CustomerOrder() {
    }

    public CustomerOrder(@NotNull String productName, @NotNull float price, @NotNull String date, @NotNull String madeBy, @NotNull Branch orderedFrom) {
        this.productName = productName;
        this.price = price;
        this.date = date;
        this.madeBy = madeBy;
        this.orderedFrom = orderedFrom;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMadeBy() {
        return madeBy;
    }

    public void setMadeBy(String madeBy) {
        this.madeBy = madeBy;
    }

    public Branch getCustomerOrderedFrom() {
        return orderedFrom;
    }

    public void setCustomerOrderedFrom(Branch orderedFrom) {
        this.orderedFrom = orderedFrom;
    }

    public int getId() {
        return id;
    }
}
