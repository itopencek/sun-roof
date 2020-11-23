package cz.cvut.fit.havasiva.dto;

import cz.cvut.fit.havasiva.entity.Branch;

import java.util.Objects;

public class CustomerOrderCreateDTO {

    private final String productName;
    private final double price;
    private final String date;
    private final String madeBy;
    private final int orderedFromId;

    public CustomerOrderCreateDTO( String productName, double price, String date, String madeBy, int orderedFromId) {
        this.productName = productName;
        this.price = price;
        this.date = date;
        this.madeBy = madeBy;
        this.orderedFromId = orderedFromId;
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

    public int getCustomerOrderedFromId() {
        return orderedFromId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerOrderCreateDTO that = (CustomerOrderCreateDTO) o;
        return Double.compare(that.price, price) == 0 &&
                orderedFromId == that.orderedFromId &&
                Objects.equals(productName, that.productName) &&
                Objects.equals(date, that.date) &&
                Objects.equals(madeBy, that.madeBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, price, date, madeBy, orderedFromId);
    }
}
