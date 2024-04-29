package com.anthonyclemens;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;

public class LineController{
    private final ObservableList<String> months = FXCollections.observableArrayList(
            "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
    );

    @FXML
    private ComboBox<String> monthComboBox;
    @FXML
    private ComboBox<Integer> yearComboBox;
    @FXML
    private LineChart<Number, Number> lineChart;

    public void makeLine(FinancialCommand data) {
        lineChart.getXAxis().setLabel("Day");
        lineChart.getYAxis().setLabel("Money left");
        lineChart.setLegendVisible(true);
        lineChart.setAlternativeRowFillVisible(false);
        lineChart.setTitle("Money Over Time");
        monthComboBox.getItems().addAll(months);
        yearComboBox.getItems().addAll(2024,2025,2026,2027); // Customize the years as needed
        // Set default values
        monthComboBox.setValue("January");
        yearComboBox.setValue(2024);
        // Add event listeners to ComboBoxes
        monthComboBox.setOnAction(e -> updateChart(data));
        yearComboBox.setOnAction(e -> updateChart(data));
    }

    private void updateChart(FinancialCommand data) {
        // Get selected month and year
        lineChart.getData().clear();
        String selectedMonth = monthComboBox.getValue();
        int selectedYear = yearComboBox.getValue();

        // Calculate total amount for each day of the selected month and year
        YearMonth yearMonth = YearMonth.of(selectedYear, months.indexOf(selectedMonth) + 1);
        int daysInMonth = yearMonth.lengthOfMonth();
        lineChart.getXAxis().autosize();

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Money Left Per Day");
        double totalAmount = 0.0;
        for (int day = 1; day <= daysInMonth; day++) {
            Date date = java.sql.Date.valueOf(LocalDate.of(selectedYear, months.indexOf(selectedMonth) + 1, day));
            totalAmount+=data.getIncomeDay(date);
            totalAmount-=data.getExpenseDay(date);
            series.getData().add(new XYChart.Data<>(day, totalAmount));
        }
        lineChart.getData().add(series);
    }
}
