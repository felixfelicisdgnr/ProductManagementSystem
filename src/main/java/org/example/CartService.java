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
        while(true) {
            char choice = this.consoleService.getString("Sepete ürün eklemek ister misiniz? (e/h): ", 1).charAt(0);
            if (choice == 'h') {
                return;
            }

            if (choice == 'e') {
                System.out.print("Ürün adı: ");
                String productName = this.scanner.nextLine();
                Product productSearchResult = this.productService.search(productName);
                if (productSearchResult == null) {
                    System.out.println("Ürün bulunamadı.");
                } else {
                    System.out.print("Miktar: ");
                    int quantity = this.consoleService.getInt("Miktar Yaz:", 0);
                    BasketProduct basketProduct = this.search(productSearchResult);
                    if (basketProduct != null) {
                        basketProduct.quantity += quantity;
                    } else {
                        this.cart.add(new BasketProduct(productSearchResult, quantity));
                    }

                    System.out.println(productSearchResult.getName() + " sepetinize eklendi.");
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
        } while(!basketProduct.product.equals(productSearchResult));

        return basketProduct;
    }

    public void calculateBasketTotal() {
        if (this.cart.isEmpty()) {
            System.out.println("Sepetiniz boş.");
        } else {
            double total = 0.0;
            Product nextProduct = null;
            int loopStart = this.cart.size() - 1;

            for(int i = 0; i < loopStart + 1; ++i) {
                if (i < loopStart) {
                    nextProduct = this.cart.get(i + 1).product;
                }

                BasketProduct basketProduct = this.cart.get(i);
                double currentProductPrice = basketProduct.product.getPrice();
                if (nextProduct != null && currentProductPrice > nextProduct.getPrice()) {
                    currentProductPrice -= nextProduct.getPrice();
                }

                total += currentProductPrice * (double)basketProduct.quantity;
            }

            System.out.println("Sepet toplamı: " + total);
        }
    }
}