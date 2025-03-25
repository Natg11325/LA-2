//package src;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class ManageAccounts {
	// HashMap to store users with username as key and User object as value
    private Map<String, Account> accounts;
    // Constant file name for storing user info
    private final String ACCOUNT_INFO = "accounts.txt";
    private final String LIBRARY_INFO_DIRECTORY = "accounts_libraries/";
    private MusicStore musicStore;
    
    
    public ManageAccounts(MusicStore musicStore) {
        this.accounts = new HashMap<>();
        this.musicStore = musicStore;
        
        // Created File object for the directory
        File directory = new File(LIBRARY_INFO_DIRECTORY);
        if (!directory.exists()) {
        	directory.mkdir(); //this creates the directory
        }
        
        // this is called cause when the program starts it needs to ensure the current user can log in 
        loadAccounts();
    }
    
    // Generate a salt
    private String generateSalt() {
    	// Initialize the random number generator
        Random randomNum = new Random();
        StringBuilder salt = new StringBuilder();
        for (int i = 0; i < 10; i++) {
        	// All h the possible ASCII characters that can be printed
            salt.append((char)(randomNum.nextInt(94) + 33));
        }
        // Convert StringBuilder to a String and return
        return salt.toString();
    }
    

    private String hashPassword(String password, String salt) {
        String newPassword = password + salt;
        int hash = 7;
        for (int i = 0; i < newPassword.length(); i++) {
            hash = hash * 31 + newPassword.charAt(i);
        }
        return String.valueOf(hash);
    }
    
    // Make a new account
    public boolean makeAccount(String username, String password) throws FileNotFoundException {
        // Check if username already exists
        if (accounts.containsKey(username)) {
            return false;
        }
        
        // Generate salt and hash password
        String salt = generateSalt();
        String passwordHash = hashPassword(password, salt);
        
        // Create new library for the users account
        LibraryModel library = new LibraryModel(musicStore);
        
        // Create user and add to the hash map
        Account account = new Account(username, passwordHash, library, salt);
        // Store user in HashMap with username as the key and the Account object as the value
        accounts.put(username, account);
        
        // Save the account data to the file!!!
        saveAccounts();
        // Return true to indicate successful registration
        return true;
    }
    
    // Login verification process
    public Account authenticateUser(String username, String password) {
    	// Try to get Account object for the username
    	// If username doesn't exist return null (authentication failed)
    	Account account = accounts.get(username);
        if (account == null) {
            return null;
        }
        
        // If an account is found, take the input password and the salt for that account and create a hash of the two combined
        String passwordHash = hashPassword(password, account.getSalt());
        // Compare the hash just created with the hash stored in the account, if the match its verified
        if (passwordHash.equals(account.getPassword())) {
        	// Return Account object if hashes match
            return account;
        }
        return null;
    }
    
    
    // Save account data to the file WHEN CHNAGES ARE MADE LIKE A NEW ACCOUNT
    // makes sure nothing is lost when the program closes
    public void saveAccounts() {
        PrintWriter writer = null;
        
        // Create File object for the account data
        File accountFile = new File(ACCOUNT_INFO);
        // (needs try catch blocks cause it deals with file operations)
        try {
            writer = new PrintWriter(accountFile);
            
            // Iterate through all accounts in the HashMap
            for (Account account : accounts.values()) {
            	 // Write the account info (username, password hash, and salt) into the file
                writer.println(account.getUsername() + "," + account.getPassword() + "," + account.getSalt());
                
                // Save this accounts library, this is in the loop cause each account needs its library saved even if its empty
                saveAccountLibrary(account);
            }
        } 
        catch (FileNotFoundException e) {
        	// Handles file creation error
            System.out.println("Error - can't write to account data to file.");
        } 
        // checks that the PrintWriter was actually created before closing it
        if (writer != null) {
        	writer.close();
        	}
        }
    
    
    // Load users from file to the accounts Hash map
    private void loadAccounts() {
        File file = new File(ACCOUNT_INFO);
        if (!file.exists()) {
            return;
        }
        
        Scanner scanner = null;
        
        try {
            scanner = new Scanner(file);
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String username = parts[0]; // First part is username
                    String passwordHash = parts[1]; // Second part is password hash
                    String salt = parts[2]; // Third part is salt
                    
                    // for each account it calls loadAccountLibrary and populates a LibraryModel with taht accounts music library info from the file
                    LibraryModel library = loadAccountLibrary(username);
                    // If a library isn't found, it creates a new library
                    if (library == null) {
                        library = new LibraryModel(musicStore);
                    }
                    
                    // Create Account object and add to HashMap
                    Account account = new Account(username, passwordHash, library, salt);
                    accounts.put(username, account);
                }
            }
        } 
        catch (FileNotFoundException e) {
            System.out.println("Error - Account data file not found.");
        } 
        if (scanner != null) {
        	scanner.close();
        	}
        }
    
    
    // Save a user's library data to their individal library file so nothing is lost when the program clsoes
    private void saveAccountLibrary(Account account) {
        String filename = LIBRARY_INFO_DIRECTORY + account.getUsername() + "_library.txt";
        // Get user's library
        LibraryModel library = account.getLibrary();
        
        PrintWriter writer = null;
        
        try {
            writer = new PrintWriter(new File(filename));
            
            // Writes the users songs from their library
            writer.println("Songs:");
            // Write song details: title, artist, favorite status, rating
            for (Song song : library.getAllLibrarySongs()) {
                String rating = "null";
                if (song.getRating() != null) {
                    rating = song.getRating().toString();
                }
                writer.println(song.getTitle() + "," + song.getArtist() + "," + song.isFavorite() + "," + rating);
            }
            
            // Writes the users albums from their library
            writer.println("Albums:");
            // Write album details: title, artist
            for (Album album : library.getAllLibraryAlbums()) {
                writer.println(album.getTitle() + "," + album.getArtist());
            }
            
            // Writes the users playlists from their library
            writer.println("Playlist:");
            for (Playlist playlist : library.getAllPlaylists()) {
                writer.println("Playlist:" + playlist.getName());
                // Write each song in playlist: title, artist
                for (Song song : playlist.getSongs()) {
                    writer.println(song.getTitle() + "," + song.getArtist());
                }
                // so you know when a playlist ends and another begins
                writer.println("End Playlist");
            }
        } 
        catch (FileNotFoundException e) {
            System.out.println("Error - Cant write to library file for account ");
        } 
        if (writer != null) {
        	writer.close();
        	}
        }
    
    
    // Load a user's library data to a new LibraryModel object that is created for each account
    private LibraryModel loadAccountLibrary(String username) {
    	// chekcs for a library file for a specific username
        String filename = LIBRARY_INFO_DIRECTORY + username + "_library.txt";
        File file = new File(filename);
        if (!file.exists()) {
            return null;
        }
        
        // Create new library
        LibraryModel library = new LibraryModel(musicStore);
        Scanner scanner = null;
        
        try {
            scanner = new Scanner(file);
            
            // Current section being processed (SONGS, ALBUMS, PLAYLISTS)
            String section = "";
            // tracks which playlist your currently adding songs to
            String currentPlaylist = null;
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                
                // tracks which section its on so it knows which part of the library it's reading
                if (line.equals("Songs:") || line.equals("Albums:") || line.equals("Playlists:")) {
                    section = line;
                } else if (line.startsWith("Playlist:")) {
                	// If line starts with "Playlist:", it's the beginning of a playlist
                    // Extract the playlist name (everything after "Playlist:")
                    currentPlaylist = line.substring(9);
                    library.createPlaylist(currentPlaylist);
                } else if (line.equals("End Playlist")) {
                	// If line is "End Playlist", we're done with the current playlist
                    currentPlaylist = null;
                } else {
                	// If not a section header, process the line as data
                    String[] parts = line.split(",");

                    if (section.equals("Songs:") && parts.length >= 4) {
                        String title = parts[0];
                        String artist = parts[1];
                        // Converts the string "true" or "false" to a boolean value
                        boolean isFavorite = Boolean.parseBoolean(parts[2]);
                        String ratingStr = parts[3];
                        
                        // Populate library with song
                        library.addSongToLibrary(title, artist);

                        // If the song was marked as favorite, mark it as favorite in the library
                        if (isFavorite) {
                            library.markSongAsFav(title, artist);
                        }

                        // If the rating is not "null", parse it as an integer and set the song's rating
                        if (!ratingStr.equals("null")) {
                            int rating = Integer.parseInt(ratingStr);
                            library.rateSong(title, artist, rating);
                        }
                         
                    } else if (section.equals("Albums:") && parts.length >= 2) {
                        String title = parts[0];
                        String artist = parts[1];

                        library.addAlbumToLibrary(title, artist);
                        
                    } else if (currentPlaylist != null && parts.length >= 2) {
                        String title = parts[0];
                        String artist = parts[1];

                        library.addSongToPlaylist(currentPlaylist, title, artist);
                    }
                }
            }
        } 
        catch (FileNotFoundException e) {
            System.out.println("Error - Library file not found for account ");
            return null;
        }
        if (scanner != null) {
        	scanner.close();
            }
        
        return library;
    }
}
