package com.example.kent.split2;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

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
        this.itemsBoughtAfterTaxes = items;
        this.picture = picture;
    }



    List<Product> getItems() {
        return items;
    }

    double getCalculatedTotal() {
        double sum =0;
        for (int i =0;i< itemsBoughtAfterTaxes.size();i++) {
            sum += itemsBoughtAfterTaxes.get(i).getPrice();
        }
        return sum;
    }

    void delete(Product product) {
        if (taxes.contains(product)) {
            removeTax(product);
            calculateTaxPercentage();
            adjustPricesForTax();
        }else {
            itemsBoughtAfterTaxes.remove(product);
        }
    }

    double getLargest() {
        double largest=0;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getPrice() > largest) {
                largest = items.get(i).getPrice();
            }
        }
        return largest;
    }

    boolean totalIsCorrect() {
        if (getLargest() - getCalculatedTotal() < getLargest()/20) {
            return true;
        }
        return false;
    }

    void addItem(Product p) {
        items.add(p);
    }

    void addTax(Product tax) {
        taxes.add(tax);
        itemsBoughtAfterTaxes.remove(tax);
        totalWithoutTax -= tax.getPrice();
        calculateTaxPercentage();
        adjustPricesForTax();
    }

    void removeTax(Product tax) {
        itemsBoughtAfterTaxes.add(tax);
        taxes.remove(tax);
        totalWithoutTax += tax.getPrice();
        calculateTaxPercentage();
        adjustPricesForTax();
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
