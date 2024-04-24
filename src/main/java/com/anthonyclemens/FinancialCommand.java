package com.anthonyclemens;

import java.util.ArrayList;
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
        System.out.println("Total spent:"+totalAmountSpent);
        Map<String, Double> categoryPercentages = new HashMap<>();
        for (String category : catList) {
            double amount = getCategoryCost(financialCommand, category);
            double percentage = amount/totalAmountSpent;
            System.out.println(category+" "+percentage);
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
}
