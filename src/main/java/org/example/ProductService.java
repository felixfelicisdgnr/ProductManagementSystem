package org.example;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class ProductService {
    private final ConsoleService consoleService;
    private final ArrayList<Product> products = new ArrayList<>();

    public ProductService(Scanner scanner, ConsoleService consoleService) {
        this.consoleService = consoleService;
    }

    void addNewProducts() {

        double productCount = (double) this.consoleService.getInt("Kaç farklı ürün gireceksiniz?,Lütfen en az 2 ürün giriniz: ", 2);

        for (int i = 0; (double) i < productCount; ++i) {
            while (true) {

                String name = this.consoleService.getString("Ürün Adı, (Ürün adı en fazla 20 karakter olmalıdır): ", 20);

                boolean nameExists = this.productExists(name);
                if (!nameExists) {

                    double price = this.consoleService.getInt("Ürün Fiyatı, (Fiyat 1 ile 100 arasında olmalıdır): ", 1, 100);
                    double quantity = (double) this.consoleService.getInt("Ürün Miktarı, (Miktar en az 1 olmalıdır): ", 1);
                    double score = this.consoleService.getInt("Değerlendirme Puanı, (Puanınız 1 ile 5 arasında olmalıdır): ", 1, 5);
                    this.products.add(new Product(name, price, (int) quantity, score));
                    break;
                }
                System.out.println("Bu isimde bir ürün bulunmaktadır, lütfen farklı bir isim girin.");
            }
        }

    }

    public void printProducts() {
        System.out.println("Sıralanmış Ürünler:");

        for (Product product : this.products) {
            PrintStream allProducts = System.out;
            String productName = product.getName();
            allProducts.println(productName + ", Fiyat: " + product.getPrice() + ", Stok: " + product.getStockQuantity() + ", Değerlendirme: " + product.getProductScore());
        }
    }

    public Product search(String name) {
        Product selectedProduct = null;

        for (Product product : this.products) {
            if (product.getName().equals(name)) {
                selectedProduct = product;
                break;
            }
        }

        return selectedProduct;
    }

    public SortParameter getSortParameter() {
        System.out.println("Hangi kritere göre sıralama yapmak istersiniz?");
        System.out.println("[1] isim");
        System.out.println("[2] fiyat");
        System.out.println("[3] miktar");
        System.out.println("[4] puan");

        int sortFuture;
        while (true) {
            sortFuture = this.consoleService.getInt("Yukarıdaki numaralarindan biri seçiniz:", 1, 4);
            if (sortFuture >= 1 && sortFuture <= 4) {
                break;
            } else {
                System.out.println("Geçersiz seçim. Lütfen 1, 2, 3 veya 4 numaralarından birini seçiniz.");
            }
        }

        SortParameter[] values = SortParameter.values();
        return values[sortFuture - 1];
    }

    public Character checkShortedType() {
        while (true) {
            String str = this.consoleService.getString("Artan (a) veya azalan (d) sıra?: ", 1);
            char sortOrder = str.charAt(0);
            if (sortOrder == 'a' || sortOrder == 'd') {
                return sortOrder;
            }

            System.out.println("Geçersiz sıra türü! Lütfen 'a' veya 'd' girin.");
        }
    }

    void sortProducts() {
        SortParameter sortParameter = this.getSortParameter();
        char sortOrderType = this.checkShortedType();
        Comparator comparator;
        switch (sortParameter) {
            case Isim:
                comparator = Comparator.comparing(Product::getName);
                break;
            case Fiyat:
                comparator = Comparator.comparingDouble(Product::getPrice);
                break;
            case Miktar:
                comparator = Comparator.comparingInt(Product::getStockQuantity);
                break;
            case Puan:
                comparator = Comparator.comparingDouble(Product::getProductScore);
                break;
            default:
                return;
        }

        if (sortOrderType == 'd') {
            comparator = comparator.reversed();
        }

        this.products.sort(comparator);
    }

    private boolean productExists(String name) {
        boolean nameExists = false;

        for (Product product : this.products) {
            if (product.getName().equals(name)) {
                nameExists = true;
                break;
            }
        }

        return nameExists;
    }
}