package com.pluralsight;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;

public class Main {
    public static ArrayList<Payments> payments = gettransactionFromFile();

    public static void main(String[] args) {


        String mainMenu = """
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
                case '1':
                    AddDeposit();

                case '2':
                    MakePayment();

                case '3':
                    Ledger();
                    break;

                case '4':
                    System.out.println("Goodbye");
                    running = false;
                default:
                    System.out.println("Invalid Command!");

            }
        }
    }

    private static void Reports() {
        System.out.println("Show my Reports");
    }

    private static void payment() {
        System.out.println("Show my Payments");
    }

    private static void Deposits() {
        System.out.println("Show my Deposits");
    }

    private static void All() {
        System.out.println("Show all transactions");
    }
//Leadger goes here








    private static <transaction> ArrayList<transaction> gettransactionFromFile() {
        ArrayList<transaction>transaction = new ArrayList<>();
        return transaction;
    }

    private static void AddDeposit(){
        System.out.println("Make a Deposit");
    }

    private static void MakePayment(){
        System.out.println("Please Make A Payment Here");
    }

    private static void Ledger(){

        String Ledger = """
                    -----------------------
                         1 - All
                    
                         2 - Deposits
                    
                         3 - Payments
                    
                         4 - Reports   
                    
                    
                     -----------------------       
                    """;


        while (true) {
            System.out.println(Ledger);
            int command = ConsoleHelper.promtForInt("ENTER YOUR SELECTION");

            switch (command) {

                case '1':
                    All();
break;
                case '2':
                    Deposits();
break;
                case '3':
                    payment();
break;
                case '4':
                    Reports();
                    break;
                default:
                    System.out.println("INVALID");


            }
        }
    }

}