package login;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.MainController;
import org.apache.commons.codec.digest.DigestUtils;
import settings.Preferences;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Sourav Mangla on 24-Jul-17.
 */
public class LoginController implements Initializable {

    @FXML
    private JFXTextField username;

    @FXML
    private JFXTextField password;

    @FXML
    private Label titleLabel;

    Preferences preferences;

    @FXML
    void handleCancelButton(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void handleLoginButton(ActionEvent event) {

        titleLabel.setText("Library Assistant Login");
        titleLabel.setStyle("-fx-background-color: black; -fx-text-fill: white");

        String uname = username.getText();
        String upass = DigestUtils.sha1Hex( password.getText() ); //convert password into hash

        if( uname.equals(preferences.getUsername()) && upass.equals(preferences.getPassword()) ){
            closeStage();
            loadWindow();
        }else{
            titleLabel.setText("Invalid Credentails");
            titleLabel.setStyle("-fx-background-color: #d01f27; -fx-text-fill: white");
        }


    }

    private void closeStage() {
        ((Stage)username.getScene().getWindow()).close();
    }

    void loadWindow(){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/main/main.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Library Assistant");
            stage.setScene(new Scene(parent));
            stage.show();
        }catch (IOException ex){
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        preferences = Preferences.getPreferences();

    }
}
