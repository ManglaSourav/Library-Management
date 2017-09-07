package addbook;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import database.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddBookController implements Initializable{

    @FXML
    private JFXTextField title;

    @FXML
    private JFXTextField id;

    @FXML
    private JFXTextField author;

    @FXML
    private JFXTextField publisher;

    @FXML
    private JFXButton saveButton;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private AnchorPane rootPane;

    DatabaseHandler databaseHandler;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            databaseHandler = DatabaseHandler.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        checkData();

    }

    @FXML
    private void addBook(ActionEvent event){

        String bookID = id.getText();
        String bookAuthor = author.getText();
        String bookName = title.getText();
        String bookPublisher = publisher.getText();

        if( bookID.isEmpty() || bookAuthor.isEmpty() || bookName.isEmpty() || bookPublisher.isEmpty() ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter in all fields");
            alert.showAndWait();
            return;
        }

        String qu = "insert into BOOK values ("
                + "'" + bookID + "',"
                + "'" + bookName + "',"
                + "'" + bookAuthor + "',"
                + "'" + bookPublisher + "',"
                + "true"
                + ")";
        System.out.println(qu);
        if(databaseHandler.ExecAction(qu)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Success");
            alert.showAndWait();

            id.clear();
            author.clear();
            publisher.clear();
            title.clear();
        }else { //for error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed");
            alert.showAndWait();
        }
    }

    @FXML
    private void cancel(ActionEvent event){
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    private void checkData(){
        String qu = " select title from BOOK";
        ResultSet rs = databaseHandler.ExecQuery(qu);
       try {
           while (rs.next()) {
               String titlex = rs.getString("title");
               System.out.println(titlex);
           }
       }catch (SQLException ex){

           Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
       }

    }
}
