package org.example;

import java.util.Scanner;

class ConsoleService {
    private final Scanner scanner;

    ConsoleService(Scanner scanner) {
        this.scanner = scanner;
    }

    public int getInt(String message, Integer min, Integer max) {
        if (min == null) {
            min = Integer.MIN_VALUE;
        }

        if (max == null) {
            max = Integer.MAX_VALUE;
        }

        Integer quantity;
        for (quantity = null; quantity == null || quantity < min || quantity > max; quantity = this.scanner.nextInt()) {
            System.out.print(message);
        }

        return quantity;
    }

    public Integer getInt(String message, Integer min) {
        return this.getInt(message, min, null);
    }

    public String getString(String message, int minLength) {
        while (true) {
            String s = this.scanner.nextLine();
            if (!s.isEmpty() && s.length() <= minLength) {
                return s;
            }

            System.out.printf(message, minLength);
            System.out.println();
        }
    }
}