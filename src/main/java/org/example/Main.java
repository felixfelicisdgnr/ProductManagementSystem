package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConsoleService consoleService = new ConsoleService(scanner);
        ProductService productService = new ProductService(scanner, consoleService);
        CartService cartService = new CartService(scanner, productService, consoleService);
        productService.addNewProducts();
        productService.sortProducts();
        productService.printProducts();
        cartService.addToBasket();
        cartService.calculateBasketTotal();

    }
}