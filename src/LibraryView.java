//package src;
import java.util.List;
import java.util.Scanner;

public class LibraryView {
    private LibraryModel model;
    private Scanner scanner;

    public LibraryView(LibraryModel model) {
        this.model = model;
        this.scanner = new Scanner(System.in);
    }

    // Start the application
    public void start() {
        boolean running = true;
        
        System.out.println("Welcome to the Music Library Application!");
        
        while (running) {
            mainMenu();
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    searchMusicStoreOptions();
                    break;
                case "2":
                    searchLibraryOptions();
                    break;
                case "3":
                    addToLibraryOptions();
                    break;
                case "4":
                    displayListsOptions();
                    break;
                case "5":
                    playlistOptions();
                    break;
                case "6":
                    manageSongsOptions();
                    break;
                case "7":
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice, choose again.");
            }
        }
    }

    // helper method that displays the main menu
    private void mainMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Search Music Store");
        System.out.println("2. Search My Library");
        System.out.println("3. Add to My Library");
        System.out.println("4. Display Lists");
        System.out.println("5. Create/Change Playlist");
        System.out.println("6. Manage Songs (Rate/Favorite)");
        System.out.println("7. Exit");
    }
 
    // If you chose Search Music Store these are your options
    private void searchMusicStoreOptions() {
        System.out.println("\nSearch Music Store:");
        System.out.println("1. Search for a song by title");
        System.out.println("2. Search for a song by artist");
        System.out.println("3. Search for an album by title");
        System.out.println("4. Search for an album by artist");
        System.out.println("5. Back to main menu");
        
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                searchStoreSongByTitle();
                break;
            case "2":
                searchStoreSongByArtist();
                break;
            case "3":
                searchStoreAlbumByTitle();
                break;
            case "4":
                searchStoreAlbumByArtist();
                break;
            case "5":
                break;
            default:
                System.out.println("Invalid choice. Returning to the main menu.");
        }
    }

    // If you chose Search My Library these are your options
    private void searchLibraryOptions() {
        System.out.println("\nSearch My Library:");
        System.out.println("1. Search for a song by title");
        System.out.println("2. Search for a song by artist");
        System.out.println("3. Search for an album by title");
        System.out.println("4. Search for an album by artist");
        System.out.println("5. Search for a playlist by name");
        System.out.println("6. Back to main menu");
        
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();  
        switch (choice) {
            case "1":
                searchLibrarySongByTitle();
                break;
            case "2":
                searchLibrarySongByArtist();
                break;
            case "3":
                searchLibraryAlbumByTitle();
                break;
            case "4":
                searchLibraryAlbumByArtist();
                break;
            case "5":
                searchPlaylistByName();
                break;
            case "6":
                break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    // If you chose Add to My Library, these are your options
    private void addToLibraryOptions() {
        System.out.println("\nAdd to My Library:");
        System.out.println("1. Add a song");
        System.out.println("2. Add an album");
        System.out.println("3. Back to main menu");
        
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                addSongToLibrary();
                break;
            case "2":
                addAlbumToLibrary();
                break;
            case "3":
                break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    // If you chose Display Lists, these are you options
    private void displayListsOptions() {
        System.out.println("\nDisplay Lists:");
        System.out.println("1. List all songs");
        System.out.println("2. List all artists");
        System.out.println("3. List all albums");
        System.out.println("4. List all playlists");
        System.out.println("5. List favorite songs");
        System.out.println("6. Back to main menu");
        
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                displayAllSongs();
                break;
            case "2":
                displayAllArtists();
                break;
            case "3":
                displayAllAlbums();
                break;
            case "4":
                displayAllPlaylists();
                break;
            case "5":
                displayFavoriteSongs();
                break;
            case "6":
                break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    // If you chose Create/Change Playlist, these are your options
    private void playlistOptions() {
        System.out.println("\nCreate/Change Playlist:");
        System.out.println("1. Create a new playlist");
        System.out.println("2. Add a song to a playlist");
        System.out.println("3. Remove a song from a playlist");
        System.out.println("4. Open a playlist");
        System.out.println("5. Back to main menu");
        
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine(); 
        switch (choice) {
            case "1":
                createPlaylist();
                break;
            case "2":
                addSongToPlaylist();
                break;
            case "3":
                removeSongFromPlaylist();
                break;
            case "4":
                viewPlaylist();
                break;
            case "5":
                break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    // If you chose Manage songs, these are your options
    private void manageSongsOptions() {
        System.out.println("\nManage Songs:");
        System.out.println("1. Mark a song as favorite");
        System.out.println("2. Rate a song");
        System.out.println("3. Back to main menu");
        
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                markSongAsFavorite();
                break;
            case "2":
                rateSong();
                break;
            case "3":
                break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }

// THIS POINT FORWARD IS AI GENERATED ===================================================================
    
    // Search for a song by title in the store
    private void searchStoreSongByTitle() {
        System.out.print("Enter the song title to search for: ");
        String title = scanner.nextLine();
    	// Retrieve a song from the model by matching the inputed title
        List<Song> results = model.searchStoreSongsWithTitle(title);
        
        // if the results come up empty print the following message
        if (results.isEmpty()) {
            System.out.println("No songs found with title containing '" + title + "'.");
        } else {
        	// otherwise it prints the number of matches found 
            System.out.println("Found " + results.size() + " songs:");
            // loops through all the results if there's multiple and prints them in a specific format
            for (int i = 0; i < results.size(); i++) {
                Song song = results.get(i);
                // (i+1) is 10 based indexing meaning it starts at 1 instead of 0
                System.out.println((i + 1) + ". " + song.getTitle() + " by " + song.getArtist() + " from album " + song.getAlbum());
            }
        }
    }
    
    // Search for a song by artist in the store
    private void searchStoreSongByArtist() {
        System.out.print("Enter the artist name to search for: ");
        String artist = scanner.nextLine();
        
        List<Song> results = model.searchStoreSongsWithArtist(artist);
        
        if (results.isEmpty()) {
            System.out.println("No songs found by artist containing '" + artist + "'.");
        } else {
            System.out.println("Found " + results.size() + " songs:");
            for (int i = 0; i < results.size(); i++) {
                Song song = results.get(i);
                System.out.println((i + 1) + ". " + song.getTitle() + " by " + song.getArtist() + " from album " + song.getAlbum());
            }
        }
    }
    
    // Search for an album by title in the store
    private void searchStoreAlbumByTitle() {
        System.out.print("Enter the album title to search for: ");
        String title = scanner.nextLine();
        
        List<Album> results = model.searchStoreAlbumsWithTitle(title);
        
        if (results.isEmpty()) {
            System.out.println("No albums found with title containing '" + title + "'.");
        } else {
            System.out.println("Found " + results.size() + " albums:");
            // Loops through each album and display its information including: (title, artist, year, genre)
            for (int i = 0; i < results.size(); i++) { 
                Album album = results.get(i);
                //(title, artist, year, genre)
                System.out.println((i + 1) + ". " + album.getTitle() + " by " + album.getArtist() + " (" + album.getYear() + ") - " + album.getGenre());
                
                // Displays all songs in the album as a nested list
                System.out.println("   Songs:");
                List<String> songTitles = album.getSongTitles();
                for (int j = 0; j < songTitles.size(); j++) {
                    System.out.println("   " + (j + 1) + ". " + songTitles.get(j));
                }
                // Adds a blank line 
                System.out.println();
            }
        }
    }
    
    // Search for an album by artist in the store
    private void searchStoreAlbumByArtist() {
        System.out.print("Enter the artist name to search for: ");
        String artist = scanner.nextLine();
        
        List<Album> results = model.searchStoreAlbumsWithArtist(artist);
        
        if (results.isEmpty()) {
            System.out.println("No albums found by artist containing '" + artist + "'.");
        } else {
            System.out.println("Found " + results.size() + " albums:");
            for (int i = 0; i < results.size(); i++) {
                Album album = results.get(i);
                System.out.println((i + 1) + ". " + album.getTitle() + " by " + album.getArtist() + " (" + album.getYear() + ") - " + album.getGenre());
                
                System.out.println("   Songs:");
                List<String> songTitles = album.getSongTitles();
                for (int j = 0; j < songTitles.size(); j++) {
                    System.out.println("   " + (j + 1) + ". " + songTitles.get(j));
                }
                System.out.println();
            }
        }
    }
    
    // Search for a song by title in the library
    private void searchLibrarySongByTitle() {
        System.out.print("Enter the song title to search for: ");
        String title = scanner.nextLine();
        
        // search the model database to find songs in the user's library matching the inputed title
        List<Song> results = model.searchLibSongsWithTitle(title);
        
        if (results.isEmpty()) {
            System.out.println("No songs found in your library with title containing '" + title + "'.");
        } else {
        	// Displays the number of matches found
            System.out.println("Found " + results.size() + " songs in your library:");
            // Loops through each result and displays formatted information
            for (int i = 0; i < results.size(); i++) {
                Song song = results.get(i);
                System.out.println((i + 1) + ". " + song.getTitle() + " by " + song.getArtist() + " from album " + song.getAlbum());
                // Lets the user know if that song is marked as favorite
                if (song.isFavorite()) {
                    System.out.println("   (Favorite)");
                }
                // Displays the rating is the song was rated 
                if (song.getRating() != null) {
                    System.out.println("   Rating: " + song.getRating() + "/5");
                }
            }
        }
    }
    
    // Search for a song by artist in the library
    private void searchLibrarySongByArtist() {
        System.out.print("Enter the artist name to search for: ");
        String artist = scanner.nextLine();
        
        List<Song> results = model.searchLibSongsWithArtist(artist);
        
        if (results.isEmpty()) {
            System.out.println("No songs found in your library by artist containing '" + artist + "'.");
        } else {
            System.out.println("Found " + results.size() + " songs in your library:");
            for (int i = 0; i < results.size(); i++) {
                Song song = results.get(i);
                System.out.println((i + 1) + ". " + song.getTitle() + " by " + song.getArtist() + " from album " + song.getAlbum());
                if (song.isFavorite()) {
                    System.out.println("   (Favorite)");
                }
                if (song.getRating() != null) {
                    System.out.println("   Rating: " + song.getRating() + "/5");
                }
            }
        }
    }
    
    // Search for an album by title in the library
    private void searchLibraryAlbumByTitle() {
        System.out.print("Enter the album title to search for: ");
        String title = scanner.nextLine();
        
        List<Album> results = model.searchLibAlbumsWithTitle(title);
        
        if (results.isEmpty()) {
            System.out.println("No albums found in your library with title containing '" + title + "'.");
        } else {
            System.out.println("Found " + results.size() + " albums in your library:");
            // Loops through each result and displays formatted information
            for (int i = 0; i < results.size(); i++) {
                Album album = results.get(i);
                System.out.println((i + 1) + ". " + album.getTitle() + " by " + album.getArtist() + " (" + album.getYear() + ") - " + album.getGenre());
                
                // Display all songs in the album as a nested list
                System.out.println("   Songs:");
                List<String> songTitles = album.getSongTitles();
                for (int j = 0; j < songTitles.size(); j++) {
                    System.out.println("   " + (j + 1) + ". " + songTitles.get(j));
                }
                // Adds a blank line
                System.out.println();
            }
        }
    }
    
    // Search for an album by artist in the library
    private void searchLibraryAlbumByArtist() {
        System.out.print("Enter the artist name to search for: ");
        String artist = scanner.nextLine();
        
        List<Album> results = model.searchLibAlbumsWithArtist(artist);
        
        if (results.isEmpty()) {
            System.out.println("No albums found in your library by artist containing '" + artist + "'.");
        } else {
            System.out.println("Found " + results.size() + " albums in your library:");
            for (int i = 0; i < results.size(); i++) {
                Album album = results.get(i);
                System.out.println((i + 1) + ". " + album.getTitle() + " by " + album.getArtist() + " (" + album.getYear() + ") - " + album.getGenre());
                System.out.println("   Songs:");
                List<String> songTitles = album.getSongTitles();
                for (int j = 0; j < songTitles.size(); j++) {
                    System.out.println("   " + (j + 1) + ". " + songTitles.get(j));
                }
                System.out.println();
            }
        }
    }
    
    // Search for a playlist by name
    private void searchPlaylistByName() {
        System.out.print("Enter the playlist name to search for: ");
        String name = scanner.nextLine();
        
        Playlist playlist = model.getPlaylistWithName(name);
        
        if (playlist == null) {
            System.out.println("No playlist found with name '" + name + "'.");
        } else {
        	// If the playlist was found, display its name
            System.out.println("Playlist: " + playlist.getName());
            // Retrieve all songs from the playlist
            List<Song> songs = playlist.getSongs();
            
            if (songs.isEmpty()) {
                System.out.println("This playlist is empty.");
            } else {
                System.out.println("Songs in playlist:");
                // Loop through each song in the playlist
                for (int i = 0; i < songs.size(); i++) {
                    Song song = songs.get(i);
                    System.out.println((i + 1) + ". " + song.getTitle() + " by " + song.getArtist());
                }
            }
        }
    }
    
    
    // Add a song to the library
    private void addSongToLibrary() {
        System.out.print("Enter the song title: ");
        String title = scanner.nextLine();
        System.out.print("Enter the artist name: ");
        String artist = scanner.nextLine();
        
        // Call the model to add the song to the user's library
        // This returns a boolean indicating whether the operation was successful
        boolean success = model.addSongToLibrary(title, artist);
        
        if (success) {
            System.out.println("Song added to your library successfully!");
        } else {
            System.out.println("Failed to add song to your library. The song might not exist in the store or is already in your library.");
        }
    }
    
    
    // Add an album to the library
    private void addAlbumToLibrary() {
        System.out.print("Enter the album title: ");
        String title = scanner.nextLine();
        System.out.print("Enter the artist name: ");
        String artist = scanner.nextLine();
        
        // Call the model to add the album to the user's library
        // This returns a boolean indicating whether the operation was successful
        boolean success = model.addAlbumToLibrary(title, artist);
        
        if (success) {
            System.out.println("Album added to your library successfully!");
        } else {
            System.out.println("Failed to add album to your library. The album might not exist in the store or is already in your library.");
        }
    }
    
    
    // Display all songs in the library
    private void displayAllSongs() {
    	// Retrieve all songs from the user's library through the model
        List<Song> songs = model.getAllLibrarySongs();
        
        if (songs.isEmpty()) {
            System.out.println("Your library has no songs.");
        } else {
            System.out.println("All songs in your library:");
            // Loop through each song in the library
            for (int i = 0; i < songs.size(); i++) {
                Song song = songs.get(i);
                System.out.println((i + 1) + ". " + song.getTitle() + " by " + song.getArtist());
            }
        }
    }
    
    // Display all artists in the library
    private void displayAllArtists() {
    	// Retrieve all unique artist names from the user's library through the model
        List<String> artists = model.getAllLibraryArtists();
        
        if (artists.isEmpty()) {
            System.out.println("Your library has no artists.");
        } else {
            System.out.println("All artists in your library:");
            // Loop through each artist name
            for (int i = 0; i < artists.size(); i++) {
                System.out.println((i + 1) + ". " + artists.get(i));
            }
        }
    }
    
    // Display all albums in the library
    private void displayAllAlbums() {
    	// Retrieve all albums from the user's library through the model
        List<Album> albums = model.getAllLibraryAlbums();
        
        if (albums.isEmpty()) {
            System.out.println("Your library has no albums.");
        } else {
            System.out.println("All albums in your library:");
            // Loop through each album
            for (int i = 0; i < albums.size(); i++) {
                Album album = albums.get(i);
                System.out.println((i + 1) + ". " + album.getTitle() + " by " + album.getArtist());
            }
        }
    }
    
    // Display all playlists
    private void displayAllPlaylists() {
    	// Retrieve all playlists created by the user through the model
        List<Playlist> playlists = model.getAllPlaylists();
        
        if (playlists.isEmpty()) {
            System.out.println("You have no playlists.");
        } else {
            System.out.println("All your playlists:");
            // Loop through each playlist
            for (int i = 0; i < playlists.size(); i++) {
                Playlist playlist = playlists.get(i);
                System.out.println((i + 1) + ". " + playlist.getName() + " (" + playlist.getSongAmt() + " songs)");
            }
        }
    }
    
    
    // Display favorite songs
    private void displayFavoriteSongs() {
    	// Retrieve all songs marked as favorites from the user's library
        List<Song> favorites = model.getFavoriteSongs();
        
        if (favorites.isEmpty()) {
            System.out.println("You have no favorite songs.");
        } else {
            System.out.println("Your favorite songs:");
            // Loop through each favorite song 
            for (int i = 0; i < favorites.size(); i++) {
            	// Get the current song from the list
                Song song = favorites.get(i);
                System.out.println((i + 1) + ". " + song.getTitle() + " by " + song.getArtist());
                // Check if the song has a rating assigned
                if (song.getRating() != null) {
                    System.out.println("   Rating: " + song.getRating() + "/5");
                }
            }
        }
    }
    
    // Create a new playlist
    private void createPlaylist() {
        System.out.print("Enter a name for the new playlist: ");
        String name = scanner.nextLine();
        
        // Call the model to create a new playlist with the specified name
        // This returns a boolean indicating whether the operation was successful
        boolean success = model.createPlaylist(name);
        
        if (success) {
            System.out.println("Playlist created successfully!");
        } else {
            System.out.println("Failed to create playlist. A playlist with this name might already exist.");
        }
    }
    
    
    // Add a song to a playlist
    private void addSongToPlaylist() {
        System.out.print("Enter the playlist name: ");
        String playlistName = scanner.nextLine();
        
        // Call the model to retrieve the playlist object with the specified name
        Playlist playlist = model.getPlaylistWithName(playlistName);
        
        if (playlist == null) {
            System.out.println("Playlist not found.");
            return;
        }
        
        System.out.print("Enter the song title: ");
        String title = scanner.nextLine();
        System.out.print("Enter the artist name: ");
        String artist = scanner.nextLine();
        
        // Call the model to add the specified song to the playlist
        // This returns a boolean indicating whether the operation was successful
        boolean success = model.addSongToPlaylist(playlistName, title, artist);
        
        if (success) {
            System.out.println("Song added to playlist successfully!");
        } else {
            System.out.println("Failed to add song to playlist. The song might not exist in your library.");
        }
    }
    
    // Remove a song from a playlist
    private void removeSongFromPlaylist() {
        System.out.print("Enter the playlist name: ");
        String playlistName = scanner.nextLine();
        
        // Call the model to retrieve the playlist object with the specified name
        Playlist playlist = model.getPlaylistWithName(playlistName);
        
        if (playlist == null) {
            System.out.println("Playlist not found.");
            return;
        }
        
        // Retrieve all songs from the playlist
        List<Song> songs = playlist.getSongs();
        // Check if the playlist contains any songs
        if (songs.isEmpty()) {
        	// If the playlist is empty, inform the user and exit the method
            System.out.println("The playlist is empty.");
            return;
        }
        
        System.out.println("Songs in playlist:");
        // Loop through each song in the playlist
        for (int i = 0; i < songs.size(); i++) {
            Song song = songs.get(i);
            System.out.println((i + 1) + ". " + song.getTitle() + " by " + song.getArtist());
        }
        
        // Prompt the user to enter the number of the song they want to remove
        System.out.print("Enter the number of the song to remove: ");
        try {
        	 // Read the user's input, convert it to an integer
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            
            // Check if the entered index is valid (within the bounds of the songs list)
            if (index >= 0 && index < songs.size()) {
                // Get the song at the selected index
                Song selectedSong = songs.get(index);
                String title = selectedSong.getTitle();
                String artist = selectedSong.getArtist();
                
                // Call the model to remove the song from the playlist using title and artist
                boolean success = model.removeSongFromPlaylist(playlistName, title, artist);
                
                // if it was successfully removed display the confirmation message
                if (success) {
                    System.out.println("Song removed from playlist successfully!");
                } else {
                    System.out.println("Failed to remove song from playlist.");
                }
            } else {
            	// if the entered intex is out of bounds, print error message
                System.out.println("Invalid song index. Please select a number between 1 and " + songs.size() + ".");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }
    
    // View a playlist
    private void viewPlaylist() {
        System.out.print("Enter the playlist name: ");
        String name = scanner.nextLine();
        
        // Call the model to retrieve the playlist object with the specified name
        Playlist playlist = model.getPlaylistWithName(name);
        
        // if the playlist was found
        if (playlist == null) {
            System.out.println("Playlist not found.");
            return;
        }
        
        // Display the name of the playlist
        System.out.println("Playlist: " + playlist.getName());
        // Retrieve all songs from the playlist
        List<Song> songs = playlist.getSongs();
        
        // Check if the playlist contains any songs
        if (songs.isEmpty()) {
            System.out.println("This playlist is empty.");
        } else {
            System.out.println("Songs in playlist:");
            // Loop through each song in the playlist
            for (int i = 0; i < songs.size(); i++) {
            	// Get the current song from the list
                Song song = songs.get(i);
                // Display the song number (1-based), title, and artist
                System.out.println((i + 1) + ". " + song.getTitle() + " by " + song.getArtist());
                // Check if the song is marked as a favorite
                if (song.isFavorite()) {
                    System.out.println("   (Favorite)");
                }
                // Check if the song is rated
                if (song.getRating() != null) {
                    System.out.println("   Rating: " + song.getRating() + "/5");
                }
            }
        }
    }
    
    // Mark a song as favorite
    private void markSongAsFavorite() {
        System.out.print("Enter the song title: ");
        String title = scanner.nextLine();
        System.out.print("Enter the artist name: ");
        String artist = scanner.nextLine();
        
        // Calls the model to mark the specified song as favorite
        boolean success = model.markSongAsFav(title, artist);
        
        // The markSongAsFav method will return a boolean which tells you if it was successful or not
        if (success) {
            System.out.println("Song marked as favorite successfully!");
        } else {
            System.out.println("Failed to mark song as favorite. The song might not exist in your library.");
        }
    }
    
    // Rate a song
    private void rateSong() {
        System.out.print("Enter the song title: ");
        String title = scanner.nextLine();
        System.out.print("Enter the artist name: ");
        String artist = scanner.nextLine();
        
        // Uses a try-catch block to handle potential number format errors
        try {
            System.out.print("Enter a rating (1-5): ");
            // Read the user's input, convert it to an integer, and store it in the 'rating' variable
            int rating = Integer.parseInt(scanner.nextLine());
            
            if (rating < 1 || rating > 5) {
                System.out.println("Invalid rating. Please enter a number between 1 and 5.");
                return;
            }
            
            // Calls the model to rate the specified song with the provided rating
            boolean success = model.rateSong(title, artist, rating);
            
            // The rateSong method will return a boolean which tells you if it was successful or not
            if (success) {
            	// iff successfully prints a message saying so, and if the rate was a 5 it mentions that the song was marked as a favorite
                System.out.println("Song rated successfully!" + (rating == 5 ? " The song was also marked as a favorite." : ""));
            } else {
                System.out.println("Failed to rate song. The song might not exist in your library.");
            }
        } catch (NumberFormatException e) {
        	// If the user's input cannot be parsed as an integer, displays an error message
            System.out.println("Invalid input. Please enter a number.");
        }
    }
}
