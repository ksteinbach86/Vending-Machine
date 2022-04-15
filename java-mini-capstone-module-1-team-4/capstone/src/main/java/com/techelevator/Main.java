package com.techelevator;

import com.techelevator.controller.VendingMachineCLI;
import com.techelevator.model.VendingMachine;
import com.techelevator.view.Menu;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu(System.in, System.out);
        VendingMachine vendingMachine = new VendingMachine();
        VendingMachineCLI cli = new VendingMachineCLI(menu, vendingMachine);
        cli.run();
    }
}
