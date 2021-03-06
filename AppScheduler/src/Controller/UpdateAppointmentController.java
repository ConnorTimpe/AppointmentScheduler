/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Controller.AddAppointmentController.customerList;
import Model.Appointment;
import Model.Customer;
import Model.Database;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Connor Timpe
 */
public class UpdateAppointmentController implements Initializable {

    Stage stage;
    Appointment appointmentToModify;

    @FXML
    private TextField AppTitleText;

    @FXML
    private TextField AppDescriptionText;

    @FXML
    private TextField AppLocationText;

    @FXML
    private TextField AppContactText;

    @FXML
    private ComboBox<String> AppCustomerDropDownMenu1;

    @FXML
    private TextField AppTypeText;

    @FXML
    private TextField AppDateMonthText;

    @FXML
    private TextField AppDateDayText;

    @FXML
    private TextField AppDateYearText;

    @FXML
    private ComboBox<Integer> AppStartTimeHourText;

    @FXML
    private ComboBox<Integer> AppStartTimeMinuteText;

    @FXML
    private ComboBox<Integer> AppEndTimeHourText;

    @FXML
    private ComboBox<Integer> AppEndTimeMinuteText;

    @FXML
    void OnActionReturnToMainMenu(ActionEvent event) throws IOException {
        String alertTitle = "Confirm";
        String alertContent = "Are you sure you'd like to go to the main menu? All changed data will be lost.";

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(alertTitle);
        alert.setContentText(alertContent);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            changeScreens(event, "/View/MainScreen.fxml");
        }
    }

    @FXML
    void onActionSaveModifiedApp(ActionEvent event) throws IOException, SQLException {
        String title = AppTitleText.getText();
        String description = AppDescriptionText.getText();
        String location = AppLocationText.getText();
        String contact = AppContactText.getText();
        String type = AppTypeText.getText();
        int month = Integer.valueOf(AppDateMonthText.getText());
        int day = Integer.valueOf(AppDateDayText.getText());
        int year = Integer.valueOf(AppDateYearText.getText());
        int startTimeHour = AppStartTimeHourText.getValue();
        int startTimeMinute = AppStartTimeMinuteText.getValue();
        int endTimeHour = AppEndTimeHourText.getValue();
        int endTimeMinute = AppEndTimeMinuteText.getValue();
        String chosenCustomerName = AppCustomerDropDownMenu1.getValue();

        //Convert date and times to localDateTime
        LocalTime appStartTime = LocalTime.of(startTimeHour, startTimeMinute);
        LocalTime appEndTime = LocalTime.of(endTimeHour, endTimeMinute);
        LocalDate appDate = LocalDate.of(year, month, day);
        LocalDateTime appStartDateTime = LocalDateTime.of(appDate, appStartTime);
        LocalDateTime appEndDateTime = LocalDateTime.of(appDate, appEndTime);
        ZonedDateTime utcStartDateTime = appStartDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime utcEndDateTime = appEndDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));

        if (isOverlappingOtherAppointment(appStartDateTime, appEndDateTime)) {
            String alertTitle = "Overlapping appointments";
            String alertContent = "This appointment overlaps with another scheduled appointment. Please select a new time.";
            createErrorMessage(alertTitle, alertContent);
        } else {

            //Get customer id
            int customerId = 0;

            for (Customer customer : customerList) {
                if (customer.getName().equals(chosenCustomerName)) {
                    customerId = customer.getId();
                }
            }

            appointmentToModify.setCustomerId(customerId);
            appointmentToModify.setCustomerName(chosenCustomerName);
            appointmentToModify.setTitle(title);
            appointmentToModify.setDescription(description);
            appointmentToModify.setLocation(location);
            appointmentToModify.setContact(contact);
            appointmentToModify.setType(type);

            appointmentToModify.setStartTime(utcStartDateTime);
            appointmentToModify.setEndTime(utcEndDateTime);

            Database.modifyAppointment(appointmentToModify);

            changeScreens(event, "/View/MainScreen.fxml");
        }
    }

    private boolean isOverlappingOtherAppointment(LocalDateTime appStartDateTime, LocalDateTime appEndDateTime) throws SQLException {
        ObservableList<Appointment> appointmentList = Database.buildAppointmentList();

        int newAppYear = appStartDateTime.getYear();
        int newAppMonth = appStartDateTime.getMonthValue();
        int newAppDay = appStartDateTime.getDayOfMonth();

        int newAppStartHour = appStartDateTime.getHour();
        int newAppStartMinutes = appStartDateTime.getMinute();
        LocalTime newAppStartTime = LocalTime.of(newAppStartHour, newAppStartMinutes);

        int newAppEndHour = appEndDateTime.getHour();
        int newAppEndMinutes = appEndDateTime.getMinute();
        LocalTime newAppEndTime = LocalTime.of(newAppEndHour, newAppEndMinutes);

        for (Appointment appointment : appointmentList) {
            int appYear = appointment.getStartTime().getYear();
            int appMonth = appointment.getStartTime().getMonthValue();
            int appDay = appointment.getStartTime().getDayOfMonth();

            int appStartHour = appointment.getStartTime().getHour();
            int appStartMinutes = appointment.getStartTime().getMinute();
            LocalTime appStartTime = LocalTime.of(appStartHour, appStartMinutes);

            int appEndHour = appointment.getEndTime().getHour();
            int appEndMinutes = appointment.getEndTime().getMinute();
            LocalTime appEndTime = LocalTime.of(appEndHour, appEndMinutes);

            if (appYear == newAppYear && appMonth == newAppMonth && appDay == newAppDay) {
                return ((newAppStartTime.isBefore(appEndTime))
                        && (newAppEndTime.isAfter(appStartTime)));
            }
        }
        return false; //Appointments do not overlap
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

    private void initializeFields(Appointment appointmentToModify) {
        AppTitleText.setText(appointmentToModify.getTitle());
        AppDescriptionText.setText(appointmentToModify.getDescription());
        AppLocationText.setText(appointmentToModify.getLocation());
        AppContactText.setText(appointmentToModify.getContact());
        AppCustomerDropDownMenu1.getSelectionModel().select(appointmentToModify.getCustomerName());
        AppTypeText.setText(appointmentToModify.getType());

        AppDateMonthText.setText(Integer.toString(appointmentToModify.getStartTime().getMonthValue()));
        AppDateDayText.setText(Integer.toString(appointmentToModify.getStartTime().getDayOfMonth()));
        AppDateYearText.setText(Integer.toString(appointmentToModify.getStartTime().getYear()));

        AppStartTimeHourText.getSelectionModel().select(appointmentToModify.getStartTime().getHour());
        AppStartTimeMinuteText.getSelectionModel().select(appointmentToModify.getStartTime().getMinute());
        AppEndTimeHourText.getSelectionModel().select(appointmentToModify.getEndTime().getHour());
        AppEndTimeMinuteText.getSelectionModel().select(appointmentToModify.getEndTime().getMinute());
    }

    static ObservableList<Customer> customerList = FXCollections.observableArrayList();
    static ObservableList<String> customerNamesList = FXCollections.observableArrayList();

    private void initializeCustomerLists() throws SQLException {
        customerList.clear();
        customerNamesList.clear();

        customerList = Database.buildCustomerList();

        customerList.forEach((customer) -> {
            customerNamesList.add(customer.getName());
        });

        AppCustomerDropDownMenu1.setItems(customerNamesList);
    }
    static ObservableList<Integer> hoursList = FXCollections.observableArrayList();
    static ObservableList<Integer> minutesList = FXCollections.observableArrayList();

    private void initializeTimeLists() {
        if (hoursList.isEmpty() || minutesList.isEmpty()) {
            hoursList.clear();
            minutesList.clear();

            //Could change hours to only display buisness hours
            final int HOURS_PER_DAY = 24;
            final int MINUTES_PER_HOUR = 60;

            for (int i = 0; i <= HOURS_PER_DAY; i++) {
                hoursList.add(i);
            }

            for (int i = 0; i < MINUTES_PER_HOUR; i++) {
                minutesList.add(i);
            }
        }

        AppStartTimeHourText.setItems(hoursList);
        AppEndTimeHourText.setItems(hoursList);

        AppStartTimeMinuteText.setItems(minutesList);
        AppEndTimeMinuteText.setItems(minutesList);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            appointmentToModify = MainScreenController.appointmentToModify;

            initializeCustomerLists();
            initializeTimeLists();
            initializeFields(appointmentToModify);
        } catch (SQLException ex) {
            Logger.getLogger(UpdateAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
