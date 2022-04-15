package com.techelevator.domain;

import org.junit.Assert;
import org.junit.Test;

public class GumTest {
    @Test
    public void Gum_constructor_gives_correct_instance_variable_assignment() {
        // Arrange
        Gum testGum = new Gum("testGumName", 1.00);
        String expectedName = "testGumName";
        double expectedPrice = 1.00;

        // Act
        String actualName = testGum.getItemName();
        double actualPrice = testGum.getPrice();

        // Assert
        Assert.assertEquals("String itemName in constructor DID NOT assign name properly", expectedName, actualName);
        Assert.assertEquals("double price in constructor DID NOT assign price properly", expectedPrice, actualPrice, 0.0);
    }
    @Test
    public void getDispenseMessage_returns_message_chew_chew() {
        //Arrange
        Gum testCandy = new Gum("testGumName", 1.00);
        String expectedString = "Chew Chew, Yum!";

        //Act
        String actualString = testCandy.getDispenseMessage();

        //Assert
        Assert.assertEquals("getDispenseMessage() object does not return \"Chew Chew, Yum!\"", expectedString, actualString);

    }

    @Test
    public void toString_returns_correct_formatted_strings() {
        //Arrange
        Gum testGum = new Gum("testGumName", 1.00);
        String expected = "testGumName $1.00";

        //Act
        String actual = testGum.toString();

        //Assert
        Assert.assertEquals("toString() did not return \"testCandyName $1.00\"", expected, actual);


    }

}
