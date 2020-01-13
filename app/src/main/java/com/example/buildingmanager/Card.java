package com.example.buildingmanager;

public class Card {
    private String line1;
    private String line2;
    private String status = "";
    private String id;

    public Card(String line1, String line2, String id) {
        this.line1 = line1;
        this.line2 = line2;
        this.id = id;

    }
    public Card(String line1, String line2, String status, String id) {
        this.line1 = line1;
        this.line2 = line2;
        this.status = status;
        this.id = id;
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

    public String getId() { return id; }
}
