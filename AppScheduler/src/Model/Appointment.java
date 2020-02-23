/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Connor Timpe
 */
public class Appointment {

    int appointmentId; //auto incremented in database
    int customerId;
    String customerName;
    int userId = 1;
    String title;
    String description;
    String location;
    String contact;
    String type;
    String url;
    ZonedDateTime startTime;
    ZonedDateTime endTime;
    LocalDateTime createDate;
    String createdBy;
    LocalDateTime lastUpdate; //auto updated in database
    String lastUpdateBy;

    String formattedStart;
    LocalDate date;

    public Appointment() {
        appointmentId = -1;
        customerId = -1;
        customerName = null;
        userId = 1;
        title = null;
        description = null;
        location = null;
        contact = null;
        type = null;
        url = null;
        startTime = null;
        endTime = null;
        createDate = LocalDateTime.now();
        createdBy = null;
        lastUpdateBy = null;
        formattedStart = null;
        date = null;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public String getFormattedStart() {
        return formattedStart;
    }

    public void setFormattedStart(String formattedStart) {
        this.formattedStart = formattedStart;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    static ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
}
