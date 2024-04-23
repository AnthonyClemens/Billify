package com.anthonyclemens;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
                    data=addData(data);
                    break;
                case 2:
                    data=remData(data);
                    break;
                case 3:
                    String summary = "There are "+data.numIncome(data)+" Income records, and "+data.numExpense(data)+" Expense Records.";
                    System.out.println(summary);
                    divider(summary);
                    listData(data,"income");
                    listData(data, "expense");
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    return;
                default:
                    break;
            }
        }
    }
    private static FinancialCommand remData(FinancialCommand data) {
        if(data.numExpense(data)==0 && data.numIncome(data)==0){
            System.out.println("There is no data to delete at this time.");
            return data;
        }
        String type = askType();
        System.out.println("Which "+type+" would you like to delete?");
        listData(data, type);
        int numData = getValidInt()-1;
        try{
            if(type.equals("income")){
                if(data.numIncome(data)==0){
                    System.out.println("There is no "+type+"s to delete at this time.");
                    return data;
                }
                data = data.delIncome(numData);
            }else{
                if(data.numExpense(data)==0){
                    System.out.println("There is no "+type+"s to delete at this time.");
                }
                data = data.delExpense(numData);
            }
            System.out.println(type+" deleted successfully.");
        }catch(Exception e){
            System.out.println("Could not delete "+type+", please ensure you are entering the correct number.");
        }
        return data;
    }
    private static String askType(){
        String type="";
        Scanner input = new Scanner(System.in);
        while(!((type.equals("income")) || (type.equals("expense")))){
            System.out.print("\nWill this be an Income or Expense? ");
            type = input.nextLine().toLowerCase();
            switch(type){
                case "income":
                    break;
                case "expense":
                    break;
                default:
                    System.err.println("Please type 'Income' or 'Expense' only.");
            }
        }
        return type;
    }
    private static int printMenu(){
        int selection = 0;
        Scanner input = new Scanner(System.in);
        while((selection<1) || (selection>4)){
            System.out.println("\n---------BillifyCMD---------\n");
            System.out.println("1 - Add an Income/Expense");
            System.out.println("2 - Remove an Income/Expense");
            System.out.println("3 - View Summary");
            System.out.println("4 - Quit");
            selection = getValidInt();
        }
        return selection;
    }

    public static int getValidInt() {
        int selection = -1;
        Scanner input = new Scanner(System.in);
        while(true){
            try{
                selection = input.nextInt();
                break;
            }catch(Exception e){
                System.out.println("Please enter a number! Try again!");
                input.nextLine();
            }
        }
        return selection;
    }
    private static FinancialCommand addData(FinancialCommand data){
        Scanner input = new Scanner(System.in);
        double amount = 0.0;
        String type = askType();
        System.out.print("What category is this "+type+"? Existing categories are: "+com.anthonyclemens.FinancialCommand.getUniqueCategories(data,type));
        String category = input.nextLine();
        System.out.print("Who is the person creating this "+type+"? ");
        String person = input.nextLine();
        System.out.print("Where is this transaction taking place? ");
        String source = input.nextLine();
        System.out.print("How much was this item(s)? ");
        while(true){
            try{
                amount = input.nextDouble();
                break;
            }catch(Exception e){
                System.out.println("Please enter a number! Try again!");
                input.nextLine();
            }
        }
        System.out.println("Please enter the date of the transaction in MM-dd-yyyy format: ");
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
        String dateString = input.next();
        Date date = null;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(type.equals("income")){
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
    }
    private static void listData(FinancialCommand data,String type){
        System.out.println("\nList of "+type+"s:");
        int nAmount = -1;
        if(type.equals("income")){
            nAmount=data.numIncome(data);
        }else{
            nAmount=data.numExpense(data);
        }
        if(nAmount==0){
            System.out.println("There are no "+type+"s.");
        }else{
            if(type.equals("income")){
                for(int i=0; i < nAmount; i++){
                    System.out.println("\nIncome number "+(i+1)+":\n"+data.getIncomes(data).get(i).getValues());
                }
            }else{
                for(int i=0; i < nAmount; i++){
                    System.out.println("\nExpense number "+(i+1)+":\n"+data.getExpenses(data).get(i).getValues());
                }
        }
    }
}
}
