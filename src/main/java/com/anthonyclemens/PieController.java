package com.anthonyclemens;

import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

public class PieController{
    @FXML
    private PieChart pieChart;
    public void makePie(FinancialCommand data) {
        Map<String, Double> categoryPercentages = com.anthonyclemens.FinancialCommand.getExpenseCategoryPercentages(data);
        for (Map.Entry<String, Double> entry : categoryPercentages.entrySet()) {
            String cap = entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1);
            pieChart.getData().add(new PieChart.Data(cap, entry.getValue()));
        }
        pieChart.setTitle("Percentage of spending by Category");
    }
}
