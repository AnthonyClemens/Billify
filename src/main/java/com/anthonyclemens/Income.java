package com.anthonyclemens;

import java.util.Date;

public class Income extends Expense{
    public Income(String cat, double am, Date da, String per, String src) {
        super(cat, am, da, per, src);
    }
    private String type = "Income";
    @Override public String getType(){
        return "Income";
    }
}
