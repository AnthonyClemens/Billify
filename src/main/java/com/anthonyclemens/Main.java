package com.anthonyclemens;

import java.util.Scanner;

import javafx.application.Application;

public class Main
{
    public static void main( String[] args )
    {
        while(true){
        switch(printMenu()){
            case 1:
                CMD.startCMD();
                break;
            case 2:
                Application.launch(BillifyFX.class, args);
                break;
            case 3:
                System.out.println("Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("You aren't supposed to be here... exiting now.");
                System.exit(0);
                break;
        }
    }
    }
    private static int printMenu(){
        int selection = 0;
        Scanner input = new Scanner(System.in);
        while((selection<1) || (selection>3)){
            System.out.println("\n-----------Billify-----------\n");
            System.out.println("1 - Launch the Command Line Interface");
            System.out.println("2 - Launch the JavaFX GUI");
            System.out.println("3 - Quit");
            selection = com.anthonyclemens.CMD.getValidInt();
        }
        return selection;
    }
}
