package main;

import database.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * Created by Sourav Mangla on 21-Jul-17.
 */

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/login/login.fxml"));
        primaryStage.setTitle("Library Assistant Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        //load database object in starting
       // DatabaseHandler.getInstance(); // to make fast execution of module

        //by using thread we can make execution more faster than before
        new Thread(() -> {
            try {
                DatabaseHandler.getInstance();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
