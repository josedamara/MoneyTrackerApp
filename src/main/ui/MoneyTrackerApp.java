package ui;

import model.CashFlow;
import model.MoneySummary;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.util.*;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private JPanel mainMenu = new JPanel();
    private JPanel moneyTrackerMenu = new JPanel();
    private JPanel loadMenu = new JPanel();
    private JPanel saveMenu = new JPanel();
    private JPanel saveMenuBeforeQuit = new JPanel();
    private JPanel categoryMenu = new JPanel();
    private JPanel debitCategoryMenu = new JPanel();
    private JPanel creditCategoryMenu = new JPanel();
    private JPanel accountMenu = new JPanel();

    private JPanel addStatusMoneyTrackerForm = new JPanel();
    private JPanel addAccountMoneyTrackerForm = new JPanel();
    private JPanel addCategoryMoneyTrackerForm = new JPanel();
    private JPanel addDateMoneyTrackerForm = new JPanel();
    private JPanel addTimeMoneyTrackerForm = new JPanel();
    private JPanel addDescriptionMoneyTrackerForm = new JPanel();
    private JPanel addAmountMoneyTrackerForm = new JPanel();

    private JPanel viewMoneyTrackerMenu = new JPanel();
    private JPanel viewMonthlyMoneyTracker = new JPanel();
    private JPanel viewMonthlyCashFlowForm = new JPanel();

    private JPanel removeMoneyTrackerMenu = new JPanel();
    private JPanel removeFindByStatus = new JPanel();
    private JPanel removeFindByAccount = new JPanel();
    private JPanel removeFindByCategory = new JPanel();
    private JPanel removeFindByDate = new JPanel();
    private JPanel removeCashFlow = new JPanel();

    private JPanel editMoneyTrackerMenu = new JPanel();
    private JPanel editFindByStatus = new JPanel();
    private JPanel editFindByAccount = new JPanel();
    private JPanel editFindByCategory = new JPanel();
    private JPanel editFindByDate = new JPanel();
    private JPanel editChooseCashFlow = new JPanel();
    private JPanel editOptionCashFlow = new JPanel();
    private JPanel editStatus = new JPanel();
    private JPanel editAccount = new JPanel();
    private JPanel editCategory = new JPanel();
    private JPanel editDate = new JPanel();
    private JPanel editTime = new JPanel();
    private JPanel editDescription = new JPanel();
    private JPanel editAmount = new JPanel();

    private ImagePanel cashflowsPanel = new ImagePanel();

    private Font labelFont = new Font("Arial", Font.PLAIN, 24);
    private Font infoFont = new Font("Arial", Font.PLAIN, 12);

    private String capturedStatus;
    private String capturedAccount;
    private String capturedCategory;
    private String capturedDate;
    private String capturedTime;
    private String capturedDescription;
    private double capturedAmount;

    private String capturedMonth;
    private String capturedYear;

    private int capturedNumber;
    private List<CashFlow> filteredCashFlows;

    private final int frameWidth = 800;
    private final int frameHeight = 600;
    private final int imagePanelWidth = frameWidth;
    private final int imagePanelHeight = 280;

    private ImageIcon logo = new ImageIcon("./data/logo.png");

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

        System.exit(0);
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
        displayLogo();
        window.add(ip, BorderLayout.NORTH);

        initAllMenu();
        setAllInvinsible();

        loadMenu.setVisible(true);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // EFFECTS: helper method to ensure the button sizing properly
    private void fixWindow() {
        window.pack();
        window.revalidate();
        window.repaint();
    }

    // EFFECTS: initializes all menu for the MoneyTrackerApp
    private void initAllMenu() {
        initMainMenu();
        initMoneyTrackerMenu();
        initLoadMenu();
        initSaveMenu();
        initSaveMenuBeforeQuit();
        initCategoryMenu();
        initDebitCategoryMenu();
        initCreditCategoryMenu();
        initAccountMenu();
        initAddMoneyTrackerForm();
        initViewMoneyTrackerMenu();
        initViewMonthlyMoneyTracker();
        initViewMonthlyMoneyTrackerForm();
        initRemoveMoneyTrackerMenu();
        initRemoveFindByStatus();
        initRemoveFindByDate();
        initRemoveCashFlow();
        initEditMoneyTrackerMenu();
        initEditFindByDate();
        initEditChooseCashFlow();
        initEditOptionCashFlow();
        initAllEditCashFlow();
    }

    // EFFECTS: helper method to initialize all panel related to editing cash flow
    private void initAllEditCashFlow() {
        initEditStatus();
        initEditDate();
        initEditTime();
        initEditDescription();
        initEditAmount();
    }

    // EFFECTS: sets all initialized to be invinsible
    private void setAllInvinsible() {
        setAllMenuInvinsible();
    }

    // EFFECTS: sets all menu to be invinsible
    private void setAllMenuInvinsible() {
        mainMenu.setVisible(false);
        moneyTrackerMenu.setVisible(false);
        loadMenu.setVisible(false);
        saveMenu.setVisible(false);
        saveMenuBeforeQuit.setVisible(false);
        categoryMenu.setVisible(false);
        debitCategoryMenu.setVisible(false);
        creditCategoryMenu.setVisible(false);
        accountMenu.setVisible(false);
        addStatusMoneyTrackerForm.setVisible(false);
        addAccountMoneyTrackerForm.setVisible(false);
        addCategoryMoneyTrackerForm.setVisible(false);
        addDateMoneyTrackerForm.setVisible(false);
        addTimeMoneyTrackerForm.setVisible(false);
        addDescriptionMoneyTrackerForm.setVisible(false);
        addAmountMoneyTrackerForm.setVisible(false);
        viewMoneyTrackerMenu.setVisible(false);
        viewMonthlyMoneyTracker.setVisible(false);
        viewMonthlyCashFlowForm.setVisible(false);
        setAllRemoveCashFlowInvisible();
        setAllEditCashFlowInvisible();
    }

    // EFFECTS: helper method to hide panel related to remove cash flow
    private void setAllRemoveCashFlowInvisible() {
        removeMoneyTrackerMenu.setVisible(false);
        removeFindByStatus.setVisible(false);
        removeFindByAccount.setVisible(false);
        removeFindByCategory.setVisible(false);
        removeFindByDate.setVisible(false);
        removeCashFlow.setVisible(false);
    }

    // EFFECTS: helper method to hide panel related to edit a cash flow
    private void setAllEditCashFlowInvisible() {
        editMoneyTrackerMenu.setVisible(false);
        editFindByStatus.setVisible(false);
        editFindByAccount.setVisible(false);
        editFindByCategory.setVisible(false);
        editFindByDate.setVisible(false);
        editChooseCashFlow.setVisible(false);
        editOptionCashFlow.setVisible(false);
        editStatus.setVisible(false);
        editAccount.setVisible(false);
        editCategory.setVisible(false);
        editDate.setVisible(false);
        editTime.setVisible(false);
        editDescription.setVisible(false);
        editAmount.setVisible(false);
    }


    // EFFECTS: sets the image panel to display the logo
    private void displayLogo() {
        ip.setLayout(new BoxLayout(ip, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("MoneyTrackerApp");
        title.setFont(new Font("Montserrat", Font.PLAIN, 36));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        Image scaledLogo = logo.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        ip.add(Box.createVerticalGlue());
        ip.add(title);
        ip.add(Box.createVerticalStrut(10));
        ip.add(logoLabel);
        ip.add(Box.createVerticalGlue());
    }

    // EFFECTS: creates buttons for the main menu
    public void initMainMenu() {
        mainMenu.setLayout(new GridLayout(3, 3));
        mainMenu.setPreferredSize(new Dimension(40, 300));

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
        mainMenu.add(saveMenuButton);
        mainMenu.add(categoryMenuButton);
        mainMenu.add(loadMenuButton);
        mainMenu.add(accountMenuButton);
        mainMenu.add(quitButton);

        window.add(mainMenu, BorderLayout.SOUTH);

        fixWindow();
    }

    // EFFECTS: initializes buttons for money tracker menu
    public void initMoneyTrackerMenu() {
        moneyTrackerMenu.setLayout(new GridLayout(3, 3));

        JButton addMoneyTrackerButton = new JButton(new AddMoneyTrackerAction());
        addMoneyTrackerButton.setPreferredSize(new Dimension(20, 100));

        JButton viewMoneyTrackerMenuButton = new JButton(new ViewMoneyTrackerMenu());
        viewMoneyTrackerMenuButton.setPreferredSize(new Dimension(20, 100));

        JButton editMoneyTrackerButton = new JButton(new EditMoneyTracker());
        editMoneyTrackerButton.setPreferredSize(new Dimension(20, 100));

        JButton removeMoneyTrackerButton = new JButton(new RemoveMoneyTracker());
        removeMoneyTrackerButton.setPreferredSize(new Dimension(20, 100));

        JButton backMainMenuButton = new JButton(new BackMainMenu());
        backMainMenuButton.setPreferredSize(new Dimension(20, 100));

        JButton quitButton = new JButton(new QuitAction());
        quitButton.setPreferredSize(new Dimension(20, 100));

        moneyTrackerMenu.add(addMoneyTrackerButton);
        moneyTrackerMenu.add(viewMoneyTrackerMenuButton);
        moneyTrackerMenu.add(editMoneyTrackerButton);
        moneyTrackerMenu.add(removeMoneyTrackerButton);
        moneyTrackerMenu.add(backMainMenuButton);
        moneyTrackerMenu.add(quitButton);

        window.add(moneyTrackerMenu, BorderLayout.SOUTH);

        fixWindow();
    }

    // EFFECTS: creates buttons for load option
    public void initLoadMenu() {
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

    // EFFECTS: creates buttons for save option
    public void initSaveMenu() {
        saveMenu.setLayout(new GridLayout(1, 1));

        JButton saveButton = new JButton(new SaveDataAction());
        saveButton.setPreferredSize(new Dimension(20, 300));

        JButton notSaveButton = new JButton(new NotSaveDataAction());
        notSaveButton.setPreferredSize(new Dimension(20, 300));

        saveMenu.add(saveButton);
        saveMenu.add(notSaveButton);

        window.add(saveMenu, BorderLayout.SOUTH);

        fixWindow();
    }

    // EFFECTS: creates buttons for save option before quitting
    public void initSaveMenuBeforeQuit() {
        saveMenuBeforeQuit.setLayout(new GridLayout(1, 2));

        JButton saveButton = new JButton(new SaveDataAction());
        saveButton.addActionListener(new Quit());
        saveButton.setPreferredSize(new Dimension(20, 300));

        JButton notSaveButton = new JButton(new NotSaveDataAction());
        notSaveButton.addActionListener(new Quit());
        notSaveButton.setPreferredSize(new Dimension(20, 300));

        saveMenuBeforeQuit.add(saveButton);
        saveMenuBeforeQuit.add(notSaveButton);

        window.add(saveMenuBeforeQuit, BorderLayout.SOUTH);

        fixWindow();
    }

    // EFFECTS: creates buttons for the category menu
    public void initCategoryMenu() {
        categoryMenu.setLayout(new GridLayout(2, 2));

        JButton creditCategoryMenuButton = new JButton(new CreditCategoryMenuAction());
        creditCategoryMenuButton.setPreferredSize(new Dimension(20, 150));

        JButton debitCategoryMenuButton = new JButton(new DebitCategoryMenuAction());
        debitCategoryMenuButton.setPreferredSize(new Dimension(20, 150));

        JButton backMainMenuButton = new JButton(new BackMainMenu());
        backMainMenuButton.setPreferredSize(new Dimension(20, 150));

        JButton quitButton = new JButton(new QuitAction());
        quitButton.setPreferredSize(new Dimension(20, 150));

        categoryMenu.add(creditCategoryMenuButton);
        categoryMenu.add(debitCategoryMenuButton);
        categoryMenu.add(backMainMenuButton);
        categoryMenu.add(quitButton);

        window.add(categoryMenu, BorderLayout.SOUTH);

        fixWindow();
    }

    // EFFECTS: creates buttons for the debit category menu
    public void initDebitCategoryMenu() {
        debitCategoryMenu.setLayout(new GridLayout(2, 2));

        JButton addDebitCategoryButton = new JButton(new AddDebitCategoryAction());
        addDebitCategoryButton.setPreferredSize(new Dimension(20, 150));

        JButton deleteDebitCategoryButton = new JButton(new DeleteDebitCategoryAction());
        deleteDebitCategoryButton.setPreferredSize(new Dimension(20, 150));

        JButton viewAllDebitCategoryButton = new JButton(new ViewDebitCategoryAction());
        viewAllDebitCategoryButton.setPreferredSize(new Dimension(20, 150));

        JButton backCategoryMenuButton = new JButton(new CategoryMenuAction());
        backCategoryMenuButton.setPreferredSize(new Dimension(20, 150));

        debitCategoryMenu.add(addDebitCategoryButton);
        debitCategoryMenu.add(deleteDebitCategoryButton);
        debitCategoryMenu.add(viewAllDebitCategoryButton);
        debitCategoryMenu.add(backCategoryMenuButton);

        window.add(debitCategoryMenu, BorderLayout.SOUTH);

        fixWindow();
    }

    // EFFECTS: creates buttons for the credit category menu
    public void initCreditCategoryMenu() {
        creditCategoryMenu.setLayout(new GridLayout(2, 2));

        JButton addCreditCategoryButton = new JButton(new AddCreditCategoryAction());
        addCreditCategoryButton.setPreferredSize(new Dimension(20, 150));

        JButton deleteCreditCategoryButton = new JButton(new DeleteCreditCategoryAction());
        deleteCreditCategoryButton.setPreferredSize(new Dimension(20, 150));

        JButton viewAllCreditCategoryButton = new JButton(new ViewCreditCategoryAction());
        viewAllCreditCategoryButton.setPreferredSize(new Dimension(20, 150));

        JButton backCategoryMenuButton = new JButton(new CategoryMenuAction());
        backCategoryMenuButton.setPreferredSize(new Dimension(20, 150));

        creditCategoryMenu.add(addCreditCategoryButton);
        creditCategoryMenu.add(deleteCreditCategoryButton);
        creditCategoryMenu.add(viewAllCreditCategoryButton);
        creditCategoryMenu.add(backCategoryMenuButton);

        window.add(creditCategoryMenu, BorderLayout.SOUTH);

        fixWindow();
    }

    // EFFECTS: creates buttons for the account menu
    public void initAccountMenu() {
        accountMenu.setLayout(new GridLayout(3, 3));

        JButton addAccountButton = new JButton(new AddAccountAction());
        addAccountButton.setPreferredSize(new Dimension(20, 100));

        JButton deleteAccountButton = new JButton(new DeleteAccountAction());
        deleteAccountButton.setPreferredSize(new Dimension(20, 100));

        JButton viewListOfAccountsButton = new JButton(new ViewListOfAccountsAction());
        viewListOfAccountsButton.setPreferredSize(new Dimension(20, 100));

        JButton viewAnAccountBalanceButton = new JButton(new ViewAnAccountBalanceAction());
        viewAnAccountBalanceButton.setPreferredSize(new Dimension(20, 100));

        JButton backMainMenuButton = new JButton(new BackMainMenu());
        backMainMenuButton.setPreferredSize(new Dimension(20, 100));

        JButton quitButton = new JButton(new QuitAction());
        quitButton.setPreferredSize(new Dimension(20, 100));

        accountMenu.add(addAccountButton);
        accountMenu.add(deleteAccountButton);
        accountMenu.add(viewListOfAccountsButton);
        accountMenu.add(viewAnAccountBalanceButton);
        accountMenu.add(backMainMenuButton);
        accountMenu.add(quitButton);

        window.add(accountMenu, BorderLayout.SOUTH);

        fixWindow();
    }

    // EFFECTS: initializes add money tracker form
    public void initAddMoneyTrackerForm() {
        initAddStatusCashFlowColumn();
        initAddDateCashFlowColumn();
        initAddTimeCashFlowColumn();
        initAddDescriptionCashFlowColumn();
        initAddAmountCashFlowColumn();
    }

    // EFFECTS: helper method to set layout and size in cashflow form
    private void setCashFlowFormLayoutAndSize(JPanel jp) {
        jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
        jp.setPreferredSize(new Dimension(40, 300));
    }

    // EFFECTS: helper method to set label font
    private void setCashFlowFormLabelAndInfoFont(JLabel label, JLabel info) {
        label.setFont(labelFont);
        info.setFont(infoFont);
    }

    // EFFECTS: helper method to add all panel to the form
    private void addAllPanelCashFlowForm(JPanel form, JPanel label, JPanel info, JPanel column, JPanel button) {
        form.add(label);
        form.add(info);
        form.add(column);
        form.add(button);
    }
    
    // EFFECTS: helper method to add action listener to capture inputted status
    private void captureStatus(JButton submitButton, JTextField textField) {
        submitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                capturedStatus = textField.getText();
            }

        });
    }

    // EFFECTS: helper method to add action listener to capture inputted account
    private void captureAccount(JButton submitButton, JTextField textField) {
        submitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                capturedAccount = textField.getText();
            }

        });
    }

    // EFFECTS: helper method to add action listener to capture inputted category
    private void captureCategory(JButton submitButton, JTextField textField) {
        submitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                capturedCategory = textField.getText();
            }

        });
    }

    // EFFECTS: helper method to add action listener to capture inputted date
    private void captureDate(JButton submitButton, JTextField textField) {
        submitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                capturedDate = textField.getText();
            }

        });
    }

    // EFFECTS: helper method to add action listener to capture inputted date
    private void captureTime(JButton submitButton, JTextField textField) {
        submitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                capturedTime = textField.getText();
            }

        });
    }

    // EFFECTS: helper method to add action listener to capture inputted description
    private void captureDescription(JButton submitButton, JTextField textField) {
        submitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                capturedDescription = textField.getText();
            }

        });
    }

    // EFFECTS: helper method to add action listener to capture inputted amount of money
    private void captureAmount(JButton submitButton, JTextField textField) {
        submitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                capturedAmount = Double.parseDouble(textField.getText());
            }

        });
    }

    // EFFECTS: helper method to add action listener to capture inputted month
    private void captureMonth(JButton submitButton, JTextField textField) {
        submitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                capturedMonth = textField.getText();
            }

        });
    }

    // EFFECTS: helper method to add action listener to capture inputted year
    private void captureYear(JButton submitButton, JTextField textField) {
        submitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                capturedYear = textField.getText();
            }

        });
    }

    // EFFECTS: helper method to add action listener to capture inputted index
    private void captureIndex(JButton submitButton, JTextField textField) {
        submitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                capturedNumber = Integer.parseInt(textField.getText());
            }

        });
    }

    // EFFECTS: helper method to add each component to its panel
    private void addAllComponentToItsPanel(JPanel lp, JLabel ll, JPanel ip, JLabel il, JPanel fp, JTextField fl,
            JPanel bp, JButton bl) {
        lp.add(ll);
        ip.add(il);
        fp.add(fl);
        bp.add(bl);
    }

    // EFFECTS: initializes the add status of cash flow form
    public void initAddStatusCashFlowColumn() {
        setCashFlowFormLayoutAndSize(addStatusMoneyTrackerForm);
        
        JPanel labelPanel = new JPanel();
        JLabel label = new JLabel("Enter status");
        labelPanel.setPreferredSize(new Dimension(20, 40));

        JPanel infoPanel = new JPanel();
        JLabel infoLabel = new JLabel("credit/debit");
        infoPanel.setPreferredSize(new Dimension(20, 40));

        setCashFlowFormLabelAndInfoFont(label, infoLabel);

        JPanel fieldPanel = new JPanel();
        JTextField statusTextField = new JTextField(20);
        fieldPanel.setPreferredSize(new Dimension(20, 200));
        statusTextField.setPreferredSize(new Dimension(20, 50));

        JPanel buttonPanel = new JPanel();
        JButton continueToAddAccountButton = new JButton(new ContinueToAddAccount());
        buttonPanel.setPreferredSize(new Dimension(20, 50));

        addAllComponentToItsPanel(labelPanel, label, infoPanel, infoLabel, fieldPanel, statusTextField, buttonPanel,
                continueToAddAccountButton);

        addAllPanelCashFlowForm(addStatusMoneyTrackerForm, labelPanel, infoPanel, fieldPanel, buttonPanel);

        window.add(addStatusMoneyTrackerForm, BorderLayout.SOUTH);

        fixWindow();

        captureStatus(continueToAddAccountButton, statusTextField);
    }

    // EFFECTS: initializes the add account of cash flow form
    public void initAddAccountCashFlowColumn() {
        addAccountMoneyTrackerForm = new JPanel();
        setCashFlowFormLayoutAndSize(addAccountMoneyTrackerForm);
        
        JPanel labelPanel = new JPanel();
        JLabel label = new JLabel("Enter account");
        labelPanel.setPreferredSize(new Dimension(20, 40));

        JPanel infoPanel = new JPanel();
        JLabel infoLabel = new JLabel(viewAccounts());
        infoPanel.setPreferredSize(new Dimension(20, 40));

        setCashFlowFormLabelAndInfoFont(label, infoLabel);

        JPanel fieldPanel = new JPanel();
        JTextField accountTextField = new JTextField(20);
        fieldPanel.setPreferredSize(new Dimension(20, 200));
        accountTextField.setPreferredSize(new Dimension(20, 50));

        JPanel buttonPanel = new JPanel();
        JButton continueToAddCategoryButton = new JButton(new ContinueToAddCategory());
        buttonPanel.setPreferredSize(new Dimension(20, 50));

        addAllComponentToItsPanel(labelPanel, label, infoPanel, infoLabel, fieldPanel, accountTextField, buttonPanel,
                continueToAddCategoryButton);

        addAllPanelCashFlowForm(addAccountMoneyTrackerForm, labelPanel, infoPanel, fieldPanel, buttonPanel);

        window.add(addAccountMoneyTrackerForm, BorderLayout.SOUTH);

        fixWindow();
        
        captureAccount(continueToAddCategoryButton, accountTextField);
    }

    // EFFECTS: initializes the add category of cash flow form
    public void initAddCategoryCashFlowColumn() {
        addCategoryMoneyTrackerForm = new JPanel();
        setCashFlowFormLayoutAndSize(addCategoryMoneyTrackerForm);
        
        JPanel labelPanel = new JPanel();
        JLabel label = new JLabel("Enter category");
        labelPanel.setPreferredSize(new Dimension(20, 40));

        JPanel infoPanel = new JPanel();
        JLabel infoLabel = new JLabel(capturedStatus == "debit" ? viewDebitCategory() : viewCreditCategory());
        infoPanel.setPreferredSize(new Dimension(20, 40));

        setCashFlowFormLabelAndInfoFont(label, infoLabel);

        JPanel fieldPanel = new JPanel();
        JTextField categoryTextField = new JTextField(20);
        fieldPanel.setPreferredSize(new Dimension(20, 200));
        categoryTextField.setPreferredSize(new Dimension(20, 50));

        JPanel buttonPanel = new JPanel();
        JButton continueToAddDateButton = new JButton(new ContinueToAddDate());
        buttonPanel.setPreferredSize(new Dimension(20, 50));

        addAllComponentToItsPanel(labelPanel, label, infoPanel, infoLabel, fieldPanel, categoryTextField, buttonPanel,
                continueToAddDateButton);

        addAllPanelCashFlowForm(addCategoryMoneyTrackerForm, labelPanel, infoPanel, fieldPanel, buttonPanel);

        window.add(addCategoryMoneyTrackerForm, BorderLayout.SOUTH);

        fixWindow();

        captureCategory(continueToAddDateButton, categoryTextField);
    }

    // EFFECTS: initializes the add date of cash flow form
    public void initAddDateCashFlowColumn() {
        setCashFlowFormLayoutAndSize(addDateMoneyTrackerForm);
        
        JPanel labelPanel = new JPanel();
        JLabel label = new JLabel("Enter date");
        labelPanel.setPreferredSize(new Dimension(20, 40));

        JPanel infoPanel = new JPanel();
        JLabel infoLabel = new JLabel("YYYY/MM/DD");
        infoPanel.setPreferredSize(new Dimension(20, 40));

        setCashFlowFormLabelAndInfoFont(label, infoLabel);

        JPanel fieldPanel = new JPanel();
        JTextField dateTextField = new JTextField(20);
        fieldPanel.setPreferredSize(new Dimension(20, 200));
        dateTextField.setPreferredSize(new Dimension(20, 50));

        JPanel buttonPanel = new JPanel();
        JButton continueToAddTimeButton = new JButton(new ContinueToAddTime());
        buttonPanel.setPreferredSize(new Dimension(20, 50));

        addAllComponentToItsPanel(labelPanel, label, infoPanel, infoLabel, fieldPanel, dateTextField, buttonPanel,
                continueToAddTimeButton);

        addAllPanelCashFlowForm(addDateMoneyTrackerForm, labelPanel, infoPanel, fieldPanel, buttonPanel);

        window.add(addDateMoneyTrackerForm, BorderLayout.SOUTH);

        fixWindow();

        captureDate(continueToAddTimeButton, dateTextField);
    }

    // EFFECTS: initializes the add time of cash flow form
    public void initAddTimeCashFlowColumn() {
        setCashFlowFormLayoutAndSize(addTimeMoneyTrackerForm);
        
        JPanel labelPanel = new JPanel();
        JLabel label = new JLabel("Enter time");
        labelPanel.setPreferredSize(new Dimension(20, 40));

        JPanel infoPanel = new JPanel();
        JLabel infoLabel = new JLabel("HH:MM");
        infoPanel.setPreferredSize(new Dimension(20, 40));

        setCashFlowFormLabelAndInfoFont(label, infoLabel);

        JPanel fieldPanel = new JPanel();
        JTextField timeTextField = new JTextField(20);
        fieldPanel.setPreferredSize(new Dimension(20, 200));
        timeTextField.setPreferredSize(new Dimension(20, 50));

        JPanel buttonPanel = new JPanel();
        JButton continueToAddDescription = new JButton(new ContinueToAddDescription());
        buttonPanel.setPreferredSize(new Dimension(20, 50));

        addAllComponentToItsPanel(labelPanel, label, infoPanel, infoLabel, fieldPanel, timeTextField, buttonPanel,
                continueToAddDescription);

        addAllPanelCashFlowForm(addTimeMoneyTrackerForm, labelPanel, infoPanel, fieldPanel, buttonPanel);

        window.add(addTimeMoneyTrackerForm, BorderLayout.SOUTH);

        fixWindow();

        captureTime(continueToAddDescription, timeTextField);
    }

    // EFFECTS: initializes the add description of cash flow form
    public void initAddDescriptionCashFlowColumn() {
        setCashFlowFormLayoutAndSize(addDescriptionMoneyTrackerForm);
        
        JPanel labelPanel = new JPanel();
        JLabel label = new JLabel("Enter description");
        labelPanel.setPreferredSize(new Dimension(20, 40));

        JPanel infoPanel = new JPanel();
        JLabel infoLabel = new JLabel("");
        infoPanel.setPreferredSize(new Dimension(20, 40));

        setCashFlowFormLabelAndInfoFont(label, infoLabel);

        JPanel fieldPanel = new JPanel();
        JTextField descriptionTextField = new JTextField(20);
        fieldPanel.setPreferredSize(new Dimension(20, 200));
        descriptionTextField.setPreferredSize(new Dimension(20, 50));

        JPanel buttonPanel = new JPanel();
        JButton continueToAddAmount = new JButton(new ContinueToAddAmount());
        buttonPanel.setPreferredSize(new Dimension(20, 50));

        addAllComponentToItsPanel(labelPanel, label, infoPanel, infoLabel, fieldPanel, descriptionTextField,
                buttonPanel, continueToAddAmount);

        addAllPanelCashFlowForm(addDescriptionMoneyTrackerForm, labelPanel, infoPanel, fieldPanel, buttonPanel);

        window.add(addDescriptionMoneyTrackerForm, BorderLayout.SOUTH);

        fixWindow();

        captureDescription(continueToAddAmount, descriptionTextField);
    }

    // EFFECTS: initializes the add amouunt of cash flow form
    public void initAddAmountCashFlowColumn() {
        setCashFlowFormLayoutAndSize(addAmountMoneyTrackerForm);
        
        JPanel labelPanel = new JPanel();
        JLabel label = new JLabel("Enter amount of money");
        labelPanel.setPreferredSize(new Dimension(20, 40));

        JPanel infoPanel = new JPanel();
        JLabel infoLabel = new JLabel("Number only");
        infoPanel.setPreferredSize(new Dimension(20, 40));

        setCashFlowFormLabelAndInfoFont(label, infoLabel);

        JPanel fieldPanel = new JPanel();
        JTextField amountTextField = new JTextField(20);
        fieldPanel.setPreferredSize(new Dimension(20, 200));
        amountTextField.setPreferredSize(new Dimension(20, 50));

        JPanel buttonPanel = new JPanel();
        JButton continueToAddCashFlow = new JButton(new ContinueToAddCashFlowAction());
        buttonPanel.setPreferredSize(new Dimension(20, 50));

        addAllComponentToItsPanel(labelPanel, label, infoPanel, infoLabel, fieldPanel, amountTextField, buttonPanel,
                continueToAddCashFlow);

        addAllPanelCashFlowForm(addAmountMoneyTrackerForm, labelPanel, infoPanel, fieldPanel, buttonPanel);

        window.add(addAmountMoneyTrackerForm, BorderLayout.SOUTH);

        fixWindow();

        captureAmount(continueToAddCashFlow, amountTextField);
    }

    // EFFECTS: initializes the view money tracker menu
    public void initViewMoneyTrackerMenu() {
        viewMoneyTrackerMenu.setLayout(new GridLayout(2, 2));

        JButton viewAllCashFlowButton = new JButton(new ViewAllCashFlowAction());
        viewAllCashFlowButton.setPreferredSize(new Dimension(20, 150));

        JButton viewMonthlyCashFlowButton = new JButton(new ViewMonthlyCashFlowAction());
        viewMonthlyCashFlowButton.setPreferredSize(new Dimension(20, 150));

        JButton backCashFlowMenuButton = new JButton(new BackCashFlowMenuAction());
        backCashFlowMenuButton.setPreferredSize(new Dimension(20, 150));

        JButton quitButton = new JButton(new QuitAction());
        quitButton.setPreferredSize(new Dimension(20, 150));

        viewMoneyTrackerMenu.add(viewAllCashFlowButton);
        viewMoneyTrackerMenu.add(viewMonthlyCashFlowButton);
        viewMoneyTrackerMenu.add(backCashFlowMenuButton);
        viewMoneyTrackerMenu.add(quitButton);

        window.add(viewMoneyTrackerMenu, BorderLayout.SOUTH);

        fixWindow();
    }

    // EFFECTS: initializes the view monthly tracker menu
    public void initViewMonthlyMoneyTracker() {
        viewMonthlyMoneyTracker.setLayout(new GridLayout(2, 2));

        JButton viewAllCashFlowButton = new JButton(new ViewAllCashFlowAction());
        viewAllCashFlowButton.setPreferredSize(new Dimension(20, 150));

        viewMonthlyCashFlowForm.setPreferredSize(new Dimension(20, 150));

        JButton backCashFlowMenuButton = new JButton(new BackCashFlowMenuAction());
        backCashFlowMenuButton.setPreferredSize(new Dimension(20, 150));

        JButton quitButton = new JButton(new QuitAction());
        quitButton.setPreferredSize(new Dimension(20, 150));

        viewMonthlyMoneyTracker.add(viewAllCashFlowButton);
        viewMonthlyMoneyTracker.add(viewMonthlyCashFlowForm);
        viewMonthlyMoneyTracker.add(backCashFlowMenuButton);
        viewMonthlyMoneyTracker.add(quitButton);

        window.add(viewMonthlyMoneyTracker, BorderLayout.SOUTH);
        
        fixWindow();
    }

    // EFFECTS: initializes the view monthly tracker form
    public void initViewMonthlyMoneyTrackerForm() {
        viewMonthlyCashFlowForm.setLayout(new GridLayout(3, 2));

        JLabel monthLabel = new JLabel("Month");
        monthLabel.setPreferredSize(new Dimension(20, 150));

        JTextField monthTextField = new JTextField();
        monthTextField.setPreferredSize(new Dimension(20, 150));

        JLabel yearLabel = new JLabel("Year");
        yearLabel.setPreferredSize(new Dimension(20, 150));

        JTextField yearTextField = new JTextField();
        yearTextField.setPreferredSize(new Dimension(20, 150));

        JButton displayMonthlyCashFlowButton = new JButton(new DisplayMonthlyCashFlowAction());
        displayMonthlyCashFlowButton.setPreferredSize(new Dimension(20, 150));

        viewMonthlyCashFlowForm.add(monthLabel);
        viewMonthlyCashFlowForm.add(yearLabel);
        viewMonthlyCashFlowForm.add(monthTextField);
        viewMonthlyCashFlowForm.add(yearTextField);
        viewMonthlyCashFlowForm.add(displayMonthlyCashFlowButton);

        captureMonth(displayMonthlyCashFlowButton, monthTextField);
        captureYear(displayMonthlyCashFlowButton, yearTextField);
    }

    // EFFECTS: initializes the menu for removing cash flow
    public void initEditMoneyTrackerMenu() {
        editMoneyTrackerMenu.setLayout(new GridLayout(3, 3));
        editMoneyTrackerMenu.setPreferredSize(new Dimension(40, 300));

        JButton editFindByStatusButton = new JButton(new EditFindByStatusAction());
        editFindByStatusButton.setPreferredSize(new Dimension(20, 100));

        JButton editFindByAccountButton = new JButton(new EditFindByAccountAction());
        editFindByAccountButton.setPreferredSize(new Dimension(20, 100));

        JButton editFindByCategoryButton = new JButton(new EditFindByCategoryAction());
        editFindByCategoryButton.setPreferredSize(new Dimension(20, 100));

        JButton editFindByDateButton = new JButton(new EditFindByDateAction());
        editFindByDateButton.setPreferredSize(new Dimension(20, 100));

        JButton backMainMenuButton = new JButton(new BackMainMenu());
        backMainMenuButton.setPreferredSize(new Dimension(20, 100));

        JButton quitButton = new JButton(new QuitAction());
        quitButton.setPreferredSize(new Dimension(20, 100));

        editMoneyTrackerMenu.add(editFindByStatusButton);
        editMoneyTrackerMenu.add(editFindByAccountButton);
        editMoneyTrackerMenu.add(editFindByCategoryButton);
        editMoneyTrackerMenu.add(editFindByDateButton);
        editMoneyTrackerMenu.add(backMainMenuButton);
        editMoneyTrackerMenu.add(quitButton);

        window.add(editMoneyTrackerMenu, BorderLayout.SOUTH);

        fixWindow();
    }

    // EFFECTS: initializes the find by status form in remove mode
    public void initEditFindByStatus() {
        editFindByStatus = new JPanel();
        setCashFlowFormLayoutAndSize(editFindByStatus);
        
        JPanel labelPanel = new JPanel();
        JLabel label = new JLabel("Enter status");
        labelPanel.setPreferredSize(new Dimension(20, 40));

        JPanel infoPanel = new JPanel();
        JLabel infoLabel = new JLabel("credit/debit");
        infoPanel.setPreferredSize(new Dimension(20, 40));

        setCashFlowFormLabelAndInfoFont(label, infoLabel);

        JPanel fieldPanel = new JPanel();
        JTextField statusTextField = new JTextField(20);
        fieldPanel.setPreferredSize(new Dimension(20, 200));
        statusTextField.setPreferredSize(new Dimension(20, 50));

        JPanel buttonPanel = new JPanel();
        JButton findByStatusButton = new JButton(new OpenEditCashFlow());
        findByStatusButton.addActionListener(new DisplayFindByStatusAction());
        buttonPanel.setPreferredSize(new Dimension(20, 50));

        addAllComponentToItsPanel(labelPanel, label, infoPanel, infoLabel, fieldPanel, statusTextField, buttonPanel,
                findByStatusButton);

        addAllPanelCashFlowForm(editFindByStatus, labelPanel, infoPanel, fieldPanel, buttonPanel);

        window.add(editFindByStatus, BorderLayout.SOUTH);

        captureStatus(findByStatusButton, statusTextField);
    }

    // EFFECTS: initializes the find by account form in remove mode
    public void initEditFindByAccount() {
        editFindByAccount = new JPanel();
        setCashFlowFormLayoutAndSize(editFindByAccount);
        
        JPanel labelPanel = new JPanel();
        JLabel label = new JLabel("Enter account");
        labelPanel.setPreferredSize(new Dimension(20, 40));

        JPanel infoPanel = new JPanel();
        JLabel infoLabel = new JLabel(viewAccounts());
        infoPanel.setPreferredSize(new Dimension(20, 40));

        setCashFlowFormLabelAndInfoFont(label, infoLabel);

        JPanel fieldPanel = new JPanel();
        JTextField accountTextField = new JTextField(20);
        fieldPanel.setPreferredSize(new Dimension(20, 200));
        accountTextField.setPreferredSize(new Dimension(20, 50));

        JPanel buttonPanel = new JPanel();
        JButton findByAccountButton = new JButton(new OpenEditCashFlow());
        findByAccountButton.addActionListener(new DisplayFindByAccountAction());
        buttonPanel.setPreferredSize(new Dimension(20, 50));

        addAllComponentToItsPanel(labelPanel, label, infoPanel, infoLabel, fieldPanel, accountTextField, buttonPanel,
                findByAccountButton);

        addAllPanelCashFlowForm(editFindByAccount, labelPanel, infoPanel, fieldPanel, buttonPanel);

        window.add(editFindByAccount, BorderLayout.SOUTH);

        fixWindow();
        
        captureAccount(findByAccountButton, accountTextField);
    }

    // EFFECTS: initializes the find by category form in remove mode
    public void initEditFindByCategory() {
        editFindByCategory = new JPanel();
        setCashFlowFormLayoutAndSize(editFindByCategory);
        
        JPanel labelPanel = new JPanel();
        JLabel label = new JLabel("Enter category");
        labelPanel.setPreferredSize(new Dimension(20, 40));

        JPanel infoPanel = new JPanel();
        JLabel infoLabel = new JLabel(capturedStatus == "debit" ? viewDebitCategory() : viewCreditCategory());
        infoPanel.setPreferredSize(new Dimension(20, 40));

        setCashFlowFormLabelAndInfoFont(label, infoLabel);

        JPanel fieldPanel = new JPanel();
        JTextField categoryTextField = new JTextField(20);
        fieldPanel.setPreferredSize(new Dimension(20, 200));
        categoryTextField.setPreferredSize(new Dimension(20, 50));

        JPanel buttonPanel = new JPanel();
        JButton findByCategoryButton = new JButton(new OpenEditCashFlow());
        findByCategoryButton.addActionListener(new DisplayFindByCategoryAction());
        buttonPanel.setPreferredSize(new Dimension(20, 50));

        addAllComponentToItsPanel(labelPanel, label, infoPanel, infoLabel, fieldPanel, categoryTextField, buttonPanel,
                findByCategoryButton);

        addAllPanelCashFlowForm(editFindByCategory, labelPanel, infoPanel, fieldPanel, buttonPanel);

        window.add(editFindByCategory, BorderLayout.SOUTH);

        fixWindow();

        captureCategory(findByCategoryButton, categoryTextField);
    }

    // EFFECTS: initializes the add date of cash flow form
    public void initEditFindByDate() {
        setCashFlowFormLayoutAndSize(editFindByDate);
        
        JPanel labelPanel = new JPanel();
        JLabel label = new JLabel("Enter date");
        labelPanel.setPreferredSize(new Dimension(20, 40));

        JPanel infoPanel = new JPanel();
        JLabel infoLabel = new JLabel("YYYY/MM/DD");
        infoPanel.setPreferredSize(new Dimension(20, 40));

        setCashFlowFormLabelAndInfoFont(label, infoLabel);

        JPanel fieldPanel = new JPanel();
        JTextField dateTextField = new JTextField(20);
        fieldPanel.setPreferredSize(new Dimension(20, 200));
        dateTextField.setPreferredSize(new Dimension(20, 50));

        JPanel buttonPanel = new JPanel();
        JButton findByDateButton = new JButton(new OpenEditCashFlow());
        findByDateButton.addActionListener(new DisplayFindByDateAction());
        buttonPanel.setPreferredSize(new Dimension(20, 50));

        addAllComponentToItsPanel(labelPanel, label, infoPanel, infoLabel, fieldPanel, dateTextField, buttonPanel,
                findByDateButton);

        addAllPanelCashFlowForm(editFindByDate, labelPanel, infoPanel, fieldPanel, buttonPanel);

        window.add(editFindByDate, BorderLayout.SOUTH);

        fixWindow();

        captureDate(findByDateButton, dateTextField);
    }

    // EFFECTS: initializes the panel to choose what to edit
    public void initEditChooseCashFlow() {
        setCashFlowFormLayoutAndSize(editChooseCashFlow);
        
        JPanel labelPanel = new JPanel();
        JLabel label = new JLabel("Choose the number of cash flow to be edited");
        labelPanel.setPreferredSize(new Dimension(20, 40));

        JPanel infoPanel = new JPanel();
        JLabel infoLabel = new JLabel("e.g: 1/2/...");
        infoPanel.setPreferredSize(new Dimension(20, 40));

        setCashFlowFormLabelAndInfoFont(label, infoLabel);

        JPanel fieldPanel = new JPanel();
        JTextField numTextField = new JTextField(20);
        fieldPanel.setPreferredSize(new Dimension(20, 200));
        numTextField.setPreferredSize(new Dimension(20, 50));

        JPanel buttonPanel = new JPanel();
        JButton chooseButton = new JButton(new EditChooseCashFlowAction());
        buttonPanel.setPreferredSize(new Dimension(20, 50));

        labelPanel.add(label);
        infoPanel.add(infoLabel);
        fieldPanel.add(numTextField);
        buttonPanel.add(chooseButton);

        addAllPanelCashFlowForm(editChooseCashFlow, labelPanel, infoPanel, fieldPanel, buttonPanel);

        window.add(editChooseCashFlow, BorderLayout.SOUTH);

        fixWindow();

        captureIndex(chooseButton, numTextField);
    }

    // EFFECTS: initializes the options to edit a cash flow
    public void initEditOptionCashFlow() {
        editOptionCashFlow.setLayout(new GridLayout(4, 2));
        editOptionCashFlow.setPreferredSize(new Dimension(40, 300));

        JButton openEditStatusButton = new JButton(new OpenEditStatusAction());
        openEditStatusButton.setPreferredSize(new Dimension(20, 75));

        JButton openEditAccountButton = new JButton(new OpenEditAccountAction());
        openEditAccountButton.setPreferredSize(new Dimension(20, 75));

        JButton openEditCategoryButton = new JButton(new OpenEditCategoryAction());
        openEditCategoryButton.setPreferredSize(new Dimension(20, 75));

        JButton openEditDateButton = new JButton(new OpenEditDateAction());
        openEditDateButton.setPreferredSize(new Dimension(20, 75));

        JButton openEditTimeButton = new JButton(new OpenEditTimeAction());
        openEditTimeButton.setPreferredSize(new Dimension(20, 75));

        JButton openEditDescriptiButton = new JButton(new OpenEditDescriptionAction());
        openEditDescriptiButton.setPreferredSize(new Dimension(20, 75));

        JButton openEditAmountButton = new JButton(new OpenEditAmountAction());
        openEditAmountButton.setPreferredSize(new Dimension(20, 75));

        JButton quitButton = new JButton(new QuitAction());
        quitButton.setPreferredSize(new Dimension(20, 75));

        addAllButtonEditOptionCashFlow(openEditStatusButton, openEditAccountButton, openEditCategoryButton,
                openEditTimeButton, openEditDateButton, openEditDescriptiButton, openEditAmountButton, quitButton);

        window.add(editOptionCashFlow, BorderLayout.SOUTH);

        fixWindow();
    }

    // EFFECTS: helper method to add all buttons to editOptionCashFlow panel
    private void addAllButtonEditOptionCashFlow(JButton b1, JButton b2, JButton b3, JButton b4, JButton b5,
            JButton b6, JButton b7, JButton b8) {
        editOptionCashFlow.add(b1);
        editOptionCashFlow.add(b2);
        editOptionCashFlow.add(b3);
        editOptionCashFlow.add(b4);
        editOptionCashFlow.add(b5);
        editOptionCashFlow.add(b6);
        editOptionCashFlow.add(b7);
        editOptionCashFlow.add(b8);
    }

    // EFFECTS: initializes the edit status of cash flow form
    public void initEditStatus() {
        setCashFlowFormLayoutAndSize(editStatus);
        
        JPanel labelPanel = new JPanel();
        JLabel label = new JLabel("Enter status");
        labelPanel.setPreferredSize(new Dimension(20, 40));

        JPanel infoPanel = new JPanel();
        JLabel infoLabel = new JLabel("credit/debit");
        infoPanel.setPreferredSize(new Dimension(20, 40));

        setCashFlowFormLabelAndInfoFont(label, infoLabel);

        JPanel fieldPanel = new JPanel();
        JTextField statusTextField = new JTextField(20);
        fieldPanel.setPreferredSize(new Dimension(20, 200));
        statusTextField.setPreferredSize(new Dimension(20, 50));

        JPanel buttonPanel = new JPanel();
        JButton editStatusButton = new JButton(new EditStatusAction());
        buttonPanel.setPreferredSize(new Dimension(20, 50));

        addAllComponentToItsPanel(labelPanel, label, infoPanel, infoLabel, fieldPanel, statusTextField, buttonPanel,
                editStatusButton);

        addAllPanelCashFlowForm(editStatus, labelPanel, infoPanel, fieldPanel, buttonPanel);

        window.add(editStatus, BorderLayout.SOUTH);

        fixWindow();

        captureStatus(editStatusButton, statusTextField);
    }

    // EFFECTS: initializes the edit account of cash flow form
    public void initEditAccount() {
        editAccount = new JPanel();
        setCashFlowFormLayoutAndSize(editAccount);
        
        JPanel labelPanel = new JPanel();
        JLabel label = new JLabel("Enter account");
        labelPanel.setPreferredSize(new Dimension(20, 40));

        JPanel infoPanel = new JPanel();
        JLabel infoLabel = new JLabel(viewAccounts());
        infoPanel.setPreferredSize(new Dimension(20, 40));

        setCashFlowFormLabelAndInfoFont(label, infoLabel);

        JPanel fieldPanel = new JPanel();
        JTextField accountTextField = new JTextField(20);
        fieldPanel.setPreferredSize(new Dimension(20, 200));
        accountTextField.setPreferredSize(new Dimension(20, 50));

        JPanel buttonPanel = new JPanel();
        JButton editAccountButton = new JButton(new EditAccountAction());
        buttonPanel.setPreferredSize(new Dimension(20, 50));

        addAllComponentToItsPanel(labelPanel, label, infoPanel, infoLabel, fieldPanel, accountTextField, buttonPanel,
                editAccountButton);

        addAllPanelCashFlowForm(editAccount, labelPanel, infoPanel, fieldPanel, buttonPanel);

        window.add(editAccount, BorderLayout.SOUTH);

        fixWindow();
        
        captureAccount(editAccountButton, accountTextField);
    }

    // EFFECTS: initializes the edit category of cash flow form
    public void initEditCategory() {
        editCategory = new JPanel();
        setCashFlowFormLayoutAndSize(editCategory);
        
        JPanel labelPanel = new JPanel();
        JLabel label = new JLabel("Enter category");
        labelPanel.setPreferredSize(new Dimension(20, 40));

        JPanel infoPanel = new JPanel();
        JLabel infoLabel = new JLabel(capturedStatus == "debit" ? viewDebitCategory() : viewCreditCategory());
        infoPanel.setPreferredSize(new Dimension(20, 40));

        setCashFlowFormLabelAndInfoFont(label, infoLabel);

        JPanel fieldPanel = new JPanel();
        JTextField categoryTextField = new JTextField(20);
        fieldPanel.setPreferredSize(new Dimension(20, 200));
        categoryTextField.setPreferredSize(new Dimension(20, 50));

        JPanel buttonPanel = new JPanel();
        JButton editCategoryButton = new JButton(new EditCategoryAction());
        buttonPanel.setPreferredSize(new Dimension(20, 50));

        addAllComponentToItsPanel(labelPanel, label, infoPanel, infoLabel, fieldPanel, categoryTextField, buttonPanel,
                editCategoryButton);

        addAllPanelCashFlowForm(editCategory, labelPanel, infoPanel, fieldPanel, buttonPanel);

        window.add(editCategory, BorderLayout.SOUTH);

        fixWindow();

        captureCategory(editCategoryButton, categoryTextField);
    }

    // EFFECTS: initializes the edit date of cash flow form
    public void initEditDate() {
        setCashFlowFormLayoutAndSize(editDate);
        
        JPanel labelPanel = new JPanel();
        JLabel label = new JLabel("Enter date");
        labelPanel.setPreferredSize(new Dimension(20, 40));

        JPanel infoPanel = new JPanel();
        JLabel infoLabel = new JLabel("YYYY/MM/DD");
        infoPanel.setPreferredSize(new Dimension(20, 40));

        setCashFlowFormLabelAndInfoFont(label, infoLabel);

        JPanel fieldPanel = new JPanel();
        JTextField dateTextField = new JTextField(20);
        fieldPanel.setPreferredSize(new Dimension(20, 200));
        dateTextField.setPreferredSize(new Dimension(20, 50));

        JPanel buttonPanel = new JPanel();
        JButton editDateButton = new JButton(new EditDateAction());
        buttonPanel.setPreferredSize(new Dimension(20, 50));

        addAllComponentToItsPanel(labelPanel, label, infoPanel, infoLabel, fieldPanel, dateTextField, buttonPanel,
                editDateButton);

        addAllPanelCashFlowForm(editDate, labelPanel, infoPanel, fieldPanel, buttonPanel);

        window.add(editDate, BorderLayout.SOUTH);

        fixWindow();

        captureDate(editDateButton, dateTextField);
    }

    // EFFECTS: initializes the edit time of cash flow form
    public void initEditTime() {
        setCashFlowFormLayoutAndSize(editTime);
        
        JPanel labelPanel = new JPanel();
        JLabel label = new JLabel("Enter time");
        labelPanel.setPreferredSize(new Dimension(20, 40));

        JPanel infoPanel = new JPanel();
        JLabel infoLabel = new JLabel("HH:MM");
        infoPanel.setPreferredSize(new Dimension(20, 40));

        setCashFlowFormLabelAndInfoFont(label, infoLabel);

        JPanel fieldPanel = new JPanel();
        JTextField timeTextField = new JTextField(20);
        fieldPanel.setPreferredSize(new Dimension(20, 200));
        timeTextField.setPreferredSize(new Dimension(20, 50));

        JPanel buttonPanel = new JPanel();
        JButton editTimeButton = new JButton(new EditTimeAction());
        buttonPanel.setPreferredSize(new Dimension(20, 50));

        addAllComponentToItsPanel(labelPanel, label, infoPanel, infoLabel, fieldPanel, timeTextField, buttonPanel,
                editTimeButton);

        addAllPanelCashFlowForm(editTime, labelPanel, infoPanel, fieldPanel, buttonPanel);

        window.add(editTime, BorderLayout.SOUTH);

        fixWindow();

        captureTime(editTimeButton, timeTextField);
    }

    // EFFECTS: initializes the edit description of cash flow form
    public void initEditDescription() {
        setCashFlowFormLayoutAndSize(editDescription);
        
        JPanel labelPanel = new JPanel();
        JLabel label = new JLabel("Enter description");
        labelPanel.setPreferredSize(new Dimension(20, 40));

        JPanel infoPanel = new JPanel();
        JLabel infoLabel = new JLabel("");
        infoPanel.setPreferredSize(new Dimension(20, 40));

        setCashFlowFormLabelAndInfoFont(label, infoLabel);

        JPanel fieldPanel = new JPanel();
        JTextField descriptionTextField = new JTextField(20);
        fieldPanel.setPreferredSize(new Dimension(20, 200));
        descriptionTextField.setPreferredSize(new Dimension(20, 50));

        JPanel buttonPanel = new JPanel();
        JButton editDescriptionButton = new JButton(new EditDescriptionAction());
        buttonPanel.setPreferredSize(new Dimension(20, 50));

        addAllComponentToItsPanel(labelPanel, label, infoPanel, infoLabel, fieldPanel, descriptionTextField,
                buttonPanel, editDescriptionButton);

        addAllPanelCashFlowForm(editDescription, labelPanel, infoPanel, fieldPanel, buttonPanel);

        window.add(editDescription, BorderLayout.SOUTH);

        fixWindow();

        captureDescription(editDescriptionButton, descriptionTextField);
    }

    // EFFECTS: initializes the edit amount of cash flow form
    public void initEditAmount() {
        setCashFlowFormLayoutAndSize(editAmount);
        
        JPanel labelPanel = new JPanel();
        JLabel label = new JLabel("Enter amount of money");
        labelPanel.setPreferredSize(new Dimension(20, 40));

        JPanel infoPanel = new JPanel();
        JLabel infoLabel = new JLabel("Number only");
        infoPanel.setPreferredSize(new Dimension(20, 40));

        setCashFlowFormLabelAndInfoFont(label, infoLabel);

        JPanel fieldPanel = new JPanel();
        JTextField amountTextField = new JTextField(20);
        fieldPanel.setPreferredSize(new Dimension(20, 200));
        amountTextField.setPreferredSize(new Dimension(20, 50));

        JPanel buttonPanel = new JPanel();
        JButton editAmountButton = new JButton(new EditAmountAction());
        buttonPanel.setPreferredSize(new Dimension(20, 50));

        addAllComponentToItsPanel(labelPanel, label, infoPanel, infoLabel, fieldPanel, amountTextField, buttonPanel,
                editAmountButton);

        addAllPanelCashFlowForm(editAmount, labelPanel, infoPanel, fieldPanel, buttonPanel);

        window.add(editAmount, BorderLayout.SOUTH);

        fixWindow();

        captureAmount(editAmountButton, amountTextField);
    }

    // EFFECTS: initializes the menu for removing cash flow
    public void initRemoveMoneyTrackerMenu() {
        removeMoneyTrackerMenu.setLayout(new GridLayout(3, 3));
        removeMoneyTrackerMenu.setPreferredSize(new Dimension(40, 300));

        JButton removeFindByStatusButton = new JButton(new RemoveFindByStatusAction());
        removeFindByStatusButton.setPreferredSize(new Dimension(20, 100));

        JButton removeFindByAccountButton = new JButton(new RemoveFindByAccountAction());
        removeFindByAccountButton.setPreferredSize(new Dimension(20, 100));

        JButton removeFindByCategoryButton = new JButton(new RemoveFindByCategoryAction());
        removeFindByCategoryButton.setPreferredSize(new Dimension(20, 100));

        JButton removeFindByDateButton = new JButton(new RemoveFindByDateAction());
        removeFindByDateButton.setPreferredSize(new Dimension(20, 100));

        JButton backMainMenuButton = new JButton(new BackMainMenu());
        backMainMenuButton.setPreferredSize(new Dimension(20, 100));

        JButton quitButton = new JButton(new QuitAction());
        quitButton.setPreferredSize(new Dimension(20, 100));

        removeMoneyTrackerMenu.add(removeFindByStatusButton);
        removeMoneyTrackerMenu.add(removeFindByAccountButton);
        removeMoneyTrackerMenu.add(removeFindByCategoryButton);
        removeMoneyTrackerMenu.add(removeFindByDateButton);
        removeMoneyTrackerMenu.add(backMainMenuButton);
        removeMoneyTrackerMenu.add(quitButton);

        window.add(removeMoneyTrackerMenu, BorderLayout.SOUTH);

        fixWindow();
    }

    // EFFECTS: initializes the find by status form in remove mode
    public void initRemoveFindByStatus() {
        setCashFlowFormLayoutAndSize(removeFindByStatus);
        
        JPanel labelPanel = new JPanel();
        JLabel label = new JLabel("Enter status");
        labelPanel.setPreferredSize(new Dimension(20, 40));

        JPanel infoPanel = new JPanel();
        JLabel infoLabel = new JLabel("credit/debit");
        infoPanel.setPreferredSize(new Dimension(20, 40));

        setCashFlowFormLabelAndInfoFont(label, infoLabel);

        JPanel fieldPanel = new JPanel();
        JTextField statusTextField = new JTextField(20);
        fieldPanel.setPreferredSize(new Dimension(20, 200));
        statusTextField.setPreferredSize(new Dimension(20, 50));

        JPanel buttonPanel = new JPanel();
        JButton findByStatusButton = new JButton(new OpenRemoveCashFlow());
        findByStatusButton.addActionListener(new DisplayFindByStatusAction());
        buttonPanel.setPreferredSize(new Dimension(20, 50));

        addAllComponentToItsPanel(labelPanel, label, infoPanel, infoLabel, fieldPanel, statusTextField, buttonPanel,
                findByStatusButton);

        addAllPanelCashFlowForm(removeFindByStatus, labelPanel, infoPanel, fieldPanel, buttonPanel);

        window.add(removeFindByStatus, BorderLayout.SOUTH);

        fixWindow();

        captureStatus(findByStatusButton, statusTextField);
    }

    // EFFECTS: initializes the find by account form in remove mode
    public void initRemoveFindByAccount() {
        removeFindByAccount = new JPanel();
        setCashFlowFormLayoutAndSize(removeFindByAccount);
        
        JPanel labelPanel = new JPanel();
        JLabel label = new JLabel("Enter account");
        labelPanel.setPreferredSize(new Dimension(20, 40));

        JPanel infoPanel = new JPanel();
        JLabel infoLabel = new JLabel(viewAccounts());
        infoPanel.setPreferredSize(new Dimension(20, 40));

        setCashFlowFormLabelAndInfoFont(label, infoLabel);

        JPanel fieldPanel = new JPanel();
        JTextField accountTextField = new JTextField(20);
        fieldPanel.setPreferredSize(new Dimension(20, 200));
        accountTextField.setPreferredSize(new Dimension(20, 50));

        JPanel buttonPanel = new JPanel();
        JButton findByAccountButton = new JButton(new OpenRemoveCashFlow());
        findByAccountButton.addActionListener(new DisplayFindByAccountAction());
        buttonPanel.setPreferredSize(new Dimension(20, 50));

        addAllComponentToItsPanel(labelPanel, label, infoPanel, infoLabel, fieldPanel, accountTextField, buttonPanel,
                findByAccountButton);

        addAllPanelCashFlowForm(removeFindByAccount, labelPanel, infoPanel, fieldPanel, buttonPanel);

        window.add(removeFindByAccount, BorderLayout.SOUTH);

        fixWindow();
        
        captureAccount(findByAccountButton, accountTextField);
    }

    // EFFECTS: initializes the find by category form in remove mode
    public void initRemoveFindByCategory() {
        removeFindByCategory = new JPanel();
        setCashFlowFormLayoutAndSize(removeFindByCategory);
        
        JPanel labelPanel = new JPanel();
        JLabel label = new JLabel("Enter category");
        labelPanel.setPreferredSize(new Dimension(20, 40));

        JPanel infoPanel = new JPanel();
        JLabel infoLabel = new JLabel(capturedStatus == "debit" ? viewDebitCategory() : viewCreditCategory());
        infoPanel.setPreferredSize(new Dimension(20, 40));

        setCashFlowFormLabelAndInfoFont(label, infoLabel);

        JPanel fieldPanel = new JPanel();
        JTextField categoryTextField = new JTextField(20);
        fieldPanel.setPreferredSize(new Dimension(20, 200));
        categoryTextField.setPreferredSize(new Dimension(20, 50));

        JPanel buttonPanel = new JPanel();
        JButton findByCategoryButton = new JButton(new OpenRemoveCashFlow());
        findByCategoryButton.addActionListener(new DisplayFindByCategoryAction());
        buttonPanel.setPreferredSize(new Dimension(20, 50));

        addAllComponentToItsPanel(labelPanel, label, infoPanel, infoLabel, fieldPanel, categoryTextField, buttonPanel,
                findByCategoryButton);

        addAllPanelCashFlowForm(removeFindByCategory, labelPanel, infoPanel, fieldPanel, buttonPanel);

        window.add(removeFindByCategory, BorderLayout.SOUTH);

        fixWindow();

        captureCategory(findByCategoryButton, categoryTextField);
    }

    // EFFECTS: initializes the add date of cash flow form
    public void initRemoveFindByDate() {
        setCashFlowFormLayoutAndSize(removeFindByDate);
        
        JPanel labelPanel = new JPanel();
        JLabel label = new JLabel("Enter date");
        labelPanel.setPreferredSize(new Dimension(20, 40));

        JPanel infoPanel = new JPanel();
        JLabel infoLabel = new JLabel("YYYY/MM/DD");
        infoPanel.setPreferredSize(new Dimension(20, 40));

        setCashFlowFormLabelAndInfoFont(label, infoLabel);

        JPanel fieldPanel = new JPanel();
        JTextField dateTextField = new JTextField(20);
        fieldPanel.setPreferredSize(new Dimension(20, 200));
        dateTextField.setPreferredSize(new Dimension(20, 50));

        JPanel buttonPanel = new JPanel();
        JButton findByDateButton = new JButton(new OpenRemoveCashFlow());
        findByDateButton.addActionListener(new DisplayFindByDateAction());
        buttonPanel.setPreferredSize(new Dimension(20, 50));

        addAllComponentToItsPanel(labelPanel, label, infoPanel, infoLabel, fieldPanel, dateTextField, buttonPanel,
                findByDateButton);

        addAllPanelCashFlowForm(removeFindByDate, labelPanel, infoPanel, fieldPanel, buttonPanel);

        window.add(removeFindByDate, BorderLayout.SOUTH);

        fixWindow();

        captureDate(findByDateButton, dateTextField);
    }

    // EFFECTS: initializes the add date of cash flow form
    public void initRemoveCashFlow() {
        setCashFlowFormLayoutAndSize(removeCashFlow);
        
        JPanel labelPanel = new JPanel();
        JLabel label = new JLabel("Choose the number of cash flow to be deleted");
        labelPanel.setPreferredSize(new Dimension(20, 40));

        JPanel infoPanel = new JPanel();
        JLabel infoLabel = new JLabel("e.g: 1/2/...");
        infoPanel.setPreferredSize(new Dimension(20, 40));

        setCashFlowFormLabelAndInfoFont(label, infoLabel);

        JPanel fieldPanel = new JPanel();
        JTextField numTextField = new JTextField(20);
        fieldPanel.setPreferredSize(new Dimension(20, 200));
        numTextField.setPreferredSize(new Dimension(20, 50));

        JPanel buttonPanel = new JPanel();
        JButton removeButton = new JButton(new RemoveCashFlowAction());
        buttonPanel.setPreferredSize(new Dimension(20, 50));

        labelPanel.add(label);
        infoPanel.add(infoLabel);
        fieldPanel.add(numTextField);
        buttonPanel.add(removeButton);

        addAllPanelCashFlowForm(removeCashFlow, labelPanel, infoPanel, fieldPanel, buttonPanel);

        window.add(removeCashFlow, BorderLayout.SOUTH);

        fixWindow();

        captureIndex(removeButton, numTextField);
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

    /*
     * Represents a class with the action to open the money tracker menu
     */
    private class MoneyTrackerMenuAction extends AbstractAction {
        MoneyTrackerMenuAction() {
            super("Money Tracker Menu");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllMenuInvinsible();
            moneyTrackerMenu.setVisible(true);
        }
    }

    /*
     * Represents a class with the action to open the category menu
     */
    private class CategoryMenuAction extends AbstractAction {
        CategoryMenuAction() {
            super("Category Menu");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllMenuInvinsible();
            categoryMenu.setVisible(true);
        }
    }

    /*
     * Represents a class with the action to open the account menu
     */
    private class AccountMenuAction extends AbstractAction {
        AccountMenuAction() {
            super("Account Menu");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllMenuInvinsible();
            accountMenu.setVisible(true);
        }
    }

    /*
     * Represents a class with the action to open the save menu
     */
    private class SaveMenuAction extends AbstractAction {
        SaveMenuAction() {
            super("Save Menu");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllMenuInvinsible();
            saveMenu.setVisible(true);
        }
    }

    /*
     * Represents a class with the action to open the load menu
     */
    private class LoadMenuAction extends AbstractAction {
        LoadMenuAction() {
            super("Load Menu");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllMenuInvinsible();
            loadMenu.setVisible(true);
        }
    }

    /*
     * Represents a class with the action to exit MoneyTrackerApp
     */
    private class QuitAction extends AbstractAction {
        QuitAction() {
            super("Quit MoneyTrackerApp");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllInvinsible();
            saveMenuBeforeQuit.setVisible(true);
        }
    }

    /*
     * Represents a class with the action to exit MoneyTrackerApp
     */
    private class Quit extends AbstractAction {
        Quit() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    /*
     * Represents a class with the action to load the data state
     */
    private class LoadDataAction extends AbstractAction {
        LoadDataAction() {
            super("Load Data");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            loadData();
            setAllMenuInvinsible();
            mainMenu.setVisible(true);
        }
    }

    /*
     * Represents a class with the action to not load the data state
     */
    private class NotLoadDataAction extends AbstractAction {
        NotLoadDataAction() {
            super("Do Not Load Data");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllMenuInvinsible();
            mainMenu.setVisible(true);
        }
    }

    /*
     * Represents a class with the action to add new money tracker
     */
    private class AddMoneyTrackerAction extends AbstractAction {
        AddMoneyTrackerAction() {
            super("Add a new cash flow");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllMenuInvinsible();
            addStatusMoneyTrackerForm.setVisible(true);
        }
    }

    /*
     * Represents a class with the action to open the view money tracker menu
     */
    private class ViewMoneyTrackerMenu extends AbstractAction {
        ViewMoneyTrackerMenu() {
            super("View cash flow");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllInvinsible();
            viewMoneyTrackerMenu.setVisible(true);
        }
    }

    /*
     * Represents a class with the action to show all cash flows
     */
    private class ViewAllCashFlowAction extends AbstractAction {
        ViewAllCashFlowAction() {
            super("View all cash flow");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            cashflowsPanel.setVisible(false);
            cashflowsPanel = new ImagePanel();
            cashflowsPanel.setLayout(new BoxLayout(cashflowsPanel, BoxLayout.Y_AXIS));
            cashflowsPanel.initImagePanel(imagePanelWidth, imagePanelHeight);
            cashflowsPanel.setBackground(Color.LIGHT_GRAY);

            JPanel listPanel = new JPanel();

            JLabel cashflows = new JLabel(displayCashFlow(moneySummary.getCashflows()));
            listPanel.add(cashflows);

            cashflowsPanel.add(listPanel);

            window.add(cashflowsPanel, BorderLayout.NORTH);

            ip.setVisible(false);

            JScrollPane scrollCashFlow = new JScrollPane(listPanel);
            scrollCashFlow.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollCashFlow.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            cashflowsPanel.add(scrollCashFlow, BorderLayout.CENTER);

        }
    }

    /*
     * Represents a class with the action to open column to show monthly cash flow
     */
    private class ViewMonthlyCashFlowAction extends AbstractAction {
        ViewMonthlyCashFlowAction() {
            super("View monthly cash flow");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            viewMoneyTrackerMenu.setVisible(false);
            viewMonthlyMoneyTracker.setVisible(true);
            viewMonthlyCashFlowForm.setVisible(true);
        }
    }

    /*
     * Represents a class with the action to display cashflow based on month and year
     */
    private class DisplayMonthlyCashFlowAction extends AbstractAction {
        DisplayMonthlyCashFlowAction() {
            super("View");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            cashflowsPanel.setVisible(false);
            cashflowsPanel = new ImagePanel();
            cashflowsPanel.setLayout(new BoxLayout(cashflowsPanel, BoxLayout.Y_AXIS));
            cashflowsPanel.initImagePanel(imagePanelWidth, imagePanelHeight);
            cashflowsPanel.setBackground(Color.LIGHT_GRAY);

            JPanel listPanel = new JPanel();

            JLabel cashflows = new JLabel(displayMonthlyCashFlow(capturedYear, capturedMonth));
            listPanel.add(cashflows);

            cashflowsPanel.add(listPanel);

            window.add(cashflowsPanel, BorderLayout.NORTH);

            ip.setVisible(false);

            JScrollPane scrollCashFlow = new JScrollPane(listPanel);
            scrollCashFlow.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollCashFlow.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            cashflowsPanel.add(scrollCashFlow, BorderLayout.CENTER);
        }
    }

    /*
     * Represents a class with the action to return to money tracker menu
     */
    private class BackCashFlowMenuAction extends AbstractAction {
        BackCashFlowMenuAction() {
            super("Back to money tracker menu");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllInvinsible();
            cashflowsPanel.setVisible(false);
            ip.setVisible(true);
            moneyTrackerMenu.setVisible(true);
        }
    }

    /*
     * Represents a class with the action to edit a money tracker
     */
    private class EditMoneyTracker extends AbstractAction {
        EditMoneyTracker() {
            super("Edit a cash flow");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllInvinsible();
            editMoneyTrackerMenu.setVisible(true);
        }
    }

    /*
     * Represents a class with the action to open the column to find the cash flow by status
     */
    private class EditFindByStatusAction extends AbstractAction {
        EditFindByStatusAction() {
            super("Find by status");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllInvinsible();
            initEditFindByStatus();
            editFindByStatus.setVisible(true);
        }
    }

    /*
     * Represents a class with the action to open the column to find the cash flow by account
     */
    private class EditFindByAccountAction extends AbstractAction {
        EditFindByAccountAction() {
            super("Find by account");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllInvinsible();
            editFindByAccount.setVisible(true);
            initEditFindByAccount();
        }
    }

    /*
     * Represents a class with the action to open the column to find the cash flow by category
     */
    private class EditFindByCategoryAction extends AbstractAction {
        EditFindByCategoryAction() {
            super("Find by category");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllInvinsible();
            editFindByCategory.setVisible(true);
            initEditFindByCategory();
        }
    }

    /*
     * Represents a class with the action to open the column to find the cash flow by date
     */
    private class EditFindByDateAction extends AbstractAction {
        EditFindByDateAction() {
            super("Find by date");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllInvinsible();
            editFindByDate.setVisible(true);
        }
    }

    /*
     * Represents a class with the action to open the options to choose what to edit
     */
    private class OpenEditCashFlow extends AbstractAction {
        OpenEditCashFlow() {
            super("Find");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllMenuInvinsible();
            editChooseCashFlow.setVisible(true);
        }
    }

    /*
     * Represents a class with the action to open the options to choose what to edit
     */
    private class EditChooseCashFlowAction extends AbstractAction {
        EditChooseCashFlowAction() {
            super("Choose");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllMenuInvinsible();
            editChooseCashFlow.setVisible(true);
            List<CashFlow> chosenCashFlow = new ArrayList<>();
            chosenCashFlow.add(filteredCashFlows.get(capturedNumber - 1));

            setAllInvinsible();

            cashflowsPanel.setVisible(false);
            cashflowsPanel = new ImagePanel();
            cashflowsPanel.setLayout(new BoxLayout(cashflowsPanel, BoxLayout.Y_AXIS));
            cashflowsPanel.initImagePanel(imagePanelWidth, imagePanelHeight);
            cashflowsPanel.setBackground(Color.LIGHT_GRAY);

            JPanel listPanel = new JPanel();

            JLabel cashflows = new JLabel(displayCashFlow(chosenCashFlow));
            listPanel.add(cashflows);

            cashflowsPanel.add(listPanel);

            window.add(cashflowsPanel, BorderLayout.NORTH);

            JScrollPane scrollCashFlow = new JScrollPane(listPanel);
            scrollCashFlow.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollCashFlow.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            cashflowsPanel.add(scrollCashFlow, BorderLayout.CENTER);

            editOptionCashFlow.setVisible(true);
        }
    }

    /*
     * Represents a class to open edit status form
     */
    private class OpenEditStatusAction extends AbstractAction {
        OpenEditStatusAction() {
            super("Edit status");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllMenuInvinsible();
            editStatus.setVisible(true);
        }
    }

    /*
     * Represents a class to open edit account form
     */
    private class OpenEditAccountAction extends AbstractAction {
        OpenEditAccountAction() {
            super("Edit account");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllMenuInvinsible();
            initEditAccount();
            editAccount.setVisible(true);
        }
    }

    /*
     * Represents a class to open edit category form
     */
    private class OpenEditCategoryAction extends AbstractAction {
        OpenEditCategoryAction() {
            super("Edit category");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllMenuInvinsible();
            initEditCategory();
            editCategory.setVisible(true);
        }
    }

    /*
     * Represents a class to open edit date form
     */
    private class OpenEditDateAction extends AbstractAction {
        OpenEditDateAction() {
            super("Edit date");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllMenuInvinsible();
            editDate.setVisible(true);
        }
    }

    /*
     * Represents a class to open edit time form
     */
    private class OpenEditTimeAction extends AbstractAction {
        OpenEditTimeAction() {
            super("Edit time");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllMenuInvinsible();
            editTime.setVisible(true);
        }
    }

    /*
     * Represents a class to open edit description form
     */
    private class OpenEditDescriptionAction extends AbstractAction {
        OpenEditDescriptionAction() {
            super("Edit description");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllMenuInvinsible();
            editDescription.setVisible(true);
        }
    }

    /*
     * Represents a class to open edit amount form
     */
    private class OpenEditAmountAction extends AbstractAction {
        OpenEditAmountAction() {
            super("Edit amount");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllMenuInvinsible();
            editAmount.setVisible(true);
        }
    }

    /*
     * Represents a class to edit a cash flow status
     */
    private class EditStatusAction extends AbstractAction {
        EditStatusAction() {
            super("Edit");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllMenuInvinsible();
            moneyTrackerMenu.setVisible(true);

            CashFlow chosen = filteredCashFlows.get(0);
            setStatus(chosen, capturedStatus);

            cashflowsPanel.setVisible(false);
            ip.setVisible(true);
        }
    }

    /*
     * Represents a class to edit a cash flow account
     */
    private class EditAccountAction extends AbstractAction {
        EditAccountAction() {
            super("Edit");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllMenuInvinsible();
            moneyTrackerMenu.setVisible(true);

            CashFlow chosen = filteredCashFlows.get(0);
            setAccount(chosen, capturedAccount);

            cashflowsPanel.setVisible(false);
            ip.setVisible(true);
        }
    }

    /*
     * Represents a class to edit a cash flow category
     */
    private class EditCategoryAction extends AbstractAction {
        EditCategoryAction() {
            super("Edit");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllMenuInvinsible();
            moneyTrackerMenu.setVisible(true);

            CashFlow chosen = filteredCashFlows.get(0);
            String chosenStatus = chosen.getStatus();
            setCategory(chosen, capturedCategory, chosenStatus);

            cashflowsPanel.setVisible(false);
            ip.setVisible(true);
        }
    }

    /*
     * Represents a class to edit a cash flow date
     */
    private class EditDateAction extends AbstractAction {
        EditDateAction() {
            super("Edit");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllMenuInvinsible();
            moneyTrackerMenu.setVisible(true);

            CashFlow chosen = filteredCashFlows.get(0);
            setDate(chosen, capturedDate);

            cashflowsPanel.setVisible(false);
            ip.setVisible(true);
        }
    }

    /*
     * Represents a class to edit a cash flow time
     */
    private class EditTimeAction extends AbstractAction {
        EditTimeAction() {
            super("Edit");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllMenuInvinsible();
            moneyTrackerMenu.setVisible(true);

            CashFlow chosen = filteredCashFlows.get(0);
            setTime(chosen, capturedTime);

            cashflowsPanel.setVisible(false);
            ip.setVisible(true);
        }
    }

    /*
     * Represents a class to edit a cash flow description
     */
    private class EditDescriptionAction extends AbstractAction {
        EditDescriptionAction() {
            super("Edit");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllMenuInvinsible();
            moneyTrackerMenu.setVisible(true);

            CashFlow chosen = filteredCashFlows.get(0);
            setDescription(chosen, capturedDescription);

            cashflowsPanel.setVisible(false);
            ip.setVisible(true);
        }
    }

    /*
     * Represents a class to edit a cash flow amount
     */
    private class EditAmountAction extends AbstractAction {
        EditAmountAction() {
            super("Edit");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllMenuInvinsible();
            moneyTrackerMenu.setVisible(true);

            CashFlow chosen = filteredCashFlows.get(0);
            setAmount(chosen, String.valueOf(capturedAmount));

            cashflowsPanel.setVisible(false);
            ip.setVisible(true);
        }
    }

    /*
     * Represents a class with the action to remove a money tracker
     */
    private class RemoveMoneyTracker extends AbstractAction {
        RemoveMoneyTracker() {
            super("Remove a cash flow");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllMenuInvinsible();
            removeMoneyTrackerMenu.setVisible(true);
        }
    }

    /*
     * Represents a class with the action to open the column to find the cash flow by status
     */
    private class RemoveFindByStatusAction extends AbstractAction {
        RemoveFindByStatusAction() {
            super("Find by status");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllInvinsible();
            removeFindByStatus.setVisible(true);
        }
    }

    /*
     * Represents a class with the action to open the column to find the cash flow by account
     */
    private class RemoveFindByAccountAction extends AbstractAction {
        RemoveFindByAccountAction() {
            super("Find by account");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllInvinsible();
            removeFindByAccount.setVisible(true);
            initRemoveFindByAccount();
        }
    }

    /*
     * Represents a class with the action to open the column to find the cash flow by category
     */
    private class RemoveFindByCategoryAction extends AbstractAction {
        RemoveFindByCategoryAction() {
            super("Find by category");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllInvinsible();
            removeFindByCategory.setVisible(true);
            initRemoveFindByCategory();
        }
    }

    /*
     * Represents a class with the action to open the column to find the cash flow by date
     */
    private class RemoveFindByDateAction extends AbstractAction {
        RemoveFindByDateAction() {
            super("Find by date");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllInvinsible();
            removeFindByDate.setVisible(true);
        }
    }

    /*
     * Represents a class with the action to display cashflows found by status
     */
    private class DisplayFindByStatusAction extends AbstractAction {
        DisplayFindByStatusAction() {
            super("Find");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllInvinsible();

            cashflowsPanel.setVisible(false);
            cashflowsPanel = new ImagePanel();
            cashflowsPanel.setLayout(new BoxLayout(cashflowsPanel, BoxLayout.Y_AXIS));
            cashflowsPanel.initImagePanel(imagePanelWidth, imagePanelHeight);
            cashflowsPanel.setBackground(Color.LIGHT_GRAY);

            JPanel listPanel = new JPanel();

            filteredCashFlows = filterByStatus(moneySummary.getCashflows(), capturedStatus);
            JLabel cashflows = new JLabel(displayCashFlow(filteredCashFlows));
            listPanel.add(cashflows);

            cashflowsPanel.add(listPanel);

            window.add(cashflowsPanel, BorderLayout.NORTH);

            ip.setVisible(false);

            JScrollPane scrollCashFlow = new JScrollPane(listPanel);
            scrollCashFlow.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollCashFlow.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            cashflowsPanel.add(scrollCashFlow, BorderLayout.CENTER);
        }
    }

    /*
     * Represents a class with the action to display cashflows found by account
     */
    private class DisplayFindByAccountAction extends AbstractAction {
        DisplayFindByAccountAction() {
            super("Find");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllInvinsible();

            cashflowsPanel.setVisible(false);
            cashflowsPanel = new ImagePanel();
            cashflowsPanel.setLayout(new BoxLayout(cashflowsPanel, BoxLayout.Y_AXIS));
            cashflowsPanel.initImagePanel(imagePanelWidth, imagePanelHeight);
            cashflowsPanel.setBackground(Color.LIGHT_GRAY);

            JPanel listPanel = new JPanel();

            filteredCashFlows = filterByAccount(moneySummary.getCashflows(), capturedAccount);
            JLabel cashflows = new JLabel(displayCashFlow(filteredCashFlows));
            listPanel.add(cashflows);

            cashflowsPanel.add(listPanel);

            window.add(cashflowsPanel, BorderLayout.NORTH);

            ip.setVisible(false);

            JScrollPane scrollCashFlow = new JScrollPane(listPanel);
            scrollCashFlow.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollCashFlow.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            cashflowsPanel.add(scrollCashFlow, BorderLayout.CENTER);
        }
    }

    /*
     * Represents a class with the action to display cashflows found by category
     */
    private class DisplayFindByCategoryAction extends AbstractAction {
        DisplayFindByCategoryAction() {
            super("Find");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllInvinsible();

            cashflowsPanel.setVisible(false);
            cashflowsPanel = new ImagePanel();
            cashflowsPanel.setLayout(new BoxLayout(cashflowsPanel, BoxLayout.Y_AXIS));
            cashflowsPanel.initImagePanel(imagePanelWidth, imagePanelHeight);
            cashflowsPanel.setBackground(Color.LIGHT_GRAY);

            JPanel listPanel = new JPanel();

            filteredCashFlows = filterByCategory(moneySummary.getCashflows(), capturedCategory);
            JLabel cashflows = new JLabel(displayCashFlow(filteredCashFlows));
            listPanel.add(cashflows);

            cashflowsPanel.add(listPanel);

            window.add(cashflowsPanel, BorderLayout.NORTH);

            ip.setVisible(false);

            JScrollPane scrollCashFlow = new JScrollPane(listPanel);
            scrollCashFlow.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollCashFlow.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            cashflowsPanel.add(scrollCashFlow, BorderLayout.CENTER);
        }
    }

    /*
     * Represents a class with the action to display cashflows found by date
     */
    private class DisplayFindByDateAction extends AbstractAction {
        DisplayFindByDateAction() {
            super("Find");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllInvinsible();

            cashflowsPanel.setVisible(false);
            cashflowsPanel = new ImagePanel();
            cashflowsPanel.setLayout(new BoxLayout(cashflowsPanel, BoxLayout.Y_AXIS));
            cashflowsPanel.initImagePanel(imagePanelWidth, imagePanelHeight);
            cashflowsPanel.setBackground(Color.LIGHT_GRAY);

            JPanel listPanel = new JPanel();

            filteredCashFlows = filterByDate(moneySummary.getCashflows(), capturedDate);
            JLabel cashflows = new JLabel(displayCashFlow(filteredCashFlows));
            listPanel.add(cashflows);

            cashflowsPanel.add(listPanel);

            ip.setVisible(false);

            window.add(cashflowsPanel, BorderLayout.NORTH);

            JScrollPane scrollCashFlow = new JScrollPane(listPanel);
            scrollCashFlow.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollCashFlow.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            cashflowsPanel.add(scrollCashFlow, BorderLayout.CENTER);
            
        }
    }

    /*
     * Represents a class with the action to show the column to choose which cash flow to remove
     */
    private class OpenRemoveCashFlow extends AbstractAction {
        OpenRemoveCashFlow() {
            super("Find");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllMenuInvinsible();
            removeCashFlow.setVisible(true);
        }
    }

    /*
     * Represents a class with the action to show the column to choose which cash flow to remove
     */
    private class RemoveCashFlowAction extends AbstractAction {
        RemoveCashFlowAction() {
            super("Remove");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllMenuInvinsible();
            removeMoneyTrackerMenu.setVisible(true);
            CashFlow chosenCashFlow = filteredCashFlows.get(capturedNumber - 1);
            moneySummary.getCashflows().remove(chosenCashFlow);
            cashflowsPanel.setVisible(false);
            ip.setVisible(true);
        }
    }

    /*
     * Represents a class with the action to return to the main menu
     */
    private class BackMainMenu extends AbstractAction {
        BackMainMenu() {
            super("Back to main menu");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllMenuInvinsible();
            mainMenu.setVisible(true);
        }
    }

    /*
     * Represents a class with the action to save the data state
     */
    private class SaveDataAction extends AbstractAction {
        SaveDataAction() {
            super("Save data");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            saveData();
            setAllMenuInvinsible();
            mainMenu.setVisible(true);
        }
    }

    /*
     * Represents a class with the action to not save the data state
     */
    private class NotSaveDataAction extends AbstractAction {
        NotSaveDataAction() {
            super("Do not save data");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllMenuInvinsible();
            mainMenu.setVisible(true);
        }
    }

    /*
     * Represents a class with the action to open the credit category menu
     */
    private class CreditCategoryMenuAction extends AbstractAction {
        CreditCategoryMenuAction() {
            super("Credit Category Menu");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllMenuInvinsible();
            creditCategoryMenu.setVisible(true);
        }
    }

    /*
     * Represents a class with the action to open the debit category menu
     */
    private class DebitCategoryMenuAction extends AbstractAction {
        DebitCategoryMenuAction() {
            super("Debit Category Menu");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllMenuInvinsible();
            debitCategoryMenu.setVisible(true);
        }
    }

    /*
     * Represents a class with the action to add a debit category
     */
    private class AddDebitCategoryAction extends AbstractAction {
        AddDebitCategoryAction() {
            super("Add a debit category");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO
        }
    }

    /*
     * Represents a class with the action to delete a debit category
     */
    private class DeleteDebitCategoryAction extends AbstractAction {
        DeleteDebitCategoryAction() {
            super("Delete a debit category");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO
        }
    }

    /*
     * Represents a class with the action to view debit categories
     */
    private class ViewDebitCategoryAction extends AbstractAction {
        ViewDebitCategoryAction() {
            super("View all debit category");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TOOD
        }
    }

    /*
     * Represents a class with the action to add a credit category
     */
    private class AddCreditCategoryAction extends AbstractAction {
        AddCreditCategoryAction() {
            super("Add a credit category");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO
        }
    }

    /*
     * Represents a class with the action to delete a credit category
     */
    private class DeleteCreditCategoryAction extends AbstractAction {
        DeleteCreditCategoryAction() {
            super("Delete a credit category");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO
        }
    }

    /*
     * Represents a class with the action to view credit categories
     */
    private class ViewCreditCategoryAction extends AbstractAction {
        ViewCreditCategoryAction() {
            super("View all credit category");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO
        }
    }

    /*
     * Represents a class with the action to add an account
     */
    private class AddAccountAction extends AbstractAction {
        AddAccountAction() {
            super("Add an account");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO
        }
    }

    /*
     * Represents a class with the action to delete an account
     */
    private class DeleteAccountAction extends AbstractAction {
        DeleteAccountAction() {
            super("Delete an account");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO
        }
    }

    /*
     * Represents a class with the action to view list of all accounts
     */
    private class ViewListOfAccountsAction extends AbstractAction {
        ViewListOfAccountsAction() {
            super("View all accounts");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO
        }
    }

    /*
     * Represents a class with the action to view an account balance
     */
    private class ViewAnAccountBalanceAction extends AbstractAction {
        ViewAnAccountBalanceAction() {
            super("View an account balance");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO
        }
    }

    /*
     * Represents a class with the action to direct to add account section in cash flow form
     */
    private class ContinueToAddAccount extends AbstractAction {
        ContinueToAddAccount() {
            super("Next");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllInvinsible();
            initAddAccountCashFlowColumn();
        }
    }

    /*
     * Represents a class with the action to direct to add category section in cash flow form
     */
    private class ContinueToAddCategory extends AbstractAction {
        ContinueToAddCategory() {
            super("Next");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllInvinsible();
            initAddCategoryCashFlowColumn();
        }
    }

    /*
     * Represents a class with the action to direct to add date section in cash flow form
     */
    private class ContinueToAddDate extends AbstractAction {
        ContinueToAddDate() {
            super("Next");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllInvinsible();
            addDateMoneyTrackerForm.setVisible(true);
        }
    }

    /*
     * Represents a class with the action to direct to add time section in cash flow form
     */
    private class ContinueToAddTime extends AbstractAction {
        ContinueToAddTime() {
            super("Next");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllInvinsible();
            addTimeMoneyTrackerForm.setVisible(true);
        }
    }

    /*
     * Represents a class with the action to direct to add description section in cash flow form
     */
    private class ContinueToAddDescription extends AbstractAction {
        ContinueToAddDescription() {
            super("Next");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllInvinsible();
            addDescriptionMoneyTrackerForm.setVisible(true);
        }
    }

    /*
     * Represents a class with the action to direct to add amount section in cash flow form
     */
    private class ContinueToAddAmount extends AbstractAction {
        ContinueToAddAmount() {
            super("Next");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllInvinsible();
            addAmountMoneyTrackerForm.setVisible(true);
        }
    }

    /*
     * Represents a class with the action to direct to add amount section in cash flow form
     */
    private class ContinueToAddCashFlowAction extends AbstractAction {
        ContinueToAddCashFlowAction() {
            super("Add cashflow");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setAllInvinsible();
            CashFlow cf = new CashFlow(capturedStatus, capturedAccount, capturedCategory, capturedDate, capturedTime,
                    capturedDescription, capturedAmount);
            moneySummary.addCashFlow(cf);
            moneyTrackerMenu.setVisible(true);
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
    public String displayAccountList() {
        List<String> accounts = this.moneySummary.getAccounts();
        String accountList = "";
        for (int i = 0; i < accounts.size(); i++) {
            accountList += Integer.toString(i + 1) + ". " + accounts.get(i);
            if (i != accounts.size() - 1) {
                accountList += "\n";
            }
        }
        System.out.println(accountList);
        return accountList;
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
        setStatus(cf, input);
        editStatus(cf);
    }

    // MODIFIES: cf
    // EFFECTS: sets the inputted status to the selected cash flow
    private void setStatus(CashFlow cf, String input) {
        if (input.equals("credit")) {
            cf.setAsCredit();
            System.out.println("\nThe cash flow has been edited");
        } else {
            cf.setAsDebit();
            System.out.println("\nThe cash flow has been edited");
        } 
    }

    // MODIFIES: cf
    // EFFECTS: changes the account of the CashFlow object
    public void editAccount(CashFlow cf) {
        spaceSeparator();
        System.out.println("Please specify the new account");
        displayAccountList();
        String account = this.scanner.nextLine();
        setAccount(cf, account);
        editAccount(cf);
    }

    // MODIFIES: cf
    // EFFECTS: sets the inputted account to the selected cash flow
    private void setAccount(CashFlow cf, String input) {
        if (isValidAccount(input)) {
            cf.setAccount(input);
            System.out.println("\nThe cash flow has been edited");
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
        }
        setCategory(cf, category, status);
        editCategory(cf);
    }

    // MODIFIES: cf
    // EFFECTS: sets the inputted category to the selected cash flow
    private void setCategory(CashFlow cf, String input, String status) {
        if (status.equals("credit")) {
            if (isValidCreditCategory(input)) {
                cf.setCategory(input);
                System.out.println("\nThe cash flow has been edited");
            }
        } else if (status.equals("debit")) {
            if (isValidDebitCategory(input)) {
                cf.setCategory(input);
                System.out.println("\nThe cash flow has been edited");
            }
        } 
    }

    // MODIFIES: cf
    // EFFECTS: changes the date of the CashFlow object
    public void editDate(CashFlow cf) {
        spaceSeparator();
        System.out.println("Please specify the new date \n");
        String date = this.scanner.nextLine();
        setDate(cf, date);
        editDate(cf);
    }

    // MODIFIES: cf
    // EFFECTS: sets the inputted date to the selected cash flow
    private void setDate(CashFlow cf, String input) {
        String format = "yyyy/MM/dd";
        if (isValidDate(input, format)) {
            cf.setDate(input);
            System.out.println("\nThe cash flow has been edited");
        }
    }

    // MODIFIES: cf
    // EFFECTS: changes the time of the CashFlow object
    public void editTime(CashFlow cf) {
        spaceSeparator();
        System.out.println("Please specify the new time \n");
        String time = this.scanner.nextLine();
        setTime(cf, time);
        editTime(cf);
    }

    // MODIFIES: cf
    // EFFECTS: sets the inputted time to the selected cash flow
    private void setTime(CashFlow cf, String input) {
        if (isValidTime(input)) {
            cf.setTime(input);
            System.out.println("\nThe cash flow has been edited");
        }
    }

    // MODIFIES: cf
    // EFFECTS: changes the category of the CashFlow object
    public void editDescription(CashFlow cf) {
        spaceSeparator();
        System.out.println("Please specify the new category \n");
        String description = this.scanner.nextLine();
        setDescription(cf, description);
    }

    // MODIFIES: cf
    // EFFECTS: sets the inputted description to the selected cash flow
    private void setDescription(CashFlow cf, String input) {
        cf.setDescription(input);
        System.out.println("\nThe cash flow has been edited");
    }

    // MODIFIES: cf
    // EFFECTS: changes the amount of the CashFlow object
    public void editAmount(CashFlow cf) {
        spaceSeparator();
        System.out.println("Please specify the new amount \n");
        String amount = this.scanner.nextLine();
        setAmount(cf, amount);
        editAmount(cf);
    }

    // MODIFIES: cf
    // EFFECTS: sets the inputted amount to the selected cash flow
    private void setAmount(CashFlow cf, String input) {
        if (isValidAmount(input)) {
            double numAmount = Double.parseDouble(input);
            
            cf.setAmount(numAmount);
            System.out.println("\nThe cash flow has been edited");
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
        List<CashFlow> newCashFlowList = filterByStatus(cashFlowList, status);

        if (newCashFlowList.size() > 0) {
            CashFlow foundCashFlow = pickCashFlow(newCashFlowList);
            removeCashFlow(foundCashFlow);
        } else {
            System.out.println("No cash flow found. Please try again!");
            handleEditCashFlow();
        }
    }

    // MODIFIES: this
    // EFFECTS: helper method to filter by status
    private List<CashFlow> filterByStatus(List<CashFlow> cashFlowList, String status) {
        List<CashFlow> newCashFlowList = new ArrayList<>();
        for (int i = 0; i < cashFlowList.size(); i++) {
            if (cashFlowList.get(i).getStatus().equals(status)) {
                newCashFlowList.add(cashFlowList.get(i));
            }
        }
        return newCashFlowList;
    }

    // MODIFIES: this
    // EFFECTS: find a CashFlow to be removed based on account inputted by the user
    public void removeCashFlowByAccount() {
        spaceSeparator();
        System.out.println("Please specify which account to be filtered \n");
        displayAccountList();
        String account = this.scanner.nextLine();
        List<CashFlow> cashFlowList = this.moneySummary.getCashflows();
        List<CashFlow> newCashFlowList = filterByAccount(cashFlowList, account);

        if (newCashFlowList.size() > 0) {
            CashFlow foundCashFlow = pickCashFlow(newCashFlowList);
            removeCashFlow(foundCashFlow);
        } else {
            System.out.println("No cash flow found. Please try again!");
            handleEditCashFlow();
        }
    }

    // MODIFIES: this
    // EFFECTS: helper method to filter by account
    private List<CashFlow> filterByAccount(List<CashFlow> cashFlowList, String account) {
        List<CashFlow> newCashFlowList = new ArrayList<>();
        for (int i = 0; i < cashFlowList.size(); i++) {
            if (cashFlowList.get(i).getAccount().equals(account)) {
                newCashFlowList.add(cashFlowList.get(i));
            }
        }
        return newCashFlowList;
    }

    // MODIFIES: this
    // EFFECTS: find a CashFlow to be removed based on category inputted by the user
    public void removeCashFlowByCategory() {
        spaceSeparator();
        System.out.println("Please specify which category to be filtered \n");
        String category = this.scanner.nextLine();
        List<CashFlow> cashFlowList = this.moneySummary.getCashflows();
        List<CashFlow> newCashFlowList = filterByCategory(cashFlowList, category);

        if (newCashFlowList.size() > 0) {
            CashFlow foundCashFlow = pickCashFlow(newCashFlowList);
            removeCashFlow(foundCashFlow);
        } else {
            System.out.println("No cash flow found. Please try again!");
            handleEditCashFlow();
        }
    }

    // MODIFIES: this
    // EFFECTS: helper method to filter by category
    private List<CashFlow> filterByCategory(List<CashFlow> cashFlowList, String category) {
        List<CashFlow> newCashFlowList = new ArrayList<>();
        for (int i = 0; i < cashFlowList.size(); i++) {
            if (cashFlowList.get(i).getCategory().equals(category)) {
                newCashFlowList.add(cashFlowList.get(i));
            }
        }
        return newCashFlowList;
    }

    // MODIFIES: this
    // EFFECTS: find a CashFlow to be removed based on date inputted by the user
    public void removeCashFlowByDate() {
        spaceSeparator();
        System.out.println("Please specify which date to be filtered \n");
        String date = this.scanner.nextLine();
        List<CashFlow> cashFlowList = this.moneySummary.getCashflows();
        List<CashFlow> newCashFlowList = filterByDate(cashFlowList, date);

        if (newCashFlowList.size() > 0) {
            CashFlow foundCashFlow = pickCashFlow(newCashFlowList);
            removeCashFlow(foundCashFlow);
        } else {
            System.out.println("No cash flow found. Please try again!");
            handleEditCashFlow();
        }
    }

    // MODIFIES: this
    // EFFECTS: helper method to filter by date
    private List<CashFlow> filterByDate(List<CashFlow> cashFlowList, String date) {
        List<CashFlow> newCashFlowList = new ArrayList<>();
        for (int i = 0; i < cashFlowList.size(); i++) {
            if (cashFlowList.get(i).getDate().equals(date)) {
                newCashFlowList.add(cashFlowList.get(i));
            }
        }
        return newCashFlowList;
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
        displayMonthlyCashFlow(year, month);
    }

    // EFFECTS: helper method to display monthly cashflow
    private String displayMonthlyCashFlow(String year, String month) {
        if (isValidYear(year) && isValidMonth(month)) {
            List<CashFlow> cashflows = this.moneySummary.getCashflows();
            List<CashFlow> filtered = new ArrayList<>();
        
            for (int i = 0; i < cashflows.size(); i++) {
                if (cashflows.get(i).getDate().substring(0,4).equals(year) 
                        && cashflows.get(i).getDate().substring(5,7).equals(month)) {
                    filtered.add(cashflows.get(i));
                }
            }

            return displayCashFlow(filtered);
        } else {
            viewMonthlyCashFlow();
        }
        return  "";
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
    public String displayCashFlow(List<CashFlow> cashFlowList) {
        sortByTime();
        sortByDate();
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        sb1.append("<html>");
        for (int i = 0; i < cashFlowList.size(); i++) {
            CashFlow cf = cashFlowList.get(i);
            sb1.append(Integer.toString(i + 1) + "<br>" + "Status: " + cf.getStatus() + "<br>");
            sb1.append("Account: " + cf.getAccount() + "<br>" + "Category: " + cf.getCategory() + "<br>");
            sb1.append("Date: " + cf.getDate() + "<br>");
            sb1.append("Time: " + cf.getTime() + "<br>");
            sb1.append("Description: " + cf.getDescription() + "<br>");
            sb1.append("Amount: " + cf.getAmount() + "<br>");

            sb2.append(Integer.toString(i + 1) + ".\n" + "Status: " + cf.getStatus() + "\n");
            sb2.append("Account: " + cf.getAccount() + "\n" + "Category: " + cf.getCategory() + "\n");
            sb2.append("Date: " + cf.getDate() + "\n");
            sb2.append("Time: " + cf.getTime() + "\n");
            sb2.append("Description: " + cf.getDescription() + "\n");
            sb2.append("Amount: " + cf.getAmount() + "\n");
        }
        sb1.append("</html>");
        System.out.println(sb2.toString());
        return sb1.toString();
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
    public String viewDebitCategory() {
        List<String> debitCategories = this.moneySummary.getDebitCategories();
        String debitCategoryList = "";
        for (int i = 0; i < debitCategories.size(); i++) {
            debitCategoryList += Integer.toString(i + 1) + ". " + debitCategories.get(i);
            if (i != debitCategories.size() - 1) {
                debitCategoryList += "\n";
            }
        }
        System.out.println(debitCategoryList);
        return debitCategoryList;
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
    public String viewCreditCategory() { 
        List<String> creditCategories = this.moneySummary.getCreditCategories();
        String creditCategoryList = "";
        for (int i = 0; i < creditCategories.size(); i++) {
            creditCategoryList += Integer.toString(i + 1) + ". " + creditCategories.get(i);
            if (i != creditCategories.size() - 1) {
                creditCategoryList += "\n";
            }
        }
        System.out.println(creditCategoryList);
        return creditCategoryList;
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
    public String viewAccounts() {
        spaceSeparator();
        System.out.println("Registered accounts: \n");
        return "Registered accounts: \n" + displayAccountList();
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