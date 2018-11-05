package com.example.kent.split2;

import com.google.android.gms.vision.text.Line;

import java.util.List;

public class Product {
    private String name;
    private double price;
    private double positionOfPrice;
    private boolean isTax = false;
    private List<Line> raw;

    Product(String name, double price, List<Line> raw) {
        this.name = name;
        this.price = price;
        this.raw = raw;
    }

    Product(String name, double price) {
        this.name = name;
        this.price = price;
        this.raw = raw;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setAsTax() {
        this.isTax = true;
    }

    public void setPrice(double newPrice) {
        price = newPrice;
    }

    public double getPositionOfPrice() {
        return positionOfPrice;
    }

    public List<Line> getRaw() {
        return raw;
    }

}
