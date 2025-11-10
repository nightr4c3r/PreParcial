package com.nightr4c3r.preparcial.Controllers;

import com.nightr4c3r.preparcial.Models.Client;
import com.nightr4c3r.preparcial.Repositories.DataStore;
import com.nightr4c3r.preparcial.Models.Product;
import com.nightr4c3r.preparcial.Models.Sale;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;

public class SalesController {

    // Sales form controls
    @FXML private ComboBox<Client> cmbSaleClient;
    @FXML private ComboBox<Product> cmbSaleProduct;
    @FXML private TextField txtSaleQuantity;
    @FXML private Label lblSaleUnitPrice;
    @FXML private Label lblSaleTotal;

    // Sales table controls
    @FXML private TableView<Sale> tblSales;
    @FXML private TableColumn<Sale, String> colSaleClient;
    @FXML private TableColumn<Sale, String> colSaleProduct;
    @FXML private TableColumn<Sale, Integer> colSaleQuantity;
    @FXML private TableColumn<Sale, Double> colSaleUnitPrice;
    @FXML private TableColumn<Sale, Double> colSaleTotal;
    @FXML private TableColumn<Sale, String> colSaleDate;

    private ObservableList<Client> clients;
    private ObservableList<Product> products;
    private ObservableList<Sale> sales;

    @FXML
    private void initialize() {
        clients = DataStore.getInstance().getClients();
        products = DataStore.getInstance().getProducts();
        sales = DataStore.getInstance().getSales();

        cmbSaleClient.setItems(clients);
        cmbSaleProduct.setItems(products);

        cmbSaleClient.setCellFactory(listView -> new ListCell<>() {
            @Override protected void updateItem(Client item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getName() + " (" + item.getDocument() + ")");
            }
        });
        cmbSaleClient.setButtonCell(new ListCell<>() {
            @Override protected void updateItem(Client item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getName());
            }
        });

        cmbSaleProduct.setCellFactory(listView -> new ListCell<>() {
            @Override protected void updateItem(Product item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getName() + " [" + item.getCode() + "]");
            }
        });
        cmbSaleProduct.setButtonCell(new ListCell<>() {
            @Override protected void updateItem(Product item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getName());
            }
        });

        cmbSaleProduct.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> updateSaleComputedFields());
        txtSaleQuantity.textProperty().addListener((obs, o, n) -> updateSaleComputedFields());

        colSaleClient.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        colSaleProduct.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colSaleQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colSaleUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colSaleTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colSaleDate.setCellValueFactory(new PropertyValueFactory<>("dateTimeFormatted"));
        tblSales.setItems(sales);

        updateSaleComputedFields();
    }

    private void updateSaleComputedFields() {
        Product p = cmbSaleProduct.getSelectionModel().getSelectedItem();
        double unit = (p != null) ? p.getPrice() : 0.0;
        lblSaleUnitPrice.setText(unit > 0 ? String.format("%.2f", unit) : "-");
        int qty = 0;
        try { qty = Integer.parseInt(txtSaleQuantity.getText().trim()); } catch (Exception ignored) {}
        double total = unit * Math.max(0, qty);
        lblSaleTotal.setText(total > 0 ? String.format("%.2f", total) : "-");
    }

    @FXML
    private void onAddSale() {
        Client client = cmbSaleClient.getSelectionModel().getSelectedItem();
        Product product = cmbSaleProduct.getSelectionModel().getSelectedItem();
        if (client == null) { warn("Select a client."); return; }
        if (product == null) { warn("Select a product."); return; }
        int qty;
        try {
            qty = Integer.parseInt(txtSaleQuantity.getText().trim());
        } catch (NumberFormatException e) {
            warn("Quantity must be an integer.");
            return;
        }
        if (qty <= 0) { warn("Quantity must be greater than zero."); return; }
        if (qty > product.getStock()) { warn("Not enough stock. Available: " + product.getStock()); return; }

        double unit = product.getPrice();
        Sale sale = new Sale(client, product, qty, unit, LocalDateTime.now());
        sales.add(sale);

        product.setStock(product.getStock() - qty);
        updateSaleComputedFields();
        clearSaleFormFields();
    }

    @FXML
    private void onDeleteSale() {
        Sale selected = tblSales.getSelectionModel().getSelectedItem();
        if (selected == null) { warn("Select a sale to delete."); return; }
        Product p = selected.getProduct();
        if (p != null) {
            p.setStock(p.getStock() + selected.getQuantity());
        }
        sales.remove(selected);
    }

    @FXML
    private void onClearSaleForm() { clearSaleFormFields(); }

    private void clearSaleFormFields() {
        cmbSaleClient.getSelectionModel().clearSelection();
        cmbSaleProduct.getSelectionModel().clearSelection();
        txtSaleQuantity.clear();
        lblSaleUnitPrice.setText("-");
        lblSaleTotal.setText("-");
        tblSales.getSelectionModel().clearSelection();
    }

    private void warn(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
