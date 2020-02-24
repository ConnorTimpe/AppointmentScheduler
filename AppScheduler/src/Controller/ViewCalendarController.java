/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Appointment;
import Model.Database;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZonedDateTime;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Connor Timpe
 */
public class ViewCalendarController implements Initializable {

    @FXML
    private TableView<Appointment> AppointmentTableView;

    @FXML
    private TableColumn<Appointment, String> AppTypeCol;

    @FXML
    private TableColumn<Appointment, String> AppCustomerCol;

    @FXML
    private TableColumn<Appointment, ZonedDateTime> AppDateCol;

    Stage stage;

    @FXML
    void OnActionGoToMainScreen(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MainScreen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void OnActionViewAll(ActionEvent event) throws SQLException {
        initializeTable();
    }

    @FXML
    void OnActionViewMonth(ActionEvent event) throws SQLException {
        appointmentList.clear();
        appointmentList = Database.buildAppointmentList();
        ObservableList<Appointment> monthAppList = FXCollections.observableArrayList();
        monthAppList.clear();

        for (Appointment appointment : appointmentList) {
            int appYear = appointment.getStartTime().getYear();
            int appMonth = appointment.getStartTime().getMonthValue();

            if (appYear == currentYear && appMonth == currentMonth) {
                monthAppList.add(appointment);
            }
        }

        AppointmentTableView.getItems().clear();
        AppointmentTableView.setItems(monthAppList);
    }

    @FXML
    void OnActionViewWeek(ActionEvent event) throws SQLException {
        appointmentList.clear();
        appointmentList = Database.buildAppointmentList();
        ObservableList<Appointment> weekAppList = FXCollections.observableArrayList();
        weekAppList.clear();

        for (Appointment appointment : appointmentList) {
            int appYear = appointment.getStartTime().getYear();
            int appMonth = appointment.getStartTime().getMonthValue();
            int appDay = appointment.getStartTime().getDayOfMonth();
            if (appYear == currentYear && appMonth == currentMonth && appDay >= currentDay && appDay <= endOfWeek) {
                weekAppList.add(appointment);
            }
        }

        AppointmentTableView.getItems().clear();
        AppointmentTableView.setItems(weekAppList);
    }

    ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    private void initializeTable() throws SQLException {
        appointmentList.clear();
        appointmentList = Database.buildAppointmentList();
        AppointmentTableView.getItems().clear();
        AppointmentTableView.setItems(appointmentList);
    }

    ZonedDateTime now;
    int currentYear;
    int currentMonth;
    int currentDay;
    int endOfWeek;

    private void initializeDates() {
        now = ZonedDateTime.now();
        currentYear = now.getYear();
        currentMonth = now.getMonthValue();
        currentDay = now.getDayOfMonth();
        endOfWeek = currentDay + 7; //This causes the table to display one full week, not one business week.   
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            initializeTable();
            initializeDates();
        } catch (SQLException ex) {
            Logger.getLogger(ViewCalendarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
