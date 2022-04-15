package com.techelevator.domain;

public class Chip extends VendingItem {

    //TODO Chip Constructor gives appropriate name and price
    public Chip(String itemName, double price) {
        super(itemName, price);
    }

    // TODO getDispenseMessage() returns "Crunch Crunch, Yum!"
    @Override
    public String getDispenseMessage() {
        return "Crunch Crunch, Yum!";
    }

    // TODO getString() method returns itemName + NumberFormat.getCurrencyInstance.format(getPrice())
}
