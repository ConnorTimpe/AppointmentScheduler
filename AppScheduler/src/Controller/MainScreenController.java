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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author conno
 */
public class MainScreenController implements Initializable {

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

    }

    @FXML
    void OnActionGoToAddApp(ActionEvent event) {

    }

    @FXML
    void OnActionGoToAddCustomer(ActionEvent event) {

    }

    @FXML
    void OnActionGoToModifyApp(ActionEvent event) {

    }

    @FXML
    void OnActionGoToModifyCustomer(ActionEvent event) {

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
