package com.anthonyclemens;

import java.io.File;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
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


    //Middle Elements
    @FXML
    private ImageView logoImg;
    @FXML
    private Button viewPie;
    @FXML
    private Label total;
    @FXML
    private Text messageText;
    @FXML
    private Button saveFileButton;
    @FXML
    private Button loadFileButton;
    @FXML
    private Button viewLine;

    @FXML
    private Button addPaycheckB;
    @FXML
    private TextField payHourly;
    @FXML
    private TextField payHours;


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
        try{ //Try-catch for if values are not present or valid
            double totalAm = 0;
            for(int i=0; i < data.numIncomes(); i++){
                totalAm = totalAm+data.getIncomes().get(i).getAmount(); //Add up all of the Income objects
            }
            for(int i=0; i < data.numExpenses(); i++){
                totalAm = totalAm-data.getExpenses().get(i).getAmount(); //Subtract all of the Expense objects
            }
            String totalStr = String.format("%,.2f", totalAm);
            total.setText("$"+totalStr); //Format and set the total text to the dollar amount left over
        } catch (Exception e){
            messageText.setText("Message: Calculation error, ensure valid data is present");
            System.out.println("Cannot compute, make sure there are values present.");
        }
    }
    public void addIncomes(){
        try{ //Try-catch to make sure valid values are present in the application
            messageText.setText("");
            LocalDate localDate = incomeDate.getValue(); //Get the localDate input
            data.addIncome(new Income(incomeCat.getText(), Double.parseDouble(incomeAmnt.getText()), java.sql.Date.valueOf(localDate), incomePer.getText(), incomeSrc.getText()));
            ObservableList<Income> observableList = FXCollections.observableList(data.getIncomes()); //Convert 
            incomeTable.setItems(observableList);
            calculateTotal();
        } catch (Exception e){
            messageText.setText("Message: Ensure Income has valid data");
            System.out.println("Fields are blank");
        }
    }
    public void addPay(){
        try{ //Try-catch to make sure valid values are present in the application
            messageText.setText("");
            double pay = Double.parseDouble(payHourly.getText());
            double hours = Double.parseDouble(payHours.getText());
            double totalPay;

            if(hours>0&&hours<=40){
                totalPay = pay*hours;
            }else{
                totalPay = (pay*40)+(1.5*(pay*(hours-40)));
            }

            if(pay>0&&pay<=22.67){
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
            totalPay=Math.floor(totalPay * 100) / 100;
            //System.out.println(pay+" an hour for "+hours+" hours, equalling "+totalPay);
            incomeAmnt.setText(Double.toString(totalPay));
            incomeCat.setText("Paycheck");
        } catch (Exception e){
            messageText.setText("Message: Ensure Income has valid data");
            System.out.println("Fields are blank");
        }
    }

    public void removeIncomes() {
        try{
            messageText.setText("");
            int selectedID = incomeTable.getSelectionModel().getSelectedIndex();
            incomeTable.getItems().remove(selectedID);
            calculateTotal();
        } catch (Exception e){
            messageText.setText("Message: No Income is selected");
            System.out.println("Nothing is selected");
        }
    }

    public void addExpenses(){
        try{
            messageText.setText("");
            LocalDate localDateEx = expenseDate.getValue();
            data.addExpense(new Expense(expenseCat.getText(), Double.parseDouble(expenseAmnt.getText()), java.sql.Date.valueOf(localDateEx), expensePer.getText(), expenseSrc.getText()));
            ObservableList<Expense> observableList = FXCollections.observableList(data.getExpenses());
            expenseTable.setItems(observableList);
            calculateTotal();
        } catch (Exception e){
            messageText.setText("Message: Ensure Expense has valid data");
            System.out.println("Fields are blank");
        }
    }

    public void removeExpenses() {
        try{
            messageText.setText("");
            int selectedID = expenseTable.getSelectionModel().getSelectedIndex();
            expenseTable.getItems().remove(selectedID);
            calculateTotal();
        } catch (Exception e){
            messageText.setText("Message: No Expense is selected");
            System.out.println("Nothing is selected");
        }
    }

    public void launchPie(){
        try {
            messageText.setText("");
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/PieChart.fxml"));
            Parent root = (Parent) loader.load();

            PieController secController=loader.getController();
            secController.makePie(data);
            Stage stage=new Stage();
            stage.setTitle("Percentage of spending by Category");
            stage.setResizable(false);
            Scene newScene = new Scene(root);
            newScene.getStylesheets().add(BillifyFX.class.getResource("/billify.css").toExternalForm());
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            messageText.setText("Message: Failed to launch Pie Chart");
            e.printStackTrace();
        }
    }

    public void launchLine(){
        try {
            messageText.setText("");
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/LineChart.fxml"));
            Parent root = (Parent) loader.load();

            LineController thirdController=loader.getController();
            thirdController.makeLine(data);
            Stage stage=new Stage();
            stage.setTitle("Money Over Time");
            stage.setResizable(false);
            Scene newScene = new Scene(root);
            newScene.getStylesheets().add(BillifyFX.class.getResource("/billify.css").toExternalForm());
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            messageText.setText("Message: Failed to launch Line Chart");
            e.printStackTrace();
        }
    }

    public void saveFile() {
        messageText.setText("");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        // Show the file dialog and get the selected file
        File selectedFile = fileChooser.showSaveDialog(saveFileButton.getScene().getWindow());

        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
            //System.out.println("Selected file path: " + filePath); //Debug Code
            String result = CSVAdapter.exportToCSV(data.getIncomes(), data.getExpenses(), total.getText(), filePath);
            System.out.println(result);
            messageText.setText(result);
        } else {
            System.out.println("No file selected.");
            messageText.setText("Message: No file selected.");
        }
    }

    public void loadFile() {
        messageText.setText("");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        // Show the file dialog and get the selected file
        File selectedFile = fileChooser.showOpenDialog(loadFileButton.getScene().getWindow());

        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
            //System.out.println("Selected file path: " + filePath); //Debug Code
            data = CSVAdapter.loadFromCSV(filePath);
            System.out.println("File loaded successfully.");
            messageText.setText("Message: File loaded successfully.");
            refreshTables();
        } else {
            System.out.println("Failed to load file.");
            messageText.setText("Message: Failed to load file.");
        }
    }

    public void refreshTables(){
        ObservableList<Income> incomeList = FXCollections.observableList(data.getIncomes());
        incomeTable.setItems(incomeList);
        ObservableList<Expense> expenseList = FXCollections.observableList(data.getExpenses());
        expenseTable.setItems(expenseList);
        calculateTotal();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Initialize Income Table
        tableDate.setCellValueFactory(new PropertyValueFactory<Income, Date>("date"));
        tableCat.setCellValueFactory(new PropertyValueFactory<Income, String>("category"));
        tableSrc.setCellValueFactory(new PropertyValueFactory<Income, String>("source"));
        tableAmnt.setCellValueFactory(new PropertyValueFactory<Income, Double>("amount"));
        tablePer.setCellValueFactory(new PropertyValueFactory<Income, String>("person"));
        //Initialize Expense Table
        tableDateEx.setCellValueFactory(new PropertyValueFactory<Expense, Date>("date"));
        tableCatEx.setCellValueFactory(new PropertyValueFactory<Expense, String>("category"));
        tableSrcEx.setCellValueFactory(new PropertyValueFactory<Expense, String>("source"));
        tableAmntEx.setCellValueFactory(new PropertyValueFactory<Expense, Double>("amount"));
        tablePerEx.setCellValueFactory(new PropertyValueFactory<Expense, String>("person"));
        File file = new File("/icon.png");
        Image image = new Image(file.toURI().toString());
        logoImg.setImage(image);
    }
}
