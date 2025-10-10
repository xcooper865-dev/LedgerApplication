package com.pluralsight;

import java.sql.SQLOutput;
import java.util.ArrayList;
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

        while(true){
            System.out.println(mainMenu);
            char command = ConsoleHelper.promtForChar("ENTER YOUR SELECTION");
            switch (command){
                case 1:
                    AddDeposit();

                case 2:
                    MakePayment();

                case 3:
                    Ledger();
                    break;

                case 4:
                    return;
                default:
                    System.out.println("EXIT");
            }

        }
    }
//Leadger goes here

        String Leadger = """
                -----------------------
                     1 - All
                
                     2 - Deposits
                
                     3 - Payments
                
                     4 - Reports   
                
                
                 -----------------------       
                """;

        while (true){

        System.out.println(Ledger);
        char command = ConsoleHelper.promtForChar("");
    }
    private static <transaction> ArrayList<transaction> gettransactionFromFile() {
            return;
    }

    private static void AddDeposit(){
        System.out.println("Make a Deposit");
    }

    private static void MakePayment(){
        System.out.println("Please Make A Payment Here");
    }

    private static void Ledger(){

    }

        public static class ConsoleHelper{
            public static int promtForInt(String enterYourSelection) {
            }

           public static Character promtForChar(String enterYourSelection) {
            }
        }
}