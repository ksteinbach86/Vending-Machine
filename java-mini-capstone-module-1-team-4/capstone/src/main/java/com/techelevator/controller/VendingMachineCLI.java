package com.techelevator.controller;

import com.techelevator.domain.VendingItem;
import com.techelevator.model.VendingMachine;
import com.techelevator.view.Menu;

public class VendingMachineCLI {
    // File path to load Vending Machine
    private static final String FILE_PATH = "vendingmachine.csv";

    // Main Menu options
    private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
    private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
    private static final String MAIN_MENU_OPTION_EXIT = "Exit";
    private static final String HIDDEN_SALES_REPORT = "Hidden sales report";
    private static final String[] MAIN_MENU_OPTIONS = {
            MAIN_MENU_OPTION_DISPLAY_ITEMS,
            MAIN_MENU_OPTION_PURCHASE,
            MAIN_MENU_OPTION_EXIT,
    };

    // Purchase Menu options
    private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
    private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
    private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
    private static final String[] PURCHASE_MENU_OPTIONS = {
            PURCHASE_MENU_OPTION_FEED_MONEY,
            PURCHASE_MENU_OPTION_SELECT_PRODUCT,
            PURCHASE_MENU_OPTION_FINISH_TRANSACTION,
    };

    // Feed Money menu options
    private static final double ONE_DOLLAR = 1.00;
    private static final double TWO_DOLLARS = 2.00;
    private static final double FIVE_DOLLARS = 5.00;
    private static final double TEN_DOLLARS = 10.00;
    private static final String FEED_MONEY_MENU_OPTION_ONE_DOLLAR = "$1.00";
    private static final String FEED_MONEY_MENU_OPTION_TWO_DOLLARS = "$2.00";
    private static final String FEED_MONEY_MENU_OPTION_FIVE_DOLLARS = "$5.00";
    private static final String FEED_MONEY_MENU_OPTION_TEN_DOLLARS = "$10.00";
    private static final String FEED_MONEY_MENU_OPTION_EXIT = "Exit";
    private static final String[] FEED_MONEY_MENU_OPTIONS = {
            FEED_MONEY_MENU_OPTION_ONE_DOLLAR,
            FEED_MONEY_MENU_OPTION_TWO_DOLLARS,
            FEED_MONEY_MENU_OPTION_FIVE_DOLLARS,
            FEED_MONEY_MENU_OPTION_TEN_DOLLARS,
            FEED_MONEY_MENU_OPTION_EXIT,
    };

    // All error messages to feed to Menu class
    private static final String INSUFFICIENT_FUNDS = "Insufficient funds!! Please feed more money.";
    private static final String INVALID_SLOT_LOCATION = "Invalid slot location!!";
    private static final String VENDING_ITEM_IS_SOLD_OUT = "Vending item is SOLD OUT!!";
    private static final String VENDING_MACHINE_LOAD_ERROR_MESSAGE = "Failed to load file! VendingMachine will self destruct in five seconds...";

    // Prompts
    private static final String CURRENT_MONEY_PROVIDED = "Current Money Provided: ";
    private static final String VENDING_MACHINE_EXIT_MESSAGE = "Thank you for your business!";

    private final Menu menu;
    private final VendingMachine vendingMachine;

    public VendingMachineCLI(Menu menu, VendingMachine vendingMachine) {
        this.menu = menu;
        this.vendingMachine = vendingMachine;
    }

    public void run() {
        if (loadVendingMachine() && createVendingMachineLog()) {
            mainMenu();
        } else {
            menu.displayMessage(VENDING_MACHINE_LOAD_ERROR_MESSAGE);
        }
    }

    private boolean loadVendingMachine() {
        return vendingMachine.loadVendingMachine(FILE_PATH);
    }

    private boolean createVendingMachineLog() {
        return vendingMachine.createLogFile();
    }

    private void mainMenu() {
        boolean loop = true;
        while (loop) {
            String choice = getMainMenuChoice();
            switch (choice) {
                case MAIN_MENU_OPTION_DISPLAY_ITEMS:
                    displayVendingMachineItems();
                    break;
                case MAIN_MENU_OPTION_PURCHASE:
                    purchaseMenu();
                    break;
                case MAIN_MENU_OPTION_EXIT:
                    menu.displayMessage(VENDING_MACHINE_EXIT_MESSAGE);
                    loop = false;
                    vendingMachine.closeLogFile();
                    break;
                case HIDDEN_SALES_REPORT:
                    // write code here
                    vendingMachine.writeSalesRecordFile();
                    break;
            }
        }
    }

    private void purchaseMenu() {
        boolean loop = true;
        while (loop) {
            String choice = getPurchaseMenuChoice();
            switch (choice) {
                case PURCHASE_MENU_OPTION_FEED_MONEY:
                    feedMoneyMenu();
                    break;
                case PURCHASE_MENU_OPTION_SELECT_PRODUCT:
                    selectProductMenu();
                    break;
                case PURCHASE_MENU_OPTION_FINISH_TRANSACTION:
                    // write code here
                    menu.displayMessage(vendingMachine.giveChange());
                    loop = false;
                    break;
            }
        }
    }

    private void feedMoneyMenu() {
        String choice = getFeedMoneyMenuChoice();
        switch (choice) {
            case FEED_MONEY_MENU_OPTION_ONE_DOLLAR:
                vendingMachine.feedMoney(ONE_DOLLAR);
                break;
            case FEED_MONEY_MENU_OPTION_TWO_DOLLARS:
                vendingMachine.feedMoney(TWO_DOLLARS);
                break;
            case FEED_MONEY_MENU_OPTION_FIVE_DOLLARS:
                vendingMachine.feedMoney(FIVE_DOLLARS);
                break;
            case FEED_MONEY_MENU_OPTION_TEN_DOLLARS:
                vendingMachine.feedMoney(TEN_DOLLARS);
                break;
            case FEED_MONEY_MENU_OPTION_EXIT:
                break;
        }
    }

    // Returns VendingItem so code is extensible to eventually pass a vending item
    // to the client, currently unnecessary
    private VendingItem selectProductMenu() {
        VendingItem vendingItem = null;

        menu.displayMessage(vendingMachine.toString());
        menu.displayMessage(CURRENT_MONEY_PROVIDED + vendingMachine.formatDoubleAsCurrency(vendingMachine.getBalance()));

        String slotLocation = menu.getSlotLocationInput();

        if (!vendingMachine.isValidSlotLocation(slotLocation)) {
            menu.displayMessage(INVALID_SLOT_LOCATION);
        } else if (!vendingMachine.isVendingItemInStock(slotLocation)) {
            menu.displayMessage(VENDING_ITEM_IS_SOLD_OUT);
        } else {
            double vendingItemPrice = (vendingMachine.getSlotLocationToVendingItems()
                    .get(slotLocation).get(0).getPrice());
            if (vendingItemPrice <= vendingMachine.getBalance()) {
                vendingItem = vendingMachine.dispenseVendingItem(slotLocation);
                menu.displayMessage(vendingItem.toString());
                menu.displayMessage(vendingItem.getDispenseMessage());
            } else
                menu.displayMessage(INSUFFICIENT_FUNDS);
        }
        return vendingItem;
    }

    private String getMainMenuChoice() {
        return (String) menu.getChoiceForMainMenuFromOptions(MAIN_MENU_OPTIONS);
    }

    private String getPurchaseMenuChoice() {
        return (String) menu.getPurchaseMenuChoiceFromOptions(PURCHASE_MENU_OPTIONS, vendingMachine.formatDoubleAsCurrency(vendingMachine.getBalance()));
    }

    private String getFeedMoneyMenuChoice() {
        return (String) menu.getChoiceFromOptions(FEED_MONEY_MENU_OPTIONS);
    }

    private void displayVendingMachineItems() {
        menu.displayMessage(vendingMachine.toString());
    }
}