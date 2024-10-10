package ui;

import model.MoneySummary;

import java.util.Scanner;

// A money tracker application that allows user to track their cash flows
// by adding it to the list, specifying its detail, editing it, and deleting
// it if needed
public class MoneyTrackerApp {
    private MoneySummary moneySummary;

    private Boolean isProgramRunning;
    private Scanner scanner;

    // EFFECTS: creates an instance of the MoneyTracker application
    public MoneyTrackerApp() {
        initMoneyTrackerApp();

        System.out.println("Welcome to the MoneyTrackerApp");

        while (this.isProgramRunning) {
            handleMainMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes some initial values needed for MoneyTrackerApp
    public void initMoneyTrackerApp() {
        this.moneySummary = new MoneySummary();
        this.scanner = new Scanner(System.in);
        this.isProgramRunning = true;
    }

    // EFFECTS: runs an interactive main menu to help user navigate what
    // they want to do
    public void handleMainMenu() {
        displayMainMenu();
        String input = this.scanner.nextLine();
        processMainMenuCommands(input);
    }

    // EFFECTS: displays the main menu
    public void displayMainMenu() {
        System.out.println("Please select a menu: \n");
        System.out.println("m: Money Tracker menu");
        System.out.println("c: Category menu");
        System.out.println("a: Accounts menu");
        System.out.println("q: Quit the MoneyTracker application");
    }

    // EFFECTS: processes the user's input in the Main Menu
    public void processMainMenuCommands(String input) {
        switch (input) {
            case "m":
                handleCashflowsMenu();
                break;
            case "c":
                handleCategoriesMenu();
                break;
            case "a":
                handleAccountsMenu();
                break;
            case "q":
                quitMoneyTrackerApp();
                break;
            default:
                System.out.println("Invalid option inputted. Please try again!");
        }
    }
    
    // EFFECTS: runs an interactive cashflows menu that allows user to navigate
    // to add, edit, or delete any cash flow
    public void handleCashflowsMenu() {
        displayCashflowsMenu();
        String input = this.scanner.nextLine();
        processCashflowsMenuCommands(input);
    }

    // EFFECTS: displays the Money Tracker menu
    public void displayCashflowsMenu() {
        System.out.println("Please select an option: \n");
        System.out.println("a: Add a cash flow");
        System.out.println("e: Edit a cash flow");
        System.out.println("r: Remove a cash flow");
        System.out.println("q: Quit the MoneyTracker application");
    }

    // EFFECTS: processes the input of user in the Money Tracker menu
    public void processCashflowsMenuCommands(String input) {
        switch (input) {
            case "a":
                // TODO
                break;
            case "e":
                // TODO
                break;
            case "r":
                // TODO
                break;
            case "q":
                quitMoneyTrackerApp();
                break;
            default:
                System.out.println("Invalid option inputted. Please try again!");
                handleCashflowsMenu();
        }
    }

    // EFFECTS: runs an interactive categories menu that allows user to navigate
    // to debitCategories menu or creditCategories menu
    public void handleCategoriesMenu() {
        displayCategoriesMenu();
        String input = this.scanner.nextLine();
        processCategoriesMenuCommands(input);
    }

    // EFFECTS: displays the Categories menu
    public void displayCategoriesMenu() {
        System.out.println("Please select which category you want to visit: \n");
        System.out.println("c: Credit category");
        System.out.println("d: Debit category");
        System.out.println("q: Quit the MoneyTracker application");
    }

    // EFFECTS: processes the user input in Categories menu
    public void processCategoriesMenuCommands(String input) {
        switch (input) {
            case "c":
                handleCreditCategoriesMenu();
                break;
            case "d":
                handleDebitCategoriesMenu();
                break;
            case "q":
                quitMoneyTrackerApp();
                break;
            default:
                System.out.println("Invalid option inputted. Please Try Again!");
                handleCategoriesMenu();
        }
    }

    // EFFECTS: runs an interactive debitCategories menu that allows user to add
    // or delete any debit category and view a list of it
    public void handleDebitCategoriesMenu() {
        displayDebitCategoriesMenu();
        String input = this.scanner.nextLine();
        processDebitCategoriesMenuCommands(input);
    }

    // EFFECTS: displays Debit category menu
    public void displayDebitCategoriesMenu() {
        System.out.println("Please select an option:");
        System.out.println("a: Add a Debit category");
        System.out.println("d: Delete a Debit category");
        System.out.println("v: View all Debit categories");
        System.out.println("q: Quit the MoneyTracker application");
    }

    // EFFECTS: processes the user's input in Debit category menu
    public void processDebitCategoriesMenuCommands(String input) {
        switch (input) {
            case "a":
                // TODO
                break;
            case "d":
                // TODO
                break;
            case "v":
                // TODO
                break;
            case "q":
                quitMoneyTrackerApp();
                break;
            default:
                System.out.println("Invalid option inputted. Please try again!");
        }
    }

    // EFFECTS: runs an interactive creditCategories menu that allows user to add
    // or delete any credit category and view a list of it
    public void handleCreditCategoriesMenu() {
        displayCreditCategoriesMenu();
        String input = this.scanner.nextLine();
        processCreditCategoriesMenuCommands(input);
    }

    // EFFECTS: displays Credit category menu
    public void displayCreditCategoriesMenu() {
        System.out.println("Please select an option:");
        System.out.println("a: Add a Credit category");
        System.out.println("d: Delete a Credit category");
        System.out.println("v: View all Credit categories");
        System.out.println("q: Quit the MoneyTracker application");
    }

    // EFFECTS: processes the user's input in Credit category menu
    public void processCreditCategoriesMenuCommands(String input) {
        switch (input) {
            case "a":
                // TODO
                break;
            case "d":
                // TODO
                break;
            case "v":
                // TODO
                break;
            case "q":
                quitMoneyTrackerApp();
                break;
            default:
                System.out.println("Invalid option inputted. Please try again!");
        }
    }

    // EFFECTS: runs an interactive accounts menu that allows user to add or
    // or delete any account and view a list of it
    public void handleAccountsMenu() {
        displayAccountsMenu();
        String input = this.scanner.nextLine();
        processAccountsMenuCommands(input);
    }

    // EFFECTS: displays Accounts menu
    public void displayAccountsMenu() {
        System.out.println("Please select an option:");
        System.out.println("a: Add an account");
        System.out.println("d: Delete an account");
        System.out.println("l: view the list of accounts");
        System.out.println("v: View an account balance");
        System.out.println("q: Quit the MoneyTracker application");
    }

    // EFFECTS: processes the user's input in Credit category menu
    public void processAccountsMenuCommands(String input) {
        switch (input) {
            case "a":
                // TODO
                break;
            case "d":
                // TODO
                break;
            case "l":
                // TODO
                break;
            case "v":
                // TODO
                break;
            case "q":
                quitMoneyTrackerApp();
                break;
            default:
                System.out.println("Invalid option inputted. Please try again!");
        }
    }

    // MODIFIES: this
    // EFFECTS: marks the application as stopped and prints some closing
    // messages
    public void quitMoneyTrackerApp() {
        this.isProgramRunning = false;
    }
}
