package org.example;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class ProductService {
    private final Scanner scanner;
    private final ConsoleService consoleService;
    private final ArrayList<Product> products = new ArrayList<>();

    public ProductService(Scanner scanner, ConsoleService consoleService) {
        this.scanner = scanner;
        this.consoleService = consoleService;
    }

    void addNewProducts() {
        String s = "Kaç farklı ürün gireceksiniz? ";
        System.out.println(s);
        double productCount = (double) this.consoleService.getInt("Lütfen en az 2 ürün giriniz: ", 2);

        for (int i = 0; (double) i < productCount; ++i) {
            while (true) {
                //System.out.println("Ürün adı: ");
                String name = this.consoleService.getString("Ürün adı en fazla %d karakter olmalıdır: ", 20);
                boolean nameExists = this.productExists(name);
                if (!nameExists) {
                    System.out.print("ürün fiyatı: ");
                    double price = this.consoleService.getInt("Fiyat 1 ile 100 arasında olmalıdır: ", 1, 100);
                    System.out.print("Ürün miktarı: ");
                    double quantity = (double) this.consoleService.getInt("Miktar en az 1 olmalıdır: ", 1);
                    System.out.print("Değerlendirme puanı (1-5): ");
                    double score = this.consoleService.getInt("Puanınız 1 ile 5 arasında olmalıdır: ", 1, 5);
                    this.scanner.nextLine();
                    this.products.add(new Product(name, price, (int) quantity, score));
                    break;
                }
                System.out.println("Bu isimde bir ürün bulunmaktadır, lütfen farklı bir isim girin.");
            }
        }

    }

    public void printProducts() {
        System.out.println("Ürünler:");

        for (Product product : this.products) {
            PrintStream var10000 = System.out;
            String var10001 = product.getName();
            var10000.println(var10001 + ", Fiyat: " + product.getPrice() + ", Stok: " + product.getStockQuantity() + ", Değerlendirme: " + product.getProductScore());
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
        int sortFuture = this.consoleService.getInt("Yukardaki numaralarindan biri seçiniz:", 0, 5);
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