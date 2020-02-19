/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Appointment;
import Model.Customer;
import Model.Database;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    private TableView<Customer> CustomerTableView;

    @FXML
    private TableColumn<Customer, String> CustomerNameCol;

    @FXML
    private TableColumn<Customer, String> CustomerAddressCol;

    @FXML
    private TableColumn<Customer, String> CustomerPhonenumberCol;

    @FXML
    private TableView<Appointment> AppointmentTableView;

    @FXML
    private TableColumn<Appointment, String> AppTypeCol;

    @FXML
    private TableColumn<Appointment, String> AppCustomerCol;

    @FXML
    private TableColumn<Appointment, Instant> AppDateCol;
    

    @FXML
    void OnActionGoToAddCustomer(ActionEvent event) throws IOException {
        changeScreens(event, "/View/AddCustomer.fxml");
    }

    @FXML
    void OnActionGoToModifyCustomer(ActionEvent event) throws IOException {
        changeScreens(event, "/View/UpdateCustomer.fxml");
    }

    @FXML
    void OnActionGoToAddApp(ActionEvent event) throws IOException {
        changeScreens(event, "/View/AddAppointment.fxml");
    }

    @FXML
    void OnActionGoToModifyApp(ActionEvent event) throws IOException {
        changeScreens(event, "/View/UpdateAppointment.fxml");
    }

    @FXML
    void OnActionGoToReports(ActionEvent event) {

    }

    @FXML
    void OnActionViewCalendar(ActionEvent event) {

    }

    @FXML
    void OnActionViewCustomer(ActionEvent event) {
        //Display modify customer screen but with editing disabled

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

    @FXML
    void OnActionDeleteCustomer(ActionEvent event) throws SQLException {
        Customer selectedCustomer = CustomerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            String alertTitle = "Error";
            String alertContent = "Please select a customer to delete.";
            createErrorMessage(alertTitle, alertContent);
        } //else if selected customer has an appointment -- make error message
        else {
            Database.deleteCustomer(selectedCustomer);
            customerList.remove(selectedCustomer);
        }
    }

    @FXML
    void OnActionDeleteApp(ActionEvent event) throws SQLException {
        Appointment selectedAppointment = AppointmentTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            String alertTitle = "Error";
            String alertContent = "Please select an appointment to delete.";
            createErrorMessage(alertTitle, alertContent);
        }//else if selected appointment has an assigned customer -- make error message
        else {
            Database.deleteAppointment(selectedAppointment);
            appointmentList.remove(selectedAppointment);
        }
    }

    private void createErrorMessage(String alertTitle, String alertContent) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(alertTitle);
        alert.setContentText(alertContent);
        alert.showAndWait();
    }

    @FXML
    void OnActionExit(ActionEvent event) throws SQLException {
        if (Database.connection.isValid(0)) {
            Database.closeConnection();
        }
        System.exit(0);
    }

    static ObservableList<Customer> customerList = FXCollections.observableArrayList();

    private void createCustomerTable() throws SQLException {
        customerList.clear();
        CustomerTableView.getItems().clear();
        
        customerList = Database.buildCustomerList();
        CustomerTableView.setItems(customerList);
    }

    static ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    private void createAppointmentTable() throws SQLException {
        appointmentList.clear();
        AppointmentTableView.getItems().clear();

        appointmentList = Database.buildAppointmentList();
        AppointmentTableView.setItems(appointmentList);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            createCustomerTable();
            createAppointmentTable();
        } catch (SQLException ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
