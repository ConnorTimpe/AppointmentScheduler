/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Customer;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Connor Timpe
 */
public class ViewCustomerController implements Initializable {

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
    private RadioButton CustomerActiveRBYes;

    @FXML
    private ToggleGroup activeRbToggleGroup;

    @FXML
    private RadioButton CustomerActiveRBNo;

    @FXML
    void OnActionReturnToMainMenu(ActionEvent event) throws IOException {
        changeScreens(event, "/View/MainScreen.fxml");
    }

    private void changeScreens(ActionEvent event, String destination) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void setAllFieldsUneditable() {
        CustomerNameText.setEditable(false);
        CustomerPhoneNumberText.setEditable(false);
        CustomerAddress1Text.setEditable(false);
        CustomerAddress2Text.setEditable(false);
        CustomerPostalCodeText.setEditable(false);
        CustomerCityText.setEditable(false);
        CustomerCountryText.setEditable(false);
        CustomerActiveRBYes.setDisable(true);
        CustomerActiveRBNo.setDisable(true);
    }

    private void initializeFields(Customer viewedCustomer) {
        CustomerNameText.setText(viewedCustomer.getName());
        CustomerPhoneNumberText.setText(viewedCustomer.getPhoneNumber());
        CustomerAddress1Text.setText(viewedCustomer.getAddress());
        CustomerAddress2Text.setText(viewedCustomer.getAddress2());
        CustomerPostalCodeText.setText(viewedCustomer.getPostalCode());
        CustomerCityText.setText(viewedCustomer.getCity());
        CustomerCountryText.setText(viewedCustomer.getCountry());
        if (viewedCustomer.getActive() == 1) {
            CustomerActiveRBYes.setSelected(true);
        } else {
            CustomerActiveRBNo.setSelected(true);
        }
    }

    Customer viewedCustomer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setAllFieldsUneditable();
        viewedCustomer = MainScreenController.viewedCustomer;
        initializeFields(viewedCustomer);
    }

}
