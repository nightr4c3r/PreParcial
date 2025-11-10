package com.nightr4c3r.preparcial.Controllers;

import com.nightr4c3r.preparcial.Repositories.DataStore;
import com.nightr4c3r.preparcial.Models.Product;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProductsController {

    @FXML private TextField txtProductCode;
    @FXML private TextField txtProductName;
    @FXML private TextField txtProductPrice;
    @FXML private TextField txtProductStock;

    @FXML private TableView<Product> tblProducts;
    @FXML private TableColumn<Product, String> colProductCode;
    @FXML private TableColumn<Product, String> colProductName;
    @FXML private TableColumn<Product, Double> colProductPrice;
    @FXML private TableColumn<Product, Integer> colProductStock;

    private ObservableList<Product> products;

    @FXML
    private void initialize() {
        products = DataStore.getInstance().getProducts();

        colProductCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colProductStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        tblProducts.setItems(products);

        tblProducts.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, sel) -> {
            if (sel != null) fillProductForm(sel);
        });
    }

    @FXML
    private void onAddProduct() {
        if (!validateProductForm()) return;
        try {
            double price = Double.parseDouble(txtProductPrice.getText().trim());
            int stock = Integer.parseInt(txtProductStock.getText().trim());
            Product p = new Product(
                    txtProductCode.getText().trim(),
                    txtProductName.getText().trim(),
                    price,
                    stock
            );
            products.add(p);
            clearProductForm();
        } catch (NumberFormatException ex) {
            warn("Price must be a number and Stock must be an integer.");
        }
    }

    @FXML
    private void onUpdateProduct() {
        Product selected = tblProducts.getSelectionModel().getSelectedItem();
        if (selected == null) { warn("Select a product to update."); return; }
        if (!validateProductForm()) return;
        try {
            selected.setCode(txtProductCode.getText().trim());
            selected.setName(txtProductName.getText().trim());
            selected.setPrice(Double.parseDouble(txtProductPrice.getText().trim()));
            selected.setStock(Integer.parseInt(txtProductStock.getText().trim()));
            tblProducts.refresh();
        } catch (NumberFormatException ex) {
            warn("Price must be a number and Stock must be an integer.");
        }
    }

    @FXML
    private void onDeleteProduct() {
        Product selected = tblProducts.getSelectionModel().getSelectedItem();
        if (selected == null) { warn("Select a product to delete."); return; }
        products.remove(selected);
        clearProductForm();
    }

    @FXML
    private void onClearProductForm() { clearProductForm(); }

    private void fillProductForm(Product p) {
        txtProductCode.setText(p.getCode());
        txtProductName.setText(p.getName());
        txtProductPrice.setText(String.valueOf(p.getPrice()));
        txtProductStock.setText(String.valueOf(p.getStock()));
    }

    private void clearProductForm() {
        txtProductCode.clear();
        txtProductName.clear();
        txtProductPrice.clear();
        txtProductStock.clear();
        tblProducts.getSelectionModel().clearSelection();
    }

    private boolean validateProductForm() {
        if (txtProductCode.getText().trim().isEmpty() || txtProductName.getText().trim().isEmpty()) {
            warn("Code and Name are required.");
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
