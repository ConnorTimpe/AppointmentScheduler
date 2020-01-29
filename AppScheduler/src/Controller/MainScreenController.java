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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Connor Timpe
 */
public class MainScreenController implements Initializable {

    Stage stage;

    @FXML
    private TableView<?> CustomerTableView;

    @FXML
    private TableColumn<?, ?> CustomerNameCol;

    @FXML
    private TableColumn<?, ?> CustomerAddressCol;

    @FXML
    private TableColumn<?, ?> CustomerPhonenumberCol;

    @FXML
    private TableView<?> AppointmentTableView;

    @FXML
    private TableColumn<?, ?> AppTypeCol;

    @FXML
    private TableColumn<?, ?> AppCustomerCol;

    @FXML
    private TableColumn<?, ?> AppDateCol;

    @FXML
    void OnActionDeleteApp(ActionEvent event) {

    }

    @FXML
    void OnActionDeleteCustomer(ActionEvent event) {

    }

    @FXML
    void OnActionExit(ActionEvent event) {
        //Disconnect from database
        System.exit(0);
    }

    @FXML
    void OnActionGoToAddApp(ActionEvent event) throws IOException {
        changeScreens(event, "/View/AddAppointment.fxml");
    }

    @FXML
    void OnActionGoToAddCustomer(ActionEvent event) throws IOException {
        changeScreens(event, "/View/AddCustomer.fxml");
    }

    @FXML
    void OnActionGoToModifyApp(ActionEvent event) throws IOException {
        changeScreens(event, "/View/UpdateAppointment.fxml");
    }

    @FXML
    void OnActionGoToModifyCustomer(ActionEvent event) throws IOException {
        changeScreens(event, "/View/UpdateCustomer.fxml");
    }

    @FXML
    void OnActionGoToReports(ActionEvent event) {

    }

    @FXML
    void OnActionViewCalendar(ActionEvent event) {

    }

    @FXML
    void OnActionViewCustomer(ActionEvent event) {

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

    private void createCustomerTable() {

    }

    private void createAppointmentTable() {

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
