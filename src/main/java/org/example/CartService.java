package org.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class CartService {
    private final Scanner scanner;
    private final ArrayList<BasketProduct> cart = new ArrayList<>();
    private final ProductService productService;
    private final ConsoleService consoleService;

    public CartService(Scanner scanner, ProductService productService, ConsoleService consoleService) {
        this.scanner = scanner;
        this.productService = productService;
        this.consoleService = consoleService;
    }

    public void addToBasket() {
        while (true) {
            char choice = this.consoleService.getString("Sepete ürün eklemek ister misiniz? (e/h): ", 1).charAt(0);
            if (choice == 'h') {
                int totalProducts = this.cart.stream().mapToInt(basketProduct -> basketProduct.quantity).sum();
                if (totalProducts < 2) { // Sepetteki toplam ürün sayısı kontrolü
                    System.out.println("Sepette en az 2 ürün olmalı. Lütfen ürün eklemeye devam edin.");
                    continue;
                } else {
                    return;
                }
            }

            if (choice == 'e') {
                while (true) { // Ürün adı girişi için iç döngü
                    System.out.print("Ürün adı: ");
                    String productName = this.scanner.nextLine();
                    Product productSearchResult = this.productService.search(productName);
                    if (productSearchResult == null) {
                        System.out.println("Ürün bulunamadı, lütfen tekrar deneyin.");
                    } else {
                        if (productSearchResult.getStockQuantity() == 0) {
                            System.out.println("Stokta ürün kalmadı, daha fazla ekleyemezsiniz.");
                            break;
                        }

                        while (true) {
                            int quantity = this.consoleService.getInt("Eklemek istediğiniz miktar:", 0);
                            if (quantity > productSearchResult.getStockQuantity()) {
                                System.out.println("Stokta yetersiz. Lütfen geçerli bir miktar girin.");
                            } else {
                                BasketProduct basketProduct = this.search(productSearchResult);
                                if (basketProduct != null) {
                                    basketProduct.quantity += quantity;
                                } else {
                                    this.cart.add(new BasketProduct(productSearchResult, quantity));
                                }
                                System.out.println(productSearchResult.getName() + " sepetinize eklendi.");
                                productSearchResult.setStockQuantity(productSearchResult.getStockQuantity() - quantity); // Stok güncellenir
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        }
    }

    private BasketProduct search(Product productSearchResult) {
        Iterator<BasketProduct> var2 = this.cart.iterator();

        BasketProduct basketProduct;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            basketProduct = var2.next();
        } while (!basketProduct.product.equals(productSearchResult));

        return basketProduct;
    }

    public void calculateBasketTotal() {
        if (this.cart.isEmpty()) {
            System.out.println("Sepetiniz boş.");
        } else {
            double total = 0.0;
            Product nextProduct = null;
            int loopStart = this.cart.size() - 1;

            for (int i = 0; i < loopStart + 1; ++i) {
                if (i < loopStart) {
                    nextProduct = this.cart.get(i + 1).product;
                }

                BasketProduct basketProduct = this.cart.get(i);
                double currentProductPrice = basketProduct.product.getPrice();
                double originalPrice = currentProductPrice;

                if (nextProduct != null && currentProductPrice > nextProduct.getPrice()) {
                    currentProductPrice -= nextProduct.getPrice();
                }

                total += currentProductPrice * (double) basketProduct.quantity;

                System.out.print("Ürün adı: " + basketProduct.product.getName());
                System.out.print(", Adet: " + basketProduct.quantity);
                if (currentProductPrice != originalPrice) {
                    System.out.print(", Orijinal Fiyat: " + originalPrice);
                    System.out.print(", İndirimli Fiyat: " + currentProductPrice);
                } else {
                    System.out.print(", Fiyat: " + currentProductPrice);
                }
                System.out.println();
            }

            System.out.println("Sepet toplamı: " + total);
        }
    }
}