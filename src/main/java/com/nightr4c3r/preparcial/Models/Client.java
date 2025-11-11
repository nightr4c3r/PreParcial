package com.nightr4c3r.preparcial.Models;

public class Client {
    private String Document;
    private String Name;
    private String Telephone;
    private String Email;

    public Client(String document, String name, String telephone, String email) {
        this.Document = document;
        this.Name = name;
        this.Telephone = telephone;
        this.Email = email;
    }

    public Client() {}

    public String getDocument() {
        return Document;
    }

    public void setDocument(String document) {
        Document = document;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @Override
    public String toString() {
        return "Client{" +
                "Document='" + Document + '\'' +
                ", Name='" + Name + '\'' +
                ", Telephone='" + Telephone + '\'' +
                ", Email='" + Email + '\'' +
                '}';
    }
}

