package database;

import com.sun.org.apache.xpath.internal.SourceTree;

import javax.swing.*;
import java.sql.*;

/**
 * Created by Sourav Mangla on 7/18/2017.
 */
public class DatabaseHandler {


    private static  DatabaseHandler handler = null;  // only one object is created for whole purpose

    private static final String DB_URL = "jdbc:derby:database;create=true";  //create database folder
    private static Connection conn = null;
    private static Statement stmt = null;



    private DatabaseHandler() throws SQLException {  //no class can make object of DatabaseHandler class (singletone pattern)

        createConnection();  // make conncetion in starting
        setupBookTable();    //set up table in sarting
        setupMemberTable();
        setupIssueTable();

    }

    public static DatabaseHandler getInstance() throws SQLException {
        if (handler == null) {

            handler = new DatabaseHandler();
        }
        return handler;
    }


    private void setupMemberTable() {

        String TABLE_NAME = "MEMBER";
        try{
            stmt = conn.createStatement();

            /**
             *it will create table only when there is no table
             */
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME, null);

            if(tables.next()){
                System.out.println(" Table " + TABLE_NAME + " already exists. Ready to go!");
            }else {

                stmt.execute("create table " + TABLE_NAME + "("
                        + "id varchar(200) primary key,\n "
                        + "name varchar(200),\n"
                        + "mobile varchar(20),\n"
                        + "email varchar(100)\n"
                        + ")" );
            }
        }catch (Exception e){
            System.err.println(e.getMessage() + "........setup database");
        }finally {
        }
    }

    void createConnection(){   //create Connection with datatbase
        try{
          Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
          conn = DriverManager.getConnection(DB_URL);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Can't load Database", "Database Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    void setupBookTable(){
       String TABLE_NAME = "BOOK";

       try{
           stmt = conn.createStatement();

           /**
            *it will create table only when there is no table
            */
           DatabaseMetaData dbm = conn.getMetaData();
           ResultSet tables = dbm.getTables(null, null, TABLE_NAME, null);

           if(tables.next()){
               System.out.println(" Table " + TABLE_NAME + " already exists. Ready to go!");
           }else {
               stmt.execute("create table " + TABLE_NAME + "("
                       + "id varchar(200) primary key,\n "
                       + "title varchar(200),\n"
                       + "author varchar(200),\n"
                       + "publisher varchar(200),\n"
                       + "isAvail boolean default true" // a new book is always available in library
                       + ")" );
           }
       }catch (Exception e){
      //     e.printStackTrace();
           System.err.println(e.getMessage() + "........setup database");
       }finally {
       }

    }

    public ResultSet ExecQuery(String query){
        ResultSet result;
        try{
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
            return result;
        }catch(SQLException ex){
            System.out.println("Exception in execQuery:DatabaseHandler" + ex.getLocalizedMessage());
            return null;
        }finally {

        }
    }

    public boolean ExecAction(String query){
        try{
            stmt = conn.createStatement();
            stmt.execute(query);
            return true;
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error" +ex.getMessage(), "error Occured", JOptionPane.ERROR_MESSAGE );
            System.out.println("Exception in execAction:DatabaseHandler" + ex.getLocalizedMessage());
            return false;
        }finally {

        }
    }

    void setupIssueTable() throws SQLException {
        String TABLE_NAME = "ISSUE";

        try{
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME, null);
            if(tables.next()){

                System.out.println(" Table " + TABLE_NAME + " already exists. Ready to go!");

            }else {
                stmt.execute("create table " + TABLE_NAME + "("
                        + "bookID varchar(200) primary key,\n "
                        + "memberID varchar(200),\n"
                        + "issueTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n"
                        + "renew_count INTEGER DEFAULT 0,\n"
                        + "FOREIGN KEY (bookID) REFERENCES BOOK(id),\n"
                        + "FOREIGN KEY (memberID) REFERENCES MEMBER(id)"
                        + ")" );
            }
        }catch (SQLException e){
        //     e.printStackTrace();
            System.err.println(e.getMessage() + "........setup database");

        } finally {
        }
    }
}
