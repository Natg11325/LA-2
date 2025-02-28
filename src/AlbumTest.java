//package src;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class AlbumTest {

	@Test
    public void testConstructor() {
        Album album = new Album("19", "Adele", "Pop", 2008);
        // Test that the constructor properly sets the fields
        assertEquals("19", album.getTitle());
        assertEquals("Adele", album.getArtist());
        assertEquals("Pop", album.getGenre());
        assertEquals(2008, album.getYear());
        assertEquals(0, album.getNumOfSongs());
    }
     
    @Test
    public void testGetTitle() {
        Album album = new Album("19", "Adele", "Pop", 2008);
        assertEquals("19", album.getTitle());
    }
    
    @Test
    public void testGetArtist() {
        Album album = new Album("19", "Adele", "Pop", 2008);
        assertEquals("Adele", album.getArtist());
    }
    
    @Test
    public void testGetGenre() {
        Album album = new Album("19", "Adele", "Pop", 2008);
        assertEquals("Pop", album.getGenre());
    }
    
    @Test
    public void testGetYear() {
        Album album = new Album("19", "Adele", "Pop", 2008);
        assertEquals(2008, album.getYear());
    }
    
    @Test
    public void testAddSongTitle() {
        Album album = new Album("19", "Adele", "Pop", 2008);
        // Add a single song
        album.addSongTitle("Daydreamer");
        assertEquals(1, album.getNumOfSongs());
        
        // Add multiple songs
        album.addSongTitle("Best for Last");
        album.addSongTitle("Chasing Pavements");
        assertEquals(3, album.getNumOfSongs());
    }
    
    @Test
    public void testGetSongTitles() {
        Album album = new Album("19", "Adele", "Pop", 2008);
        // Test with empty song list
        List<String> emptySongs = album.getSongTitles();
        assertTrue(emptySongs.isEmpty());
        
        // Add songs and verify the list
        album.addSongTitle("Daydreamer");
        album.addSongTitle("Best for Last");
        
        List<String> songTitles = album.getSongTitles();
        assertEquals(2, songTitles.size());
        assertEquals("Daydreamer", songTitles.get(0));
        assertEquals("Best for Last", songTitles.get(1));
        
        // Verify that getSongTitles returns a defensive copy
        songTitles.add("THE OG SHOULD BE UNTOUCHED");
        assertEquals(2, album.getNumOfSongs());
    }
    
    @Test
    public void testGetNumOfSongs() {
        Album album = new Album("19", "Adele", "Pop", 2008);
        // Test with empty song list
        assertEquals(0, album.getNumOfSongs());
        
        // Add songs and verify count
        album.addSongTitle("Daydreamer");
        assertEquals(1, album.getNumOfSongs());
        
        album.addSongTitle("Best for Last");
        assertEquals(2, album.getNumOfSongs());
    }
    
    @Test
    public void testToString() {
        Album album = new Album("19", "Adele", "Pop", 2008);
        String expected = "Album: 19, Adele, Pop, 2008";
        assertEquals(expected, album.toString());
    }
    
    @Test
    public void testMultipleAlbums() {
        // Test the behavior of multiple Albums
        Album album = new Album("19", "Adele", "Pop", 2008);
        Album album2 = new Album("Coat of Many Colors", "Dolly Parton", "Traditional Country", 1971);
        
        // Verify album2 properties
        assertEquals("Coat of Many Colors", album2.getTitle());
        assertEquals("Dolly Parton", album2.getArtist());
        assertEquals("Traditional Country", album2.getGenre());
        assertEquals(1971, album2.getYear());
        
        // Add songs to both albums and verify they're independent
        album.addSongTitle("Daydreamer");
        album2.addSongTitle("Traveling Man");
        album2.addSongTitle("My Blue Tears");
        
        assertEquals(1, album.getNumOfSongs());
        assertEquals(2, album2.getNumOfSongs());
        
        // Verify toString for album2
        String expected = "Album: Coat of Many Colors, Dolly Parton, Traditional Country, 1971";
        assertEquals(expected, album2.toString());
    }
}
