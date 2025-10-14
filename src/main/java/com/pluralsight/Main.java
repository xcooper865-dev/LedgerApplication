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

        while (true) {
            System.out.println("""
                    =============================
                        Home Screen
                    =============================
                    D) Add Deposit
                    P) Make Payment (Debit)
                    L) Ledger
                    X) Exit
                    -----------------------------
                    """);

            String choice = ConsoleHelper.promptForString("Enter your choice").trim().toUpperCase();

            switch (choice) {
                case "D" -> addDeposit();
                case "P" -> makePayment();
                case "L" -> ledgerMenu();
                case "X" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // ------------------- ADD DEPOSIT -------------------
    private static void addDeposit() throws IOException {
        LocalDate date = ConsoleHelper.promptForDate("Enter Date (YYYY-MM-DD)");
        LocalTime time = ConsoleHelper.promptForTime("Enter Time (HH:MM:SS)");
        String description = ConsoleHelper.promptForString("Enter Description");
        String vendor = ConsoleHelper.promptForString("Enter Vendor");
        double amount = ConsoleHelper.promptForDouble("Enter Deposit Amount");

        Payments deposit = new Payments(date, time, description, vendor, amount);
        saveTransactionToFile(deposit);
        payments.add(deposit);

        System.out.println("Deposit saved successfully!\n");
    }

    // ------------------- MAKE PAYMENT -------------------
    private static void makePayment() throws IOException {
        LocalDate date = ConsoleHelper.promptForDate("Enter Date (YYYY-MM-DD)");
        LocalTime time = ConsoleHelper.promptForTime("Enter Time (HH:MM:SS)");
        String description = ConsoleHelper.promptForString("Enter Description");
        String vendor = ConsoleHelper.promptForString("Enter Vendor");
        double amount = ConsoleHelper.promptForDouble("Enter Payment Amount");

        Payments payment = new Payments(date, time, description, vendor, -Math.abs(amount)); // store as negative
        saveTransactionToFile(payment);
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
        sorted.sort((a, b) -> b.getDate().compareTo(a.getDate())); // newest first

        System.out.printf("%-12s | %-8s | %-20s | %-15s | %10s%n",
                "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("-".repeat(70));

        for (Payments p : sorted) {
            if (filter.equals("DEPOSITS") && p.getAmount() < 0) continue;
            if (filter.equals("PAYMENTS") && p.getAmount() >= 0) continue;
            System.out.printf("%-12s | %-8s | %-20s | %-15s | $%10.2f%n",
                    p.getDate(), p.getTime(), p.getDescription(), p.getVendor(), p.getAmount());
        }
        System.out.println();
    }

    // ------------------- REPORTS MENU -------------------
    private static void reportsMenu() {
        while (true) {
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
                case "1" -> reportMonthToDate();
                case "2" -> reportPreviousMonth();
                case "3" -> reportYearToDate();
                case "4" -> reportPreviousYear();
                case "5" -> searchByVendor();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // ------------------- REPORT IMPLEMENTATIONS -------------------
    private static void reportMonthToDate() {
        LocalDate now = LocalDate.now();
        payments.stream()
                .filter(p -> p.getDate().getYear() == now.getYear()
                        && p.getDate().getMonth() == now.getMonth())
                .forEach(System.out::println);
    }

    private static void reportPreviousMonth() {
        LocalDate now = LocalDate.now().minusMonths(1);
        payments.stream()
                .filter(p -> p.getDate().getYear() == now.getYear()
                        && p.getDate().getMonth() == now.getMonth())
                .forEach(System.out::println);
    }

    private static void reportYearToDate() {
        int year = LocalDate.now().getYear();
        payments.stream()
                .filter(p -> p.getDate().getYear() == year)
                .forEach(System.out::println);
    }

    private static void reportPreviousYear() {
        int year = LocalDate.now().getYear() - 1;
        payments.stream()
                .filter(p -> p.getDate().getYear() == year)
                .forEach(System.out::println);
    }

    private static void searchByVendor() {
        String vendor = ConsoleHelper.promptForString("Enter vendor name").toLowerCase();
        payments.stream()
                .filter(p -> p.getVendor().toLowerCase().contains(vendor))
                .forEach(System.out::println);
    }

    // ------------------- FILE HELPERS -------------------
    private static void saveTransactionToFile(Payments transaction) throws IOException {
        boolean fileExists = new File(CSV_FILE).exists();

        try (FileWriter writer = new FileWriter(CSV_FILE, true)) {
            if (!fileExists) {
                writer.write("date,time,description,vendor,amount\n");
            }
            writer.write(String.format("%s,%s,%s,%s,%.2f%n",
                    transaction.getDate(),
                    transaction.getTime(),
                    transaction.getDescription(),
                    transaction.getVendor(),
                    transaction.getAmount()));
        }
    }

    private static void loadTransactionsFromFile() {
        File file = new File(CSV_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine(); // skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 5) continue;

                Payments p = new Payments(
                        LocalDate.parse(parts[0]),
                        LocalTime.parse(parts[1]),
                        parts[2],
                        parts[3],
                        Double.parseDouble(parts[4])
                );
                payments.add(p);
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