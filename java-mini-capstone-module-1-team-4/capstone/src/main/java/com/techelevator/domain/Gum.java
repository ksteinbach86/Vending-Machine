package com.techelevator.domain;

public class Gum extends VendingItem {

    //TODO Gum Constructor gives appropriate name and price
    public Gum(String itemName,double price) {
        super(itemName, price);
    }

    // TODO getDispenseMessage() returns "Chew Chew, Yum!"
    @Override
    public String getDispenseMessage() {
        return "Chew Chew, Yum!";
    }

    // TODO getString() method returns itemName + NumberFormat.getCurrencyInstance.format(getPrice())
}
