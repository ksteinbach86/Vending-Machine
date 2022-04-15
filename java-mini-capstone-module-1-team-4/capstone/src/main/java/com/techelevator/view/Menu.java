package com.techelevator.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Menu {

    private PrintWriter out;
    private Scanner in;

    public Menu(InputStream input, OutputStream output) {
        this.out = new PrintWriter(output);
        this.in = new Scanner(input);
    }


    public Object getChoiceFromOptions(Object[] options) {
        Object choice = null;
        while (choice == null) {
            displayMenuOptions(options);
            choice = getChoiceFromUserInput(options);
        }
        return choice;
    }

    private void displayMenuOptions(Object[] options) {
        out.println();
        for (int i = 0; i < options.length; i++) {
            int optionNum = i + 1;
            out.println(optionNum + ") " + options[i]);
        }
        out.print(System.lineSeparator() + "Please choose an option >>> ");
        out.flush();
    }

    // Hidden sales report functionality in main menu
    public Object getChoiceForMainMenuFromOptions(Object[] options) {
        Object choice = null;
        while (choice == null) {
            displayMenuOptions(options);
            choice = getChoiceForMainMenuFromUserInput(options);
        }
        return choice;
    }

    public Object getPurchaseMenuChoiceFromOptions(Object[] options, String currentMoneyProvided) {
        Object choice = null;
        while (choice == null) {
            displayPurchaseMenuOptions(options, currentMoneyProvided);
            choice = getChoiceFromUserInput(options);
        }
        return choice;
    }

    private void displayPurchaseMenuOptions(Object[] options, String currentMoneyProvided) {
        out.println();
        for (int i = 0; i < options.length; i++) {
            int optionNum = i + 1;
            out.println(optionNum + ") " + options[i]);
        }
        out.println();
        out.println("Current money provided: " + currentMoneyProvided);
        out.print(System.lineSeparator() + "Please choose an option >>> ");
        out.flush();
    }

    // Same as
    private Object getChoiceForMainMenuFromUserInput(Object[] options) {
        Object choice = null;
        String userInput = in.nextLine();
        try {
            int selectedOption = Integer.valueOf(userInput);
            if (selectedOption > 0 && selectedOption <= options.length) {
                choice = options[selectedOption - 1];
            }
            if (selectedOption == options.length + 1) {
                choice = "Hidden sales report";
            }
        } catch (NumberFormatException e) {
            // eat the exception, an error message will be displayed below since choice will be null
        }
        if (choice == null) {
            invalidOption(userInput);
        }
        return choice;
    }

    private Object getChoiceFromUserInput(Object[] options) {
        Object choice = null;
        String userInput = in.nextLine();
        try {
            int selectedOption = Integer.valueOf(userInput);
            if (selectedOption > 0 && selectedOption <= options.length) {
                choice = options[selectedOption - 1];
            }
        } catch (NumberFormatException e) {
            // eat the exception, an error message will be displayed below since choice will be null
        }
        if (choice == null) {
            invalidOption(userInput);
        }
        return choice;
    }

    private void invalidOption(String userInput) {
        out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
    }

    public String getSlotLocationInput() {
        out.println();
        out.print("Enter slot location: ");
        out.flush();
        return in.nextLine();
    }

    public void displayMessage(String message) {
        out.println();
        out.println(message);
        out.flush();
    }
}
