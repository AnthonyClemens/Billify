package com.anthonyclemens;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Date;

public class CSVAdapter {
    public static String exportToCSV(List<Income> incomeData, List<Expense> expenseData, String total, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filePath)))) {
            // Write CSV header
            writer.write(",Date,Category,Source,Amount,Person\n");

            // Write income data
            writer.write("Incomes\n"); // Label for income section
            for (Income income : incomeData) {
                writer.write("," + income.getDate() + "," + income.getCategory() + "," +
                             income.getSource() + "," + income.getAmount() + "," +
                             income.getPerson() + "\n");
            }

            // Write expense data
            writer.write("\nExpenses\n"); // Label for expense section
            for (Expense expense : expenseData) {
                writer.write("," + expense.getDate() + "," + expense.getCategory() + "," +
                             expense.getSource() + "," + expense.getAmount() + "," +
                             expense.getPerson() + "\n");
            }
            writer.write("\n\nTotal:,"+total);
            writer.write("\n\nExported,by,BillifyFX,by,Anthony,Clemens");

            return "Message: Data exported to CSV successfully!";
        } catch (IOException e) {
            return "Message: Data export to CSV failed!";
        }
    }

    public static FinancialCommand loadFromCSV(String filePath) {
        FinancialCommand data = new FinancialCommand();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isIncomeSection = false;
            boolean isExpenseSection = false;

            while ((line = reader.readLine()) != null) {
                if(line.contains("Exported")){
                    isExpenseSection=false;
                    isIncomeSection=false;
                    break;
                }
                else if (line.equals("Incomes")) {
                    isIncomeSection = true;
                    isExpenseSection = false;
                } else if (line.equals("Expenses")) {
                    isIncomeSection = false;
                    isExpenseSection = true;
                } else if (isIncomeSection) {
                    Income income = parseIncomeLine(line);
                    if (income != null) {
                        data.addIncome(income);
                    }
                } else if (isExpenseSection) {
                    Expense expense = parseExpenseLine(line);
                    if (expense != null) {
                        data.addExpense(expense);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    private static Income parseIncomeLine(String line) {
        String[] fields = line.split(",");
        if (fields.length >= 5) {
            Date date = null;
            try{
                date=java.sql.Date.valueOf(fields[1]); // Assuming date format is ISO_LOCAL_DATE (yyyy-MM-dd)
            }catch (Exception e){
                System.out.println("Error converting date value to a date? "+fields[1]);
            }
            String category = fields[2];
            String source = fields[3];
            double amount = Double.parseDouble(fields[4]);
            String person = fields[5];
            return new Income(category,amount,date,person,source);
        }
        return null;
    }

    private static Expense parseExpenseLine(String line) {
        String[] fields = line.split(",");
        if (fields.length >= 5) {
            Date date = null;
            try{
                date=java.sql.Date.valueOf(fields[1]); // Assuming date format is ISO_LOCAL_DATE (yyyy-MM-dd)
            }catch (Exception e){
                System.out.println("Error converting date value to a date? "+fields[1]);
            }
                String category = fields[2];
            String source = fields[3];
            double amount = Double.parseDouble(fields[4]);
            String person = fields[5];
            return new Expense(category,amount,date,person,source);
        }
        return null;
    }

}
