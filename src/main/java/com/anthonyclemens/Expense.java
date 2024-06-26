package com.anthonyclemens;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Expense {
    private String category;
    private String person;
    private String source;
    private double amount;
    private Date date;

    public Expense(String cat, double am, Date da, String per, String src){
        category=cat;
        amount=am;
        date=da;
        person=per;
        source=src;
    }

    public Expense setCategory(String category) {
        this.category = category;
        return this;
    }

    public Expense setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public Expense setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getCategory(){
        return this.category;
    }

    public double getAmount(){
        return this.amount;
    }

    public String getSource(){
        return this.source;
    }

    public String getPerson(){
        return this.person;
    }


    public String getType(){
        return "Expense";
    }

    public Date getDate(){
        return this.date;
    }

    public String getValues(){
        return "\nDate: "+(new SimpleDateFormat("MM/dd/yyyy").format(this.date))+"\nCategory: "+this.category+"\nPerson: "+this.person+"\nAmount: "+this.amount+"\nSource: "+this.source;
    }
}
