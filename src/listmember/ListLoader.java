package listmember;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Sourav Mangla on 19-Jul-17.
 */
public class ListLoader extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("member_list.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();


    }
}
