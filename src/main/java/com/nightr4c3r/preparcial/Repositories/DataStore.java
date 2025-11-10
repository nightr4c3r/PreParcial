package com.nightr4c3r.preparcial.Repositories;

import com.nightr4c3r.preparcial.Models.Client;
import com.nightr4c3r.preparcial.Models.Product;
import com.nightr4c3r.preparcial.Models.Sale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public final class DataStore {
    private static final DataStore INSTANCE = new DataStore();

    private final ObservableList<Client> clients = FXCollections.observableArrayList();
    private final ObservableList<Product> products = FXCollections.observableArrayList();
    private final ObservableList<Sale> sales = FXCollections.observableArrayList();

    private DataStore() {}

    public static DataStore getInstance() {
        return INSTANCE;
    }

    public ObservableList<Client> getClients() {
        return clients;
    }

    public ObservableList<Product> getProducts() {
        return products;
    }

    public ObservableList<Sale> getSales() {
        return sales;
    }
}
