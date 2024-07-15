package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Functions {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<Product> products = new ArrayList<>();
    private static final ArrayList<Product> cart = new ArrayList<>();

    static void enterProductFeature() {
        System.out.print("Kaç farklı ürün gireceksiniz? ");
        int productCount = scanner.nextInt();
        scanner.nextLine(); // Clear the newline

        while (productCount <= 1) {
            System.out.print("Lütfen en az 2 ürün giriniz: ");
            productCount = scanner.nextInt();
            scanner.nextLine(); // Clear the newline
        }

        for (int i = 0; i < productCount; i++) {
            String name;
            while (true) {
                System.out.print("Ürün adı: ");
                name = scanner.nextLine();
                if (name.length() > 20) {
                    System.out.print("Ürün adı en fazla 20 karakter olmalıdır, lütfen tekrar giriniz: ");
                    continue;
                }

                boolean nameExists = false;
                for (Product product : products) {
                    if (product.name.equals(name)) {
                        nameExists = true;
                        break;
                    }
                }

                if (nameExists) {
                    System.out.println("Bu isimde bir ürün bulunmaktadır, lütfen farklı bir isim girin.");
                } else {
                    break;
                }
            }

            System.out.print("ürün fiyatı: ");
            double price = scanner.nextDouble();
            while (price < 1 || price > 100) {
                System.out.print("Fiyat 1 ile 100 arasında olmalıdır, lütfen tekrar giriniz: ");
                price = scanner.nextDouble();
            }

            System.out.print("Ürün miktarı: ");
            int quantity = scanner.nextInt();
            while (quantity < 1) {
                System.out.print("Miktar en az 1 olmalıdır, lütfen tekrar giriniz: ");
                quantity = scanner.nextInt();
            }

            System.out.print("Değerlendirme puanı (1-5): ");
            double score = scanner.nextDouble();
            while (score < 1 || score > 5) {
                System.out.print("Puanınız 1 ile 5 arasında olmalıdır, lütfen tekrar giriniz: ");
                score = scanner.nextDouble();
            }
            scanner.nextLine(); // Clear the newline

            products.add(new Product(name, price, quantity, score));
        }
    }

    static void sortProducts() {
        System.out.print("Which criterion would you like to sort by? (name, price, quantity, score): ");
        String criterion = scanner.nextLine();
        System.out.print("Ascending (a) or descending (d) order?: ");
        char sortOrder = scanner.nextLine().charAt(0);

        Comparator<Product> comparator;
        switch (criterion) {
            case "name":
                comparator = Comparator.comparing(product -> product.name);
                break;
            case "price":
                comparator = Comparator.comparingDouble(product -> product.price);
                break;
            case "quantity":
                comparator = Comparator.comparingInt(product -> product.quantity);
                break;
            case "score":
                comparator = Comparator.comparingDouble(product -> product.productScore);
                break;
            default:
                System.out.println("Invalid criterion!");
                return;
        }

        if (sortOrder == 'd') {
            comparator = comparator.reversed();
        }

        products.sort(comparator);
        printProducts();
    }

    static void printProducts() {
        System.out.println("Products:");
        for (Product product : products) {
            System.out.println(product);
        }
    }

    static void addToCart() {
        while (true) {
            System.out.print("Would you like to add a product to the cart? (y/n): ");
            char choice = scanner.nextLine().charAt(0);

            if (choice == 'n') {
                break;
            } else if (choice == 'y') {
                System.out.print("Product name: ");
                String productName = scanner.nextLine();
                Product selectedProduct = null;

                for (Product product : products) {
                    if (product.name.equals(productName)) {
                        selectedProduct = product;
                        break;
                    }
                }

                if (selectedProduct == null) {
                    System.out.println("Product not found.");
                    continue;
                }

                System.out.print("Quantity: ");
                int quantity = scanner.nextInt();
                scanner.nextLine(); // Clear the newline

                while (quantity > selectedProduct.quantity) {
                    System.out.print("Not enough stock. Please enter a different quantity: ");
                    quantity = scanner.nextInt();
                    scanner.nextLine(); // Clear the newline
                }

                for (int i = 0; i < quantity; i++) {
                    cart.add(selectedProduct);
                }
                selectedProduct.quantity -= quantity;
            }
        }
    }

    static void calculateCartTotal() {
        double total = 0;
        double previousPrice = 0;

        for (Product product : cart) {
            double price = product.price;
            if (previousPrice > price) {
                price -= previousPrice - price;
            }
            total += price;
            previousPrice = product.price;
        }

        System.out.println("Cart total: " + total);
    }
}
