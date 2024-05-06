package com.anthonyclemens;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FinancialCommand {
    private ArrayList<Expense> expenseList = new ArrayList<>();
    private ArrayList<Income> incomeList = new ArrayList<>();

    public static List<String> getUniqueCategories(FinancialCommand financialCommand, String type){
        List<String> catList = new ArrayList<>();
        if(type.equals("income")){
            for(int i=0; i<financialCommand.incomeList.size(); i++){
                catList.add(financialCommand.incomeList.get(i).getCategory());
            }
            catList = catList.stream().distinct().toList();
        }else{
            for(int i=0; i<financialCommand.expenseList.size(); i++){
                catList.add(financialCommand.expenseList.get(i).getCategory());
            }
            catList = catList.stream().distinct().toList();
        }
        return catList;
    }

    public static Map<String, Double> getExpenseCategoryPercentages(FinancialCommand financialCommand) {
        List<String> catList = new ArrayList<>();
        double totalAmountSpent = 0.0;
        for (Expense expense : financialCommand.getExpenses()) {
            catList.add(expense.getCategory());
            totalAmountSpent += expense.getAmount();
        }
        if(totalAmountSpent==0){
            return new HashMap<>();
        }
        //System.out.println("Total spent:"+totalAmountSpent); //Debug Code
        Map<String, Double> categoryPercentages = new HashMap<>();
        for (String category : catList) {
            double amount = getCategoryCost(financialCommand, category);
            double percentage = amount/totalAmountSpent;
            //System.out.println(category+" "+percentage); //Debug Code
            categoryPercentages.put(category, percentage);
        }
        return categoryPercentages;
    }

    private static double getCategoryCost(FinancialCommand financialCommand, String category){
        double total = 0.0;
        for(int i=0; i<financialCommand.expenseList.size(); i++){
            if(financialCommand.expenseList.get(i).getCategory().equals(category)){
                total=total+financialCommand.expenseList.get(i).getAmount();
            }
        }
        return total;
    }

    public void addExpense(Expense newExpense){
        this.expenseList.add(newExpense);
    }

    public void addIncome(Income newIncome){
        this.incomeList.add(newIncome);
    }

    public void delExpense(int i){
        this.expenseList.remove(i);
    }

    public void delIncome(int i){
        this.incomeList.remove(i);
    }

    public int numExpenses(){
        return this.expenseList.size();
    }

    public int numIncomes(){
        return this.incomeList.size();
    }

    public List<Expense> getExpenses(){ //Return the Arraylist of Expense objects
        return this.expenseList;
    }

    public List<Income> getIncomes(){ //Return the ArrayList of Income objects
        return this.incomeList;
    }

    public double getIncomeDay(Date day){
        double total = 0.0;
        for(int i=0; i<this.incomeList.size(); i++){
            if(day.equals(this.incomeList.get(i).getDate())){
                total=total+this.incomeList.get(i).getAmount();
            }
        }
        return total;
    }
    public double getExpenseDay(Date day){
        double total = 0.0;
        for(int i=0; i<this.expenseList.size(); i++){
            if(day.equals(this.expenseList.get(i).getDate())){
                total=total+this.expenseList.get(i).getAmount();
            }
        }
        return total;
    }
    public void addPaycheck(String cat, Date da, String per, String src, double hours, double pay){
        double totalPay;
        if(hours<=40){
            totalPay = pay*hours;
        }else{
            totalPay = (pay*40)+(1.5*(pay*(hours-40)));
        }
        if(pay<=22.67){
            totalPay = totalPay * .88;
        } else if(pay>22.67&&pay<=48.33){
            totalPay = totalPay * .78;
        } else if(pay>48.33&&pay<=92.28){
            totalPay = totalPay * .76;
        } else if(pay>92.28&&pay<=117.18){
            totalPay = totalPay * .68;
        } else if(pay>117.18&&pay<=292.96){
            totalPay = totalPay * .65;
        } else if(pay>292.96){
            totalPay = totalPay * .37;
        } else{
            totalPay = 0.0;
        }
    }
}
