/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author conno
 */
public class LogInScreenController implements Initializable {

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
    void OnActionLogIn(ActionEvent event) {

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
