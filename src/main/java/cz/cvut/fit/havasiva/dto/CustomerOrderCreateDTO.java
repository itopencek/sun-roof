package cz.cvut.fit.havasiva.dto;

import cz.cvut.fit.havasiva.entity.Branch;

public class CustomerOrderCreateDTO {

    private final String productName;
    private final float price;
    private final String date;
    private final String madeBy;
    private final int orderedFromId;

    public CustomerOrderCreateDTO( String productName, float price, String date, String madeBy, int orderedFromId) {
        this.productName = productName;
        this.price = price;
        this.date = date;
        this.madeBy = madeBy;
        this.orderedFromId = orderedFromId;
    }

    public String getProductName() {
        return productName;
    }

    public float getPrice() {
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
}
