package com.anthonyclemens;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class CMD {
    public static void startCMD(){
        FinancialCommand data = new FinancialCommand();
        System.out.println("Welcome to Billify (Console)");
        while(true){
            int choice = printMenu();
            switch (choice) {
                case 1:
                    data=addExpense(data);
                    break;
                case 2:
                    break;
                case 3:
                    String summary = "There are "+data.numIncome(data)+" Income records, and "+data.numExpense(data)+" Expense Records.";
                    System.out.println(summary);
                    divider(summary);
                    System.out.println("\n"+data.getExpenses(data).get(0).getValues());
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }
    private static int printMenu(){
        int selection = 0;
        Scanner input = new Scanner(System.in);
        while((selection<1) || (selection>4)){
            System.out.println("\n-----------Billify-----------\n");
            System.out.println("1 - Add an Income/Expense");
            System.out.println("2 - Remove an Income/Expense");
            System.out.println("3 - View Summary");
            System.out.println("4 - Quit");

            selection = input.nextInt();
        }
        return selection;
    }

    private static FinancialCommand addExpense(FinancialCommand data){
        Scanner input = new Scanner(System.in);
        String type="None";
        while(!((type.contains("Income")) || (type.contains("Expense")))){
            System.out.print("\nWill this be an Income or Expense?");
            type = input.nextLine();
            if((!type.contains("Income")) || (!type.contains("Expense"))){
                System.err.println("Please type 'Income' or 'Expense' only.");
            }else{
                break;
            }
        }
        System.out.print("What category is this expense? ");
        String category = input.nextLine();
        System.out.print("Who is the person creating this expense? ");
        String person = input.nextLine();
        System.out.print("Where is this transaction taking place? ");
        String source = input.nextLine();
        System.out.print("How much was this item(s)? ");
        double amount = input.nextDouble();
        System.out.println("Please enter the date of the transaction in MM-dd-yyyy format: ");
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
        String dateString = input.next();
        Date date = null;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(type.equals("Income")){
            data.addIncome(new Income(category, amount, date, person, source));
        }else{
            data.addExpense(new Expense(category, amount, date, person, source));
        }
        return data;
    }

    private static void divider(String text){
        for(int i= 0; i < text.length(); i++){
            System.out.print("-");
        }
        System.out.println();
        return;
    }
}
