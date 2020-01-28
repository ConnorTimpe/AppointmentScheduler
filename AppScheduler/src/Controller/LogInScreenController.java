/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
 * @author conno
 */
public class LogInScreenController implements Initializable {

    Stage stage;
    Parent scene;
    private final String USERNAME = "test";
    private final String PASSWORD = "test";

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
    void OnActionLogIn(ActionEvent event) throws IOException {
        if (validCredentials()) {
            connectToDatabase();
            goToMainScreen(event);
        } else {
            createErrorMessage();
        }
    }

    private boolean validCredentials() {
        return LogInUsernameText.getText().equals(USERNAME)
                && LogInPasswordText.getText().equals(PASSWORD);
    }

    private void connectToDatabase() {
        System.out.println("Connected");
    }

    private void goToMainScreen(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MainScreen.fxml"));
        Controller.MainScreenController controller = new Controller.MainScreenController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void createErrorMessage() {
        String alertTitle = "Error"; //loginScreenRB.getString("alertTitle")
        String alertContent = "Incorrect login info"; //loginScreenRB.getString("alertContent")

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
        //Initialize resource bundle
        //
    }

}