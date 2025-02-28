import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class MusicStoreTest {
    
    @Test
    public void testMusicStore() throws FileNotFoundException {
        // Create the music store that will read existing files
        MusicStore musicStore = new MusicStore();
        
        // Verify initialization was successful
        assertNotNull(musicStore);
        
        // Verify albums were loaded
        List<Album> albums = musicStore.getAllAlbums();
        assertFalse(albums.isEmpty());
        
        // Verify songs were loaded
        List<Song> songs = musicStore.getAllSongs();
        assertFalse(songs.isEmpty());
    }
    

    @Test
    public void testSearchSongsWithTitle() throws FileNotFoundException {
        MusicStore musicStore = new MusicStore();
        
        // Only get the first song to use its title for the search
        List<Song> allSongs = musicStore.getAllSongs();
        assertFalse(allSongs.isEmpty());
        String searchTitle = allSongs.get(0).getTitle();
        
        // Test the exact match with the search title
        List<Song> songs = musicStore.searchSongsWithTitle(searchTitle);
        assertFalse(songs.isEmpty(), searchTitle);
        assertEquals(searchTitle, songs.get(0).getTitle()); 
    }
    
    
    @Test
    public void testSearchSongsWithArtist() throws FileNotFoundException {
        MusicStore musicStore = new MusicStore();
        
        // Get the first song to use its artist for search
        List<Song> allSongs = musicStore.getAllSongs();
        assertFalse(allSongs.isEmpty());
        String searchArtist = allSongs.get(0).getArtist();
         
        // Test the exact match with search artist
        List<Song> songs = musicStore.searchSongsWithArtist(searchArtist);
        assertFalse(songs.isEmpty());
        assertEquals(searchArtist, songs.get(0).getArtist());
         
    }
    
    @Test
    public void testSearchAlbumsWithTitle() throws FileNotFoundException {
        MusicStore musicStore = new MusicStore();
        
        // Get the first album to use its title for search
        List<Album> allAlbums = musicStore.getAllAlbums();
        assertFalse(allAlbums.isEmpty());
        String searchTitle = allAlbums.get(0).getTitle();
        
        // Test the exact match with search title
        List<Album> albums = musicStore.searchAlbumsWithTitle(searchTitle);
        assertFalse(albums.isEmpty());
        assertEquals(searchTitle, albums.get(0).getTitle());

    }
    
    @Test
    public void testSearchAlbumsWithArtist() throws FileNotFoundException {
        MusicStore musicStore = new MusicStore();
        
        // Get the first album to use its artist for search
        List<Album> allAlbums = musicStore.getAllAlbums();
        assertFalse(allAlbums.isEmpty());
        String searchArtist = allAlbums.get(0).getArtist();
        
        // Test the exact match with search artist
        List<Album> albums = musicStore.searchAlbumsWithArtist(searchArtist);
        assertFalse(albums.isEmpty());
        assertEquals(searchArtist, albums.get(0).getArtist());
    }
    
    
    @Test
    public void testGetSongWithTitleAndArtist() throws FileNotFoundException {
        MusicStore musicStore = new MusicStore();
        
        // Get the first song to use its title and artist for search
        List<Song> allSongs = musicStore.getAllSongs();
        assertFalse(allSongs.isEmpty());
        String searchTitle = allSongs.get(0).getTitle();
        String searchArtist = allSongs.get(0).getArtist();
        
        Song song = musicStore.getSongWithTitleAndArtist(searchTitle, searchArtist);
        assertNotNull(song);
        assertEquals(searchTitle, song.getTitle());
        assertEquals(searchArtist, song.getArtist());
         
    }
    
    @Test
    public void testGetAlbumWithTitleAndArtist() throws FileNotFoundException {
        MusicStore musicStore = new MusicStore();
       
        // Get the first album to use its title and artist for search
        List<Album> allAlbums = musicStore.getAllAlbums();
        assertFalse(allAlbums.isEmpty());
        String searchTitle = allAlbums.get(0).getTitle();
        String searchArtist = allAlbums.get(0).getArtist();
        
        Album album = musicStore.getAlbumWithTitleAndArtist(searchTitle, searchArtist);
        assertNotNull(album);
        assertEquals(searchTitle, album.getTitle());
        assertEquals(searchArtist, album.getArtist());
        
    }
    
    @Test
    public void testGetSongsFromAlbum() throws FileNotFoundException {
        MusicStore musicStore = new MusicStore();
        
        // Get the first album to use its title and artist for search
        List<Album> allAlbums = musicStore.getAllAlbums();
        assertFalse(allAlbums.isEmpty());
        String searchTitle = allAlbums.get(0).getTitle();
        String searchArtist = allAlbums.get(0).getArtist();
        
        List<Song> songs = musicStore.getSongsFromAlbum(searchTitle, searchArtist);
        assertFalse(songs.isEmpty());
        
        // Verify all the returned songs belong to the album
        for (Song song : songs) {
            assertEquals(searchTitle, song.getAlbum());
            assertEquals(searchArtist, song.getArtist());
        }

    }
    
    
    @Test
    public void testGetAllAlbums() throws FileNotFoundException {
        MusicStore musicStore = new MusicStore();
        // Get all albums
        List<Album> albums = musicStore.getAllAlbums();
        
        // Verify it has albums
        assertFalse(albums.isEmpty());
        
        Album firstAlbum = albums.get(0);
        assertNotNull(firstAlbum.getTitle());
        assertNotNull(firstAlbum.getArtist());
        assertNotNull(firstAlbum.getGenre());
        assertTrue(firstAlbum.getYear() > 0);
    }

    
    @Test
    public void testGetAllSongs() throws FileNotFoundException {
        MusicStore musicStore = new MusicStore();
        // Get all songs
        List<Song> songs = musicStore.getAllSongs();
        
        // Verify it has songs
        assertFalse(songs.isEmpty());
        
        Song firstSong = songs.get(0);
        assertNotNull(firstSong.getTitle());
        assertNotNull(firstSong.getArtist());
        assertNotNull(firstSong.getAlbum());
        assertNotNull(firstSong.getGenre());
        assertTrue(firstSong.getYear() > 0);
    }
}