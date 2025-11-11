package com.nightr4c3r.preparcial.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class DashboardController {

    @FXML
    private StackPane contentArea;

    @FXML
    private void initialize() {
        onShowClients();
    }

    @FXML
    private void onShowHome() {
        contentArea.getChildren().setAll(new StackPane());
    }

    @FXML
    private void onShowClients() {
        loadContent("/com/nightr4c3r/preparcial/clients.fxml");
    }

    @FXML
    private void onShowProducts() {
        loadContent("/com/nightr4c3r/preparcial/products.fxml");
    }

    @FXML
    private void onShowSales() {
        loadContent("/com/nightr4c3r/preparcial/sales.fxml");
    }

    private void loadContent(String resourcePath) {
        try {
            Node root = FXMLLoader.load(getClass().getResource(resourcePath));
            contentArea.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
