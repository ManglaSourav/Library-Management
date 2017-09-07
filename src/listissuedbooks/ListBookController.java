package listissuedbooks;

import addbook.AddBookController;
import database.DatabaseHandler;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import listbook.Book_ListController;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Sourav Mangla on 24-Jul-17.
 */
public class ListBookController implements Initializable {


    @FXML
    private AnchorPane rootPane;

    @FXML
    private TableView<IssuedBook> tableview;

    @FXML
    private TableColumn<IssuedBook, String> bookidCol;

    @FXML
    private TableColumn<IssuedBook, String> holderIDCol;

    @FXML
    private TableColumn<IssuedBook, Integer> snoCol;

    @FXML
    private TableColumn<IssuedBook, String> issuedateCol;

    DatabaseHandler handler;

    ObservableList<IssuedBook> list = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadData() throws SQLException {

        handler = DatabaseHandler.getInstance();
        String qu = " select * from Issue";
        ResultSet rs = handler.ExecQuery(qu);
        Integer sno = 0;
        try {
            while (rs.next()) {

                String id = rs.getString("BookID");
                String holderID = rs.getString("memberID");
                String issueTime = rs.getString("issuetime");
                list.add(new IssuedBook( sno, id, holderID, issueTime));

            }
        }catch (SQLException ex){

            Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println(list);
        tableview.getItems().setAll(list);
    }
    private void initCol() {//Bind Book variables with table col

        snoCol.setCellValueFactory(new PropertyValueFactory<>("sno"));
        bookidCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        holderIDCol.setCellValueFactory(new PropertyValueFactory<>("holderID"));
        issuedateCol.setCellValueFactory(new PropertyValueFactory<>("issuedDate"));
    }

    public static class IssuedBook{

        private static Integer sno ;
        private final SimpleStringProperty id;
        private final SimpleStringProperty holderID;
        private final SimpleStringProperty issuedDate;

        public IssuedBook(Integer sno, String id, String holderID, String issueDate) {

            this.sno = sno;
            this.id = new SimpleStringProperty(id);
            this.holderID = new SimpleStringProperty(holderID);
            this.issuedDate = new SimpleStringProperty(issueDate);

        }

        public Integer getSno() {
            return sno++;
        }
        public String getId() {
            return id.get();
        }
        public String getHolderID() {
            return holderID.get();
        }
        public String getIssuedDate() { return issuedDate.get(); }
    }
}
