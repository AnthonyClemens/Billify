package com.anthonyclemens;

//import java.util.Scanner;

import javafx.application.Application;

public class Main
{
    public static void main( String[] args )
    {
        Application.launch(BillifyFX.class,args);
    }
        /*while(true){
        switch(printMenu()){
            case 1:
                CMD.startCMD(); //Launch the commandline version
                break;
            case 2:
                Application.launch(BillifyFX.class, args); //Launch the JavaFX version
                System.exit(0);
            case 3:
                System.out.println("Goodbye!"); //Kill the program
                System.exit(0);
                break;
            default: //If somehow the user gets through printMenu without giving a 0 through 3
                System.out.println("You aren't supposed to be here... exiting now.");
                System.exit(0); //Kill the program
                break;
        }
    }
    }
    private static int printMenu(){
        int selection = 0;
        Scanner input = new Scanner(System.in); //Create a scanner
        while((selection<1) || (selection>3)){ //Limit the inputs to 1 through 3
            System.out.println("\n-----------Billify-----------\n");
            System.out.println("1 - Launch the Command Line Interface");
            System.out.println("2 - Launch the JavaFX GUI");
            System.out.println("3 - Quit");
            selection = com.anthonyclemens.CMD.getValidInt(); //Retrieve a valid integer from the terminal
        }
        return selection; //Return the user selection
    }
    */
}
