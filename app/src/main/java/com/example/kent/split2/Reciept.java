package com.example.kent.split2;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Reciept {

    private List<Product> items;
    private List<Product> itemsBoughtAfterTaxes = new ArrayList<>();
    private List<Product> taxes = new ArrayList<>();
    private double totalWithoutTax;
    private double taxPercentage;
    private Bitmap picture;

    Reciept(List<Product> items, Bitmap picture) {
        this.items = items;
        this.picture = picture;
    }

    double getTotal() {
        double sum =0;
        for (int i =0;i< itemsBoughtAfterTaxes.size();i++) {
            sum += itemsBoughtAfterTaxes.get(i).getPrice();
        }
        return sum;
    }

    void addTax(Product tax) {
        taxes.add(tax);
        totalWithoutTax -= tax.getPrice();
        calculateTaxPercentage();
    }

    void removeTax(Product tax) {
        taxes.remove(tax);
        totalWithoutTax += tax.getPrice();
        calculateTaxPercentage();
    }

    void calculateTaxPercentage() {
        double totalTax =0;
        for (int i = 0; i < taxes.size();i++) {
            taxPercentage += taxes.get(i).getPrice();
        }
        taxPercentage = taxPercentage/totalWithoutTax;
    }

    void adjustPricesForTax() {
        itemsBoughtAfterTaxes.clear();
        for (int i =0; i<items.size();i++) {
            Product p = items.get(i);
            if (!taxes.contains(p)) {
                p.setPrice(p.getPrice()*(1+taxPercentage));
                itemsBoughtAfterTaxes.add(items.get(i));
            }
        }
    }

    Bitmap getPicture() {
        return picture;
    }

}
