import java.util.Scanner;

/**
 * Online Food Simulation App
 * A console-based multi-restaurant food ordering system.
 * Author: Mahnoor Shahbaz
 */
public class OnlineFoodSimulationApp {

    // ─── CONSTANTS ───────────────────────────────────────────
    static final int DISCOUNT_THRESHOLD = 3000;
    static final double DISCOUNT_RATE = 0.10;

    // ─── MENU DATA: {itemCode, itemName, itemPrice} ───────────
    static String[][] burgerMenu = {
            {"101", "Zinger Burger", "550"},
            {"102", "Beef Burger",   "650"},
            {"103", "Fries",         "250"}
    };

    static String[][] pizzaMenu = {
            {"201", "Chicken Pizza", "1200"},
            {"202", "Fajita Pizza",  "1400"},
            {"203", "Garlic Bread",  "300"}
    };

    static String[][] desiMenu = {
            {"301", "Chicken Karahi", "1100"},
            {"302", "Biryani",        "350"},
            {"303", "Raita",          "80"}
    };

    // ─── GET MENU BY RESTAURANT CHOICE ───────────────────────
    static String[][] getMenu(int restaurantChoice) {
        if (restaurantChoice == 1) return burgerMenu;
        if (restaurantChoice == 2) return pizzaMenu;
        return desiMenu;
    }

    // ─── DISPLAY AVAILABLE RESTAURANTS ───────────────────────
    static void displayRestaurants() {
        System.out.println("\n=== SELECT A RESTAURANT ===");
        System.out.println("1. Burger House");
        System.out.println("2. Pizza Palace");
        System.out.println("3. Desi Delight");
    }

    // ─── DISPLAY MENU ITEMS WITH CODES AND PRICES ─────────────
    static void displayMenu(String[][] menu) {
        System.out.println("\n" + String.format("%-6s %-20s %s", "Code", "Item", "Price (PKR)"));
        System.out.println(String.format("%-6s %-20s %s", "----", "----", "-----------"));
        for (int i = 0; i < menu.length; i++) {
            System.out.println(String.format("%-6s %-20s %s",
                    menu[i][0], menu[i][1], menu[i][2]));
        }
    }

    // ─── CHECK IF ITEM CODE EXISTS IN MENU ───────────────────
    static boolean isValidCode(String[][] menu, String itemCode) {
        for (int i = 0; i < menu.length; i++) {
            if (menu[i][0].equals(itemCode)) return true;
        }
        return false;
    }

    // ─── GET ITEM PRICE BY CODE ───────────────────────────────
    static int getItemPrice(String[][] menu, String itemCode) {
        for (int i = 0; i < menu.length; i++) {
            if (menu[i][0].equals(itemCode)) return Integer.parseInt(menu[i][2]);
        }
        return 0;
    }

    // ─── GET ITEM NAME BY CODE ────────────────────────────────
    static String getItemName(String[][] menu, String itemCode) {
        for (int i = 0; i < menu.length; i++) {
            if (menu[i][0].equals(itemCode)) return menu[i][1];
        }
        return "";
    }

    // ─── CALCULATE ORDER SUBTOTAL ─────────────────────────────
    static int calculateSubtotal(String[][] menu, String[] itemCodes, int[] quantities) {
        int subtotal = 0;
        for (int i = 0; i < itemCodes.length; i++) {
            subtotal += getItemPrice(menu, itemCodes[i]) * quantities[i];
        }
        return subtotal;
    }

    // ─── CALCULATE DISCOUNT (10% IF ORDER ABOVE 3000 PKR) ────
    static int calculateDiscount(int subtotal) {
        if (subtotal > DISCOUNT_THRESHOLD) {
            return (int) (subtotal * DISCOUNT_RATE);
        }
        return 0;
    }

    // ─── PRINT ITEMIZED RECEIPT ───────────────────────────────
    static void printReceipt(String[][] menu, String[] itemCodes, int[] quantities, int subtotal, int discount) {
        System.out.println("\n======== RECEIPT ========");
        for (int i = 0; i < itemCodes.length; i++) {
            String itemName  = getItemName(menu, itemCodes[i]);
            int    itemPrice = getItemPrice(menu, itemCodes[i]);
            int    lineTotal = itemPrice * quantities[i];
            System.out.println(String.format("%-20s x%-3d PKR %d",
                    itemName, quantities[i], lineTotal));
        }
        System.out.println("-------------------------");
        System.out.println(String.format("%-24s PKR %d", "Subtotal :", subtotal));
        System.out.println(String.format("%-24s PKR %d", "Discount :", discount));
        System.out.println(String.format("%-24s PKR %d", "Final Bill :", (subtotal - discount)));
        System.out.println("=========================");
        System.out.println("Thank you for your order!");
    }

    // ─── MAIN METHOD ──────────────────────────────────────────
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== WELCOME TO FOOD ORDER SYSTEM ===");

        // Step 1: Select restaurant
        displayRestaurants();
        System.out.print("Enter your choice (1-3): ");
        int restaurantChoice = scanner.nextInt();

        if (restaurantChoice < 1 || restaurantChoice > 3) {
            System.out.println("Invalid choice. Please restart and select 1, 2, or 3.");
            scanner.close();
            return;
        }

        // Step 2: Display menu
        String[][] selectedMenu = getMenu(restaurantChoice);
        displayMenu(selectedMenu);

        // Step 3: Take order
        System.out.print("\nHow many items would you like to order? ");
        int itemCount = scanner.nextInt();

        String[] itemCodes  = new String[itemCount];
        int[]    quantities = new int[itemCount];

        for (int i = 0; i < itemCount; i++) {
            // Validate item code
            System.out.print("Enter item code: ");
            String itemCode = scanner.next();
            while (!isValidCode(selectedMenu, itemCode)) {
                System.out.print("Invalid code. Please enter a valid item code: ");
                itemCode = scanner.next();
            }

            // Validate quantity
            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();
            while (quantity <= 0) {
                System.out.print("Quantity must be at least 1. Re-enter: ");
                quantity = scanner.nextInt();
            }

            itemCodes[i]  = itemCode;
            quantities[i] = quantity;
        }

        // Step 4: Calculate and print receipt
        int subtotal = calculateSubtotal(selectedMenu, itemCodes, quantities);
        int discount = calculateDiscount(subtotal);
        printReceipt(selectedMenu, itemCodes, quantities, subtotal, discount);

        scanner.close();
    }
}