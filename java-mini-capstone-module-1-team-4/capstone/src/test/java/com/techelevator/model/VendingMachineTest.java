package com.techelevator.model;

import com.sun.source.tree.Tree;
import com.techelevator.domain.Candy;
import org.junit.Assert;
import org.junit.Test;

import java.util.TreeMap;

public class VendingMachineTest {
    private final String FILE_PATH = "vendingmachine.csv";

    //Test VendingMachine balance, total sales, hashmap, treemap, arraylist
    @Test
    public void VendingMachine_constructor_for_empty_TreeMap_and_zero_balance() {
        // Arrange
        VendingMachine testVendingMachine = new VendingMachine();
        double expectedBalance = 0.0;
        double expectedTotalSales = 0.0;
        int expectedItemNameToQuantitySoldSize = 0;
        int expectedSlotLocationToVendingItemSize = 0;
        int expectedVendingItemMasterListSize = 0;

        // Act
        double actualBalance = testVendingMachine.getBalance();
        double actualTotalSales = testVendingMachine.getTotalSales();
        int actualItemNameToQuantitySoldSize = testVendingMachine.getItemNameToQuantitySold().size();
        int actualSlotLocationToVendingItemSize = testVendingMachine.getSlotLocationToVendingItems().size();
        int actualVendingItemMasterListSize = testVendingMachine.getVendingItemMasterList().size();

        // Assert
        Assert.assertEquals("balance was not initialized to zero", expectedBalance, actualBalance, 0.0);
        Assert.assertEquals("totalSales was not initialized to zero", expectedTotalSales, actualTotalSales, 0.0);
        Assert.assertEquals("itemNameToQuantitySold was not initialized to empty", expectedItemNameToQuantitySoldSize, actualItemNameToQuantitySoldSize);
        Assert.assertEquals("slotLocationToVendingItem was not initialized to empty", expectedSlotLocationToVendingItemSize, actualSlotLocationToVendingItemSize);
        Assert.assertEquals("vendingItemMasterList was not initialized to empty", expectedVendingItemMasterListSize, actualVendingItemMasterListSize);

    }

    @Test
    public void feedMoney_adds_to_the_balance_correctly() {
        //Arrange
        VendingMachine testVendingMachine = new VendingMachine();
        testVendingMachine.createLogFile();
        double expected = 1.00;

        //Act
        testVendingMachine.feedMoney(1.00);
        double actual = testVendingMachine.getBalance();

        //Arrange
        Assert.assertEquals("When balance is 0, getBalance() did not return 1.0 when VendingMachine called feedMoney(1.00)", expected, actual, 0.0);

    }


}

