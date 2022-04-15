package com.techelevator.domain;

public class Candy extends VendingItem {

    //TODO Candy Constructor gives appropriate name and price
    public Candy(String itemName, double price) {
        super(itemName, price);
    }

    // TODO getDispenseMessage() returns "Munch Munch, Yum!"
    @Override
    public String getDispenseMessage() {
        return "Munch Munch, Yum!";
    }

    // TODO getString() method returns itemName + NumberFormat.getCurrencyInstance.format(getPrice())
}
