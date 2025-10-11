package com.pluralsight;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;

public class Main {
    public static ArrayList<Payments> payments = gettransactionFromFile();

    public static void main(String[] args) {


        String mainMenu = """
                           Main Menu
                -------------------------------    
                    1 - Add Deposit
                
                    2 - Make Payment
                
                    3 - Ledger
                
                    4 - Exit
                ----------------------------------    
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

    private static void Reports() {
        System.out.println("");
        String reports = """
                       REPORTS
                ----------------------   
                   1 - Month To Date
                
                   2 - Previos Month
                
                   3 - Year To Date
                
                   4 - Previous Year
                
                   5 - Search By Vendor
                
                   6 - Ledger
                
                
                
                
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

            }
        }
    }
// end of Reports menu
    private static void SearchByVendor() {
    }

    private static void PreviousYear() {
    }

    private static void YearToDate() {
    }

    private static void PreviousMonth() {
    }
    // start of reports menu

    private static void MonthToDate() {


    }
// End of Ledger
    public static void payment() {
        System.out.println("Show my Payments");
        String date;
        double amount;
        String description;
    }

    private static void Deposits() {
        System.out.println("Show my Deposits");
    }

    private static void All() {
        System.out.println("Show all transactions");
    }
//Start of Ledger








    private static <transaction> ArrayList<transaction> gettransactionFromFile() {
       // ArrayList<transaction>transaction = new ArrayList<>();
        return new ArrayList<>();
    }

    private static void AddDeposit(){
        System.out.println("Make a Deposit");
        for (Payments p :payments ) {
            System.out.println(p);
        }

    }

    private static void MakePayment(){
        System.out.println("Please Make A Payment Here");

    }

    private static void Ledger(){

        String Ledger = """
                            Ledger
                    -----------------------
                         1 - All
                    
                         2 - Deposits
                    
                         3 - Payments
                    
                         4 - Reports   
                    
                    
                     -----------------------       
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
