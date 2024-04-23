package com.anthonyclemens;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;



public class BillifyFX extends Application{
    @Override
    public void start(Stage stage) throws Exception{
      //Load the main FXML file
      Parent root = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));
      stage.setTitle("BillifyFX: Financial Management Simplified");
      //I cannot get the icon to work for the life of me >:(
      //Image icon = new Image(getClass().getResourceAsStream("/icon.png"));
      Image icon = new Image("file:resources/icon.png");
      stage.getIcons().add(icon);
      //Create the window
      Scene scene = new Scene(root, 1280, 720);
      scene.getStylesheets().add(BillifyFX.class.getResource("/billify.css").toExternalForm());
      stage.setScene(scene);
      stage.setResizable(false);
      stage.show();
    }
   public static void main(String[] args) {
      Application.launch(args);

   }
}
