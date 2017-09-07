package addmember;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Sourav Mangla on 19-Jul-17.
 */
public class MemberAddLoader extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("member_add.fxml"));
        primaryStage.setTitle("Add Member");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();


    }
}
