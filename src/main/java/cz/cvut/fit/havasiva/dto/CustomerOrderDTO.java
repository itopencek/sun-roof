package cz.cvut.fit.havasiva.dto;

import cz.cvut.fit.havasiva.entity.Branch;

import java.util.List;
import java.util.Objects;

public class CustomerOrderDTO {

    private final int id;
    private final String productName;
    private final double price;
    private final String date;
    private final String madeBy;
    private final int orderedFromId;

    public CustomerOrderDTO(int id, String productName, double price, String date, String madeBy, int orderedFromId) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.date = date;
        this.madeBy = madeBy;
        this.orderedFromId = orderedFromId;
    }

    public int getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public String getMadeBy() {
        return madeBy;
    }

    public int getOrderedFromId() {
        return orderedFromId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerOrderDTO that = (CustomerOrderDTO) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
