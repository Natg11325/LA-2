//package src;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class LibraryView {
    private LibraryModel model;
    private Scanner scanner;
    private ManageAccounts manageAccounts;
    private Account currentAccount;
 
    public LibraryView(ManageAccounts manageAccounts) {
        this.model = null;
        this.scanner = new Scanner(System.in);
        this.manageAccounts = manageAccounts;
    }

    public void start() throws FileNotFoundException {
        boolean running = true;
        
        System.out.println("Welcome to the Music Library Application!");
        
        while (running) {
            if (currentAccount == null) {
                showLoginMenu();
            } else {
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
                    	manageSongsAlbumsOptions();
                        break;
                    case "7":
                        logout();
                        break;
                    default:
                        System.out.println("Invalid choice, choose again.");
                }
                choice = null;
            }
        }
    }

    // Show login menu
    private void showLoginMenu() throws FileNotFoundException{
        System.out.println("\nSelect an option:");
        System.out.println("1. Login");
        System.out.println("2. Make Account");
        System.out.println("3. Exit");
        
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();
        
        switch (choice) {
            case "1":
                login();
                break;
            case "2":
                makeAccount();
                break;
            case "3":
                System.out.println("Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice, choose again.");
        }
    }
    
    // Login process
    private void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        Account account = manageAccounts.authenticateUser(username, password);
        
        if (account != null) {
        	currentAccount = account;
            model = account.getLibrary();
            System.out.println("Hello again, " + username + "!");
        } else {
            System.out.println("Invalid username or password.");
        }
    }
    
    private void makeAccount() throws FileNotFoundException{
        System.out.print("Enter new username: ");
        String username = scanner.nextLine();
        
        System.out.print("Enter new password: ");
        String password = scanner.nextLine();
        
        boolean success = manageAccounts.makeAccount(username, password);
        
        if (success) {
            System.out.println("Account made successfully! Please log in.");
        } else {
            System.out.println("Username already exists. Try again.");
        }
    }
    
    // Logout
    private void logout() {
    	manageAccounts.saveAccounts(); // Save current user's data
        currentAccount = null;
        model = null;
        System.out.println("Logged out successfully.");
    }

    // helper method that displays the main menu
    private void mainMenu() {
        System.out.println("\nMain Menu (Logged in as " + currentAccount.getUsername() + "):");
        System.out.println("1. Search Music Store");
        System.out.println("2. Search My Library");
        System.out.println("3. Add to My Library");
        System.out.println("4. Display Lists");
        System.out.println("5. Create/Change Playlist");
        System.out.println("6. Manage Songs/Albums (Rate/Favorite)");
        System.out.println("7. Logout");
    }
    
 
    // If you chose Search Music Store these are your options
    private void searchMusicStoreOptions() {
        System.out.println("\nSearch Music Store:");
        System.out.println("1. Search for a song by title");
        System.out.println("2. Search for a song by artist");
        System.out.println("3. Search for a song by genre");
        System.out.println("4. Search for an album by title");
        System.out.println("5. Search for an album by artist");
        System.out.println("6. Back to main menu");
        
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
            	searchStoreSongByGenre();
            	break;
            case "4":
                searchStoreAlbumByTitle();
                break;
            case "5":
                searchStoreAlbumByArtist();
                break;
            case "6":
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
        System.out.println("3. Search for a song by genre");
        System.out.println("4. Search for an album by title");
        System.out.println("5. Search for an album by artist");
        System.out.println("6. Search for a playlist by name");
        System.out.println("7. Back to main menu");
        
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
            	searchLibrarySongByGenre();
            case "4":
                searchLibraryAlbumByTitle();
                break;
            case "5":
                searchLibraryAlbumByArtist();
                break;
            case "6":
                searchPlaylistByName();
                break;
            case "7":
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
        System.out.println("6. List most recently played songs.");
        System.out.println("7. List most played songs.");
        System.out.println("8. List sorted songs");
        System.out.println("9. List shuffled songs");
        System.out.println("10. Back to main menu");

        
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
            	displayRecentlyPlayed();
                break;
            case "7":
            	displayMostPlayed();
            	break;
            case "8":
                displaySortedSongsOptions();
                break;
            case "9":
            	shuffleSongsOption();
            	break;
            case "10":
            	break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    } 
    
    private void shuffleSongsOption() {
        System.out.println("\nShuffle Options:");
        System.out.println("1. Shuffle all library songs");
        System.out.println("2. Shuffle a playlist");
        System.out.println("3. Back to main menu");
        
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();
        
        switch (choice) {
            case "1":
                displayShuffledLibrarySongs();
                break;
            case "2":
                shuffleAndDisplayPlaylist();
                break;
            case "3":
                break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    
    private void displaySortedSongsOptions() {
        System.out.println("\nDisplay Sorted Songs:");
        System.out.println("1. Sort by title");
        System.out.println("2. Sort by artist");
        System.out.println("3. Sort by rating");
        System.out.println("4. Back to main menu");
        
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();
        
        if (choice.equals("1")) {
            displaySongsSortedByTitle();
        } else if (choice.equals("2")) {
            displaySongsSortedByArtist();
        } else if (choice.equals("3")) {
            displaySongsSortedByRating();
        } else if (choice.equals("4")) {
            // Return to main menu
        } else {
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
        System.out.println("5. Open automatic playlists");
        System.out.println("6. Back to main menu");
        
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
                viewAutomaticPlaylists();
                break;
            case "6":
            	break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }
    
    
    private void viewAutomaticPlaylists() {
        System.out.println("\nAutomatic Playlists:");
        System.out.println("1. Favorites Playlist");
        System.out.println("2. Top Rated Playlist");
        System.out.println("3. Genre Playlists");
        System.out.println("4. Recently Played Playlist");
        System.out.println("5. Most Played Playlist");
        System.out.println("6. Back to main menu");
        
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();
        
        switch (choice) {
            case "1":
                openAutoPlaylist("Automatic Favorites: ");
                break;
            case "2":
                openAutoPlaylist("Automatic Top Rated: ");
                break;
            case "3":
                viewGenrePlaylists();
                break;
            case "4":
            	displayRecentlyPlayed();
            case "5":
            	displayMostPlayed();
            case "6":
                break;
            default:
                System.out.println("Invalid choice. Returning to playlist menu.");
        }
    }

    // If you chose Manage songs, these are your options
    private void manageSongsAlbumsOptions() {
        System.out.println("\nManage Songs/Albums:");
        System.out.println("1. Mark a song as favorite");
        System.out.println("2. Rate a song");
        System.out.println("3. Play a song.");
        // added to for the remove song and album function
        System.out.println("4. Remove a song from library");
        System.out.println("5. Remove an album from library");
        System.out.println("6. Back to main menu");

        
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
            	playSong();
            	break;
            case "4":
           	  removeSongFromLibrary();
              break;
            case "5":
            	removeAlbumFromLibrary();
            	break;
            case "6":
            	break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }
    
    
    private void createMostPlayedPlaylist() {
    	Song[] songs = model.getMostPlayed();
    	if((songs[0] == null)) {
    		//if no songs have been played, do not create.
    		return;
    	}else {
    		Playlist playlist = model.getPlaylistWithName("Most Played Songs");
    		if(playlist == null) {
    			//create the playlist if it does not exist.
    			model.createPlaylist("Most Played Songs");
    			playlist = model.getPlaylistWithName("Most Played Songs");
    		}else {
    			//delete the playlist and re create it to update it. 
    			model.removePlaylist("Most Played Songs");
    			model.createPlaylist("Most Played Songs");
    			playlist = model.getPlaylistWithName("Most Played Songs");
    		}
    		for(int i = 0; i < songs.length; i++) {
    			if(songs[i] == null) {
    				//when it gets to a song that has no plays, it stops.
    				break;
    			}
    			model.addSongToPlaylist(playlist.getName(), songs[i].getTitle(), songs[i].getArtist());
    		}
    	}
    }
    
    private void displayMostPlayed() {
    	Song[] songs = model.getMostPlayed();
    	if((songs[0] == null)) {
    		System.out.println("You have not played any songs.");
    	}else {
    		System.out.println("Most played songs:");
    		for(int i = 0; i < songs.length; i++) {
    			if(songs[i] == null) {
    				//when it gets to a song that has no plays, it stops. 
    				break;
    			}
    			System.out.println(songs[i].getTitle() + " by " + songs[i].getArtist());
    		}
//    		for(Song song : songs) {
//    			System.out.println(((Song) song).getTitle() + " by " + ((Song) song).getArtist());
//    		}
    	}
    }
    
    private void createRecentlyPlayedPlaylist() {
    	Object[] songs = model.getrecentlyPlayed();
    	if (songs == null) {
    		//does not create the playlist if no songs have been played.
    		return;
    	}else {
    		Playlist playlist = model.getPlaylistWithName("Recently Played Songs");
    		if(playlist == null) {
    			//create the playlist if it does not exist.
    			model.createPlaylist("Recently Played Songs");
    			playlist = model.getPlaylistWithName("Recently Played Songs");
    		}else {
    			//delete the playlist and re create it to update it. 
    			model.removePlaylist("Recently Played Songs");
    			model.createPlaylist("Recently Played Songs");
    			playlist = model.getPlaylistWithName("Recently Played Songs");
    			model.removeAutoPlaylist("Automatic Recently Played: ");
    			model.addAutoPlaylist("Automatic Recently Played: ", playlist);
    		}
    		for(int i = songs.length-1; i >= 0; i--) {
    			 Object song = songs[i];
    			 model.addSongToPlaylist(playlist.getName(), ((Song) song).getTitle(), ((Song) song).getArtist());
    		}
    	}
    }
    
    private void displayRecentlyPlayed() {
    	Object[] songs = model.getrecentlyPlayed();
    	if (songs == null) {
    		System.out.println("You have not played any songs.");
    	}else {
    		System.out.println("All songs that have been recently played:");
    		for(int i = songs.length-1; i >= 0; i--) {
    			 Object song = songs[i];
                 System.out.println(((Song) song).getTitle() + " by " + ((Song) song).getArtist());
    		}
    	}
    }
    
    //Plays a song.
    private void playSong() {
    	System.out.print("Enter the song title: ");
        String title = scanner.nextLine();
        System.out.print("Enter the artist name: ");
        String artist = scanner.nextLine();
        
        Playlist playlist = model.getPlaylistWithName("Most Played Songs");
		if(playlist == null) {
			model.createPlaylist("Most Played Songs");
			playlist = model.getPlaylistWithName("Most Played Songs");
		}
        boolean success = model.playSong(title, artist);
        if (success) {
            // if the song was successfully played, prints a message saying so.
        	System.out.println("Now playing: " + title + ", by " + artist);
        	model.addSongToPlaylist(playlist.getName(), title, artist);
        	//update played song playlists each time we play a song. 
        	createMostPlayedPlaylist();
        	createRecentlyPlayedPlaylist();
        } else {
        	 System.out.println("Failed to play song. The song might not exist in your library.");
           }
    }
    
    private void getSongAlbum(Song song) {
    	 System.out.println("Display the album info of the previous song(s)?");
         System.out.println("1. Yes");
         System.out.println("2. No");
         String choice = scanner.nextLine();
         if(choice.equals("2")) {
        	 //when no, do not display. 
         	return;
         }
    	String title = song.getAlbum();
    	List<Album> results = model.searchStoreAlbumsWithTitle(title);
    	System.out.println("Album info for this song: ");
    	for (int i = 0; i < results.size(); i++) {
            Album album = results.get(i);
            System.out.println(album.getTitle() + " by " + album.getArtist() + " (" + album.getYear() + ") - " + album.getGenre());
            System.out.println("   Songs:");
            List<String> songTitles = album.getSongTitles();
            for (int j = 0; j < songTitles.size(); j++) {
                System.out.println("   " + (j + 1) + ". " + songTitles.get(j));
            }
            System.out.println();
        }
    	if(!model.hasAlbum(title)) {
    		System.out.println("This album is not in your library.");
    	}else {
    		System.out.println("This album is currently in your library.");
    	}
    }
    
    private void searchStoreSongByGenre() {
    	printGenres();
    	System.out.println("Enter the genre to search.");
    	String genre = scanner.nextLine().toLowerCase();
    	List<Song> results = model.searchStoreSongsWithGenre(genre);
    	
    	if(results.isEmpty()) {
    		System.out.println("No songs found under this genre.");
    	}else {
    		System.out.println("Found " + results.size() + " songs: ");
    		String previousAlbum = "";
            for (int i = 0; i < results.size(); i++) {
                Song song = results.get(i);
                // (i+1) is 10 based indexing meaning it starts at 1 instead of 0
                System.out.println((i + 1) + ". " + song.getTitle() + " by " + song.getArtist() + " under genre " + song.getGenre());
                if(!previousAlbum.equals(song.getAlbum())) {
                	//only print the same album info once. 
                	getSongAlbum(song);
                }
                previousAlbum = song.getAlbum();
            }
    	}
    }
    
    private void printGenres() {
    	System.out.println("The genres available are: ");
    	System.out.println("Pop");
    	System.out.println("Alternative");
    	System.out.println("Traditional");
    	System.out.println("Latin");
    	System.out.println("Rock");
    	System.out.println("Singer/Songwriter");
    }
    
    private void searchLibrarySongByGenre() {
    	printGenres();
    	System.out.println("Enter the genre to search.");
    	String genre = scanner.nextLine().toLowerCase();
    	List<Song> results = model.searchLibrarySongsWithGenre(genre);
    	if(results.isEmpty()) {
    		System.out.println("No songs found under this genre.");
    	}
    	else {
    		System.out.println("Found " + results.size() + " songs: ");
    		String previousAlbum = "";
            for (int i = 0; i < results.size(); i++) {
                Song song = results.get(i);
                // (i+1) is 10 based indexing meaning it starts at 1 instead of 0
                System.out.println((i + 1) + ". " + song.getTitle() + " by " + song.getArtist() + " under genre " + song.getGenre());
                if(!previousAlbum.equals(song.getAlbum())) {
                	//only print the same album info once. 
                	getSongAlbum(song);
                }
                previousAlbum = song.getAlbum();
            }
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
            String previousAlbum = "";
            for (int i = 0; i < results.size(); i++) {
                Song song = results.get(i);
                // (i+1) is 10 based indexing meaning it starts at 1 instead of 0
                System.out.println((i + 1) + ". " + song.getTitle() + " by " + song.getArtist() + " from album " + song.getAlbum());
                if(!previousAlbum.equals(song.getAlbum())) {
                	//only print the same album info once. 
                	getSongAlbum(song);
                }
                previousAlbum = song.getAlbum();
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
            String previousAlbum = "";
            for (int i = 0; i < results.size(); i++) {
                Song song = results.get(i);
                System.out.println((i + 1) + ". " + song.getTitle() + " by " + song.getArtist() + " from album " + song.getAlbum());
                if(!previousAlbum.equals(song.getAlbum())) {
                	//only print the same album info once. 
                	getSongAlbum(song);
                }
                previousAlbum = song.getAlbum();
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
            String previousAlbum = "";
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
                if(!previousAlbum.equals(song.getAlbum())) {
                	//only print the same album info once. 
                	getSongAlbum(song);
                }
                previousAlbum = song.getAlbum();
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
            String previousAlbum = "";
            for (int i = 0; i < results.size(); i++) {
                Song song = results.get(i);
                System.out.println((i + 1) + ". " + song.getTitle() + " by " + song.getArtist() + " from album " + song.getAlbum());
                if (song.isFavorite()) {
                    System.out.println("   (Favorite)");
                }
                if (song.getRating() != null) {
                    System.out.println("   Rating: " + song.getRating() + "/5");
                }
                if(!previousAlbum.equals(song.getAlbum())) {
                	//only print the same album info once. 
                	getSongAlbum(song);
                }
                previousAlbum = song.getAlbum();
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
    
    //Displays all songs sorted by title
    private void displaySongsSortedByTitle() {
        List<Song> songs = model.songsSortedByTitle();
        
        if (songs.isEmpty()) {
            System.out.println("Your library has no songs.");
        } else {
            System.out.println("Songs in your library sorted by title: ");
            for (int i = 0; i < songs.size(); i++) {
                Song song = songs.get(i);
                System.out.println((i + 1) + ". " + song.getTitle() + " by " + song.getArtist());
            }
        }
    }


    // Displays all songs sorted by artist
    private void displaySongsSortedByArtist() {
        List<Song> songs = model.songsSortedByArtist();
        
        if (songs.isEmpty()) {
            System.out.println("Your library has no songs.");
        } else {
            System.out.println("Songs in your library sorted by artist: ");
            for (int i = 0; i < songs.size(); i++) {
                Song song = songs.get(i);
                System.out.println((i + 1) + ". " + song.getArtist() + " - " + song.getTitle());
            }
        }
    }


    // Displays all songs sorted by rating
    private void displaySongsSortedByRating() {
        List<Song> songs = model.songsSortedByRating();
        
        if (songs.isEmpty()) {
            System.out.println("Your library has no songs.");
        } else {
            System.out.println("Songs in your library sorted by rating: ");
            for (int i = 0; i < songs.size(); i++) {
                Song song = songs.get(i);
                System.out.println((i + 1) + ". " + song.getTitle() + " by " + song.getArtist() + " - Rating: " + song.getRating() + "/5");
            }
        }
    } 
    
    
    // Method for removing a song from the library
    private void removeSongFromLibrary() {
        // Display all songs in the library first
        List<Song> songs = model.getAllLibrarySongs();
        
        if (songs.isEmpty()) {
            System.out.println("Your library has no songs to remove.");
            return;
        }
        
        System.out.println("\nAll songs in your library:");
        for (int i = 0; i < songs.size(); i++) {
            Song song = songs.get(i);
            System.out.println((i + 1) + ". " + song.getTitle() + " by " + song.getArtist() + " from album " + song.getAlbum());
        }
        
        System.out.print("\nEnter the number of the song you want to remove: ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            
            if (index >= 0 && index < songs.size()) {
                Song selectedSong = songs.get(index);
                String title = selectedSong.getTitle();
                String artist = selectedSong.getArtist();
                
                boolean success = model.removeSongFromLibrary(title, artist);
                
                if (success) {
                    System.out.println("Song '" + title + "' by " + artist + " was successfully removed from your library.");
                } else {
                    System.out.println("Failed to remove the song from your library.");
                }
            } else {
                System.out.println("Invalid song number. Please enter a number between 1 and " + songs.size() + ".");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }
    
    
    // Method for removing an album from the library
 // Method for removing an album from the library
    private void removeAlbumFromLibrary() {
        // Display all albums in the library first
        List<Album> albums = model.getAllLibraryAlbums();
        
        if (albums.isEmpty()) {
            System.out.println("Your library has no albums to remove.");
            return;
        }
        
        System.out.println("\nAll albums in your library:");
        for (int i = 0; i < albums.size(); i++) {
            Album album = albums.get(i);
            System.out.println((i + 1) + ". " + album.getTitle() + " by " + album.getArtist() + " (" + album.getYear() + ") - " + album.getGenre());
        }
        
        System.out.print("\nEnter the number of the album you want to remove: ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            
            if (index >= 0 && index < albums.size()) {
                Album selectedAlbum = albums.get(index);
                String title = selectedAlbum.getTitle();
                String artist = selectedAlbum.getArtist();
                
                // Removed the confirmation prompt
                boolean success = model.removeAlbumFromLibrary(title, artist);
                
                if (success) {
                    System.out.println("Album '" + title + "' by " + artist + " was successfully removed from your library.");
                } else {
                    System.out.println("Failed to remove the album from your library.");
                }
            } else {
                System.out.println("Invalid album number. Please enter a number between 1 and " + albums.size() + ".");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }
    
    
 // Method to open and display any automatic playlist by name
    private void openAutoPlaylist(String playlistName) {
        Playlist playlist = model.getPlaylistWithName(playlistName);
        
        if (playlist == null) {
            System.out.println("Playlist not found.");
            return;
        }
        
        System.out.println(playlist.getName());
        
        List<Song> songs = playlist.getSongs();
        
        if (songs.isEmpty()) {
            System.out.println("This playlist is empty.");
        } else {
            for (int i = 0; i < songs.size(); i++) {
                Song song = songs.get(i);
                System.out.println((i + 1) + ". " + song.getTitle() + " by " + song.getArtist());
                
                // Show additional info for songs
                if (song.isFavorite()) {
                    System.out.println("   (Favorite)");
                }
                if (song.getRating() != null) {
                    System.out.println("   Rating: " + song.getRating() + "/5");
                }
            }
        }
        
        // Pause to allow user to read the playlist content
        System.out.println("\nPress Enter to exit");
        scanner.nextLine();
    }

    // Method to display all genre playlists
    private void viewGenrePlaylists() {
        // Get all auto playlists
        List<Playlist> autoPlaylists = model.getAutoPlaylists();
        
        // Filter only genre playlists
        List<Playlist> genrePlaylists = new ArrayList<>();
        for (Playlist playlist : autoPlaylists) {
            if (playlist.getName().startsWith("Automatic Genre: ")) {
                genrePlaylists.add(playlist);
            }
        }
        
        if (genrePlaylists.isEmpty()) {
            System.out.println("\nThere are no songs in your library. Press enter to exit");
            scanner.nextLine();
            return;
        }
        
        System.out.println("\nGenre Playlists: ");
        for (int i = 0; i < genrePlaylists.size(); i++) {
            Playlist playlist = genrePlaylists.get(i);
            String genreName = playlist.getName().substring("Automatic Genre: ".length());
            System.out.println((i + 1) + ". " + genreName + " (" + playlist.getSongAmt() + " songs)");
        }
        
        System.out.println((genrePlaylists.size() + 1) + ". Back to main menu");
        
        System.out.print("\nEnter your choice: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            
            if (choice >= 1 && choice <= genrePlaylists.size()) {
                Playlist selectedPlaylist = genrePlaylists.get(choice - 1);
                openAutoPlaylist(selectedPlaylist.getName());
            } else if (choice != genrePlaylists.size() + 1) { // Not the "Back" option
                System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }
    
    private void displayShuffledLibrarySongs() {
        List<Song> shuffledSongs = model.getShuffledLibrarySongs();
        
        if (shuffledSongs.isEmpty()) {
            System.out.println("Your library has no songs to shuffle.");
        } else {
            System.out.println("\nYour library songs in shuffled order:");
            for (int i = 0; i < shuffledSongs.size(); i++) {
                Song song = shuffledSongs.get(i);
                System.out.println((i + 1) + ". " + song.getTitle() + " by " + song.getArtist());
            }
            
            // Wait for user to press enter before returning to menu
            System.out.println("\nPress Enter to return to main menu");
            scanner.nextLine();
        }
    }

    
    private void shuffleAndDisplayPlaylist() {
        // Display available playlists
        List<Playlist> playlists = model.getAllPlaylists();
        
        if (playlists.isEmpty()) {
            System.out.println("You have no playlists to shuffle.");
            return;
        }
        
        System.out.println("\nAvailable playlists:");
        for (int i = 0; i < playlists.size(); i++) {
            Playlist playlist = playlists.get(i);
            System.out.println((i + 1) + ". " + playlist.getName() + " (" + playlist.getSongAmt() + " songs)");
        }
        
        System.out.print("\nEnter the number of the playlist to shuffle (or 0 to cancel): ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            
            if (index == -1) {
                return; // User canceled
            }
            
            if (index >= 0 && index < playlists.size()) {
                Playlist selectedPlaylist = playlists.get(index);
                
                // Shuffle the playlist
                Playlist shuffledPlaylist = model.shufflePlaylist(selectedPlaylist.getName());
                
                if (shuffledPlaylist == null || shuffledPlaylist.getSongs().isEmpty()) {
                    System.out.println("The selected playlist is empty or could not be shuffled.");
                } else {
                    System.out.println("\nPlaylist '" + shuffledPlaylist.getName() + "' in shuffled order:");
                    List<Song> songs = shuffledPlaylist.getSongs();
                    for (int i = 0; i < songs.size(); i++) {
                        Song song = songs.get(i);
                        System.out.println((i + 1) + ". " + song.getTitle() + " by " + song.getArtist());
                    }
                    
                    // Wait for user to press enter before returning to menu
                    System.out.println("\nPress Enter to return to main menu");
                    scanner.nextLine();
                }
            } else {
                System.out.println("Invalid playlist number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

  
}