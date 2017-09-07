package settings;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Sourav Mangla on 22-Jul-17.
 */
public class SettingsController implements Initializable {

    @FXML
    private JFXTextField mDays;

    @FXML
    private JFXTextField finePerDay;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    void handleCancelButton(ActionEvent event) {
        ((Stage)mDays.getScene().getWindow()).close();
    }

    @FXML
    void handleSaveButton(ActionEvent event) {

        int mdays = Integer.parseInt(mDays.getText());
        float fine = Float.parseFloat(finePerDay.getText());
        String uname = username.getText();
        String pass = password.getText();

        Preferences preferences = Preferences.getPreferences();
        preferences.setnDaysWithoutFine(mdays);
        preferences.setFinePerDay(fine);
        preferences.setUsername(uname);
        preferences.setPassword(pass);

        Preferences.writePreferencesToFile(preferences); //write new preferences to config file

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initDefaultValue();

    }

    private void initDefaultValue() { //load dafault values for all variable
        Preferences preferences = Preferences.getPreferences();
        mDays.setText(String.valueOf(preferences.getnDaysWithoutFine()));
        finePerDay.setText(String.valueOf(preferences.getFinePerDay()));
        username.setText(String.valueOf(preferences.getUsername()));
        password.setText(String.valueOf(preferences.getPassword()));
    }
}
