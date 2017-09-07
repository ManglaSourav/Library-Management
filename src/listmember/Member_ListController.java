package listmember;

import addbook.AddBookController;
import database.DatabaseHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Sourav Mangla on 19-Jul-17.
 */
public class Member_ListController implements Initializable{

    @FXML
    private AnchorPane rootPane =new AnchorPane();

    @FXML
    private TableView<Member> tableView;

    @FXML
    private  TableColumn<Member, String> nameCol;

    @FXML
    private TableColumn<Member, String> idCol;

    @FXML
    private TableColumn<Member, String> mobileCol;

    @FXML
    private TableColumn<Member, String> emailCol;


    DatabaseHandler handler;

    ObservableList<Member> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
     initCol();
     loadData();

    }

    private void loadData() {

        try {
            handler = DatabaseHandler.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String qu = " select * from MEMBER";
        ResultSet rs = handler.ExecQuery(qu);
        try {
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String mobile = rs.getString("mobile");
                String email = rs.getString("email");

                // tableView.getItems().setAll(new Book(title, id, author, publisher, availability));
                list.addAll(new Member(name, id, mobile, email));
            }
        }catch (SQLException ex){

            Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(list);
        //tableView.getColumns().addAll(titleCol, idCol, authorCol, publisherCol, availabilityCol); //add coloumns to table
        tableView.getItems().setAll(list);
    }


    private void initCol() {//Bind Book variables with table col

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mobileCol.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    }
    public static class Member{
        private final SimpleStringProperty name;
        private final SimpleStringProperty id;
        private final SimpleStringProperty mobile;
        private final SimpleStringProperty email;


        public Member(String name, String id, String mobile, String email) {
            this.name = new SimpleStringProperty(name);
            this.id = new SimpleStringProperty(id);
            this.mobile = new SimpleStringProperty(mobile);
            this.email = new SimpleStringProperty(email);
        }

        public String getName() {
            return name.get();
        }

        public String getId() {
            return id.get();
        }

        public String getMobile() {
            return mobile.get();
        }

        public String getEmail() {
            return email.get();
        }

    }

}
