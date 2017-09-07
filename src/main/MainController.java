package main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;
import database.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Sourav Mangla on 19-Jul-17.
 */
public class MainController implements Initializable {


    @FXML
    private HBox book_info;

    @FXML
    private TextField bookIDInput;

    @FXML
    private Label bookName;

    @FXML
    private Label  bookAuthor;

    @FXML
    private Label bookStatus;

    @FXML
    private HBox member_info;

    @FXML
    private JFXButton issueButton;

    @FXML
    private TextField memberIDInput;

    @FXML
    private Label memberName;

    @FXML
    private Label memberMobile;

    @FXML
    private JFXTextField bookID;

    @FXML
    private ListView<String> issueDataList = new ListView<>();

    @FXML
    private StackPane rootPane;

    Boolean isReadyForSubmission = false;

    DatabaseHandler databaseHandler;

    @FXML
    void loadIssuedBooks(ActionEvent event) {

        loadWindow("/listissuedbooks/listbook.fxml", "Issued Book List");

    }

    @FXML
    void handleMenuFullScreen(ActionEvent event) {

        Stage stage = ((Stage)rootPane.getScene().getWindow());
        stage.setFullScreen(true);
    }

    @FXML
    void handleMenuViewBooks(ActionEvent event) {

        loadWindow("/listbook/book_list.fxml", "Book List");

    }

    @FXML
    void handleMenuViewMembers(ActionEvent event) {

        loadWindow("/listmember/member_list.fxml", "Member List");

    }
    @FXML
    void handleMenuAddBook(ActionEvent event) {

        loadWindow("/addbook/addBook.fxml", "Add New Book");
    }

    @FXML
    void handleMenuAddMember(ActionEvent event) {

        loadWindow("/addmember/member_add.fxml", "Add New Member");
    }
    @FXML
    void handleMenuClose(ActionEvent event) {
        ((Stage)rootPane.getScene().getWindow()).close();
    }
    @FXML
    void loadRenewOperation(ActionEvent event) {

        if( !isReadyForSubmission ) {  //check that a valid book is Renewed or not
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please Select a Book to Renew");
            alert.setHeaderText(null);
            alert.setTitle("Failed");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Renew Operation");
        alert.setHeaderText(null);
        alert.setContentText("Are you Want to Renew the book ?");
        Optional<ButtonType> response = alert.showAndWait();

        if (response.get() == ButtonType.OK) {
            String qu = "update ISSUE set issueTime = CURRENT_TIMESTAMP, renew_count = renew_count+1  where bookId = '" +bookID.getText() +"'";

            if( databaseHandler.ExecAction(qu) ){

                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setContentText("Book has been Renewed");
                alert1.setHeaderText(null);
                alert1.setTitle("Success");
                alert1.showAndWait();

                issueDataList.getItems().clear(); //clear the list after renewing a book

            }else{

                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setContentText("Renew operation has been Failed");
                alert2.setHeaderText(null);
                alert2.setTitle("Failed");
                alert2.showAndWait();
            }
        }else{

            Alert alert4 = new Alert(Alert.AlertType.INFORMATION);
            alert4.setTitle("Cancelled");
            alert4.setHeaderText(null);
            alert4.setContentText("Renew Operation Cancelled");
            alert4.showAndWait();
        }
    }
    @FXML
    void loadSubmissionOp(ActionEvent event) {

        if( !isReadyForSubmission ) {  //check that a valid book is submitted or not
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please Select a Book to Submit");
            alert.setHeaderText(null);
            alert.setTitle("Failed");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Submit Operation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to Return the book ?");
        Optional<ButtonType> response = alert.showAndWait();

        if(response.get() == ButtonType.OK){
            String id = bookID.getText();
            String qu = "Delete from ISSUE where bookID = '" +id +"'";
            String qu1 = "Update BOOK set isAvail = true where id = '" +id +"'" ;

            if( databaseHandler.ExecAction(qu) && databaseHandler.ExecAction(qu1) ){

                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setContentText("Book has been Submitted");
                alert1.setHeaderText(null);
                alert1.setTitle("Success");
                alert1.showAndWait();

                issueDataList.getItems().clear();  //clear the screen after successfull submission
             }else{
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setContentText("Submission has been Failed");
                alert2.setHeaderText(null);
                alert2.setTitle("Failed");
                alert2.showAndWait();
            }
        }else{

            Alert alert4 = new Alert(Alert.AlertType.INFORMATION);
            alert4.setTitle("Cancelled");
            alert4.setHeaderText(null);
            alert4.setContentText("Submission Operation Cancelled");
            alert4.showAndWait();

        }
    }

    @FXML
    void loadBookInfo2(ActionEvent event) {

        ObservableList<String> issueData = FXCollections.observableArrayList();
        isReadyForSubmission = false;
        String id = bookID.getText();
        String qu = "select * from ISSUE where bookID = '" +id +"'";
        ResultSet rs = databaseHandler.ExecQuery(qu);

        try {
            while(rs.next()){
                String mBookId = id;
                String mMemberID = rs.getString("memberID");
                Timestamp mIssueTime = rs.getTimestamp("issueTime");
                int mRenewCount = rs.getInt("renew_count");

                issueData.add("Issue data and time :" +mIssueTime.toGMTString());
                issueData.add("Renew count " +mRenewCount);

                issueData.add(" ");
                issueData.add("Book Information :-");
                qu = "select * from BOOK where id = '" + mBookId + "'" ;
                ResultSet r1 = databaseHandler.ExecQuery(qu);
                while(r1.next()){

                    issueData.add("\tName :"+ r1.getString("title"));
                    issueData.add("\tID :" +r1.getString("id"));
                    issueData.add("\tAuthor :" +r1.getString("author"));
                    issueData.add("\tPublisher :" +r1.getString("publisher"));
                }

                issueData.add(" ");
                issueData.add("Member Information :-");
                qu = "select * from MEMBER where id = '" + mMemberID + "'" ;
                r1 = databaseHandler.ExecQuery(qu);
                while(r1.next()){

                    issueData.add("\tName :" +r1.getString("name"));
                    issueData.add("\tMobile :" +r1.getString("mobile"));
                    issueData.add("\tEmail :" +r1.getString("email"));

                }
                isReadyForSubmission = true;
            }
        }catch (SQLException ex){

            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println(issueData);
        issueDataList.getItems().setAll(issueData);

    }

    @FXML
    void loadIssueOperation(ActionEvent event) {


        String memberID = memberIDInput.getText();
        String bookID = bookIDInput.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Issue Operation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to issue the book " + bookName.getText() + "\n to " + memberName.getText() +" ?");
        Optional<ButtonType> response = alert.showAndWait();

        if(response.get() == ButtonType.OK){

            String str = "insert into ISSUE(memberID, bookID) values ( "
                    + "'" + memberID + "',"
                    + "'" + bookID + "')";
            String str2 = "update BOOK set isAvail = false where id = '" + bookID + "'" ;

            if( databaseHandler.ExecAction(str) && databaseHandler.ExecAction(str2) ){
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Success");
                alert1.setHeaderText(null);
                alert1.setContentText("Book issue Complete");
                alert1.showAndWait();
                bookIDInput.setText("");
                memberIDInput.setText("");
                clearBookCache();
                clearMemberCache();

            }else{
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Failed");
                alert2.setHeaderText(null);
                alert2.setContentText("Book issue Failed");
                alert2.showAndWait();
            }
        }else{

            Alert alert4 = new Alert(Alert.AlertType.INFORMATION);
            alert4.setTitle("Cancelled");
            alert4.setHeaderText(null);
            alert4.setContentText("Book Operation Cancelled");
            alert4.showAndWait();

        }
    }

    void clearBookCache(){
        bookAuthor.setText("");
        bookName.setText("");
        bookStatus.setText("");

    }
    void clearMemberCache(){
        memberName.setText("");
        memberMobile.setText("");
    }

    @FXML
    void loadMemberInfo(ActionEvent event) {

        clearMemberCache();
        String id = memberIDInput.getText();
        String qu = "Select * from MEMBER where id ='" + id + "'";

        ResultSet rs = databaseHandler.ExecQuery(qu);
        Boolean flag = false; //to check book is present or not;

        try {
            while (rs.next()) {

                String mName = rs.getString("name");
                String mMobile = rs.getString("mobile");

                memberName.setText(mName);
                memberMobile.setText(mMobile);
                flag = true;
            }
            if(!flag){
                memberName.setText("No Member Available");
                //memberMobile.setText(""); clearMemberCache() function will do that work

            }
        }catch(SQLException ex){

            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void loadBookInfo(ActionEvent event) {

        clearBookCache();
        String id = bookIDInput.getText();
        String qu = "Select * from BOOK where id ='" + id + "'";

        //System.out.println(qu); for check

        ResultSet rs = databaseHandler.ExecQuery(qu);
        Boolean flag = false; //to check book is present or not;

        try {
            while (rs.next()) {

                //System.out.println(rs.getString("title")); for check
                String bName = rs.getString("title");
                String bAuthor = rs.getString("author");
                Boolean bStatus = rs.getBoolean("isAvail");

                bookName.setText(bName);
                bookAuthor.setText(bAuthor);
                String status = (bStatus)? "Available" : "Not Available" ;
                bookStatus.setText(status);
                flag = true;
            }
            if(!flag){
                bookAuthor.setText("No Such Book is Available");
                //bookName.setText(""); clearBookCache() function will do that work
                //bookStatus.setText("");
            }
        }catch(SQLException ex){

        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    @FXML
    void loadAddBook(ActionEvent event) {

        loadWindow("/addbook/addBook.fxml","Add New Book");
    }

    @FXML
    void loadAddMember(ActionEvent event) {

        loadWindow("/addmember/member_add.fxml", "Add New Member");
    }

    @FXML
    void loadBookTable(ActionEvent event) {

        loadWindow("/listbook/book_list.fxml", "Book List");

    }

    @FXML
    void loadMemberTable(ActionEvent event) {

        loadWindow("/listmember/member_list.fxml", "Member List");

    }

    @FXML
    void loadSettings(ActionEvent event) {

        loadWindow("/settings/settings.fxml", "Settings");
    }

    void loadWindow(String loc, String title){

        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        }catch (IOException ex){
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        JFXDepthManager.setDepth(book_info, 1);
        JFXDepthManager.setDepth(member_info, 1);
        try {
            databaseHandler = DatabaseHandler.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
