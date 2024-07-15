package org.example;

public class BasketProduct {
    Product product;
    int quantity;

    public BasketProduct(Product productSearchResult, int quantity) {
        this.product = productSearchResult;
        this.quantity = quantity;
    }
}
