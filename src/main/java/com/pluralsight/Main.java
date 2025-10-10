package com.pluralsight;

import java.sql.SQLOutput;
import java.util.logging.ConsoleHandler;

public class Main {
    public static void main(String[] args) {


        String mainMenu = """
        
        1 - Add Deposit
        
        2 - Make Payment
        
        3 - Ledger
        
        4 - Exit
        
        """;

        while(true){
            System.out.println(mainMenu);
            int command = ConsoleHandler.promptForInt("ENTER YOUR SELECTION");
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

    private static void AddDeposit(){
        System.out.println("Make a Deposit");
    }

    private static void MakePayment(){
        System.out.println("Please Make A Payment Here");
    }

    private static void Ledger(){

    }
    public class ConsoleHelper{
        
    }
}