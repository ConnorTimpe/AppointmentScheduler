/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Database;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author connor
 */
public class LogInScreenController implements Initializable {

    Stage stage;

    @FXML
    private Label UsernameLabel;

    @FXML
    private TextField LogInUsernameText;

    @FXML
    private Label PasswordLabel;

    @FXML
    private TextField LogInPasswordText;

    @FXML
    private Button LogInButton;

    @FXML
    private Label PleaseLogInLabel;

    @FXML
    void OnActionLogIn(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
        if (validCredentials()) {
            connectToDatabase();
            recordLogin();
            goToMainScreen(event);
        } else {
            createErrorMessage();
        }
    }

    private final String USERNAME = "test";
    private final String PASSWORD = "test";

    private boolean validCredentials() {
        return LogInUsernameText.getText().equals(USERNAME)
                && LogInPasswordText.getText().equals(PASSWORD);
    }

    private void connectToDatabase() throws ClassNotFoundException, SQLException {
        Database.makeConnection();
    }

    private void recordLogin() throws FileNotFoundException, UnsupportedEncodingException, IOException {
        Charset utf8 = StandardCharsets.UTF_8;
        String user = "test";
        String fileName = "userActivity.txt";

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd yyyy HH:mm");
        String time = dateTime.format(formatter);

        String logText = "User: " + user + " logged in at " + time + ".\n";
        List<String> lines = Arrays.asList(logText);

        Files.write(Paths.get(fileName), lines, utf8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        System.out.println("Wrote " + logText);
    }

    private void goToMainScreen(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MainScreen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    ResourceBundle loginResourceBundle;

    private void createErrorMessage() {
        String alertTitle = loginResourceBundle.getString("alertTitle");
        String alertContent = loginResourceBundle.getString("alertContent");

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(alertTitle);
        alert.setContentText(alertContent);
        alert.showAndWait();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Uncomment below to set default locale and timezone to Japanese
            //Locale.setDefault(new Locale("jp", "JP"));
            //TimeZone.setDefault(TimeZone.getTimeZone("Japan"));

        localizeText();

    }

    private void localizeText() {
        Locale locale = Locale.getDefault();
        loginResourceBundle = ResourceBundle.getBundle("Resources.LoginScreen", locale);

        UsernameLabel.setText(loginResourceBundle.getString("UsernameLabel"));
        PasswordLabel.setText(loginResourceBundle.getString("PasswordLabel"));
        LogInButton.setText(loginResourceBundle.getString("LogInButton"));
        PleaseLogInLabel.setText(loginResourceBundle.getString("PleaseLogInLabel"));

    }

}
