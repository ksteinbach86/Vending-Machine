package com.techelevator.domain;

import org.junit.Assert;
import org.junit.Test;

public class DrinkTest {
    @Test
    public void Drink_constructor_gives_correct_instance_variable_assignment() {
        // Arrange
        Drink testDrink = new Drink("testDrinkName", 1.00);
        String expectedName = "testDrinkName";
        double expectedPrice = 1.00;

        // Act
        String actualName = testDrink.getItemName();
        double actualPrice = testDrink.getPrice();

        // Assert
        Assert.assertEquals("String itemName in constructor DID NOT assign name properly", expectedName, actualName);
        Assert.assertEquals("double price in constructor DID NOT assign price properly", expectedPrice, actualPrice, 0.0);
    }
    @Test
    public void getDispenseMessage_returns_message_glug_glug() {
        //Arrange
        Drink testDrink = new Drink("testDrinkName", 1.00);
        String expectedString = "Glug Glug, Yum!";

        //Act
        String actualString = testDrink.getDispenseMessage();

        //Assert
        Assert.assertEquals("getDispenseMessage() object does not return \"Glug Glug, Yum!\"", expectedString, actualString);

    }
    @Test
    public void toString_returns_correct_formatted_strings() {
        //Arrange
        Drink testDrink = new Drink("testDrinkName", 1.00);
        String expected = "testDrinkName $1.00";

        //Act
        String actual = testDrink.toString();

        //Assert
        Assert.assertEquals("toString() did not return \"testDrinkName $1.00\"", expected, actual);


    }

}
