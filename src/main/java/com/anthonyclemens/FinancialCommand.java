package com.anthonyclemens;

import java.util.ArrayList;
import java.util.List;

public class FinancialCommand {
    private ArrayList<Expense> expenseList = new ArrayList<>();
    private ArrayList<Income> incomeList = new ArrayList<>();

    public static List<String> getCategories(FinancialCommand financialCommand, String type){
        List<String> catList = new ArrayList<>();
        List<String> catUnique = new ArrayList<String>() {
        };
        if(type.equals("income")){
            for(int i=0; i<financialCommand.incomeList.size(); i++){
                catList.add(financialCommand.incomeList.get(i).getCategory());
            }
            catUnique = catList.stream().distinct().toList();
        }else{
            for(int i=0; i<financialCommand.expenseList.size(); i++){
                catList.add(financialCommand.expenseList.get(i).getCategory());
            }
            catUnique = catList.stream().distinct().toList();
        }
        return catUnique;
    }

    public FinancialCommand addExpense(Expense newExpense){
        expenseList.add(newExpense);
        return this;
    }

    public FinancialCommand addIncome(Income newIncome){
        incomeList.add(newIncome);
        return this;
    }

    public FinancialCommand delExpense(int i){
        expenseList.remove(i);
        return this;
    }

    public FinancialCommand delIncome(int i){
        incomeList.remove(i);
        return this;
    }

    public int numExpense(FinancialCommand financialCommand){
        return financialCommand.expenseList.size();
    }

    public int numIncome(FinancialCommand financialCommand){
        return financialCommand.incomeList.size();
    }

    public List<Expense> getExpenses(FinancialCommand financialCommand){
        return financialCommand.expenseList;
    }

    public List<Income> getIncomes(FinancialCommand financialCommand){
        return financialCommand.incomeList;
    }
}
