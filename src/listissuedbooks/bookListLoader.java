package listissuedbooks;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Sourav Mangla on 24-Jul-17.
 */
public class bookListLoader extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("listbook.fxml"));
        stage.setTitle("Issued Books");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
