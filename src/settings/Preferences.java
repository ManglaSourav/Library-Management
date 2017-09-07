package settings;

import com.google.gson.Gson;
import javafx.scene.control.Alert;
import org.apache.commons.codec.digest.DigestUtils;
import sun.dc.pr.PRError;

import java.io.*;

/**
 * Created by Sourav Mangla on 22-Jul-17.
 */
public class Preferences {

    public static final String CONFIG_FILE = "config.txt";
    int nDaysWithoutFine;
    float finePerDay;
    String username;
    String password;

    public Preferences() {
        nDaysWithoutFine = 14;
        finePerDay = 10;
        username = "admin";
        setPassword("admin");
    }

    public int getnDaysWithoutFine() {
        return nDaysWithoutFine;
    }

    public void setnDaysWithoutFine(int nDaysWithoutFine) {
        this.nDaysWithoutFine = nDaysWithoutFine;
    }

    public float getFinePerDay() {
        return finePerDay;
    }

    public void setFinePerDay(float finePerDay) {
        this.finePerDay = finePerDay;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        if(password.length() < 16) {
            String pass = DigestUtils.sha1Hex(password); //convert password to hash code
            this.password = pass;
        }else
            this.password = password;   //if password already in hash form
    }

    public static void initConfig(){

        Writer writer = null;
        try {
            Preferences preferences = new Preferences();
            Gson gson = new Gson();
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(preferences, writer);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Preferences getPreferences(){
        Gson gson = new Gson();
        Preferences preferences = new Preferences();

        try {
            preferences = gson.fromJson(new FileReader(CONFIG_FILE), Preferences.class);
        } catch (FileNotFoundException e) {
            initConfig();  //if file note found than create a file
            e.printStackTrace();
        }
        return preferences;
    }


    public static void writePreferencesToFile(Preferences preferences){

        Writer writer = null;
        try {
            Gson gson = new Gson();
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(preferences, writer);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Success");
            alert.setContentText("Settings Updated");
            alert.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Failed");
            alert.setContentText("Can't Save Configuration File");
            alert.showAndWait();

        }finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }










}
