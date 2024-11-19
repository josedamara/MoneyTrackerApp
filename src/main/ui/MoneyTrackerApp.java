package ui;

import model.CashFlow;
import model.MoneySummary;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.util.*;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

// A money tracker application that allows user to track their cash flows
// by adding it to the list, specifying its detail, editing it, and deleting
// it if needed
public class MoneyTrackerApp extends JFrame {
    private static final String JSON_FILE = "./data/moneysummary.json";
    private MoneySummary moneySummary;

    private Boolean isProgramRunning;
    private Scanner scanner;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JFrame window;
    private ImagePanel ip;

    private JPanel loadMenu = new JPanel();
    private JPanel mainMenu = new JPanel();

    // EFFECTS: creates an instance of the MoneyTracker application
    public MoneyTrackerApp() {
        initMoneyTrackerApp();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initFrame();
            }
        });

        System.out.println("Welcome to the MoneyTrackerApp");

        handleLoadMenu();

        while (this.isProgramRunning) {
            handleMainMenu();
            sortByTime();
            sortByDate();
        }

        handleSaveMenu();
    }

    // MODIFIES: this
    // EFFECTS: initializes some initial values needed for MoneyTrackerApp
    public void initMoneyTrackerApp() {
        this.moneySummary = new MoneySummary();
        moneySummary.addInitialDebitCategories();
        moneySummary.addInitialCreditCategories();
        this.scanner = new Scanner(System.in);
        this.isProgramRunning = true;
        jsonWriter = new JsonWriter(JSON_FILE);
        jsonReader = new JsonReader(JSON_FILE);
    }

    // EFFECTS: creates a JFrame as the base of the MoneyTrackerApp
    public void initFrame() {
        int frameWidth = 800;
        int frameHeight = 600;
        int imagePanelWidth = frameWidth;
        int imagePanelHeight = 280;

        window = new JFrame();
        window.setTitle("MoneyTrackerApp");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(frameWidth, frameHeight);
        window.getContentPane().setBackground(Color.GRAY);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        ip = new ImagePanel();
        ip.initImagePanel(imagePanelWidth, imagePanelHeight);
        ip.setBackground(Color.LIGHT_GRAY);
        window.add(ip, BorderLayout.NORTH);

        setLoadMenu();

    }

    // EFFECTS: helper method to ensure the button sizing properly
    private void fixWindow() {
        window.pack();
        window.revalidate();
        window.repaint();
    }

    // EFFECTS: creates buttons for load option
    public void setLoadMenu() {
        loadMenu.setLayout(new GridLayout(1, 1));

        JButton loadButton = new JButton(new LoadDataAction());
        loadButton.setPreferredSize(new Dimension(20, 300));

        JButton notLoadButton = new JButton(new NotLoadDataAction());
        notLoadButton.setPreferredSize(new Dimension(20, 300));

        loadMenu.add(loadButton);
        loadMenu.add(notLoadButton);

        window.add(loadMenu, BorderLayout.SOUTH);

        fixWindow();
    }

    // EFFECTS: creates buttons for load option
    public void setMainMenu() {
        mainMenu.setLayout(new GridLayout(3, 3));

        JButton moneyTrackerMenuButton = new JButton(new MoneyTrackerMenuAction());
        moneyTrackerMenuButton.setPreferredSize(new Dimension(20, 100));

        JButton categoryMenuButton = new JButton(new CategoryMenuAction());
        categoryMenuButton.setPreferredSize(new Dimension(20, 100));

        JButton accountMenuButton = new JButton(new AccountMenuAction());
        accountMenuButton.setPreferredSize(new Dimension(20, 100));

        JButton saveMenuButton = new JButton(new SaveMenuAction());
        saveMenuButton.setPreferredSize(new Dimension(20, 100));

        JButton loadMenuButton = new JButton(new LoadMenuAction());
        loadMenuButton.setPreferredSize(new Dimension(20, 100));

        JButton quitButton = new JButton(new QuitAction());
        quitButton.setPreferredSize(new Dimension(20, 100));

        mainMenu.add(moneyTrackerMenuButton);
        mainMenu.add(categoryMenuButton);
        mainMenu.add(accountMenuButton);
        mainMenu.add(saveMenuButton);
        mainMenu.add(loadMenuButton);
        mainMenu.add(quitButton);

        window.add(mainMenu, BorderLayout.SOUTH);

        fixWindow();
    }

    // EFFECTS: runs an interactive menu with options to load data from file or not
    private void handleLoadMenu() {
        displayLoadMenu();
        System.out.println("");
        String input = this.scanner.nextLine();
        processLoadMenuCommands(input);
    }

    // EFFECTS: runs an interactive menu with options to save data to file or not
    private void handleSaveMenu() {
        displaySaveMenu();
        System.out.println("");
        String input = this.scanner.nextLine();
        processSaveMenuCommands(input);
    }

    // EFFECTS: displays options for load data from file or not
    private void displayLoadMenu() {
        spaceSeparator();
        System.out.println("Please select an option:\n");
        System.out.println("l: load data");
        System.out.println("n: do not load data");
    }

    // EFFECTS: processes user's input to load data or not
    private void processLoadMenuCommands(String input) {
        switch (input) {
            case "l":
                loadData();
                break;
            case "n":
                break;
        }
    }

    // TODO
    private class LoadDataAction extends AbstractAction {
        LoadDataAction() {
            super("Load Data");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            loadData();
            loadMenu.setVisible(false);
            setMainMenu();
        }
    }

    // TODO
    private class NotLoadDataAction extends AbstractAction {
        NotLoadDataAction() {
            super("Do Not Load Data");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            loadMenu.setVisible(false);
            setMainMenu();
        }
    }

    // TODO
    private class MoneyTrackerMenuAction extends AbstractAction {
        MoneyTrackerMenuAction() {
            super("Money Tracker Menu");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            mainMenu.setVisible(false);
            setMainMenu();
        }
    }

    // TODO
    private class CategoryMenuAction extends AbstractAction {
        CategoryMenuAction() {
            super("Category Menu");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            mainMenu.setVisible(false);
            setMainMenu();
        }
    }

    // TODO
    private class AccountMenuAction extends AbstractAction {
        AccountMenuAction() {
            super("Account Menu");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            mainMenu.setVisible(false);
            setMainMenu();
        }
    }

    // TODO
    private class SaveMenuAction extends AbstractAction {
        SaveMenuAction() {
            super("Save Menu");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            mainMenu.setVisible(false);
            setMainMenu();
        }
    }

    // TODO
    private class LoadMenuAction extends AbstractAction {
        LoadMenuAction() {
            super("Load Menu");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            mainMenu.setVisible(false);
            setMainMenu();
        }
    }

    // TODO
    private class QuitAction extends AbstractAction {
        QuitAction() {
            super("Quit MoneyTrackerApp");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            mainMenu.setVisible(false);
            setMainMenu();
        }
    }

    // EFFECTS: loads moneysummary to the file
    private void loadData() {
        try {
            moneySummary = jsonReader.read();
            System.out.println("Loaded " + moneySummary.getName() +  " from " + JSON_FILE);
        } catch (IOException e) {
            System.out.println("Unable to read from file " + JSON_FILE);
        }
    }

    // EFFECTS: displays options for save data to file or not
    private void displaySaveMenu() {
        spaceSeparator();
        System.out.println("Please select an option:\n");
        System.out.println("s: save data");
        System.out.println("n: do not save data");
    }

    // EFFECTS: processes user's input to load data or not
    private void processSaveMenuCommands(String input) {
        switch (input) {
            case "s":
                saveData();
                break;
            case "n":
                break;
        }
    }
    
    // EFFECTS: saves moneysummary to the file
    private void saveData() {
        try {
            jsonWriter.open();
            jsonWriter.write(moneySummary);
            jsonWriter.close();
            System.out.println("Saved " + moneySummary.getName() +  " to " + JSON_FILE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file " + JSON_FILE);
        }
    }

    // MODIFIES: this
    // EFFECTS: sorts the CashFlows based on the time
    public void sortByTime() {
        Collections.sort(this.moneySummary.getCashflows(), new Comparator<CashFlow>() {
            public int compare(CashFlow cf1, CashFlow cf2) {
                return cf1.getTime().compareTo(cf2.getTime());
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: sorts the CashFlows based on the date
    public void sortByDate() {
        Collections.sort(this.moneySummary.getCashflows(), new Comparator<CashFlow>() {
            public int compare(CashFlow cf1, CashFlow cf2) {
                return cf1.getDate().compareTo(cf2.getDate());
            }
        });
    }

    // EFFECTS: runs an interactive main menu to help user navigate what
    // they want to do
    public void handleMainMenu() {
        displayMainMenu();
        System.out.println("");
        String input = this.scanner.nextLine();
        processMainMenuCommands(input);
    }

    // EFFECTS: displays the main menu
    public void displayMainMenu() {
        spaceSeparator();
        System.out.println("Please select a menu: \n");
        System.out.println("m: Money Tracker menu");
        System.out.println("c: Category menu");
        System.out.println("a: Accounts menu");
        System.out.println("s: Save menu");
        System.out.println("l: Load menu");
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
            case "s":
                handleSaveMenu();
                break;
            case "l":
                handleLoadMenu();
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
        System.out.println("");
        String input = this.scanner.nextLine();
        processCashflowsMenuCommands(input);
    }

    // EFFECTS: displays the Money Tracker menu
    public void displayCashflowsMenu() {
        spaceSeparator();
        System.out.println("Please select an option: \n");
        System.out.println("a: Add a cash flow");
        System.out.println("e: Edit a cash flow");
        System.out.println("r: Remove a cash flow");
        System.out.println("v: View cash flows");
        System.out.println("b: Back to main menu");
        System.out.println("q: Quit the MoneyTracker application");
    }

    // EFFECTS: processes the input of user in the Money Tracker menu
    public void processCashflowsMenuCommands(String input) {
        switch (input) {
            case "a":
                addNewCashFlow();
                break;
            case "e":
                handleEditCashFlow();
                break;
            case "r":
                handleRemoveCashFlow();
                break;
            case "v":
                viewCashFlow();
                break;
            case "b":
                handleMainMenu();
                break;
            case "q":
                quitMoneyTrackerApp();
                break;
            default:
                System.out.println("Invalid option inputted. Please try again!");
                handleCashflowsMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: add a CashFlow to the cashflows
    public void addNewCashFlow() {
        if (isReadyToAddCashFlow()) {
            spaceSeparator();
            String status = checkStatus();
            spaceSeparator();
            String account = checkAccount();
            spaceSeparator();
            String category = checkCategory(status);
            spaceSeparator();
            String date = checkDate();
            spaceSeparator();
            String time = checkTime();
            spaceSeparator();
            String description = checkDescription();
            spaceSeparator();
            double amount = checkAmount();
            CashFlow cf = new CashFlow(status, account, category, date, time, description, amount);
            this.moneySummary.addCashFlow(cf);
        } else if (isAccountsEmpty()) {
            System.out.println("Please add an account first");
        } else {
            System.out.println("Please add a debit and credit category first! \n");
        }
    }

    // EFFECTS: returns true if accounts, debitCategories, and creditCategories are not empty
    public boolean isReadyToAddCashFlow() {
        boolean condOne = this.moneySummary.getAccounts().size() != 0;
        boolean condTwo = this.moneySummary.getCreditCategories().size() != 0;
        boolean condThree = this.moneySummary.getDebitCategories().size() != 0;
        return condOne && condTwo && condThree;
    }

    // EFFECTS: returns true if accounts is empty
    public boolean isAccountsEmpty() {
        return this.moneySummary.getAccounts().size() == 0;
    }

    // EFFECTS: check user's status input is a valid status or not
    public String checkStatus() {
        System.out.println("Please specify the status: (credit/debit)");
        System.out.println("q: Quit the MoneyTracker application \n");
        String status = this.scanner.nextLine();

        if (status.equals("q")) {
            System.exit(0);
        } else if (isValidStatus(status)) {
            return status;
        } else {
            return status = checkStatus();
        }

        return status;
    }

    // EFFECTS: returns true if inputted status is either credit or debit
    public boolean isValidStatus(String status) {
        return (status.equals("credit") || status.equals("debit"));
    }

    // EFFECTS: check user's account input is a valid account or not
    public String checkAccount() {
        System.out.println("Please type the account name:");
        displayAccountList();
        System.out.println("q: Quit the MoneyTracker application \n");
        String account = this.scanner.nextLine();

        if (account.equals("q")) {
            System.exit(0);
        } else if (isValidAccount(account)) {
            return account;
        } else {
            account = checkAccount();
        }

        return account;
    }

    // EFFECTS: displays the list of registered accounts
    public void displayAccountList() {
        List<String> accounts = this.moneySummary.getAccounts();
        String accountList = "";
        for (int i = 0; i < accounts.size(); i++) {
            accountList += Integer.toString(i + 1) + ". " + accounts.get(i);
            if (i != accounts.size() - 1) {
                accountList += "\n";
            }
        }
        System.out.println(accountList);
    }

    // EFFECTS: returns true if inputted account exists in accounts
    public boolean isValidAccount(String account) {
        return this.moneySummary.getAccounts().indexOf(account) != -1;
    }

    // EFFECTS: check if user's category input is listed in its category
    // (debit/credit)
    public String checkCategory(String status) {
        System.out.println("Please type the category name:");
        displayCategoryList(status);
        System.out.println("q: Quit the MoneyTracker application \n");
        String category = this.scanner.nextLine();

        if (category.equals("q")) {
            System.exit(0);
        } else if (status.equals("credit")) {
            if (isValidCreditCategory(category)) {
                return category;
            }
        } else if (status.equals("debit")) {
            if (isValidDebitCategory(category)) {
                return category;
            }
        }
        
        return category = checkCategory(status);
    }

    // EFFECTS: output the list of category based on selected status
    public void displayCategoryList(String status) {
        if (status.equals("credit")) {
            viewCreditCategory();
        } else {
            viewDebitCategory();
        }
    }

    // EFFECTS: returns true if inputted credit category exists in creditCategories
    public boolean isValidCreditCategory(String category) {
        return this.moneySummary.getCreditCategories().indexOf(category) != -1;
    }

    // EFFECTS: returns true if inputted debit category exists in debitCategories
    public boolean isValidDebitCategory(String category) {
        return this.moneySummary.getDebitCategories().indexOf(category) != -1;
    }

    // EFFECTS: check if user's date input is a valid date or not
    public String checkDate() {
        System.out.println("Please specify the date: (YYYY/MM/DD)");
        System.out.println("q: Quit the MoneyTracker application \n");
        String date = this.scanner.nextLine();
        String format = "yyyy/MM/dd";

        if (date.equals("q")) {
            System.exit(0);
        } else if (isValidDate(date, format)) {
            return date;
        } else {
            date = checkDate();
        }

        return date;
    }

    // EFFECTS: returns true if it is a valid date
    public boolean isValidDate(String date, String format) {
        DateTimeFormatter template = DateTimeFormatter.ofPattern(format);

        try {
            LocalDate.parse(date, template);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    // EFFECTS: check if user's time input is a valid time or not
    public String checkTime() {
        System.out.println("Please specify the time: (HH:MM)");
        System.out.println("q: Quit the MoneyTracker application \n");
        String time = this.scanner.nextLine();


        if (time.equals("q")) {
            System.exit(0);
        } else if (isValidTime(time)) {
            return time;
        } else {
            time = checkTime();
        }

        return time;
    }

    // EFFECTS: returns true if it is a valid time
    public boolean isValidTime(String time) {
        String timeRegex = "([0-1][0-9]|2[0-3]):([0-5][0-9])";

        return time.matches(timeRegex);
    }


    // EFFECTS: check user's input for description
    public String checkDescription() {
        System.out.println("Please enter the description:");
        System.out.println("q: Quit the MoneyTracker application \n");
        String description = this.scanner.nextLine();

        if (description.equals("q")) {
            System.exit(0);
        } else {
            return description;
        }

        return description;
    }

    // EFFECTS: check if user's time input is a valid time or not
    public double checkAmount() {
        System.out.println("Please specify the amount of money");
        System.out.println("q: Quit the MoneyTracker application \n");
        String amount = this.scanner.nextLine();

        if (isValidAmount(amount)) {
            double numAmount = Double.parseDouble(amount);

            if (amount.equals("q")) {
                System.exit(0);
            } else if (numAmount > 0.0) {
                return numAmount;
            } else {
                numAmount = checkAmount();
            }

            return numAmount;
        } else {
            return checkAmount();
        }
    }

    // EFFECTS: returns true if user's amount input is valid
    public boolean isValidAmount(String amount) {
        try {
            Double.parseDouble(amount);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // EFFECTS: runs an interactive options to find and edit a registered
    // cash flow
    public void handleEditCashFlow() {
        displayFindCashFlow();
        System.out.println("");
        String input = this.scanner.nextLine();
        processEditCashFlow(input);
    }

    // EFFECTS: displays option for finding a cash flow to edit
    public void displayFindCashFlow() {
        spaceSeparator();
        System.out.println("How would you like to find the cash flow? \n");
        System.out.println("s: Find by status");
        System.out.println("a: Find by account");
        System.out.println("c: Find by category");
        System.out.println("d: Find by date");
        System.out.println("m: Back to main menu");
        System.out.println("q: Quit the MoneyTracker application");
    }

    // EFFECTS: processses user's input in handleEditCashFlow
    public void processEditCashFlow(String input) {
        switch (input) {
            case "s":
                editCashFlowByStatus();
                break;
            case "a":
                editCashFlowByAccount();
                break;
            case "c":
                editCashFlowByCategory();
                break;
            case "d":
                editCashFlowByDate();
                break;
            case "m":
                handleMainMenu();
                break;
            case "q":
                quitMoneyTrackerApp();
                break;
            default:
                System.out.println("Invalid option inputted. Please try again!");
                handleEditCashFlow();
        }
    }

    // MODIFIES this
    // EFFECTS: find a CashFlow based on status inputted by the user
    public void editCashFlowByStatus() {
        spaceSeparator();
        System.out.println("Please specify which status to be filtered \n");
        String status = this.scanner.nextLine();
        List<CashFlow> newCashFlowList = this.moneySummary.filterStatus(status);

        if (newCashFlowList.size() > 0) {
            CashFlow foundCashFlow = pickCashFlow(newCashFlowList);
            editCashFlow(foundCashFlow);
        } else {
            System.out.println("No cash flow found. Please try again!");
            handleEditCashFlow();
        }
    } 

    // MODIFIES: this
    // EFFECTS: find a CashFlow based on account inputted by the user
    public void editCashFlowByAccount() {
        spaceSeparator();
        System.out.println("Please specify which account to be filtered");
        String account = this.scanner.nextLine();
        List<CashFlow> newCashFlowList = this.moneySummary.filterAccount(account);

        if (newCashFlowList.size() > 0) {
            CashFlow foundCashFlow = pickCashFlow(newCashFlowList);
            editCashFlow(foundCashFlow);
        } else {
            System.out.println("No cash flow found. Please try again!");
            handleEditCashFlow();
        }
    }

    // MODIFIES: this
    // EFFECTS: find a CashFlow based on category inputted by the user
    public void editCashFlowByCategory() {
        spaceSeparator();
        System.out.println("Please specify which category to be filtered");
        String category = this.scanner.nextLine();
        List<CashFlow> cashFlowList = this.moneySummary.getCashflows();
        List<CashFlow> newCashFlowList = new ArrayList<>();

        for (int i = 0; i < cashFlowList.size(); i++) {
            if (cashFlowList.get(i).getCategory().equals(category)) {
                newCashFlowList.add(cashFlowList.get(i));
            }
        }

        if (newCashFlowList.size() > 0) {
            CashFlow foundCashFlow = pickCashFlow(newCashFlowList);
            editCashFlow(foundCashFlow);
        } else {
            System.out.println("No cash flow found. Please try again!");
            handleEditCashFlow();
        }
    }

    // MODIFIES: this
    // EFFECTS: find a CashFlow based on date inputted by the user
    public void editCashFlowByDate() {
        spaceSeparator();
        System.out.println("Please specify which account to be filtered \n");
        displayAccountList();
        String date = this.scanner.nextLine();
        List<CashFlow> cashFlowList = this.moneySummary.getCashflows();
        List<CashFlow> newCashFlowList = new ArrayList<>();

        for (int i = 0; i < cashFlowList.size(); i++) {
            if (cashFlowList.get(i).getDate().equals(date)) {
                newCashFlowList.add(cashFlowList.get(i));
            }
        }

        if (newCashFlowList.size() > 0) {
            CashFlow foundCashFlow = pickCashFlow(newCashFlowList);
            editCashFlow(foundCashFlow);
        } else {
            System.out.println("No cash flow found. Please try again!");
            handleEditCashFlow();
        }
    }

    // EFFECTS: helper method to pick a CashFlow
    public CashFlow pickCashFlow(List<CashFlow> newCashFlowList) {
        spaceSeparator();
        displayCashFlow(newCashFlowList);
        System.out.println("Please specify the number of the cash flow \n");
        int index = this.scanner.nextInt();
        scanner.nextLine();
        CashFlow foundCashFlow = newCashFlowList.get(index - 1);
        return foundCashFlow;
    }

    // MODIFIES: cf
    // EFFECTS: edits a selected CashFlow
    public void editCashFlow(CashFlow cf) {
        displayEditSpecifiedCashFlowOne();
        System.out.println("");
        String input = this.scanner.nextLine();
        processEditSpecifiedCashFlowOne(input, cf);
        processEditSpecifiedCashFlowTwo(input, cf);
    }

    // EFFECTS: displays handle for edit a specified CashFlow (part one)
    public void displayEditSpecifiedCashFlowOne() {
        spaceSeparator();
        System.out.println("What do you want to change? \n");
        System.out.println("s: Status");
        System.out.println("a: Account");
        System.out.println("c: Category");
        System.out.println("d: Date");
        System.out.println("t: Time");
        System.out.println("x: Description");
        System.out.println("m: Amount");
        System.out.println("q: Quit the MoneyTracker application");
    }

    // EFFECTS: processes user's input in editCashFlow (part one)
    public void processEditSpecifiedCashFlowOne(String input, CashFlow cf) {
        switch (input) {
            case "s":
                editStatus(cf);
                break;
            case "a":
                editAccount(cf);
                break;
            case "c":
                editCategory(cf);
                break;
            case "d":
                editDate(cf);
                break;
        }
    }

    // EFFECTS: processes user's input in editCashFlow (part two)
    public void processEditSpecifiedCashFlowTwo(String input, CashFlow cf) {
        switch (input) {
            case "t":
                editTime(cf);
                break;
            case "x":
                editDescription(cf);
                break;
            case "m":
                editAmount(cf);
                break;
            case "q":
                quitMoneyTrackerApp();
                break;
        }
    }

    // MODIFIES: cf
    // EFFECTS: changes the status of the CashFlow object
    public void editStatus(CashFlow cf) {
        spaceSeparator();
        System.out.println("Please specify the new status \n");
        String input = this.scanner.nextLine();
        if (input.equals("credit")) {
            cf.setAsCredit();
            System.out.println("\nThe cash flow has been edited");
        } else if (input.equals("debit")) {
            cf.setAsDebit();
            System.out.println("\nThe cash flow has been edited");
        } else {
            editStatus(cf);
        }
    }

    // MODIFIES: cf
    // EFFECTS: changes the account of the CashFlow object
    public void editAccount(CashFlow cf) {
        spaceSeparator();
        System.out.println("Please specify the new account");
        displayAccountList();
        String account = this.scanner.nextLine();
        if (isValidAccount(account)) {
            cf.setAccount(account);
            System.out.println("\nThe cash flow has been edited");
        } else {
            editAccount(cf);
        }
    }

    // MODIFIES: cf
    // EFFECTS: changes the category of the CashFlow object
    public void editCategory(CashFlow cf) {
        spaceSeparator();
        System.out.println("Please specify the new category");
        String status = cf.getStatus();
        displayCategoryList(status);
        String category = this.scanner.nextLine();

        if (category.equals("q")) {
            System.exit(0);
        } else if (status.equals("credit")) {
            if (isValidCreditCategory(category)) {
                cf.setCategory(category);
                System.out.println("\nThe cash flow has been edited");
            }
        } else if (status.equals("debit")) {
            if (isValidDebitCategory(category)) {
                cf.setCategory(category);
                System.out.println("\nThe cash flow has been edited");
            }
        } else {
            editCategory(cf);
        }
    }

    // MODIFIES: cf
    // EFFECTS: changes the date of the CashFlow object
    public void editDate(CashFlow cf) {
        spaceSeparator();
        System.out.println("Please specify the new date \n");
        String date = this.scanner.nextLine();
        String format = "yyyy/MM/dd";

        if (isValidDate(date, format)) {
            cf.setDate(date);
            System.out.println("\nThe cash flow has been edited");
        } else {
            editDate(cf);
        }
    }

    // MODIFIES: cf
    // EFFECTS: changes the time of the CashFlow object
    public void editTime(CashFlow cf) {
        spaceSeparator();
        System.out.println("Please specify the new time \n");
        String time = this.scanner.nextLine();
        if (isValidTime(time)) {
            cf.setTime(time);
            System.out.println("\nThe cash flow has been edited");
        } else {
            editTime(cf);
        }
    }

    // MODIFIES: cf
    // EFFECTS: changes the category of the CashFlow object
    public void editDescription(CashFlow cf) {
        spaceSeparator();
        System.out.println("Please specify the new category \n");
        String description = this.scanner.nextLine();
        cf.setDescription(description);
        System.out.println("\nThe cash flow has been edited");
    }

    // MODIFIES: cf
    // EFFECTS: changes the amount of the CashFlow object
    public void editAmount(CashFlow cf) {
        spaceSeparator();
        System.out.println("Please specify the new amount \n");
        String amount = this.scanner.nextLine();
        if (isValidAmount(amount)) {
            double numAmount = Double.parseDouble(amount);
            
            cf.setAmount(numAmount);
            System.out.println("\nThe cash flow has been edited");
        } else {
            editAmount(cf);
        }
    }

    // EFFECTS: runs an interactive options to find and remove a registered
    // cash flow
    public void handleRemoveCashFlow() {
        displayFindCashFlow();
        System.out.println("");
        String input = this.scanner.nextLine();
        processRemoveCashFlow(input);
    }

    // EFFECTS: processes user's input in handleRemoveCashFlow
    public void processRemoveCashFlow(String input) {
        switch (input) {
            case "s":
                removeCashFlowByStatus();
                break;
            case "a":
                removeCashFlowByAccount();
                break;
            case "c":
                removeCashFlowByCategory();
                break;
            case "d":
                removeCashFlowByDate();
                break;
            case "m":
                handleMainMenu();
                break;
            case "q":
                quitMoneyTrackerApp();
                break;
            default:
                System.out.println("Invalid option inputted. Please try again!");
                handleEditCashFlow();
        }
    }

    // MODIFIES: this
    // EFFECTS: find a CashFlow to be removed based on status inputted by the user
    public void removeCashFlowByStatus() {
        spaceSeparator();
        System.out.println("Please specify which status to be filtered \n");
        String status = this.scanner.nextLine();
        List<CashFlow> cashFlowList = this.moneySummary.getCashflows();
        List<CashFlow> newCashFlowList = new ArrayList<>();

        for (int i = 0; i < cashFlowList.size(); i++) {
            if (cashFlowList.get(i).getStatus().equals(status)) {
                newCashFlowList.add(cashFlowList.get(i));
            }
        }

        if (newCashFlowList.size() > 0) {
            CashFlow foundCashFlow = pickCashFlow(newCashFlowList);
            removeCashFlow(foundCashFlow);
        } else {
            System.out.println("No cash flow found. Please try again!");
            handleEditCashFlow();
        }
    }

    // MODIFIES: this
    // EFFECTS: find a CashFlow to be removed based on account inputted by the user
    public void removeCashFlowByAccount() {
        spaceSeparator();
        System.out.println("Please specify which account to be filtered \n");
        displayAccountList();
        String account = this.scanner.nextLine();
        List<CashFlow> cashFlowList = this.moneySummary.getCashflows();
        List<CashFlow> newCashFlowList = new ArrayList<>();

        for (int i = 0; i < cashFlowList.size(); i++) {
            if (cashFlowList.get(i).getAccount().equals(account)) {
                newCashFlowList.add(cashFlowList.get(i));
            }
        }

        if (newCashFlowList.size() > 0) {
            CashFlow foundCashFlow = pickCashFlow(newCashFlowList);
            removeCashFlow(foundCashFlow);
        } else {
            System.out.println("No cash flow found. Please try again!");
            handleEditCashFlow();
        }
    }

    // MODIFIES: this
    // EFFECTS: find a CashFlow to be removed based on category inputted by the user
    public void removeCashFlowByCategory() {
        spaceSeparator();
        System.out.println("Please specify which category to be filtered \n");
        String category = this.scanner.nextLine();
        List<CashFlow> cashFlowList = this.moneySummary.getCashflows();
        List<CashFlow> newCashFlowList = new ArrayList<>();

        for (int i = 0; i < cashFlowList.size(); i++) {
            if (cashFlowList.get(i).getCategory().equals(category)) {
                newCashFlowList.add(cashFlowList.get(i));
            }
        }

        if (newCashFlowList.size() > 0) {
            CashFlow foundCashFlow = pickCashFlow(newCashFlowList);
            removeCashFlow(foundCashFlow);
        } else {
            System.out.println("No cash flow found. Please try again!");
            handleEditCashFlow();
        }
    }

    // MODIFIES: this
    // EFFECTS: find a CashFlow to be removed based on date inputted by the user
    public void removeCashFlowByDate() {
        spaceSeparator();
        System.out.println("Please specify which date to be filtered \n");
        String date = this.scanner.nextLine();
        List<CashFlow> cashFlowList = this.moneySummary.getCashflows();
        List<CashFlow> newCashFlowList = new ArrayList<>();

        for (int i = 0; i < cashFlowList.size(); i++) {
            if (cashFlowList.get(i).getDate().equals(date)) {
                newCashFlowList.add(cashFlowList.get(i));
            }
        }

        if (newCashFlowList.size() > 0) {
            CashFlow foundCashFlow = pickCashFlow(newCashFlowList);
            removeCashFlow(foundCashFlow);
        } else {
            System.out.println("No cash flow found. Please try again!");
            handleEditCashFlow();
        }
    }

    // MODIFIES: this
    // EFFECTS: removes a selected CashFlow
    public void removeCashFlow(CashFlow cf) {
        this.moneySummary.getCashflows().remove(cf);
        System.out.println("");
        System.out.println("The cash flow has been removed");
    }
    
    // EFFECTS: gives options to view all cashflows or monthly cashflows
    public void viewCashFlow() {
        displayViewCashFlow();
        System.out.println("");
        String input = this.scanner.nextLine();
        processViewCashflow(input);
    }
    
    // EFFECTS: displays the menu of viewCashFlow
    public void displayViewCashFlow() {
        spaceSeparator();
        System.out.println("Please select an option: \n");
        System.out.println("a: View all cash flow");
        System.out.println("m: View month-specific cash flow");
    }

    // EFFECTS: processes the input of user in the Money Tracker menu
    public void processViewCashflow(String input) {
        switch (input) {
            case "a":
                viewAllCashFlow();
                break;
            case "m":
                viewMonthlyCashFlow();
                break;
            default:
                System.out.println("Invalid option inputted. Please try again!");
                viewCashFlow();
        }
    }

    // EFFECTS: displays all registered cash flows
    public void viewAllCashFlow() {
        spaceSeparator();
        displayCashFlow(this.moneySummary.getCashflows());
    }

    // EFFECTS: displays cash flows based on specified year and month
    public void viewMonthlyCashFlow() {
        spaceSeparator();
        System.out.println("Please type the year:");
        String year = this.scanner.nextLine();
        System.out.println("Please type the month");
        String month = this.scanner.nextLine();
        System.out.println();
        if (isValidYear(year) && isValidMonth(month)) {
            List<CashFlow> cashflows = this.moneySummary.getCashflows();
            List<CashFlow> filtered = new ArrayList<>();
        
            for (int i = 0; i < cashflows.size(); i++) {
                if (cashflows.get(i).getDate().substring(0,4).equals(year) 
                        && cashflows.get(i).getDate().substring(5,7).equals(month)) {
                    filtered.add(cashflows.get(i));
                }
            }

            displayCashFlow(filtered);
        } else {
            viewMonthlyCashFlow();
        }

    }

    // EFFECTS: returns true if it is a valid year
    public boolean isValidYear(String year) {
        String yearForm = "(19[0-9][0-9]|20[0-9][0-9]|2100)";

        return year.matches(yearForm);
    }

    // EFFECTS: returns true if it is a valid month
    public boolean isValidMonth(String month) {
        String monthForm = "(0[1-9]|1[0-2])";

        return month.matches(monthForm);
    }

    // EFFECTS: displays selected cash flows
    public void displayCashFlow(List<CashFlow> cashFlowList) {
        for (int i = 0; i < cashFlowList.size(); i++) {
            CashFlow cf = cashFlowList.get(i);
            System.out.println(Integer.toString(i + 1) + ".");
            System.out.println("Status: " + cf.getStatus());
            System.out.println("Account: " + cf.getAccount());
            System.out.println("Category: " + cf.getCategory());
            System.out.println("Date: " + cf.getDate());
            System.out.println("Time: " + cf.getTime());
            System.out.println("Description: " + cf.getDescription());
            System.out.println("Amount: " + cf.getAmount());
        }
    }

    // EFFECTS: runs an interactive categories menu that allows user to navigate
    // to debitCategories menu or creditCategories menu
    public void handleCategoriesMenu() {
        displayCategoriesMenu();
        System.out.println("");
        String input = this.scanner.nextLine();
        processCategoriesMenuCommands(input);
    }

    // EFFECTS: displays the Categories menu
    public void displayCategoriesMenu() {
        spaceSeparator();
        System.out.println("Please select which category you want to visit: \n");
        System.out.println("c: Credit category");
        System.out.println("d: Debit category");
        System.out.println("b: Back to main menu");
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
            case "b":
                handleMainMenu();
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
        System.out.println("");
        String input = this.scanner.nextLine();
        processDebitCategoriesMenuCommands(input);
    }

    // EFFECTS: displays Debit category menu
    public void displayDebitCategoriesMenu() {
        spaceSeparator();
        System.out.println("Please select an option: \n");
        System.out.println("a: Add a Debit category");
        System.out.println("d: Delete a Debit category");
        System.out.println("v: View all Debit categories");
        System.out.println("c: Back to category menu");
        System.out.println("q: Quit the MoneyTracker application");
    }

    // EFFECTS: processes the user's input in Debit category menu
    public void processDebitCategoriesMenuCommands(String input) {
        switch (input) {
            case "a":
                addInputtedDebitCategory();
                break;
            case "d":
                deleteInputtedDebitCategory();
                break;
            case "v":
                spaceSeparator();
                viewDebitCategory();
                break;
            case "c":
                handleCategoriesMenu();
                break;
            case "q":
                quitMoneyTrackerApp();
                break;
            default:
                System.out.println("Invalid option inputted. Please try again!");
                handleDebitCategoriesMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a debit category to debitCategories
    public void addInputtedDebitCategory() {
        spaceSeparator();
        System.out.println("Please specify a debit category to be added");
        System.out.println("q: Quit the MoneyTracker application \n");
        String debitCategory = this.scanner.nextLine();

        if (debitCategory.equals("q")) {
            System.exit(0);
        } else if (this.moneySummary.getDebitCategories().indexOf(debitCategory) == -1) {
            this.moneySummary.addDebitCategory(debitCategory);
        } else {
            addInputtedDebitCategory();
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes a debit category from debitCategories
    public void deleteInputtedDebitCategory() {
        spaceSeparator();
        System.out.println("Please type a debit category to be deleted");
        viewDebitCategory();
        System.out.println("q: Quit the MoneyTracker application \n");
        String debitCategory = this.scanner.nextLine();

        if (debitCategory.equals("q")) {
            System.exit(0);
        } else if (this.moneySummary.getDebitCategories().indexOf(debitCategory) != -1) {
            this.moneySummary.deleteDebitCategory(debitCategory);
            this.moneySummary.deleteAllCashFlowOfTheDebitCategory(debitCategory);
        } else {
            deleteInputtedDebitCategory();
        }
    }

    // EFFECTS: displays all registered debit category
    public void viewDebitCategory() {
        List<String> debitCategories = this.moneySummary.getDebitCategories();
        String debitCategoryList = "";
        for (int i = 0; i < debitCategories.size(); i++) {
            debitCategoryList += Integer.toString(i + 1) + ". " + debitCategories.get(i);
            if (i != debitCategories.size() - 1) {
                debitCategoryList += "\n";
            }
        }
        System.out.println(debitCategoryList);
    }

    // EFFECTS: runs an interactive creditCategories menu that allows user to add
    // or delete any credit category and view a list of it
    public void handleCreditCategoriesMenu() {
        displayCreditCategoriesMenu();
        System.out.println("");
        String input = this.scanner.nextLine();
        processCreditCategoriesMenuCommands(input);
    }

    // EFFECTS: displays Credit category menu
    public void displayCreditCategoriesMenu() {
        spaceSeparator();
        System.out.println("Please select an option \n");
        System.out.println("a: Add a Credit category");
        System.out.println("d: Delete a Credit category");
        System.out.println("v: View all Credit categories");
        System.out.println("c: Back to category menu");
        System.out.println("q: Quit the MoneyTracker application");
    }

    // EFFECTS: processes the user's input in Credit category menu
    public void processCreditCategoriesMenuCommands(String input) {
        switch (input) {
            case "a":
                addInputtedCreditCategory();
                break;
            case "d":
                deleteInputtedCreditCategory();
                break;
            case "v":
                spaceSeparator();
                viewCreditCategory();
                break;
            case "c":
                handleCategoriesMenu();
                break;
            case "q":
                quitMoneyTrackerApp();
                break;
            default:
                System.out.println("Invalid option inputted. Please try again!");
                handleCreditCategoriesMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a credit category to creditCategories
    public void addInputtedCreditCategory() {
        spaceSeparator();
        System.out.println("Please specify a credit category to be added");
        System.out.println("q: Quit the MoneyTracker application \n");
        String creditCategory = this.scanner.nextLine();

        if (creditCategory.equals("q")) {
            System.exit(0);
        } else if (this.moneySummary.getCreditCategories().indexOf(creditCategory) == -1) {
            this.moneySummary.addCreditCategory(creditCategory);
        } else {
            addInputtedCreditCategory();
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes a credit category from creditCategories
    public void deleteInputtedCreditCategory() {
        spaceSeparator();
        System.out.println("Please type a credit category to be deleted");
        viewCreditCategory();
        System.out.println("q: Quit the MoneyTracker application \n");
        String creditCategory = this.scanner.nextLine();

        if (creditCategory.equals("q")) {
            System.exit(0);
        } else if (this.moneySummary.getCreditCategories().indexOf(creditCategory) != -1) {
            this.moneySummary.deleteCreditCategory(creditCategory);
            this.moneySummary.deleteAllCashFlowOfTheCreditCategory(creditCategory);
        } else {
            deleteInputtedCreditCategory();
        }
    }

    // EFFECTS: displays all registered credit category
    public void viewCreditCategory() { 
        List<String> creditCategories = this.moneySummary.getCreditCategories();
        String creditCategoryList = "";
        for (int i = 0; i < creditCategories.size(); i++) {
            creditCategoryList += Integer.toString(i + 1) + ". " + creditCategories.get(i);
            if (i != creditCategories.size() - 1) {
                creditCategoryList += "\n";
            }
        }
        System.out.println(creditCategoryList);
    }

    // EFFECTS: runs an interactive accounts menu that allows user to add or
    // or delete any account and view a list of it
    public void handleAccountsMenu() {
        displayAccountsMenu();
        System.out.println("");
        String input = this.scanner.nextLine();
        processAccountsMenuCommands(input);
    }

    // EFFECTS: displays Accounts menu
    public void displayAccountsMenu() {
        spaceSeparator();
        System.out.println("Please select an option: \n");
        System.out.println("a: Add an account");
        System.out.println("d: Delete an account");
        System.out.println("l: view the list of accounts");
        System.out.println("v: View an account balance");
        System.out.println("b: Back to main menu");
        System.out.println("q: Quit the MoneyTracker application");
    }

    // EFFECTS: processes the user's input in Credit category menu
    public void processAccountsMenuCommands(String input) {
        switch (input) {
            case "a":
                addInputtedAccount();
                break;
            case "d":
                deleteInputtedAccount();
                break;
            case "l":
                viewAccounts();
                break;
            case "v":
                viewBalance();
                break;
            case "b":
                handleMainMenu();
                break;
            case "q":
                quitMoneyTrackerApp();
                break;
            default:
                System.out.println("Invalid option inputted. Please try again!");
                handleAccountsMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds an inputted account to the accounts
    public void addInputtedAccount() {
        spaceSeparator();
        System.out.println("Please specify an account to be added");
        System.out.println("q: Quit the MoneyTracker application \n");
        String account = this.scanner.nextLine();

        if (account.equals("q")) {
            System.exit(0);
        } else if (this.moneySummary.getAccounts().indexOf(account) == -1) {
            this.moneySummary.addAccount(account);
        } else {
            addInputtedAccount();
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes an inputted account from the accounts
    public void deleteInputtedAccount() {
        if (isAccountNotEmpty()) {
            spaceSeparator();
            System.out.println("Please type a registered account to be deleted");
            displayAccountList();
            System.out.println("q: Quit the MoneyTracker application \n");
            String account = this.scanner.nextLine();

            if (account.equals("q")) {
                System.exit(0);
            } else if (this.moneySummary.getAccounts().indexOf(account) != -1) {
                this.moneySummary.deleteAccount(account);
                this.moneySummary.deleteAllCashFlowOfTheAccount(account);
            } else {
                deleteInputtedAccount();
            }
        } else {
            System.out.println("Please add an account first");
            handleAccountsMenu();
        }
        
    }

    // EFFECTS: displays all accounts in the accounts
    public void viewAccounts() {
        spaceSeparator();
        System.out.println("Registered accounts: \n");
        displayAccountList();
    }

    // EFFECTS: displays the balance of a specified account
    public void viewBalance() {
        if (isAccountNotEmpty()) {
            spaceSeparator();
            System.out.println("Please type the account name");
            displayAccountList();
            System.out.println("");
            String account = this.scanner.nextLine();
            if (isValidAccount(account)) {
                double balance = calculateBalance(account);
                System.out.println("");
                System.out.println("The balance of" + " " + account + " " + "is" + " " + balance);
            } else {
                viewBalance();
            }
        } else {
            System.out.println("Please add an account first");
            handleAccountsMenu();
        }
    }

    // EFFECTS: returns true if accounts contains at least one account
    public boolean isAccountNotEmpty() {
        return moneySummary.getAccounts().size() != 0;
    }

    // EFFECTS: calculates the balance of inputted account
    private double calculateBalance(String account) {
        double balance = 0;

        for (int i = 0; i < this.moneySummary.getCashflows().size(); i++) {
            CashFlow cashflow = this.moneySummary.getCashflows().get(i);
            if (cashflow.getAccount().equals(account)) {
                if (cashflow.getStatus().equals("debit")) {
                    balance += cashflow.getAmount();
                } else {
                    balance -= cashflow.getAmount();
                }
            }
        }

        return balance;
    }

    // MODIFIES: this
    // EFFECTS: marks the application as stopped and prints some closing
    // messages
    public void quitMoneyTrackerApp() {
        this.isProgramRunning = false;
    }

    // EFFECTS: prints a line space separator
    public void spaceSeparator() {
        System.out.println("----------------------------------------");
    }
}