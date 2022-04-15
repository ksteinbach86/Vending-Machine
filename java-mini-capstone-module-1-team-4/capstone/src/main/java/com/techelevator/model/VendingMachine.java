package com.techelevator.model;

import com.techelevator.domain.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.PrintWriter;

public class VendingMachine {
    private double balance;
    private double totalSales;
    private final Map<String, List<VendingItem>> slotLocationToVendingItems;
    private final List<VendingItem> vendingItemMasterList; // Add vending item as you load from file to this list also
    private final Map<String, Integer> itemNameToQuantitySold;  // Do not need vendingItemMasterList after this implementation
    private final String LOG_PATH_NAME = "Log.txt";
    private PrintWriter log;

    // CURRENCY is used to format the double when printing the balance
    private static final NumberFormat CURRENCY = NumberFormat.getCurrencyInstance();

    // Format helper constants for toString() method
    // Replaces 'magic strings' in toString method
    // Unsure if this saves memory, but it does make code more readable
    private static final String SLOT_LOCATION = "Slot Location";
    private static final String ITEM_NAME = "Item";
    private static final String PRICE = "Price";
    private static final String QUANTITY = "Quantity";
    private static final String DELIMITER = ": ";
    private static final String SPACE = " ";
    private static final String SOLD_OUT = "SOLD OUT";

    private static final int CENTS_PER_DOLLAR = 100;
    private static final int CENTS_PER_QUARTER = 25;
    private static final int CENTS_PER_DIME = 10;
    private static final int CENTS_PER_NICKEL = 5;
    private static final int MAX_VENDING_ITEMS_PER_SLOT_LOCATION = 5;

    // Log constant variables
    private static final String FEED_MONEY = "FEED MONEY:";
    private static final String GIVE_CHANGE = "GIVE CHANGE:";

    public double getTotalSales() {
        return totalSales;
    }

    public List<VendingItem> getVendingItemMasterList() {
        return vendingItemMasterList;
    }

    public Map<String, Integer> getItemNameToQuantitySold() {
        return itemNameToQuantitySold;
    }

    public VendingMachine() {
        this.balance = 0.00;
        this.totalSales = 0.00;
        // TreeMap used here to assist in ordered printing by slot location key
        // Ex: A1 is printed first, D4 is printed last in toString() method
        this.slotLocationToVendingItems = new TreeMap<>();
        this.itemNameToQuantitySold = new HashMap<>();
        this.vendingItemMasterList = new ArrayList<>();
    }

    //TODO: Test feedMoney() to make sure money is added appropriately to balance
    public void feedMoney(double moneyToAdd) {
        String moneyToAddFormatted = formatDoubleAsCurrency(moneyToAdd);
        this.balance += moneyToAdd;
        String finalBalance = formatDoubleAsCurrency(this.balance);
        writeToLog(formattedDateAndTimeForLog(), FEED_MONEY, moneyToAddFormatted, finalBalance);
    }

    /**
     * TODO: test dispenseVendingItem to make sure that a VendingItem is returned, balance is reduced by VendingItem price
     */
    // Method assumes checking for valid VendingItem has already happened
    // In this implementation that is done in VendingMachineCLI
    // Dispensing a VendingItem must track the total sales
    //
    public VendingItem dispenseVendingItem(String slotLocation) {
        VendingItem vendingItem = this.slotLocationToVendingItems.get(slotLocation).remove(0);
        String vendingItemName = vendingItem.getItemName();

        // This code increments the total sales of this item by one
        itemNameToQuantitySold.put(vendingItemName, itemNameToQuantitySold.get(vendingItemName) + 1);
        // Keep this total sales number to print to sales log
        this.totalSales += vendingItem.getPrice();

        String initialBalance = formatDoubleAsCurrency(balance);

        this.balance -= vendingItem.getPrice();
        String finalBalance = formatDoubleAsCurrency(this.balance);

        writeToLog(formattedDateAndTimeForLog(), vendingItem.getItemName(), slotLocation, initialBalance, finalBalance);
        return vendingItem;
    }

    //TODO formattedDateAndTimeForLog returns appropriate form for date and time
    private String formattedDateAndTimeForLog() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/uuuu hh:mm:ss a");
        return localDateTime.format(format);
    }

    // Need this helper method to help with printing to hidden save records
    private String formattedDateAndTimeForSalesRecord() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-dd-uuuu-hh-mm-ss-a");
        return localDateTime.format(format);
    }

    //TODO unsure how to test write to log
    private void writeToLog(String timeAndDate,
                            String functionCalled,
                            String initialBalance,
                            String finalBalance) {
        log.println(timeAndDate + SPACE +
                functionCalled + SPACE +
                initialBalance + SPACE +
                finalBalance);
    }

    private void writeToLog(String timeAndDate,
                            String itemName,
                            String slotLocation,
                            String initialBalance,
                            String finalBalance) {
        log.println(timeAndDate + SPACE +
                itemName + SPACE +
                slotLocation + SPACE +
                initialBalance + SPACE +
                finalBalance + SPACE);
    }

    // Writes hidden menu sales record
    public void writeSalesRecordFile() {
        String fileName = "sales-record-" + formattedDateAndTimeForSalesRecord() + ".txt";
        File salesRecord = new File(fileName);

        try (PrintWriter fileWriter = new PrintWriter(salesRecord)) {
            // ArrayList<VendingItem> used to maintain order for printing
            for (VendingItem vendingItem : vendingItemMasterList) {
                String itemName = vendingItem.getItemName();
                String line = itemName + "|" + itemNameToQuantitySold.get(itemName);
                fileWriter.println(line);
            }
            fileWriter.println();
            fileWriter.println(System.lineSeparator() + "Total Sales: " + CURRENCY.format(totalSales));
        } catch (Exception e){
            // Eat Exception
        }
    }

    //TODO: VendingMachine populates map correctly (all items initialized to 5) and fails if file path error
    public boolean loadVendingMachine(String filePath) {
        boolean loadSuccess = false;
        File file = new File(filePath);
        try (Scanner fileReader = new Scanner(file)) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] parts = line.split("\\|");

                String slotLocation = parts[0];
                String itemName = parts[1];
                double price = Double.parseDouble(parts[2]);
                String className = parts[3];
                List<VendingItem> list = makeVendingItems(itemName, price, className, MAX_VENDING_ITEMS_PER_SLOT_LOCATION);
                slotLocationToVendingItems.put(slotLocation, list);


                if (!vendingItemMasterList.contains(list.get(0)))
                    this.vendingItemMasterList.add(list.get(0));
            }
            initializeItemNameToQuantitySold();
            loadSuccess = true;
        } catch (FileNotFoundException e) {
            // eat exception
        }
        return loadSuccess;
    }

    private void initializeItemNameToQuantitySold() {
        for (VendingItem vendingItem : vendingItemMasterList)
            itemNameToQuantitySold.put(vendingItem.getItemName(), 0);
    }

    public boolean createLogFile() {
        boolean fileCreated = false;
        try {
            this.log = new PrintWriter(LOG_PATH_NAME);
            fileCreated = true;
        } catch (Exception e) {
            // Eat exception and return false
        }
        return fileCreated;
    }

    public void closeLogFile() {
        this.log.close();
    }

    // TODO: vendingItem is returned if slot location is not mapped to empty List<VendingItem>
    // Fixed implementation for hidden sales menu so a list of size 1 is considered empty
    public boolean isVendingItemInStock(String slotLocation) {
        return slotLocationToVendingItems.get(slotLocation).size() > 0;
    }

    //TODO isValidSlotLocation returns true if slot Location exists and false otherwise
    public boolean isValidSlotLocation(String slotLocation) {
        return slotLocationToVendingItems.containsKey(slotLocation);
    }

    // TODO makeVendingItems returns a list of six VendingItems
    private List<VendingItem> makeVendingItems(String itemName, double price, String className, int quantity) {
        List<VendingItem> list = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            VendingItem vendingItem = null;
            switch (className) {
                case "Chip":
                    vendingItem = new Chip(itemName, price);
                    break;
                case "Candy":
                    vendingItem = new Candy(itemName, price);
                    break;
                case "Drink":
                    vendingItem = new Drink(itemName, price);
                    break;
                case "Gum":
                    vendingItem = new Gum(itemName, price);
                    break;
            }
            list.add(vendingItem);
        }
        return list;
    }

    public Map<String, List<VendingItem>> getSlotLocationToVendingItems() {
        return slotLocationToVendingItems;
    }

    public double getBalance() {
        return balance;
    }

    // TODO returns appropriate formatting for balance
    public String formatDoubleAsCurrency(double balance) {
        return CURRENCY.format(balance);
    }

    //TODO giveChange() returns correct amount of quarters, dimes, and nickels given a balance
    public String giveChange() {
        String initialBalance = formatDoubleAsCurrency(balance);
        int balanceAsInt = (int) (balance * CENTS_PER_DOLLAR);

        resetBalance();
        String finalBalance = formatDoubleAsCurrency(balance);

        if (balanceAsInt != 0)
            writeToLog(formattedDateAndTimeForLog(), GIVE_CHANGE, initialBalance, finalBalance);

        int quarters = balanceAsInt / CENTS_PER_QUARTER;
        balanceAsInt -= quarters * CENTS_PER_QUARTER;

        int dimes = balanceAsInt / CENTS_PER_DIME;
        balanceAsInt -= dimes * CENTS_PER_DIME;

        // balanceAsInt should always be a multiple of CENTS_PER_NICKEL
        int nickels = balanceAsInt / CENTS_PER_NICKEL;

        // ternary operator (condition ? true : false)
        // inline ternary operator
        return String.format("Change due: %d " + (quarters == 1 ? "quarter" : "quarters") +
                        " %d " + (dimes == 1 ? "dime" : "dimes") +
                        " %d " + (nickels == 1 ? "nickel" : "nickels"),
                quarters, dimes, nickels);
    }

    // TODO resetBalance sets balance to zero
    private void resetBalance() {
        this.balance = 0;
    }

    // Formatting toString() method to print slot location, item name, price, and quantity
    // Does not keep record of what is sold out, only indicates that the slot is SOLD OUT
    // when list is empty
    //TODO toString() returns the appropriate string from map
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String slotLocation : slotLocationToVendingItems.keySet()) {
            int vendingItemQuantity = slotLocationToVendingItems.get(slotLocation).size();
            if (vendingItemQuantity != 0) {
                VendingItem vendingItem = slotLocationToVendingItems.get(slotLocation).get(0);
                String itemName = vendingItem.getItemName();
                double price = vendingItem.getPrice();


                sb.append(SLOT_LOCATION).append(DELIMITER).append(slotLocation).append(SPACE)
                        .append(ITEM_NAME).append(DELIMITER).append(itemName).append(SPACE)
                        .append(PRICE).append(DELIMITER).append(CURRENCY.format(price)).append(SPACE)
                        .append(QUANTITY).append(DELIMITER).append(vendingItemQuantity);
            } else
                sb.append(SOLD_OUT);
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
