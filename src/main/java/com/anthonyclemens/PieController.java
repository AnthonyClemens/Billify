package com.anthonyclemens;

import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class PieController{
    @FXML
    private PieChart pieChart;

    @FXML
    private Label percentageLabel;

    public void makePie(FinancialCommand data) {
        Map<String, Double> categoryPercentages = com.anthonyclemens.FinancialCommand.getExpenseCategoryPercentages(data);
        for (Map.Entry<String, Double> entry : categoryPercentages.entrySet()) {
            String cap = entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1);
            PieChart.Data pieData = new PieChart.Data(cap, entry.getValue());
            pieChart.getData().add(pieData);

            // Add event handler for displaying percentage on hover
            pieData.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                double total = 0;
                for (PieChart.Data d : pieChart.getData()) {
                    total += d.getPieValue();
                }
                String percentageText = String.format("%.1f%%", 100 * pieData.getPieValue() / total);
                //System.out.println("Hovered over " + cap + ": " + percentageText); // Debug line
                showPercentageLabel(e.getSceneX(), e.getSceneY(),percentageText);
            });
        }
        pieChart.setTitle("Percentage of spending by Category");
    }
    private void showPercentageLabel(double x, double y, String text) {
        //Display a label for showing the percentage
        percentageLabel.setText(text);
        percentageLabel.setLayoutX(x);
        percentageLabel.setLayoutY(y);
    }
}
