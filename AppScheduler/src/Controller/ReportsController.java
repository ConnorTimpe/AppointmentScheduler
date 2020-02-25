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
import java.time.Month;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Connor Timpe
 */
public class ReportsController implements Initializable {

    Stage stage;
    ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    @FXML
    private ComboBox<Integer> appTypesMonthComboBox;

    @FXML
    private ComboBox<String> scheduleUserComboBox;

    @FXML
    private ComboBox<Integer> appLengthMonthComboBox;

    @FXML
    void OnActionCreateAppAverageLengthReport(ActionEvent event) {
        int numberOfAppsThisMonth = 0;
        int totalMinutes = 0;
        int selectedMonth = appLengthMonthComboBox.getSelectionModel().getSelectedItem();

        for (Appointment appointment : appointmentList) {
            int appMonth = appointment.getStartTime().getMonthValue();

            if (appMonth == selectedMonth) {
                int appStartHour = appointment.getStartTime().getHour();
                int appStartMinutes = appointment.getStartTime().getMinute();
                int appStartTotalMinutes = appStartHour * 60 + appStartMinutes;

                int appEndHour = appointment.getEndTime().getHour();
                int appEndMinutes = appointment.getEndTime().getMinute();
                int appEndTotalMinutes = appEndHour * 60 + appEndMinutes;

                int appDuration = appEndTotalMinutes - appStartTotalMinutes;

                totalMinutes += appDuration;
                numberOfAppsThisMonth++;
            }
        }

        int averageHour = totalMinutes / numberOfAppsThisMonth / 60;
        int averageMinutes = totalMinutes / numberOfAppsThisMonth % 60;

        String monthStringLower = Month.of(selectedMonth).toString().toLowerCase();
        String monthString = monthStringLower.substring(0, 1).toUpperCase() + monthStringLower.substring(1);

        String alertTitle = "Average appointment length in " + monthString;

        if (numberOfAppsThisMonth == 0) {
            String alertContent = "There are no appointments scheduled in " + monthString + "\n"
                    + "Please select another month and try again.";
            createAlertMessage(alertTitle, alertContent);
        } else {
            String alertContent = "The average appointment length in " + monthString + " is " + averageHour + " hour and " + averageMinutes + " minutes.";
            createAlertMessage(alertTitle, alertContent);
        }

    }

    @FXML
    void OnActionCreateAppTypesReport(ActionEvent event) {
        int numberOfAppointmentTypes = 0;
        int selectedMonth = appTypesMonthComboBox.getSelectionModel().getSelectedItem();

        ObservableList<String> appTypeList = FXCollections.observableArrayList();

        for (Appointment appointment : appointmentList) {
            int appMonth = appointment.getStartTime().getMonthValue();

            if (appMonth == selectedMonth) {
                String appType = appointment.getType();

                if (!appTypeList.contains(appType)) {
                    appTypeList.add(appType);
                    numberOfAppointmentTypes++;
                }
            }
        }

        String monthStringLower = Month.of(selectedMonth).toString().toLowerCase();
        String monthString = monthStringLower.substring(0, 1).toUpperCase() + monthStringLower.substring(1);

        String alertTitle = "Number of appointment types in " + monthString;

        switch (numberOfAppointmentTypes) {
            case 0: {
                String alertContent = "There are no appointments scheduled in " + monthString + "\n"
                        + "Please select another month and try again.";
                createAlertMessage(alertTitle, alertContent);
                break;
            }
            case 1: {
                String alertContent = "There is " + numberOfAppointmentTypes + " type of appointment scheduled in " + monthString + ".";
                createAlertMessage(alertTitle, alertContent);
                break;
            }
            default: {
                String alertContent = "There are " + numberOfAppointmentTypes + " types of appointments scheduled in " + monthString + ".";
                createAlertMessage(alertTitle, alertContent);
                break;
            }
        }

    }

    @FXML
    void OnActionCreateUserScheduleReport(ActionEvent event) {
        String selectedUser = scheduleUserComboBox.getSelectionModel().getSelectedItem();
        String alertTitle = "Schedule for " + selectedUser;
        String alertContent = "";
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("HH:mm MM/dd/yyyy");

        for (Appointment appointment : appointmentList) {
            if (appointment.getUserName().equals(selectedUser)) {

                String formattedStart = appointment.getStartTime().format(formater);

                alertContent += "Consultant: " + selectedUser + ", has an appointment with " + appointment.getCustomerName() + " at "
                        + formattedStart + ".\n";
            }
        }

        if (alertContent.equals("")) {
            alertContent = "This consultant has no scheduled appointments.";
        }

        createAlertMessage(alertTitle, alertContent);

    }

    private void createAlertMessage(String alertTitle, String alertContent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Report");
        alert.setTitle(alertTitle);
        alert.setContentText(alertContent);
        alert.showAndWait();
    }

    @FXML
    void OnActionGoToMainMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MainScreen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void initializeAppointmentList() throws SQLException {
        appointmentList.clear();
        appointmentList = Database.buildAppointmentList();
    }

    private void initializeMonthComboBoxes() {
        ObservableList<Integer> monthList = FXCollections.observableArrayList();

        final int NUMBER_OF_MONTHS = 12;
        for (int i = 1; i <= NUMBER_OF_MONTHS; i++) {
            monthList.add(i);
        }

        appTypesMonthComboBox.setItems(monthList);
        appLengthMonthComboBox.setItems(monthList);
    }

    private void initializeUserComboBox() {
        ObservableList<String> userList = FXCollections.observableArrayList();
        userList.add("Test");
        userList.add("Connor");
        scheduleUserComboBox.setItems(userList);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            initializeAppointmentList();
            initializeMonthComboBoxes();
            initializeUserComboBox();
        } catch (SQLException ex) {
            Logger.getLogger(ReportsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
