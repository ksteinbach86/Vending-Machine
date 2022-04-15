package com.techelevator.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLClientInfoException;

public class CandyTest {
    private Candy testCandy;

    @Before
    public void Candy() {
        this.testCandy = new Candy("testCandyName", 1.00);
    }

    @Test
    public void Candy_constructor_gives_correct_instance_variable_assignment() {
        // Arrange
        String expectedName = "testCandyName";
        double expectedPrice = 1.00;

        // Act
        String actualName = testCandy.getItemName();
        double actualPrice = testCandy.getPrice();

        // Assert
        Assert.assertEquals("String itemName in constructor DID NOT assign name properly", expectedName, actualName);
        Assert.assertEquals("double price in constructor DID NOT assign price properly", expectedPrice, actualPrice, 0.0);
    }

    @Test
    public void getDispenseMessage_returns_message_munch_munch() {
        //Arrange
        String expectedString = "Munch Munch, Yum!";

        //Act
        String actualString = testCandy.getDispenseMessage();

        //Assert
        Assert.assertEquals("getDispenseMessage() object does not return \"Munch Munch, Yum!\"", expectedString, actualString);
    }

    @Test
    public void toString_returns_correct_formatted_strings() {
        //Arrange
        String expected = "testCandyName $1.00";

        //Act
        String actual = testCandy.toString();

        //Assert
        Assert.assertEquals("toString() did not return \"testCandyName $1.00\"", expected, actual);
    }
}
