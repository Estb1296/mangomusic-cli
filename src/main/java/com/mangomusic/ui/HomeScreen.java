package com.mangomusic.ui;

import com.mangomusic.util.ConsoleColors;
import com.mangomusic.util.InputValidator;

/**
 * Main home screen for the MangoMusic Database Tool
 */
public class HomeScreen {

    private final SearchScreen searchScreen;
    private final ReportsScreen reportsScreen;
    private final SpecialReportsScreen specialReportsScreen;
    private final CatalogManagementScreen catalogManagementScreen;

    public HomeScreen(SearchScreen searchScreen, ReportsScreen reportsScreen,
                      SpecialReportsScreen specialReportsScreen,CatalogManagementScreen catalogManagementScreen) {
        this.searchScreen = searchScreen;
        this.reportsScreen = reportsScreen;
        this.specialReportsScreen = specialReportsScreen;
        this.catalogManagementScreen = catalogManagementScreen;
    }

    /**
     * Displays the home screen and handles navigation
     */
    public void display() {
        boolean running = true;

        while (running) {
            InputValidator.clearScreen();
            displayMenu();

            int choice = InputValidator.getIntInRange("Select an option: ", 0, 4);

            switch (choice) {
                case 1:
                    searchScreen.display();
                    break;
                case 2:
                    reportsScreen.display();
                    break;
                case 3:
                    specialReportsScreen.display();
                    break;
                case 4:
                    catalogManagementScreen.display();
                case 0:
                    running = false;
                    System.out.println("\nThank you for using MangoMusic Database Tool!");
                    break;
            }
        }
    }

    /**
     * Displays the home menu
     */
    private void displayMenu() {
        ConsoleColors.printHeader("MANGOMUSIC DATABASE TOOL");

        System.out.println("\n1. Search & Queries");
        System.out.println("2. View Reports");
        System.out.println("3. Special Reports");
        System.out.println("4. Bonus Searches");
        System.out.println("0. Exit");
        System.out.println();
    }
}