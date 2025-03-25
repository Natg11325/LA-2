import static org.junit.Assert.*;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner; 
 
public class ManageAccountsTest {
    
    @Test
    public void testMakeAccount() throws FileNotFoundException {
        MusicStore musicStore = new MusicStore();
        ManageAccounts manageAccounts = new ManageAccounts(musicStore);
        
        try {
            boolean result = manageAccounts.makeAccount("testAccount", "somePassword");
            assertTrue(result);
            
            // tests that authentication works
            Account account = manageAccounts.authenticateUser("testAccount", "somePassword");
            assertNotNull(account);
            assertEquals("testAccount", account.getUsername());
        } finally {
        	// this is necessary for clean up after each test case, cause it makes sure whatever is done to the file in this test case doesn't affect the others
        	deleteFiles();
        }
    }
    
    @Test
    public void testMakeingAccountWithDuplicateUsernames() throws FileNotFoundException {
        MusicStore musicStore = new MusicStore();
        ManageAccounts manageAccounts = new ManageAccounts(musicStore);
        
        try {
            // Create first account
            boolean firstAccount = manageAccounts.makeAccount("testAccount", "somePassword");
            
            // Try creating duplicate account
            boolean secondAccount = manageAccounts.makeAccount("testAccount", "somePassword1");
            
            assertTrue(firstAccount);
            assertFalse(secondAccount);
        } finally {
            deleteFiles();
        } 
    }
    
    @Test
    public void testAuthenticateUser() throws FileNotFoundException {
        MusicStore musicStore = new MusicStore();
        ManageAccounts manageAccounts = new ManageAccounts(musicStore);
        
        try {
            manageAccounts.makeAccount("testAccount", "somePassword");
            
            Account account = manageAccounts.authenticateUser("testAccount", "somePassword");
            
            assertNotNull(account);
            assertEquals("testAccount", account.getUsername());
        } finally {
            deleteFiles();
        }
    }
    
    @Test
    public void testAuthenticateUserWithWrongPassword() throws FileNotFoundException {
        MusicStore musicStore = new MusicStore();
        ManageAccounts manageAccounts = new ManageAccounts(musicStore);
        
        try {
            // Create account
            manageAccounts.makeAccount("testAccount", "somePassword");
            
            // Try with wrong password
            Account account = manageAccounts.authenticateUser("testAccount", "wrongPassword");
            
            assertNull(account);
        } finally {
            deleteFiles();
        }
    }
    
    @Test
    public void testAuthenticateUserWithANonexistentUser() throws FileNotFoundException {
        MusicStore musicStore = new MusicStore();
        ManageAccounts manageAccounts = new ManageAccounts(musicStore);
        
        try {
            // Try authenticating non-existent user
            Account account = manageAccounts.authenticateUser("nonExistentAccount", "somePassword");
            
            assertNull(account);
        } finally {
            deleteFiles();
        }
    }
    
    @Test
    public void testSaveAndLoadAccounts() throws FileNotFoundException {
        MusicStore musicStore = new MusicStore();
        
        try {
            // creates a new account and should save it to the file
            ManageAccounts manageAccounts1 = new ManageAccounts(musicStore);
            manageAccounts1.makeAccount("test", "somePassword");
            
            // The ManageAccounts class should write the account data to the accounts.txt file
            ManageAccounts manageAccounts2 = new ManageAccounts(musicStore);
            
            // Test authentication with second instance
            Account loadedAccount = manageAccounts2.authenticateUser("test", "somePassword");
            
            // makes sure the account exists and then checks the username
            assertNotNull(loadedAccount);
            assertEquals("saveLoadTest", loadedAccount.getUsername());
        } finally {
            deleteFiles();
        }
    }
    
    @Test
    public void testLoadAccountsWithNonExistentFile() throws FileNotFoundException {
        try {
            // Delete the accounts file if it exists
            File accountFile = new File("accounts.txt");
            if (accountFile.exists()) {
                accountFile.delete();
            }
            
            // Setup with a nonexistent file
            MusicStore musicStore = new MusicStore();
            ManageAccounts manageAccounts = new ManageAccounts(musicStore);
            
            // Try authentication on it
            Account account = manageAccounts.authenticateUser("testUser", "somePassword");
            
            assertNull(account);
        } finally {
            deleteFiles();
        }
    }
    
    @Test
    public void testLibraryDirectoryCreation() throws FileNotFoundException {
        try {
            // Delete the directory if it exists
            File directory = new File("accounts_libraries");
            if (directory.exists()) {
                deleteDirectory(directory);
            }
            
            // create a directory
            MusicStore musicStore = new MusicStore();
            new ManageAccounts(musicStore);
            
            assertTrue(directory.exists());
        } finally {
            deleteFiles();
        }
    }
    
    @Test
    public void testTheWrongFileFormat() {
        try {
            File accountFile = new File("accounts.txt");
            FileWriter writer = new FileWriter(accountFile);
            // write in a username thats missing parts and completely in the wrong format
            writer.write("invalidUsername\n");
            writer.close();
            
            MusicStore musicStore = new MusicStore();
            ManageAccounts manageAccounts = new ManageAccounts(musicStore);
            
            // Try authentication on it
            Account account = manageAccounts.authenticateUser("invalidUsername", "somePassword");
            
            assertNull(account);
        } catch (IOException e) {
            fail("Shouldn't throw an exception");
        } finally {
            deleteFiles();
        }
    }
    
    
    @Test
    public void testLoadingAccountLibraryWithSongs() {
        try {
            // Create an account in accounts.txt
            createAccountInFile("libraryAccount", "hash1", "salt1");
            
            // Create a directory 
            File directory = new File("accounts_libraries");
            if (!directory.exists()) {
                directory.mkdir();
            }
            
            // Create a library file with some songs
            String libraryPath = "accounts_libraries/libraryAccount_library.txt";
            FileWriter writer = new FileWriter(libraryPath);
            writer.write("Songs:\n");
            writer.write("Tired,Adele,true,5\n");
            writer.write("Daydreamer,Adele,false,null\n");
            writer.write("Albums:\n");
            writer.close();
            
            MusicStore musicStore = new MusicStore();
            ManageAccounts manageAccounts = new ManageAccounts(musicStore);
            
            // This should load the library with the songs
            Account account = manageAccounts.authenticateUser("libraryAccount", "somePassword");
            
            // if theres no exception the test passed
            assertNotNull(account);
        } catch (IOException e) {
            fail("Should not throw exception: " + e.getMessage());
        } finally {
            deleteFiles();
        }
    }
    
    @Test
    public void testLoadAccountLibraryWithAlbums() {
        try {
            createAccountInFile("albumAccount", "hash1", "salt1");
            
            // Create directory if not exists
            File directory = new File("accounts_libraries");
            if (!directory.exists()) {
                directory.mkdir();
            }
            
            // Create library file with albums
            String libraryPath = "accounts_libraries/albumAccount_library.txt";
            FileWriter writer = new FileWriter(libraryPath);
            writer.write("Songs:\n");
            writer.write("Albums:\n");
            writer.write("19,Adele\n");
            writer.write("Sons,The Heavy\n");
            writer.write("Playlist:\n");
            writer.close();
            
            MusicStore musicStore = new MusicStore();
            ManageAccounts manageAccounts = new ManageAccounts(musicStore);
            
            Account account = manageAccounts.authenticateUser("albumAccount", "somePassword");
            
            assertNotNull(account);
        } catch (IOException e) {
            fail("Should not throw exception: " + e.getMessage());
        } finally {
            // Cleanup
            deleteFiles();
        }
    }
    
    @Test
    public void testLoadAccountLibraryWithPlaylists() throws IOException {
        try {
            createAccountInFile("playlistAccount", "hash1", "salt1");
            
            // Create directory if not exists
            File directory = new File("accounts_libraries");
            if (!directory.exists()) {
                directory.mkdir();
            }
            
            // Create library file with playlists
            String libraryPath = "accounts_libraries/playlistAccount_library.txt";
            FileWriter writer = new FileWriter(libraryPath);
            writer.write("Songs:\n");
            writer.write("Albums:\n");
            writer.write("Playlist:\n");
            writer.write("Playlist:MyPlaylist1\n");
            writer.write("Tired,Adele\n");
            writer.write("Daydreamer,Adele\n");
            writer.write("End Playlist\n");
            writer.write("Playlist:MyPlaylist2\n");
            writer.write("Someone Like You,Adele\n");
            writer.write("End Playlist\n");
            writer.close();
            
            MusicStore musicStore = new MusicStore();
            ManageAccounts manageAccounts = new ManageAccounts(musicStore);
            
            Account account = manageAccounts.authenticateUser("playlistAccount", "somePassword");
            
            assertNotNull(account);
        } finally {
            // Cleanup
            deleteFiles();
        }
    }
    
    @Test
    public void testLoadAccountLibrarySomeRandomData() throws IOException {
        try {
            createAccountInFile("playlistAccount", "hash1", "salt1");
            
            File directory = new File("accounts_libraries");
            if (!directory.exists()) {
                directory.mkdir();
            }
            
            // Created library file with a playlist but included data that's not part of any section
            String libraryPath = "accounts_libraries/playlistAccount_library.txt";
            FileWriter writer = new FileWriter(libraryPath);
            writer.write("Songs:\n");
            writer.write("Albums:\n");
            writer.write("Playlist:\n");
            
            writer.write("Playlist:myPlaylist\n");
            writer.write("Tired,Adele\n");
            writer.write("Daydreamer,Adele\n");
            writer.write("End Playlist\n");
            
            writer.write("random,someArtist1\n");
            writer.write("random,someArtist2\n");
            
            writer.close();
            
            MusicStore musicStore = new MusicStore();
            ManageAccounts manageAccounts = new ManageAccounts(musicStore);
            
            Account account = manageAccounts.authenticateUser("playlistAccount", "somePassword");
            
            assertNotNull(account);
        } finally {
            deleteFiles();
        }
    }
    
    @Test
    public void testLoadAccountLibraryWithNoLibraryFile() throws IOException {
        try {
            createAccountInFile("noLibraryFile", "hash1", "salt1");
            
            MusicStore musicStore = new MusicStore();
            ManageAccounts manageAccounts = new ManageAccounts(musicStore);
            
            // This should create a new empty library for the user
            Account account = manageAccounts.authenticateUser("noLibraryFile", "somePassword");
            
            assertNotNull(account);
            assertNotNull(account.getLibrary());

        } finally {
            deleteFiles();
        }
    }
    
    @Test
    public void testLoadAccountLibraryWithFileNotFoundException() throws IOException {
        try {
            createAccountInFile("fileNotFoundUser", "hash1", "salt1");
            
            File directory = new File("accounts_libraries");
            if (!directory.exists()) {
                directory.mkdir();
            }
            
            // Create the library file path but make it non-readable so that the file not found
            String libraryPath = "accounts_libraries/fileNotFoundUser_library.txt";
            File libraryFile = new File(libraryPath);
            
            // creates the file
            FileWriter writer = new FileWriter(libraryFile);
            writer.write("Songs:\n");
            writer.close();
            
            // makes it non-readable
            libraryFile.setReadable(false);
            
            MusicStore musicStore = new MusicStore();
            ManageAccounts manageAccounts = new ManageAccounts(musicStore);
            
            // This should cause the FileNotFoundException
            Account account = manageAccounts.authenticateUser("fileNotFoundUser", "somePassword");
            
            // Even when the file is not found the account should exist
            assertNotNull(account);
            assertNotNull(account.getLibrary());
            
            // Make file readable again just in case
            libraryFile.setReadable(true);
            
        } finally {
            deleteFiles();
        }
    }
    
    @Test
    public void testSavingSongsWithDiffRatings() throws IOException {
        try {
            MusicStore musicStore = new MusicStore();
            ManageAccounts manageAccounts = new ManageAccounts(musicStore);
            manageAccounts.makeAccount("testAccount", "somePassword");
            
            // Get the account
            Account account = manageAccounts.authenticateUser("testAccount", "somePassword");
            assertNotNull(account);
            
            String libraryPath = "accounts_libraries/testAccount_library.txt";
            FileWriter writer = new FileWriter(libraryPath);
            writer.write("Songs:\n");
            writer.write("Tired,Adele,true,5\n");
            writer.write("Daydreamer,Adele,false,null\n");
            writer.write("Albums:\n");
            writer.write("19,Adele\n");
            writer.write("Playlist:\n");
            writer.close();
            
            manageAccounts = new ManageAccounts(musicStore);
            account = manageAccounts.authenticateUser("testAccount", "somePassword");
            assertNotNull(account);
            
            manageAccounts.saveAccounts();
            
            // Verify that the library file was saved correctly
            File libraryFile = new File(libraryPath);
            assertTrue(libraryFile.exists());
            
            // Read all of the saved file to make sure everything shows up in it correctly
            boolean songsHeaderFound = false;
            boolean songWithRatingFound = false;
            boolean songWithNullRatingFound = false;
            boolean albumsHeaderFound = false;
            boolean albumFound = false;
            
            Scanner scanner = new Scanner(libraryFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.equals("Songs:")) {
                    songsHeaderFound = true;
                } else if (line.contains("Tired,Adele,true,5")) {
                    songWithRatingFound = true;
                } else if (line.contains("Daydreamer,Adele,false,null")) {
                    songWithNullRatingFound = true;
                } else if (line.equals("Albums:")) {
                    albumsHeaderFound = true;
                } else if (line.contains("19,Adele")) {
                    albumFound = true;
                }
            }
            scanner.close();
            
            assertTrue(songsHeaderFound);
            assertTrue(songWithRatingFound);
            assertTrue(songWithNullRatingFound);
            assertTrue(albumsHeaderFound);
            assertTrue(albumFound);
            
        } finally {
            deleteFiles();
        }
    }
    
    @Test
    public void testSaveAccountLibraryWithPlaylists() throws IOException {
        MusicStore musicStore = new MusicStore();
        ManageAccounts manageAccounts = new ManageAccounts(musicStore);
        
        try {
            manageAccounts.makeAccount("testAccount", "somePassword");
            
            // Create a controlled library format in the account's library file
            String libraryPath = "accounts_libraries/testAccount_library.txt";
            FileWriter writer = new FileWriter(libraryPath);
            writer.write("Songs:\n");
            writer.write("Albums:\n");
            writer.write("Playlist:\n");
            writer.write("Playlist:myPlaylist\n");
            writer.write("Tired,Adele\n");
            writer.write("End Playlist\n");
            writer.close();
            
            Account account = manageAccounts.authenticateUser("testAccount", "somePassword");
            assertNotNull(account);

            manageAccounts.saveAccounts();
            
            File libraryFile = new File(libraryPath);
            assertTrue(libraryFile.exists());
            
            boolean foundPlaylist = false;
            boolean foundPlaylistSong = false;
            boolean foundEndPlaylist = false;
             
            Scanner scanner = new Scanner(libraryFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("Playlist:myPlaylist")) {
                    foundPlaylist = true;
                } else if (foundPlaylist && line.contains("Tired,Adele")) {
                    foundPlaylistSong = true;
                } else if (foundPlaylist && line.equals("End Playlist")) {
                    foundEndPlaylist = true;
                }
            }
            scanner.close();
            
            assertTrue(foundPlaylist);
            assertTrue(foundPlaylistSong);
            assertTrue(foundEndPlaylist);
            
        } finally {
            deleteFiles();
        }
    }
    
    @Test
    public void testSaveAccountLibraryWithFileError() throws FileNotFoundException {
        MusicStore musicStore = new MusicStore();
        ManageAccounts manageAccounts = new ManageAccounts(musicStore);
        
        try {
            manageAccounts.makeAccount("errorTest", "somePassword");
            Account account = manageAccounts.authenticateUser("errorTest", "somePassword");
            assertNotNull(account);
            
            // Delete the library directory to cause an error when trying to save to it
            File directory = new File("accounts_libraries");
            if (directory.exists()) {
                deleteDirectory(directory);
            }
            
            try {
            	// this'll throw an error
                manageAccounts.saveAccounts();
                
                assertTrue(true);
            } finally {                
                // Recreate the directory for cleanup
                if (!directory.exists()) {
                    directory.mkdir();
                }
            }
        } finally {
            deleteFiles();
        }
    }
    

    private void deleteFiles() {
        File accountFile = new File("accounts.txt");
        if (accountFile.exists()) {
            accountFile.delete();
        }
        
        File directory = new File("accounts_libraries");
        if (directory.exists()) {
            deleteDirectory(directory);
        }
    }
     
    private void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                // Makes sure the file is readable and writable before deleting
                file.setReadable(true);
                file.setWritable(true);
                file.delete();
            }
        }
        directory.delete();
    }
    
    private void createAccountInFile(String username, String passwordHash, String salt) throws IOException {
        File accountFile = new File("accounts.txt");
        FileWriter writer = new FileWriter(accountFile);
        writer.write(username + "," + passwordHash + "," + salt + "\n");
        writer.close();
    }
}