package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet5Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

public class EditMaintenanceTicketController {
    @FXML
    private TableView<TOMaintenanceTicket> editTicketsTableView;
    @FXML
    private TableColumn<TOMaintenanceTicket, Integer> numberColumn;
    @FXML
    private TableColumn<TOMaintenanceTicket, String> issuerColumn;
    @FXML
    private TableColumn<TOMaintenanceTicket, String> statusColumn;
    @FXML
    private TableColumn<TOMaintenanceTicket, String> dateRaisedColumn;
    @FXML
    private Button cancelEditButton;
    @FXML
    private Button doneEditButton;
    @FXML
    private TextField ticketId;
    @FXML
    private TextField description;
    @FXML
    private TextField email;
    @FXML
    private TextField assetNumber;
    @FXML
    private DatePicker datePicker;
    private TOMaintenanceTicket selectedTicket;

    @FXML
    public void initialize() {
        ViewUtils.loadTicketsIntoTableView(editTicketsTableView, numberColumn, issuerColumn, statusColumn, dateRaisedColumn);
        // TODO: auto select the ticket that was already selected
    }

    public void setSelectedTicket(TOMaintenanceTicket ticket) {
        this.selectedTicket = ticket;
        ObservableList<TOMaintenanceTicket> tickets = editTicketsTableView.getItems();

        // Find the index of the selected ticket
        int selectedIndex = tickets.indexOf(selectedTicket);

        // Set the selected ticket in the second TableView
        if (selectedIndex >= 0) {
            editTicketsTableView.getSelectionModel().select(selectedIndex);
        }
    }

    @FXML

    public void doneEditClicked(ActionEvent actionEvent) {
        if (selectedTicket == null) {
            System.out.println("No ticket selected.");
            return;
        }

        int id = Integer.parseInt(ticketId.getText());
        String descriptionText = description.getText();
        String userEmail = email.getText();
        String assetNum = assetNumber.getText();
        LocalDate localDate = datePicker.getValue();

        // Create a new TOMaintenanceTicket object using the read values
        String result = AssetPlusFeatureSet4Controller.updateMaintenanceTicket(
                id,
                Date.valueOf(localDate),
                descriptionText,
                userEmail,
                Integer.parseInt(assetNum)
        );
        if (ViewUtils.successful(result)) {
            ViewUtils.returnToTicketStatusPage(getClass(), (Stage) doneEditButton.getScene().getWindow());
        }
        else {
            ViewUtils.showError(result);
        }
    }

    public void cancelEditClicked(ActionEvent event) {
        ViewUtils.returnToTicketStatusPage(getClass(), (Stage) cancelEditButton.getScene().getWindow());
    }
}