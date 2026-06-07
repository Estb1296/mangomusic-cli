package com.mangomusic.ui;

import com.mangomusic.data.ReportsDao;
import com.mangomusic.models.ReportResult;
import com.mangomusic.util.ConsoleColors;
import com.mangomusic.util.InputValidator;

import java.util.List;

public class CatalogManagementScreen {
    private final ReportsDao reportsDao;
    public CatalogManagementScreen(ReportsDao reportsDao){
        this.reportsDao=reportsDao;
    }
    public void display(){
        boolean running = true;
        while(running){
            InputValidator.clearScreen();
            displayMenu();

            int choice = InputValidator.getIntInRange("Select an option: ", 0, 4);
            switch(choice){
                case 1:
                    // Album Completion Leaderboard
                    showAlbumCompletionLeaderboard();
                    break;
                case 2:
                    // Catalog Age vs. Engagement Analysis
                    break;
                case 3:
                    // Top 3 Genres Per Country
                    break;
                case 0:
                    running = false;
                    break;
            }
        }
    }
    private void displayMenu(){
        ConsoleColors.printHeader("Catalog Management");

        System.out.println("\n Catalog Analytics");

        System.out.println("1: Album Completion Leaderboard");
        System.out.println("2: Catalog Age vs. Engagement Analysis");
        System.out.println("3: Top 3 Genres Per Country");

        System.out.println("\n0. Back to main menu");

    }
    private void showAlbumCompletionLeaderboard(){
        List<ReportResult> results = reportsDao.getAlbumCompletionLeaderboard();
        if(results.isEmpty()){
            System.out.println("Something went wrong! Unable to pull up data.");
        }
        System.out.printf("%-25s  %-20s  %-10s  %-11s  %-15s  %-10s%n",
                "Title", "Artist", "Genre", "Total Plays", "Completed Plays", "Completion Rate");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        for(ReportResult result:results){
            System.out.printf("%-25.25s  %-20.20s  %-10.10s  %,-11d  %,-15d  %6.2f%%%n",
                    result.getString("album_title"),
                    result.getString("artist_name"),
                    result.getString("genre"),
                    result.getInt("total_plays"),
                    result.getInt("completed_plays"),
                    result.getDouble("completion_rate"));
        }
    }
}
