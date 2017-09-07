package addmember;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.sun.org.apache.xpath.internal.operations.Bool;
import database.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Sourav Mangla on 19-Jul-17.
 */
public class MemberAddController implements Initializable {

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField id;

    @FXML
    private JFXTextField mobile;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXButton saveButton;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private AnchorPane rootPane;

    DatabaseHandler handler;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            handler = DatabaseHandler.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void addMember(ActionEvent event){

        String mName = name.getText();
        String mID = id.getText();
        String mMobile = mobile.getText();
        String mEmail = email.getText();

        Boolean flag = mName.isEmpty() || mID.isEmpty() || mMobile.isEmpty() || mEmail.isEmpty();
        if( flag ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter in all fields");
            alert.showAndWait();
            return;
        }


        String st = "insert into MEMBER values ("
                + "'" + mID + "',"
                +  "'" + mName + "',"
                + "'" + mMobile + "',"
                +  "'" + mEmail + "'"
                + ")";
        System.out.println(st);
        if( handler.ExecAction(st) ){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Success");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Error Occured");
            alert.showAndWait();
        }

    }

    @FXML
    private void cancel(ActionEvent event) {

        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }



}
