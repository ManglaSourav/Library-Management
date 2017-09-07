package listbook;

import addbook.AddBookController;
import database.DatabaseHandler;
import javafx.beans.property.SimpleBooleanProperty;
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
public class Book_ListController implements Initializable{


    @FXML
    private AnchorPane rootPane =new AnchorPane();

    @FXML
    private TableView<Book> tableView = new TableView<>();;

    @FXML
    private TableColumn<Book, String> titleCol = new TableColumn<>();

    @FXML
    private TableColumn<Book, String> idCol = new TableColumn<>();

    @FXML
    private TableColumn<Book, String> authorCol = new TableColumn<>();

    @FXML
    private TableColumn<Book, String> publisherCol = new TableColumn<>();

    @FXML
    private TableColumn<Book, Boolean> availabilityCol = new TableColumn<>();

    DatabaseHandler handler;

    ObservableList<Book> list = FXCollections.observableArrayList();

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
        String qu = " select * from BOOK";
        ResultSet rs = handler.ExecQuery(qu);
        try {
            while (rs.next()) {
                String id = rs.getString("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                Boolean availability = rs.getBoolean("isavail");
                list.addAll(new Book(title, id, author, publisher, availability));
            }
        }catch (SQLException ex){

            Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println(list);
        tableView.getItems().setAll(list);
    }
    private void initCol() {//Bind Book variables with table col

        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        availabilityCol.setCellValueFactory(new PropertyValueFactory<>("availability"));

    }

    public static class Book{

        private final SimpleStringProperty title;
        private final SimpleStringProperty id;
        private final SimpleStringProperty author;
        private final SimpleStringProperty publisher;
        private final SimpleBooleanProperty availability;

        public Book(String title, String id, String author, String publisher, Boolean availability) {
            this.title = new SimpleStringProperty(title);
            this.id = new SimpleStringProperty(id);
            this.author = new SimpleStringProperty(author);
            this.publisher = new SimpleStringProperty(publisher);
            this.availability = new SimpleBooleanProperty(availability);
        }

        public String getTitle() {
            return title.get();
        }

        public String getId() {
            return id.get();
        }

        public String getAuthor() {
            return author.get();
        }

        public String getPublisher() {
            return publisher.get();
        }

        public Boolean getAvailability() {
            return availability.get();
        }
    }
}
