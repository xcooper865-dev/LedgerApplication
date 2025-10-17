package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Main {
    private static final String CSV_FILE = "transactions.csv";
    private static final ArrayList<Payments> payments = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        loadTransactionsFromFile(); // Load existing transactions from the CSV file (if it exists)

        while (true) {  //loop keeps main menu running until user presses D or exit
            System.out.println("""
                    =============================
                        Home Screen
                    =============================
                    A) Add Deposit
                    B) Make Payment (Debit)
                    C) Ledger
                    D) Exit
                    -----------------------------
                                           page.1
                    """);
                                                                                    //.toUppercase meaning case insinsitive
            String choice = ConsoleHelper.promptForString("Enter your choice").trim().toUpperCase(); // Ask the user for a choice

            switch (choice) {
                case "A":
                    addDeposit();// adding a deposit
                    break;
                case "B":
                    makePayment();//money leaving out (debit)
                    break;
                case "C":
                    ledgerMenu();//Opens the ledger menu
                    break;
                case "D": {              // quit
                    System.out.println("Goodbye!");
                    return;  //application ends
                }
                default: System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    //    Start of main menu
    //Prompts the user for deposit information (date, time, description, vendor, amount)
    //    saves it as a positive transaction.
    // ------------------- ADD DEPOSIT -------------------
    private static void addDeposit() throws IOException {// throws IOException file writing might fail
        LocalDate date = ConsoleHelper.promptForDate("Enter Date (YYYY-MM-DD)");  //ConsoleHelper.promptForDate() shows prompt and validates date format
        LocalTime time = ConsoleHelper.promptForTime("Enter Time (HH:MM:SS)");// Returns a LocalTime object
        String description = ConsoleHelper.promptForString("Enter Description");
        String vendor = ConsoleHelper.promptForString("Enter Vendor");
        double amount = ConsoleHelper.promptForDouble("Enter Deposit Amount");

         //creating a new deposit
        Payments deposit = new Payments(date, time, description, vendor, amount); // Groups all related data into a single object
        saveTransactionToFile(deposit); //saving deposit to CSV file memory list
        payments.add(deposit);// add to memory Arraylist

        System.out.println("Deposit saved successfully!\n");
    }

   // Prompts the user for payment (debit) information and saves it
     // as a negative transaction.
    // ------------------- MAKE PAYMENT -------------------
    private static void makePayment() throws IOException {
        LocalDate date = ConsoleHelper.promptForDate("Enter Date (YYYY-MM-DD)");//collect the information from user
        LocalTime time = ConsoleHelper.promptForTime("Enter Time (HH:MM:SS)");
        String description = ConsoleHelper.promptForString("Enter Description");
        String vendor = ConsoleHelper.promptForString("Enter Vendor");
        double amount = ConsoleHelper.promptForDouble("Enter Payment Amount");

        //converting amount to a negitive                       // basically money leaving the account
        Payments payment = new Payments(date, time, description, vendor, -Math.abs(amount));// -Math.abs(amount) ensures the amount is always negative
        saveTransactionToFile(payment); //saving payment to CSV memory list
        payments.add(payment);

        System.out.println("Payment saved successfully!\n");


    }
    //Displays all transaction categories (All, Deposits, Payments, Reports)
    // routes the user to the appropriate submenu.
    // ------------------- LEDGER MENU -------------------
    private static void ledgerMenu() throws FileNotFoundException {
        while (true) {
            // SUBMENU LOOP stays in ledger menu until the user chooses to go back to the home screen
            // uses the same infinite loop pattern as main menu
            System.out.println("""
                    =============================
                           Ledger Menu
                    =============================
                    A) All
                    D) Deposits
                    P) Payments
                    R) Reports
                    H) Home
                    -----------------------------
                                           page.2
                    """);

            String choice = ConsoleHelper.promptForString("Enter your choice").trim().toUpperCase();

            switch (choice) {
                case "A":
                    displayTransactions("ALL");
                    break;
                case "D":
                    displayTransactions("DEPOSITS");
                break;
                case "P" :displayTransactions("PAYMENTS");
                break;
                case "R":
                    reportsMenu();
                    break;
                case "H": {
                    return;
                }
                default: System.out.println("Invalid choice.");
            }
        }
    }

    // ------------------- DISPLAY TRANSACTIONS -------------------
    //this method handles all three view modes
    //when the filter is all neither of the filter checks will match one another allowing you to see both pos+ and neg- numbers
    private static void displayTransactions(String filter) {//filter can be "ALL", "DEPOSITS", or "PAYMENTS"

        List<Payments> sorted = new ArrayList<>(payments);// creates a copy of the payments list
        sorted.sort((a, b) -> b.getDate().compareTo(a.getDate())); // sort transaction newest to oldest

        System.out.println("----------------------------------------------------------------------------");
        System.out.printf("%-12s | %-8s | %-20s | %-15s | %10s%n",
                "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("-".repeat(80));

        for (Payments p : sorted) { //loop through transactions //p is the current Payment object
            if (filter.equals("DEPOSITS") && p.getAmount() < 0) continue;  //deposits show only skips if negitve
                   // If showing deposits and the amount is negative (payment) then continue only positive numbers pass through

            if (filter.equals("PAYMENTS") && p.getAmount() >= 0) continue;// payments show only skips if positive
                                                                          // opposite of (deposit) only negitive numbers show
            //if showing payments and the amount is positive (deposit) then continue only negitive numbers pass through

            System.out.printf("%-12s | %-8s | %-20s | %-15s | $%10.2f%n", //this line prints everything

                                                    //if the filter is equal to deposits and all negitives were skipped print deposit pos+
                                                    //if the filter is equal to payment and all positives were skipped print payment neg-
                    p.getDate(), p.getTime(), p.getDescription(), p.getVendor(), p.getAmount());
        }
        System.out.println();
    }

    // ------------------- REPORTS MENU -------------------
    private static void reportsMenu() {
        while (true) { // loop keeps going until user presses 0
            System.out.println("""
                    =============================
                             Reports
                    =============================
                    1) Month To Date  
                    2) Previous Month
                    3) Year To Date
                    4) Previous Year
                    5) Search by Vendor
                    0) Back
                    -----------------------------
                                            page.3
                    """);

            String choice = ConsoleHelper.promptForString("Enter your choice");

            switch (choice) {  //alternate -> arrow syntax allows for the use of no breaks
                case "1":
                    reportMonthToDate(); // showing current month transactions
                    break;
                case "2":
                    reportPreviousMonth();//showing previous month transactions
                    break;
                case "3":
                    reportYearToDate();// show the current year to date
                    break;
                case "4":
                    reportPreviousYear();// show transactions from last year
                    break;
                case "5":
                    searchByVendor();//search by vendor
                    break;
                case "0":{ // going back to the ledger menu
                    return; //going back to ledger
                }
                default: System.out.println("Invalid choice.");
            }
        }
    }

    // ------------------- REPORT IMPLEMENTATIONS -------------------
    private static void reportMonthToDate() {// show transaction from 1st day of the month
        System.out.println("==========================");
        System.out.println("    Month   To   Date ");
        System.out.println("==========================");
        LocalDate now = LocalDate.now();// gets todays date
        payments.stream() //converting the arraylist into a stream
                // a Stream is a sequence of elements supporting functional operations

                //filter keeps transactions from the current month and year that match
                .filter(p -> p.getDate().getYear() == now.getYear() //return same year
                        && p.getDate().getMonth() == now.getMonth())  //return same month  // both conditions must be true using &&
                .forEach(System.out::println); //print the matching transaction and calling each string to  that payment
    }          //.forEach(p -> System.out.println(p)): alternate
              // prints each matching transaction as a toString method

    private static void reportPreviousMonth() {//show transaction from previous month
        System.out.println("=====================");
        System.out.println("  Previous   Month");
        System.out.println("=====================");
        LocalDate now = LocalDate.now().minusMonths(1);//going back to the previous month by 1
        payments.stream()
                .filter(p -> p.getDate().getYear() == now.getYear() // filter & keeps transactions from the previous month by year and month
                        && p.getDate().getMonth() == now.getMonth())
                .forEach(System.out::println);//print matching transactions

    }
//

    private static void reportYearToDate() {// show all transactions from the start of year until now
        System.out.println("=====================");
        System.out.println("Year     To    Date");
        System.out.println("=====================");
        int year = LocalDate.now().getYear();//current year gets year as a Integer

        payments.stream() //filter and keeps transaction from the current year
                .filter(p -> p.getDate().getYear() == year)  //transaction from current year
                .forEach(System.out::println); //print the matching transactions
    }

    private static void reportPreviousYear() {// show all transactions from the last year
        System.out.println("====================================");
        System.out.println("      Previous years Payments   :");
        System.out.println("====================================");
        int year = LocalDate.now().getYear() - 1;// calculating the previous year( minus 1)
        payments.stream()
                .filter(p -> p.getDate().getYear() == year) // filter and keep transactions from previous year
                .forEach(System.out::println);// print all the matching transactions

    }

    private static void searchByVendor() { //find transactions by the vendor
        String vendor = ConsoleHelper.promptForString("Enter vendor name").toLowerCase();//asking the user for the vendor name & converting string into lowercase
        payments.stream()
                      //checking if vendor name contains the search
                .filter(p -> p.getVendor().toLowerCase().contains(vendor))
                .forEach(System.out::println);                      //contain allows for partal matches
    }               //print all matching descriptions

    // ------------------- FILE HELPERS -------------------
    private static void saveTransactionToFile(Payments transaction) throws IOException {
        boolean fileExists = new File(CSV_FILE).exists();// .exist checks if file exist

                                      //opens the file in append mode
        try (FileWriter writer = new FileWriter(CSV_FILE, true)) {//if true it won't erase the old data //If false  file would be erased before writing

            if (!fileExists) {   // if the file IS new you will write the column header first
                writer.write("date,time,description,vendor,amount\n");

            }    //%s string date,time,description,vendor //%.2f%n round the number two decimal places
            writer.write(String.format("%s,%s,%s,%s,%.2f%n",  //writing one line to the CVS including all the payment info
                    transaction.getDate(),  //payment Date
                    transaction.getTime(),//payment Time
                    transaction.getDescription(), //payment Description
                    transaction.getVendor(),  //vendor of payment going to
                    transaction.getAmount())); // Amount
        }
    }

    private static void loadTransactionsFromFile() {
        File file = new File(CSV_FILE);//file object being created that represents the CSV file

        if (!file.exists()) return; //If the file doesn't exist the method stops avoiding errors

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {// closes File Reader
            reader.readLine(); // skip header
            String line;
            while ((line = reader.readLine()) != null) {// reads the line until it retruns null= nothing
                String[] parts = line.split(",");
                if (parts.length < 5) continue;//if the line doesn't have 5 parts skip

                Payments p = new Payments( // creates a new payment object using the data above
                        LocalDate.parse(parts[0]), //Date
                        LocalTime.parse(parts[1]),//Time
                        parts[2],        //Description
                        parts[3],       //vendor
                        Double.parseDouble(parts[4]) //amount
                );
                payments.add(p); // add payment object to payment list
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
//   public class Payments{
//    @Override
//       public String toString(){
//        return "Example Payment Record";
//    }