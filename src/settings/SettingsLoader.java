package settings;

import database.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Sourav Mangla on 21-Jul-17.
 */

public class SettingsLoader extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("settings.fxml"));
        primaryStage.setTitle("Settings");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        //Preferences.initConfig();
    }
}
