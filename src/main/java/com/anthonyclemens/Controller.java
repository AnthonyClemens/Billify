package com.anthonyclemens;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Controller implements Initializable{
    FinancialCommand data = new FinancialCommand();

    //Income Table
    @FXML
    private TableView<Income> incomeTable;
    @FXML
    private TableColumn<Income, Date> tableDate;
    @FXML
    private TableColumn<Income, String> tableCat;
    @FXML
    private TableColumn<Income, String> tableSrc;
    @FXML
    private TableColumn<Income, Double> tableAmnt;
    @FXML
    private TableColumn<Income, String> tablePer;
    @FXML
    private Button addIncome;
    @FXML
    private Button removeIncome;

    //Income Input
    @FXML
    private DatePicker incomeDate;
    @FXML
    private TextField incomeCat;
    @FXML
    private TextField incomeSrc;
    @FXML
    private TextField incomeAmnt;
    @FXML
    private TextField incomePer;


    //Middle Commands
    @FXML
    private Button viewPie;
    @FXML
    private Button calcTotal;
    @FXML
    private Label total;


    //Expense Table
    @FXML
    private TableView<Expense> expenseTable;
    @FXML
    private TableColumn<Expense, Date> tableDateEx;
    @FXML
    private TableColumn<Expense, String> tableCatEx;
    @FXML
    private TableColumn<Expense, String> tableSrcEx;
    @FXML
    private TableColumn<Expense, Double> tableAmntEx;
    @FXML
    private TableColumn<Expense, String> tablePerEx;
    @FXML
    private Button addExpense;
    @FXML
    private Button removeExpense;

    //Expense Input
    @FXML
    private DatePicker expenseDate;
    @FXML
    private TextField expenseCat;
    @FXML
    private TextField expenseSrc;
    @FXML
    private TextField expenseAmnt;
    @FXML
    private TextField expensePer;


    public void calculateTotal(){
        try{
            double totalAm = 0;
            for(int i=0; i < data.getIncomes(data).size(); i++){
                totalAm = totalAm+data.getIncomes(data).get(i).getAmount();
            }
            for(int i=0; i < data.getExpenses(data).size(); i++){
                totalAm = totalAm-data.getExpenses(data).get(i).getAmount();
            }
            String totalStr = String.format("%,.2f", totalAm);
            total.setText("$"+totalStr);
        } catch (Exception e){
            System.out.println("Cannot compute, make sure there are values present.");
        }
    }
    public void addIncomes(){
        try{
            LocalDate localDate = incomeDate.getValue();
            data.addIncome(new Income(incomeCat.getText(), Double.parseDouble(incomeAmnt.getText()), java.sql.Date.valueOf(localDate), incomePer.getText(), incomeSrc.getText()));
            ObservableList<Income> observableList = FXCollections.observableList(data.getIncomes(data));
            incomeTable.setItems(observableList);
        } catch (Exception e){
            System.out.println("Fields are blank");
        }
    }
    public void removeIncomes() {
        try{
            int selectedID = incomeTable.getSelectionModel().getSelectedIndex();
            incomeTable.getItems().remove(selectedID);
        } catch (Exception e){
            System.out.println("Nothing is selected");
        }
    }

    public void addExpenses(){
        try{
            LocalDate localDateEx = expenseDate.getValue();
            data.addExpense(new Expense(expenseCat.getText(), Double.parseDouble(expenseAmnt.getText()), java.sql.Date.valueOf(localDateEx), expensePer.getText(), expenseSrc.getText()));
            ObservableList<Expense> observableList = FXCollections.observableList(data.getExpenses(data));
            expenseTable.setItems(observableList);
        } catch (Exception e){
            System.out.println("Fields are blank");
        }
    }
    public void removeExpenses() {
        try{
            int selectedID = expenseTable.getSelectionModel().getSelectedIndex();
            expenseTable.getItems().remove(selectedID);
        } catch (Exception e){
            System.out.println("Nothing is selected");
        }
    }

    public void launchPie(){
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/PieChart.fxml"));
            Parent root = (Parent) loader.load();

            PieController secController=loader.getController();
            secController.makePie(data);
            Stage stage=new Stage();
            stage.setTitle("Percentage of spending by Category");
            Scene newScene = new Scene(root);
            newScene.getStylesheets().add(BillifyFX.class.getResource("/billify.css").toExternalForm());
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableDate.setCellValueFactory(new PropertyValueFactory<Income, Date>("date"));
        tableCat.setCellValueFactory(new PropertyValueFactory<Income, String>("category"));
        tableSrc.setCellValueFactory(new PropertyValueFactory<Income, String>("source"));
        tableAmnt.setCellValueFactory(new PropertyValueFactory<Income, Double>("amount"));
        tablePer.setCellValueFactory(new PropertyValueFactory<Income, String>("person"));

        tableDateEx.setCellValueFactory(new PropertyValueFactory<Expense, Date>("date"));
        tableCatEx.setCellValueFactory(new PropertyValueFactory<Expense, String>("category"));
        tableSrcEx.setCellValueFactory(new PropertyValueFactory<Expense, String>("source"));
        tableAmntEx.setCellValueFactory(new PropertyValueFactory<Expense, Double>("amount"));
        tablePerEx.setCellValueFactory(new PropertyValueFactory<Expense, String>("person"));
    }
}
