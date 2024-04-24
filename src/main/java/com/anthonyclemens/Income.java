package com.anthonyclemens;

import java.util.Date;

public class Income extends Expense{
    public Income(String cat, double am, Date da, String per, String src) { //Inherit all methods and attributes of an Expense object
        super(cat, am, da, per, src);
    }
    @Override public String getType(){ //Override the getType method to be "Income" instead of "Expense"
        return "Income";
    }
}
