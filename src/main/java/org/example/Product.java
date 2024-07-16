package org.example;

public class Product {
    private final String name;
    private final double price;
    private int stockQuantity;
    private final double productScore;

    public Product(String name, double price, int quantity, double productScore) {
        this.name = name;
        this.price = price;
        this.stockQuantity = quantity;
        this.productScore = productScore;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public int getStockQuantity() {
        return this.stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public double getProductScore() {
        return this.productScore;
    }
}
