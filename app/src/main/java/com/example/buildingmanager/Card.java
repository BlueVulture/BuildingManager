package com.example.buildingmanager;

public class Card {
    private String line1;
    private String line2;
    private String status = "";

    public Card(String line1, String line2) {
        this.line1 = line1;
        this.line2 = line2;

    }
    public Card(String line1, String line2, String status) {
        this.line1 = line1;
        this.line2 = line2;
        this.status = status;
    }

    public Card(String line1) {
        this.line1 = line1;
    }

    public String getLine1() {
        return line1;
    }

    public String getLine2() {
        return line2;
    }

    public String getStatus() {
        return status;
    }

}
