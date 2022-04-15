package com.techelevator.domain;

import java.text.NumberFormat;
import java.util.Objects;

public abstract class VendingItem {
    private final String itemName;
    private double price;

    public VendingItem(String itemName, double price) {
        this.itemName = itemName;
        this.price = price;
    }

    public abstract String getDispenseMessage();

    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VendingItem)) return false;
        VendingItem that = (VendingItem) o;
        return Double.compare(that.getPrice(), getPrice()) == 0 && getItemName().equals(that.getItemName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItemName(), getPrice());
    }

    @Override
    public String toString() {
        return itemName + " " + NumberFormat.getCurrencyInstance().format(price);
    }
}
