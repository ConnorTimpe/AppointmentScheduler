/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Customer;
import Model.Database;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Connor Timpe
 */
public class AddCustomerController implements Initializable {

    Stage stage;

    @FXML
    private TextField CustomerNameText;

    @FXML
    private TextField CustomerPhoneNumberText;

    @FXML
    private TextField CustomerAddress1Text;

    @FXML
    private TextField CustomerAddress2Text;

    @FXML
    private TextField CustomerPostalCodeText;

    @FXML
    private TextField CustomerCityText;

    @FXML
    private TextField CustomerCountryText;

    @FXML
    void OnActionReturnToMainMenu(ActionEvent event) throws IOException {
        String alertTitle = "Confirm";
        String alertContent = "Are you sure you'd like to go to the main menu? All entered data will be lost.";

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(alertTitle);
        alert.setContentText(alertContent);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            changeScreens(event, "/View/MainScreen.fxml");
        }
    }

    @FXML
    void onActionSaveNewCustomer(ActionEvent event) throws SQLException, IOException {
        String name = CustomerNameText.getText();
        String phoneNumber = CustomerPhoneNumberText.getText();
        String address = CustomerAddress1Text.getText();
        String address2 = CustomerAddress2Text.getText();
        String postalCode = CustomerPostalCodeText.getText();
        String city = CustomerCityText.getText();
        String country = CustomerCountryText.getText();

        if (validData(name, phoneNumber, address, postalCode, city, country)) {

            Customer newCustomer = new Customer();

            newCustomer.setName(name);
            newCustomer.setPhoneNumber(phoneNumber);
            newCustomer.setAddress(address);
            newCustomer.setAddress2(address2);
            newCustomer.setPostalCode(postalCode);
            newCustomer.setCity(city);
            newCustomer.setCountry(country);
            newCustomer.setActive(1);

            Database.addCustomer(newCustomer);

            changeScreens(event, "/View/MainScreen.fxml");
        } 
    }

    private boolean validData(String name, String phoneNumber, String address, String postalCode, String city, String country) {
        String alertTitle = "Error. Invalid entry.";
        String checkForDigit = ".*\\d.*";

        if (name.matches(checkForDigit) || name.length() == 0) {
            String alertContent = "Error. Please enter a valid name.";
            createErrorMessage(alertTitle, alertContent);
            return false;
        }
        if (!phoneNumber.matches("[0-9]+") || phoneNumber.length() != 10) {
            String alertContent = "Error. Please enter a valid phone number.";
            createErrorMessage(alertTitle, alertContent);
            return false;
        }
        if (address.length() == 0) {
            String alertContent = "Error. Please enter a valid address.";
            createErrorMessage(alertTitle, alertContent);
            return false;
        }
        if (!postalCode.matches("[0-9]+") || postalCode.length() != 5) {
            String alertContent = "Error. Please enter a valid postal code.";
            createErrorMessage(alertTitle, alertContent);
            return false;
        }
        if (city.matches(checkForDigit) || city.length() == 0) {
            String alertContent = "Error. Please enter a valid city.";
            createErrorMessage(alertTitle, alertContent);
            return false;
        }
        if (country.matches(checkForDigit)  || country.length() == 0) {
            String alertContent = "Error. Please enter a valid country.";
            createErrorMessage(alertTitle, alertContent);
            return false;
        }

        return true;
    }

    private void createErrorMessage(String alertTitle, String alertContent) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(alertTitle);
        alert.setContentText(alertContent);
        alert.showAndWait();
    }

    public void changeScreens(ActionEvent event, String destination) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
