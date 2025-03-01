package src;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SongTest {

    @Test
    public void testConstructorAndGetters() {
        Song song = new Song("19", "Adele", "Daydreamer", "Pop", 2008);
        
        assertEquals("19", song.getTitle());
        assertEquals("Adele", song.getArtist());
        assertEquals("Daydreamer", song.getAlbum());
        assertEquals("Pop", song.getGenre());
        assertEquals(2008, song.getYear());
        // Default value should be false
        assertFalse(song.isFavorite());
        // Default value should be null
        assertNull(song.getRating());
    }
    
    @Test
    public void testSetFavorite() {
        Song song = new Song("19", "Adele", "Best for Last", "Pop", 2008);
        
        song.setFavorite(true);
        assertTrue(song.isFavorite());
        
        song.setFavorite(false);
        assertFalse(song.isFavorite());
    }
     
    @Test
    public void testSetRatingValid() {
        Song song = new Song("19", "Adele", "Chasing Pavements", "Pop", 2008);
        
        song.setRating(4);
        assertEquals(Integer.valueOf(4), song.getRating());
        // Since rating is 4 it shouldn't set favorite to true
        assertFalse(song.isFavorite()); 
    }
    
    @Test
    public void testSetRatingToFive() {
        Song song = new Song("19", "Adele", "Chasing Pavements", "Pop", 2008);
        
        // Test that rating 5 automatically sets favorite to true
        song.setRating(5);
        assertEquals(Integer.valueOf(5), song.getRating());
        assertTrue(song.isFavorite());
        
        // Test that favorite stays true even if we set it to false after a rating of 5
        song.setFavorite(false);
        assertFalse(song.isFavorite());
    }
    
    @Test
    public void testSetRatingInvalid() {
        Song song = new Song("19", "Adele", "Chasing Pavements", "Pop", 2008);
        
        // Test setting an invalid rating like a number less than 1
        song.setRating(0);
        assertNull(song.getRating());
        
        // Test setting an invalid rating like a number greater than 5
        song.setRating(6);
        assertNull(song.getRating());
        
        // Test setting a null rating by first setting a valid rating
        song.setRating(3);
        assertEquals(Integer.valueOf(3), song.getRating());
        // Then set it to null. It should keep the old value
        song.setRating(null);
        assertEquals(Integer.valueOf(3), song.getRating());
    }
    
    @Test
    public void testToString() {
        Song song = new Song("Chasing Pavements", "Adele", "19", "Pop", 2008);
 
        String expected = "Chasing Pavements by Adele from album 19";
        assertEquals(expected, song.toString());

    }
    
    
    @Test
    public void testSetRatingWithExistingFavorite() {
        Song song = new Song("19", "Adele", "Chasing Pavements", "Pop", 2008);
        
        // First set the song as favorite
        song.setFavorite(true);
        assertTrue(song.isFavorite());
        
        // Now set a rating less than 5
        song.setRating(3);
        
        // Verify that the rating was set and the favorite status remained true
        assertEquals(Integer.valueOf(3), song.getRating());
        assertTrue(song.isFavorite());
    }
    
    @Test
    public void test_equals() {
    	Song song = new Song("Chasing Pavements", "Adele", "19", "Pop", 2008);
    	Song song2 = new Song("Daydreamer", "Adele", "19", "Pop", 2008);
    	assertFalse(song.equals(song2));
    	
    	Song song3 = new Song("Chasing Pavements", "Adele", "19", "Pop", 2008);
    	assertEquals(song, song3);
    }
}
