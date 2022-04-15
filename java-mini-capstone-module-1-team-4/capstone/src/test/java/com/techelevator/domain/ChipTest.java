package com.techelevator.domain;

import org.junit.Assert;
import org.junit.Test;

public class ChipTest {

    @Test
    public void Chip_constructor_gives_correct_instance_variable_assignment() {
        // Arrange
        Chip testChip = new Chip("testChipName", 1.00);
        String expectedName = "testChipName";
        double expectedPrice = 1.00;

        // Act
        String actualName = testChip.getItemName();
        double actualPrice = testChip.getPrice();

        // Assert
        Assert.assertEquals("String itemName in constructor DID NOT assign name properly", expectedName, actualName);
        Assert.assertEquals("double price in constructor DID NOT assign price properly", expectedPrice, actualPrice, 0.0);
    }

    @Test
    public void getDispenseMessage_returns_message_crunch_crunch() {
        //Arrange
        Chip testChip = new Chip("testChipName", 1.00);
        String expectedString = "Crunch Crunch, Yum!";

        //Act
        String actualString = testChip.getDispenseMessage();

        //Assert
        Assert.assertEquals("getDispenseMessage() object does not return \"Crunch Crunch, Yum!\"", expectedString, actualString);

    }
    @Test
    public void toString_returns_correct_formatted_strings() {
        //Arrange
        Chip testChip = new Chip("testChipName", 1.00);
        String expected = "testChipName $1.00";

        //Act
        String actual = testChip.toString();

        //Assert
        Assert.assertEquals("toString() did not return \"testCandyName $1.00\"", expected, actual);


    }

}
