package com.pluralsight;

import javax.imageio.IIOException;
import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {
   // private static final String CSV_FILE = ;
     private static final String CSV_FILE = "transaction.csv";
    public static ArrayList<Payments> payments = gettransactionFromFile();

    public static void main(String[] args) throws IOException {


        String mainMenu = """
                           Main Menu
                -------------------------------------    
                    1 - Add Deposit
                
                    2 - Make Payment
                
                    3 - Ledger
                
                    4 - Exit
                -------------------------------------    
                                            page:1
                """;
        boolean running = true;

        while (running) {
            System.out.println(mainMenu);
            int command = ConsoleHelper.promtForInt("ENTER YOUR SELECTION");
            switch (command) {
                case 1:
                    AddDeposit();

                case 2:
                    MakePayment();

                case 3:
                    Ledger();
                    break;

                case 4:
                    System.out.println("Goodbye");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid Command!");

            }
        }
    }
         //Report menu
    private static void Reports() {
        System.out.println("");
        String reports = """
                         REPORTS
                ---------------------------   
                   1 - Month To Date
                
                   2 - Previos Month
                
                   3 - Year To Date
                
                   4 - Previous Year
                
                   5 - Search By Vendor
                
                   6 - Ledger
                
                
                
                -------------------------
                                   page:2
                """;


        boolean inreports = true;
        while (inreports) {
            System.out.println(reports);
            int command = ConsoleHelper.promtForInt("ENTER YOUR SELECTION");


            switch (command) {
                case 1: MonthToDate();

                case 2: PreviousMonth();

                case 3: YearToDate();

                case 4: PreviousYear();

                case 5: SearchByVendor();

                case 6: Ledger();
                    break;

                case 7: inreports = false;
                    System.out.println("return");

                default:
                    System.out.println("INVALID COMMAND");
                    // end of Reports menu
            }
        }
    }

    // ask cx to search by vendor
    private static void SearchByVendor() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("\n Search vendor name");
        String SearchVendor = scanner.nextLine().toLowerCase();

        try(BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))){
            String line;
            System.out.println("\n Date        |   Description        |    Vendor  |    Amount");
            System.out.println("-----------------------------------------------------------------");
            reader.readLine();

            boolean found = false;
            while ((line = reader.readLine()) != null){
                String[] parts = line.split("-");

                if (parts.length >=3){
                    String vendor = parts[2].trim().toLowerCase();

                    if (vendor.contains(SearchVendor)){
                        found= true;
                        try {
                            double amount = Double.parseDouble(parts[3].trim());
                            System.out.printf("%-10s   |    %-15s    |   %-15s  || $%.2f%n",
                                    parts[0].trim(),
                                    parts[1].trim(),
                                    parts[2].trim(),
                                    amount);
                        } catch (NumberFormatException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }if (! found){
                System.out.println("No Transactions found");
            }
            System.out.println("---------------------------------------------------------------");
        }catch (IIOException e){
            System.out.println("Error reading CSV file");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // ask cx to search by previous year
    private static void PreviousYear() {
        //date
        int currentYear = java.time.LocalDate.now().getYear();
        int previosYear = currentYear - 1;

        try(BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            System.out.println("\n==== Transaction From" + previosYear);
            System.out.println("\n Date   | Description   |    Amount  |   Vendor "  );
            System.out.println("---------------------------");
            reader.readLine();

            boolean found = false;
            double totalAmount = 0;

            while ((line = reader.readLine()) != null){
                String[] parts = line.split("-");

                if (parts.length >3){
                    String date = parts[0].trim();

                    String[] dateParts = date.split("-");
                    if (dateParts.length>=1){
                        try {  //transaction year
                            int transactionYear = Integer.parseInt(dateParts[0]);

                            if (transactionYear == previosYear) {
                                found = true;
                                double amount = Double.parseDouble(parts[3].trim());
                                totalAmount += amount;
                                System.out.printf("%-10s  |  %-16s  |    %-16s   |$%.2f%n",
                                        date,
                                        parts[1].trim(),
                                        parts[2].trim(),
                                        amount);

                            }


                        }catch (NumberFormatException e){

                        }
                    }
                }
            }

            if (! found){
                System.out.println("No transacion found");

            }else {
                System.out.println("----------------------");
                System.out.printf("Total   $%.2f%n",totalAmount);
            }

        }catch (IOException e){
            System.out.println("error reading CSV file "  + e.getMessage());
        }


        }

   // ask cx to search by Year
    private static void YearToDate() {
        java.time.LocalDate today = java.time.LocalDate.now();
        int currentYear = today.getYear();
        java.time.LocalDate startOfYear = java.time.LocalDate.of(currentYear,1,1);

        try(BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))){
            String line;
            System.out.println("\n===  YEAR TO DATE ("+ startOfYear +"to"+ today+")===");
            System.out.println("   Date    |    Description     |   Vendor    |  Amount  ");
            System.out.println("-------------------------------------------------------------");

            reader.readLine();
            boolean found = false;
            double totalAmount = 0;

            while ((line = reader.readLine()) != null){
                String[] parts = line.split("-");

                if(parts.length>=3){
                    String dateStart = parts[0].trim();
                    try {
                        java.time.LocalDate transactionDate = java.time.LocalDate.parse(dateStart);
                        if(! transactionDate.isBefore(startOfYear)  && !transactionDate.isAfter(today)){
                            found = true;
                            double amount = Double.parseDouble((parts[3]).trim());
                            totalAmount += amount;
              //System.out.printf not println
                            System.out.printf("%-10s     |   %-16s     |   %-16s    |$%.2f%n",
                            dateStart,
                            parts[1].trim(),
                            parts[2].trim(),
                            amount);
                        }

                    }catch (Exception e){

                    }
                }

            }
            if (!found){
                System.out.println("No transactions found");

            }else {
                System.out.println("--------------------------------");
                System.out.printf("Total  $%.2f%n",totalAmount);

            }
        }catch (IOException e){
            System.out.println("Error reading CSV file"+e.getMessage());
        }
    }  //TODO

      // ask cx to search by the month
    private static void PreviousMonth() {
    }  //TODO


    private static void MonthToDate() {
        // start of reports menu

//TODO
    }
    // End of Ledger
    public static void payment() {
       System.out.println("\n=== PAYMENTS ONLY ====");
        displayPaymentFromCSV();
    }
    private static void displayPaymentFromCSV(){
       // String CSV_FILE ="";
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            System.out.println("DATE     |   DESCRIPTION     |   VENDOR");
            System.out.println("-".repeat(50));

            reader.readLine();

            while((line= reader.readLine()) != null){
                String[] parts = line.split("-");
                if (parts.length>=3){
                    try {
                        double amount = Double.parseDouble(parts[3].trim());
                        if (amount < 0) {
                            System.out.printf("%-8s    |   %-16s    |    %-20s% ",
                                    parts[0].trim(),
                                    parts[1].trim(),
                                    parts[2].trim());
                        }

                    } catch (NumberFormatException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        }catch (IOException e ){
            System.out.println(" Error reading CSV file ");
        }
    }



//deposit method starts
    private static void Deposits() {
        System.out.println("\n=== DEPOSITS===");
        displayDepositsFromCSV();

    }
     private static void displayDepositsFromCSV() {
        String CSV_FILE = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            System.out.println("Date      | Time        | Description      | Amount");
            System.out.println("-------------------------------------------------------");

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length >= 4) {
                    try {
                        double amount = Double.parseDouble(parts[3].trim());
                        if (amount > 0) {
                            System.out.printf("%-10s  |  %-12s   |  %-20s   |  $%.2f%n",
                                    parts[0].trim(),
                                    parts[1].trim(),
                                    parts[2].trim(),
                                    amount);
                        }

                    } catch (NumberFormatException e) {

                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading CSV file" + e.getMessage());
        }
    }

          //all should read each transaction

        private static void All () {
            System.out.println("\n=== ALL TRANSACTIONS");
            displayTransactionFromCSV();
        }
        private static void displayTransactionFromCSV () {
            try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
                String line;
                System.out.println("Date     |  Description    | Vendor      |  Amount");
                System.out.println("-".repeat(70));

                reader.readLine();

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");

                    if (parts.length == 4) {
                        String date = parts[0].trim();
                        String description = parts[1].trim();
                        String vendor = parts[2].trim();
                        String amount = parts[3].trim();

                        System.out.printf("%-8s | %-15s  |  %-15   |   $%s%n", date, description, vendor, amount);
                    }
                }
                System.out.println("-".repeat(70));

            } catch (IOException e) {
                System.out.println("Error reading transaction" + e.getMessage());
            }

        }
//Start of Ledger


        private static <transaction > ArrayList < transaction > gettransactionFromFile() {
            // ArrayList<transaction>transaction = new ArrayList<>();
            return new ArrayList<>();
        }
        //CSV_FILE = "trasaction.csv";
        private static void AddDeposit () throws IOException {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter date(YYYY-MM-DD");
            String date = scanner.nextLine();

            System.out.println("Enter Description");
            String description = scanner.nextLine();

            System.out.println("Enter Vendor");
            String vendor = scanner.nextLine();

            System.out.println("Enter amount");
            double amount = scanner.nextDouble();
            scanner.nextLine();

            Payments deposit = new Payments(date, description, vendor, amount);
            savetransactionTOFile(deposit);
            System.out.println("Deposit saved successfully");
        }
        private static void savetransactionTOFile (Payments transaction) throws IOException {
            try (FileWriter writer = new FileWriter(CSV_FILE, true)) {
                File file = new File((CSV_FILE));
                if (file.length() == 0) {
                    writer.write("date,description,vendor,amount\n");
                }
                writer.write(transaction.getDate() + ",");
                writer.write(transaction.getDescription() + ",");
                writer.write(transaction.getVendor() + ",");
                writer.write(transaction.getAmount() + ",");
                writer.flush();
            } catch (IIOException e) {
                System.out.println("Error saving to file");
            }


//        System.out.println("Make a Deposit");
//        for (Payments p :payments ) {
//            System.out.println(p);
        }

              // your asking the cx to make a payment here
        private static void MakePayment () throws IOException {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter Date(YYYY_MM_DD)");
            String date = scanner.nextLine();

            System.out.println("Enter Description");
            String description = scanner.nextLine();

            System.out.println("Enter amount");
            String vendor = scanner.nextLine();

            System.out.println("Enter amount");
            double amount = scanner.nextDouble();
            scanner.nextLine();

            Payments payment = new Payments(date, description, vendor, amount);

            savetransactionTOFile(payment);
            System.out.println("Payment saved successfully");


        }
        //Ledger menu
        private static void Ledger () {

            String Ledger = """
                                    Ledger
                             -----------------------
                                  1 - All
                    
                                  2 - Deposits
                    
                                  3 - Payments
                    
                                  4 - Reports   
                    
                    
                            -----------------------    
                                     page:3   
                    """;

            boolean inLedger = true;
            while (inLedger) {
                System.out.println(Ledger);
                int command = ConsoleHelper.promtForInt("ENTER YOUR SELECTION");

                switch (command) {

                    case 1:
                        All();
                        break;
                    case 2:
                        Deposits();
                        break;
                    case 3:
                        payment();
                        break;
                    case 4:
                        Reports();
                        break;
                    case 5:
                        inLedger = false;
                        break;
                    default:
                        System.out.println("INVALID");


                }
            }
        }


    }

//   public class Payments{
//    @Override
//       public String toString(){
//        return "Example Payment Record";
//    }