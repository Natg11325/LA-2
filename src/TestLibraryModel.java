package src;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.jupiter.api.Test;

class TestLibraryModel {

//	@Test
//	void test() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testConstructor() throws FileNotFoundException {
		MusicStore myStore = new MusicStore();
		LibraryModel myModel = new LibraryModel(myStore);
		assertNotNull(myModel); //verify that constructor works
	}
	
	@Test
	public void test_addSongToLibrary() throws FileNotFoundException {
		MusicStore myStore = new MusicStore();
		LibraryModel myModel = new LibraryModel(myStore);
		//tests if song object becomes null.
		assertFalse(myModel.addSongToLibrary( "wrongValue", "wrongValue"));
		
		//tests when library does not already contain the song.
		assertTrue(myModel.addSongToLibrary( "Daydreamer", "Adele"));
		
		//tests when library already contains the song.
		assertFalse(myModel.addSongToLibrary( "Daydreamer", "Adele"));
	}
	
	@Test
	public void test_addAlbumToLibrary() throws FileNotFoundException {
		MusicStore myStore = new MusicStore();
		LibraryModel myModel = new LibraryModel(myStore);
		
		//tests when the album becomes null.
		assertFalse(myModel.addAlbumToLibrary( "wrongValue", "wrongValue"));
		
		//adds a song so that the library already contains at least one song from an album being added.
		myModel.addSongToLibrary( "Daydreamer", "Adele");
		
		//tests when library does not already contain the album.
		assertTrue(myModel.addAlbumToLibrary( "19", "Adele"));
		
		//tests when library already contains the album. A song from this album has already been added.
		assertFalse(myModel.addAlbumToLibrary( "19", "Adele"));
	}
	
    @Test
    public void testRateSong() throws FileNotFoundException {
        MusicStore musicStore = new MusicStore();
        LibraryModel libraryModel = new LibraryModel(musicStore);
        
        // Get the first song from the allSongs list from music store
        List<Song> allSongs = musicStore.getAllSongs();
        assertFalse(allSongs.isEmpty());
        // get the title and artist from that song
        String songTitle = allSongs.get(0).getTitle();
        String songArtist = allSongs.get(0).getArtist();
        String songTitle2 = allSongs.get(0).getTitle();
        
        // Add the song to the library
        libraryModel.addSongToLibrary(songTitle, songArtist);

        // Test rating the song with a valid rating, meaning 1-5
        assertTrue(libraryModel.rateSong(songTitle, songArtist, 5));
        assertFalse(libraryModel.rateSong(songTitle, songTitle2, 3));
        // Test rating the song with an invalid rating like 0
        assertFalse(libraryModel.rateSong(songTitle, songArtist, 0));
        // Test rating the song with an invalid rating, like 6
        assertFalse(libraryModel.rateSong(songTitle, songArtist, 6));
        assertFalse(libraryModel.rateSong("doenst exist", "doesnt exist", 3), "cant rate, doesnt exist");
    }
    
    @Test
    public void test_createPlaylist() throws FileNotFoundException {
    	MusicStore musicStore = new MusicStore();
        LibraryModel libraryModel = new LibraryModel(musicStore);
        
        //makes a new play list.
        assertTrue(libraryModel.createPlaylist("Music i like ig"));
        
        //makes a play list already created.
        assertFalse(libraryModel.createPlaylist("Music i like ig"));
    }
    
    @Test
    public void testMarkSongAsFav() throws FileNotFoundException {
        MusicStore musicStore = new MusicStore();
        LibraryModel libraryModel = new LibraryModel(musicStore);
        
        // Get the first song from the allSongs list from music store
        List<Song> allSongs = musicStore.getAllSongs();
        assertFalse(allSongs.isEmpty());
        String songTitle = allSongs.get(0).getTitle();
        String songArtist = allSongs.get(0).getArtist();
        String songTitle2 = allSongs.get(0).getTitle();
        
        // Add the song to the library
        libraryModel.addSongToLibrary(songTitle, songArtist);
        
        // Test marking the song as favorite
        assertTrue(libraryModel.markSongAsFav(songTitle, songArtist));
        assertFalse(libraryModel.markSongAsFav(songTitle, songTitle2));

        
        // Test marking a song that doesnt exist as favorite
        assertFalse(libraryModel.markSongAsFav("doesnt exist", "doesnt exist"));
    }
    
    @Test
    public void testSearchLibSongsWithTitle() throws FileNotFoundException {
        MusicStore musicStore = new MusicStore();
        LibraryModel libraryModel = new LibraryModel(musicStore);
        
        // Get the first song from the allSongs list from music store
        List<Song> allSongs = musicStore.getAllSongs();
        assertFalse(allSongs.isEmpty());
        String songTitle = allSongs.get(0).getTitle();
        String songArtist = allSongs.get(0).getArtist();
        
        // Add the song to the library
        libraryModel.addSongToLibrary(songTitle, songArtist);
        
        // Test searching for songs by the title
        List<Song> results = libraryModel.searchLibSongsWithTitle(songTitle);
        assertFalse(results.isEmpty());
        assertEquals(songTitle, results.get(0).getTitle());
        
        // Test searching for a title that doesnt exist
        results = libraryModel.searchLibSongsWithTitle("title doesnt exist");
        assertTrue(results.isEmpty());
    }

    @Test
    public void testSearchLibSongsWithArtist() throws FileNotFoundException {
        MusicStore musicStore = new MusicStore();
        LibraryModel libraryModel = new LibraryModel(musicStore);
        
        List<Song> allSongs = musicStore.getAllSongs();
        assertFalse(allSongs.isEmpty());
        String songTitle = allSongs.get(0).getTitle();
        String songArtist = allSongs.get(0).getArtist();
        
        libraryModel.addSongToLibrary(songTitle, songArtist);
        
        List<Song> results = libraryModel.searchLibSongsWithArtist(songArtist);        
        results = libraryModel.searchLibSongsWithArtist("artist doesnt exist");
        assertTrue(results.isEmpty());
    }
	
    @Test
    public void testSearchLibAlbumsWithTitle() throws FileNotFoundException {
        MusicStore musicStore = new MusicStore();
        LibraryModel libraryModel = new LibraryModel(musicStore);
        
        List<Album> allAlbums = musicStore.getAllAlbums();
        assertFalse(allAlbums.isEmpty());
        String albumTitle = allAlbums.get(0).getTitle();
        String songArtist = allAlbums.get(0).getArtist();
        
        libraryModel.addAlbumToLibrary(albumTitle, songArtist);
        
        List<Album> results = libraryModel.searchLibAlbumWithTitle(albumTitle);
        assertFalse(results.isEmpty());
        assertEquals(albumTitle, results.get(0).getTitle());
        
        results = libraryModel.searchLibAlbumWithTitle("title doesnt exist");
        assertTrue(results.isEmpty());
    }
    
    @Test
    public void testSearchLibAlbumsWithArtist() throws FileNotFoundException {
        MusicStore musicStore = new MusicStore();
        LibraryModel libraryModel = new LibraryModel(musicStore);
        
        List<Album> allAlbums = musicStore.getAllAlbums();
        assertFalse(allAlbums.isEmpty());
        String albumTitle = allAlbums.get(0).getTitle();
        String albumArtist = allAlbums.get(0).getArtist();
        
        libraryModel.addAlbumToLibrary(albumTitle, albumArtist);
        
        List<Album> results = libraryModel.searchLibAlbumsWithArtist(albumArtist);
                
        results = libraryModel.searchLibAlbumsWithArtist("artist doesnt exist");
        assertTrue(results.isEmpty());
    }
	
    @Test
    public void test_getPlaylistWithName() throws FileNotFoundException {
    	MusicStore musicStore = new MusicStore();
        LibraryModel libraryModel = new LibraryModel(musicStore);
        
        //tests if the play list does not exist. 
        assertNull(libraryModel.getPlaylistWithName("wrongValue"));
        
        libraryModel.createPlaylist("Music i like ig");
        assertNotNull(libraryModel.getPlaylistWithName("Music i like ig"));
    }
	
    @Test
    public void testSearchStoreSongsWithTitle() throws FileNotFoundException {
        MusicStore musicStore = new MusicStore();
        LibraryModel libraryModel = new LibraryModel(musicStore);
        
        // Get a song from the music store to search for
        List<Song> allSongs = musicStore.getAllSongs();
        assertFalse(allSongs.isEmpty());
        String songTitle = allSongs.get(0).getTitle();
        
        // Search for songs with the title
        List<Song> results = libraryModel.searchStoreSongsWithTitle(songTitle);
        assertFalse(results.isEmpty());
        assertEquals(songTitle, results.get(0).getTitle());
        
        results = libraryModel.searchStoreSongsWithTitle("title doesn't exist");
        assertTrue(results.isEmpty());
    }
    
    
    @Test
    public void testSearchStoreSongsWithArtist() throws FileNotFoundException {
        MusicStore musicStore = new MusicStore();
        LibraryModel libraryModel = new LibraryModel(musicStore);
        
        List<Song> allSongs = musicStore.getAllSongs();
        assertFalse(allSongs.isEmpty());
        String songArtist = allSongs.get(0).getArtist();
        
        List<Song> results = libraryModel.searchStoreSongsWithArtist(songArtist);
        assertFalse(results.isEmpty());
        assertEquals(songArtist, results.get(0).getArtist());
        
        results = libraryModel.searchStoreSongsWithArtist("artist doesn't exist");
        assertTrue(results.isEmpty());
    }
    
    @Test
    public void testSearchStoreAlbumsWithTitle() throws FileNotFoundException {
        MusicStore musicStore = new MusicStore();
        LibraryModel libraryModel = new LibraryModel(musicStore);
        
        // Get an album from the music store to search for
        List<Album> allAlbums = musicStore.getAllAlbums();
        assertFalse(allAlbums.isEmpty());
        String albumTitle = allAlbums.get(0).getTitle();
        
        // Search for albums with the title
        List<Album> results = libraryModel.searchStoreAlbumsWithTitle(albumTitle);
        assertFalse(results.isEmpty());
        assertEquals(albumTitle, results.get(0).getTitle());
        
        // Test searching for a title that doesn't exist
        results = libraryModel.searchStoreAlbumsWithTitle("title doesn't exist");
        assertTrue(results.isEmpty());
    }
    
    @Test
    public void testSearchStoreAlbumsWithArtist() throws FileNotFoundException {
        MusicStore musicStore = new MusicStore();
        LibraryModel libraryModel = new LibraryModel(musicStore);
        
        List<Album> allAlbums = musicStore.getAllAlbums();
        assertFalse(allAlbums.isEmpty());
        String albumArtist = allAlbums.get(0).getArtist();
        
        List<Album> results = libraryModel.searchStoreAlbumsWithArtist(albumArtist);
        assertFalse(results.isEmpty());
        assertEquals(albumArtist, results.get(0).getArtist());
        
        results = libraryModel.searchStoreAlbumsWithArtist("artist doesn't exist");
        assertTrue(results.isEmpty());
    }
    
    @Test
    public void test_addSongToPlaylist() throws FileNotFoundException {
    	MusicStore musicStore = new MusicStore();
        LibraryModel libraryModel = new LibraryModel(musicStore);
        
        //tests if the play list does not exist.
        assertFalse(libraryModel.addSongToPlaylist("wrongValue", null, null));
        
        //following tests assume that the play list does exist.
        libraryModel.createPlaylist("Music I like ig");
        
        //tests if the song is not in the library by title and artist.
        assertFalse(libraryModel.addSongToPlaylist("Music I like ig", "no song", "no song"));
        assertFalse(libraryModel.addSongToPlaylist("Music I like ig", "Daydreamer", "wrong artist"));
        assertFalse(libraryModel.addSongToPlaylist("Music I like ig", "Daydreamer", "Adele"));
        
        //tests if the song is in the library.
        libraryModel.addSongToLibrary( "Daydreamer", "Adele");
        assertTrue(libraryModel.addSongToPlaylist("Music I like ig", "Daydreamer", "Adele"));
    }
    
    @Test
    public void testGetAllLibrarySongs() throws FileNotFoundException{
        MusicStore musicStore = new MusicStore();
        LibraryModel libraryModel = new LibraryModel(musicStore);
        
        // since the library should start out as empty, check if thats true
        List<Song> librarySongs = libraryModel.getAllLibrarySongs();
        assertTrue(librarySongs.isEmpty());
        
        // Add a song to the library
        List<Song> allSongs = musicStore.getAllSongs();
        assertFalse(allSongs.isEmpty());
        String songTitle = allSongs.get(0).getTitle();
        String songArtist = allSongs.get(0).getArtist();
        Song song1 = allSongs.get(0);
        
        libraryModel.addSongToLibrary(songTitle, songArtist);
        
        // checks if the song made it to the library
        assertEquals(song1, libraryModel.getAllLibrarySongs().get(0));
        
    }
    
    @Test
    public void test_removeSongFromPlaylist() throws FileNotFoundException {
    	MusicStore musicStore = new MusicStore();
        LibraryModel libraryModel = new LibraryModel(musicStore);
        
        //tests if the play list does not exist.
        assertFalse(libraryModel.addSongToPlaylist("wrongValue", null, null));
        
        //following tests assume that the play list does exist.
        libraryModel.createPlaylist("Music I like ig");
        libraryModel.addSongToPlaylist("Music I like ig", "Daydreamer", "Adele");
        //tests if the song is not in the library by title and artist.
        assertFalse(libraryModel.removeSongFromPlaylist("Music I like ig", "no song", "no song"));
        assertFalse(libraryModel.removeSongFromPlaylist("Music I like ig", "Daydreamer", "wrong artist"));
        assertFalse(libraryModel.removeSongFromPlaylist("Music I like ig", "Daydreamer", "Adele"));
        
//        libraryModel.addSongToPlaylist("Music I like ig", "Tired", "Adele");
//        assertTrue(libraryModel.removeSongFromPlaylist("Music I like ig", "Tired", "Adele"));

        
    }
    
    @Test
    public void test_getAllLibraryArtists() throws FileNotFoundException {
    	MusicStore musicStore = new MusicStore();
        LibraryModel libraryModel = new LibraryModel(musicStore);
        
        //checks if method found the song.
        libraryModel.addSongToLibrary("Daydreamer", "Adele");
        assertEquals("Adele", libraryModel.getAllLibraryArtists().get(0));
    }
    
    @Test
    public void test_getFavoriteSongs() throws FileNotFoundException {
    	MusicStore musicStore = new MusicStore();
        LibraryModel libraryModel = new LibraryModel(musicStore);
        
        //tests if the method found a favorite. 
        libraryModel.addSongToLibrary("Daydreamer", "Adele");
        libraryModel.markSongAsFav("Daydreamer", "Adele");
        Song song = libraryModel.getAllLibrarySongs().get(0);
        assertEquals(song, libraryModel.getFavoriteSongs().get(0));
    }
    
	
}
