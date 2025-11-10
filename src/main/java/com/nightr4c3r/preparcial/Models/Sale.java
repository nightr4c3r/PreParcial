package com.nightr4c3r.preparcial.Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Sale {
    private Client client;
    private Product product;
    private int quantity;
    private double unitPrice;
    private double total;
    private LocalDateTime dateTime;

    public Sale() {}

    public Sale(Client client, Product product, int quantity, double unitPrice, LocalDateTime dateTime) {
        this.client = client;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.total = unitPrice * quantity;
        this.dateTime = dateTime;
    }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; this.total = this.unitPrice * quantity; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; this.total = unitPrice * this.quantity; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }


    public String getClientName() { return client != null ? client.getName() : ""; }
    public String getProductName() { return product != null ? product.getName() : ""; }
    public String getDateTimeFormatted() {
        if (dateTime == null) return "";
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
