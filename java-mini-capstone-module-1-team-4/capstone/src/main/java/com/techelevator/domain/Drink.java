package com.techelevator.domain;

public class Drink extends VendingItem {

    //TODO Drink Constructor gives appropriate name and price
    public Drink(String itemName, double price) {
        super(itemName, price);
    }

    // TODO getDispenseMessage() returns "Glug Glug, Yum!"
    @Override
    public String getDispenseMessage() {
        return "Glug Glug, Yum!";
    }

    // TODO getString() method returns itemName + NumberFormat.getCurrencyInstance.format(getPrice())
}
