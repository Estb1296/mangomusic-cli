package com.mangomusic.ui;

import com.mangomusic.data.ReportsDao;
import com.mangomusic.models.ReportResult;
import com.mangomusic.util.ConsoleColors;
import com.mangomusic.util.InputValidator;

import java.util.List;

public class SpecialReportsScreen {

    private final ReportsDao reportsDao;

    public SpecialReportsScreen(ReportsDao reportsDao) {
        this.reportsDao = reportsDao;
    }

    public void display() {
        boolean running = true;

        while (running) {
            InputValidator.clearScreen();
            displayMenu();

            int choice = InputValidator.getIntInRange("Select an option: ", 0, 4);

            switch (choice) {
                case 1:
                    showMangoMusicMapped();
                    break;
                case 2:
                    //@TODO - Create report
                    showMostPlayedAlbumsByGenre();
                    break;
                case 3:
                    //@TODO - Create report
                    showUserDiversityScore();
                    break;
                case 4:
                    //@TODO - Create report
                    showPeakListeningHours();
                    break;
                case 0:
                    running = false;
                    break;
            }
        }
    }

    private void displayMenu() {
        ConsoleColors.printHeader("SPECIAL REPORTS");

        System.out.println("\nPERSONALIZED ANALYTICS:");
        System.out.println("1. MangoMusic Mapped (Year in Review)");
        System.out.println("2. Most Played Albums by Genre");
        System.out.println("3. User Listening Diversity Score");
        System.out.println("4. Peak Listening Hours Analysis");

        System.out.println("\n0. Back to main menu");
        System.out.println();
    }

    private void showMangoMusicMapped() {
        InputValidator.clearScreen();
        ConsoleColors.printHeader("🎵 MANGOMUSIC MAPPED 🎵");
        System.out.println("Your personalized year in review\n");

        int userId = InputValidator.getIntInRange("Enter user ID: ", 1, Integer.MAX_VALUE);

        ReportResult mapped = reportsDao.getMangoMusicMapped(userId);

        int year = mapped.getInt("year");

        if (mapped.getInt("total_plays") == 0) {
            ConsoleColors.printWarning("No listening data found for user ID " + userId + " in " + year + ".");
        } else {
            System.out.println("\n" + "=".repeat(70));
            System.out.println("YOUR " + year + " LISTENING STORY");
            System.out.println("=".repeat(70));

            System.out.println("\n🎧 LISTENING STATS:");
            System.out.println("   Total Plays: " + mapped.getInt("total_plays"));
            System.out.println("   Albums Explored: " + mapped.getInt("unique_albums"));
            System.out.println("   Artists Discovered: " + mapped.getInt("unique_artists"));
            System.out.println("   Completion Rate: " +
                    String.format("%.1f%%", (mapped.getInt("completed_plays") * 100.0 / mapped.getInt("total_plays"))));

            System.out.println("\n⭐ YOUR TOP PICKS:");
            System.out.println("   #1 Artist: " + mapped.getString("top_artist") +
                    " (" + mapped.getInt("top_artist_plays") + " plays)");
            System.out.println("   Favorite Genre: " + mapped.getString("top_genre"));
            System.out.println("   Most Active Month: " + mapped.getString("top_month") +
                    " (" + mapped.getInt("top_month_plays") + " plays)");

            System.out.println("\n🔥 FUN FACTS:");
            System.out.println("   Longest Listening Streak: " + mapped.getInt("longest_streak") + " days");
            System.out.println("   Listener Personality: " + mapped.getString("listener_personality"));

            System.out.println("\n" + "=".repeat(70));
            System.out.println("Thanks for making " + year + " a year full of music! 🎶");
            System.out.println("=".repeat(70));
        }

        InputValidator.pressEnterToContinue();
    }
        private void showMostPlayedAlbumsByGenre(){
        List<ReportResult> results = reportsDao.getMostPlayedAlbumsByGenre();
        if(results.isEmpty()){
            System.out.println("Something went wrong! Unable to pull up searches.");
        }
        System.out.println("Genre          Album Title                                               Artist Name                           Plays    Rank");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
        for (ReportResult result : results) {
            System.out.printf("%-15s  %-55s %-35s %7d   %3d %n",
                    result.getString("genre"),
                    result.getString("album_title"),
                    result.getString("artist_name"),
                    result.getInt("play_count"),
                    result.getInt("genre_rank"));
        }
    }

    private void  showUserDiversityScore(){
        List<ReportResult>results = reportsDao.getUserDiversityReport();
        if(results.isEmpty()){
            System.out.println("Something went wrong! Unable to pull up searches.");
        }
        System.out.println("User ID  Username             Subscription          Artists  Genres  Plays   Diversity");
        System.out.println("-------  -------------------  ---------------       -------  -------  -----   ----------");
        for(ReportResult result:results){
            System.out.printf("%-7d  %-23s  %-15s  %7d  %7d  %5d   %8.2f %n",
            result.getInt("user_id"),
            result.getString("username"),
            result.getString("subscription_type"),
            result.getInt("distinct_artists_played"),
            result.getInt("distinct_genres"),
            result.getInt("total_plays"),
            result.getDouble("diversity_score"));
        }
    }
    private void showPeakListeningHours(){
        List<ReportResult>results = reportsDao.getPeakListeningHoursReport();
        if(results.isEmpty()){
            System.out.println("Something went wrong! Unable to pull up data.");
        }
        System.out.println("Hour   Total Plays  Unique Users  Avg Plays/User");
        System.out.println("----   -----------  ------------  --------------");
        for(ReportResult result:results){
            System.out.printf("%2d     %11d  %12d  %14.2f %n",
            result.getInt("hour_of_day"),
            result.getInt("total_plays"),
            result.getInt("unique_users"),
            result.getDouble("avg_plays_per_user"));
        }
    }

}