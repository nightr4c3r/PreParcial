package com.nightr4c3r.preparcial.Controllers;

import com.nightr4c3r.preparcial.Models.Client;
import com.nightr4c3r.preparcial.Repositories.DataStore;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;

public class ClientsController {


    @FXML private TextField txtClientDocument;
    @FXML private TextField txtClientName;
    @FXML private TextField txtClientTelephone;
    @FXML private TextField txtClientEmail;


    @FXML private TableView<Client> tblClients;
    @FXML private TableColumn<Client, String> colClientDocument;
    @FXML private TableColumn<Client, String> colClientName;
    @FXML private TableColumn<Client, String> colClientTelephone;
    @FXML private TableColumn<Client, String> colClientEmail;

    private ObservableList<Client> clients;

    @FXML
    private void initialize() {
        clients = DataStore.getInstance().getClients();

        colClientDocument.setCellValueFactory(new PropertyValueFactory<>("document"));
        colClientName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colClientTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        colClientEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tblClients.setItems(clients);

        tblClients.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, sel) -> {
            if (sel != null) fillClientForm(sel);
        });
    }

    @FXML
    private void onAddClient() {
        if (!validateClientForm()) return;
        Client c = new Client(
                txtClientDocument.getText().trim(),
                txtClientName.getText().trim(),
                txtClientTelephone.getText().trim(),
                txtClientEmail.getText().trim()
        );
        clients.add(c);
        clearClientForm();
    }

    @FXML
    private void onUpdateClient() {
        Client selected = tblClients.getSelectionModel().getSelectedItem();
        if (selected == null) { warn("Select a client to update."); return; }
        if (!validateClientForm()) return;
        selected.setDocument(txtClientDocument.getText().trim());
        selected.setName(txtClientName.getText().trim());
        selected.setTelephone(txtClientTelephone.getText().trim());
        selected.setEmail(txtClientEmail.getText().trim());
        tblClients.refresh();
    }

    @FXML
    private void onDeleteClient() {
        Client selected = tblClients.getSelectionModel().getSelectedItem();
        if (selected == null) { warn("Select a client to delete."); return; }
        clients.remove(selected);
        clearClientForm();
    }

    @FXML
    private void onClearClientForm() { clearClientForm(); }

    private void fillClientForm(Client c) {
        txtClientDocument.setText(c.getDocument());
        txtClientName.setText(c.getName());
        txtClientTelephone.setText(c.getTelephone());
        txtClientEmail.setText(c.getEmail());
    }

    private void clearClientForm() {
        txtClientDocument.clear();
        txtClientName.clear();
        txtClientTelephone.clear();
        txtClientEmail.clear();
        tblClients.getSelectionModel().clearSelection();
    }

    private boolean validateClientForm() {
        if (txtClientDocument.getText().trim().isEmpty() || txtClientName.getText().trim().isEmpty()) {
            warn("Document and Name are required.");
            return false;
        }
        return true;
    }

    private void warn(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
