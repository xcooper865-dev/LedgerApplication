package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Main {
    private static final String CSV_FILE = "transactions.csv";
    private static final ArrayList<Payments> payments = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        loadTransactionsFromFile();

        while (true) {  //loop keeps going until user presses D
            System.out.println("""
                    =============================
                        Home Screen
                    =============================
                    A) Add Deposit
                    B) Make Payment (Debit)
                    C) Ledger
                    D) Exit
                    -----------------------------
                    """);

            String choice = ConsoleHelper.promptForString("Enter your choice").trim().toUpperCase(); // user can press a or A app still works

            switch (choice) {
                case "A" -> addDeposit();  // adding money
                case "B" -> makePayment(); //money leaving out
                case "C" -> ledgerMenu();  //viewing trasiactions
                case "D" -> {              // quit
                    System.out.println("Goodbye!");
                    return;  //application ends
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    //    Start of main menu
    // ------------------- ADD DEPOSIT -------------------
    private static void addDeposit() throws IOException {
        LocalDate date = ConsoleHelper.promptForDate("Enter Date (YYYY-MM-DD)");  //collect the information from user
        LocalTime time = ConsoleHelper.promptForTime("Enter Time (HH:MM:SS)");
        String description = ConsoleHelper.promptForString("Enter Description");
        String vendor = ConsoleHelper.promptForString("Enter Vendor");
        double amount = ConsoleHelper.promptForDouble("Enter Deposit Amount");
         //adding descripion date vendor and amount to deposit & saving deposit
        Payments deposit = new Payments(date, time, description, vendor, amount);
        saveTransactionToFile(deposit); //saving deposit to csv
        payments.add(deposit); //adding the deposit t

        System.out.println("Deposit saved successfully!\n");
    }

    // ------------------- MAKE PAYMENT -------------------
    private static void makePayment() throws IOException {
        LocalDate date = ConsoleHelper.promptForDate("Enter Date (YYYY-MM-DD)");//collect the information from user
        LocalTime time = ConsoleHelper.promptForTime("Enter Time (HH:MM:SS)");
        String description = ConsoleHelper.promptForString("Enter Description");
        String vendor = ConsoleHelper.promptForString("Enter Vendor");
        double amount = ConsoleHelper.promptForDouble("Enter Payment Amount");
        // saving date time description vendor                        // basically money leaving the account
        Payments payment = new Payments(date, time, description, vendor, -Math.abs(amount));
        saveTransactionToFile(payment); //saving payment to csv
        payments.add(payment);

        System.out.println("Payment saved successfully!\n");
    }

    // ------------------- LEDGER MENU -------------------
    private static void ledgerMenu() throws FileNotFoundException {
        while (true) {
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
                    """);

            String choice = ConsoleHelper.promptForString("Enter your choice").trim().toUpperCase();

            switch (choice) {
                case "A" -> displayTransactions("ALL");
                case "D" -> displayTransactions("DEPOSITS");
                case "P" -> displayTransactions("PAYMENTS");
                case "R" -> reportsMenu();
                case "H" -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // ------------------- DISPLAY TRANSACTIONS -------------------
    private static void displayTransactions(String filter) {
        List<Payments> sorted = new ArrayList<>(payments);
        sorted.sort((a, b) -> b.getDate().compareTo(a.getDate())); // sort transaction newest to oldest

        System.out.printf("%-12s | %-8s | %-20s | %-15s | %10s%n",
                "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("-".repeat(70));

        for (Payments p : sorted) { //loop through transactions
            if (filter.equals("DEPOSITS") && p.getAmount() < 0) continue;  //deposits show only skips if negitive
            if (filter.equals("PAYMENTS") && p.getAmount() >= 0) continue;// payments show only skips if positive
            System.out.printf("%-12s | %-8s | %-20s | %-15s | $%10.2f%n",
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
                    """);

            String choice = ConsoleHelper.promptForString("Enter your choice");

            switch (choice) {
                case "1" -> reportMonthToDate(); // showing current month transactions
                case "2" -> reportPreviousMonth();//showing previous month transactions
                case "3" -> reportYearToDate();// show the current year to date
                case "4" -> reportPreviousYear();// show transactions from last year
                case "5" -> searchByVendor();//search by vendor
                case "0" -> { // going back to the ledger menu
                    return; //going back to ledger
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // ------------------- REPORT IMPLEMENTATIONS -------------------
    private static void reportMonthToDate() {// show transaction from 1st day of the month
        LocalDate now = LocalDate.now();
        payments.stream() //convrting the arraylist into a stream

                //filter keeps transactions from the current month and year
                .filter(p -> p.getDate().getYear() == now.getYear() //return same year
                        && p.getDate().getMonth() == now.getMonth())  //return same month
                .forEach(System.out::println); //print the matching transaction and calling each string to  that payment
    }

    private static void reportPreviousMonth() {  //show transactio from previous month
        LocalDate now = LocalDate.now().minusMonths(1);//going back to the previous month by 1
        payments.stream()
                .filter(p -> p.getDate().getYear() == now.getYear() // filter & keeps transactions from the previous month by year and month
                        && p.getDate().getMonth() == now.getMonth())
                .forEach(System.out::println);//print matching transactions
    }

    private static void reportYearToDate() {// show all transactions from the start of year until now
        int year = LocalDate.now().getYear();//current year

        payments.stream() //filter and keeps transaction from the current year
                .filter(p -> p.getDate().getYear() == year)  //transaction from current year
                .forEach(System.out::println); //print the matching transactions
    }

    private static void reportPreviousYear() { // show all transactions from the last year
        int year = LocalDate.now().getYear() - 1;// calculating the previous year( minus 1)
        payments.stream()
                .filter(p -> p.getDate().getYear() == year) // filter and keep transactions from previous year
                .forEach(System.out::println);// print all the matching transactions
    }

    private static void searchByVendor() { //find transactions by the vendor
        String vendor = ConsoleHelper.promptForString("Enter vendor name").toLowerCase();//asking the user for the vendor name & converting sting into lowercase
        payments.stream()
                      //checking if vendor name contains the search
                .filter(p -> p.getVendor().toLowerCase().contains(vendor))
                .forEach(System.out::println);                      //contain allows for partal matches
    }               //print all matching descriptions

    // ------------------- FILE HELPERS -------------------
    private static void saveTransactionToFile(Payments transaction) throws IOException {
        boolean fileExists = new File(CSV_FILE).exists();// checks if file exist

                                      //opens the file in append mode
        try (FileWriter writer = new FileWriter(CSV_FILE, true)) {//if true it won't erase the old data

            if (!fileExists) {   // if the file IS new you will write the column header first
                writer.write("date,time,description,vendor,amount\n");
            }
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