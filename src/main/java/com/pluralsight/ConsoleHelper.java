package com.pluralsight;

import java.util.Scanner;

public class ConsoleHelper {
private static Scanner scanner = new Scanner(System.in);

    public static int promtForInt(String prompt) {
        System.out.println(prompt + ": ");
        Scanner scanner = new Scanner(System.in);
        //int result = scanner.nextInt();
       return scanner.nextInt();

    }
  //  public static char promtForChar(String enterYourSelection) {
    // System.out.println(prompt + ": ");
      //  return scanner.nextLine();
    }









