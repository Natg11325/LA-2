//package default;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.jupiter.api.Test;

class LibraryModelTest {


	@Test
	public void testConstructor() throws FileNotFoundException {
		MusicStore myStore = new MusicStore();
		LibraryModel myModel = new LibraryModel(myStore);
		assertNotNull(myModel); //verify that constructor works
	}
	
	@Test
	public void testAddAutoPlaylist() throws FileNotFoundException{
		MusicStore myStore = new MusicStore();
		LibraryModel myModel = new LibraryModel(myStore);
		Playlist playlist = new Playlist("test");
		myModel.addAutoPlaylist("key", playlist);
		assertNotNull(myModel.getAutoPlaylists().get(0));
	}
	
	@Test
	public void testRemoveAutoPlaylist() throws FileNotFoundException{
		MusicStore myStore = new MusicStore();
		LibraryModel myModel = new LibraryModel(myStore);
		Playlist playlist = new Playlist("test");
		myModel.addAutoPlaylist("key", playlist);
		assertTrue(myModel.removeAutoPlaylist("key"));
	}
	
	@Test
	public void testPlaySong() throws FileNotFoundException{
		MusicStore myStore = new MusicStore();
		LibraryModel myModel = new LibraryModel(myStore);
		assertTrue(myModel.getAllLibrarySongs().isEmpty());
		//test playing a song that is not in the library.
		assertFalse(myModel.playSong("Daydreamer", "Adele"));
		
		myModel.addSongToLibrary( "wrongValue", "wrongValue");
		myModel.addSongToLibrary( "Daydreamer", "Adele");
		myModel.addSongToLibrary( "Chasing Pavements", "Adele");
		//make sure adding the song worked
		assertFalse(myModel.getAllLibrarySongs().isEmpty());
		//test a song that does not match the title. 
		assertFalse(myModel.playSong("nonsense", "Adele"));
		//test a song that does not match the artist.
		assertFalse(myModel.playSong("Daydreamer", "nonsense"));
		//test a song that matches both(song is inside of library).
		assertTrue(myModel.playSong("Daydreamer", "Adele"));
		//test that the plays was successfully incremented. 
		assertTrue(myModel.getrecentlyPlayed()[0] != null);
	}
	
	@Test
	public void testGetRecentlyPlayed() throws FileNotFoundException{
		MusicStore myStore = new MusicStore();
		LibraryModel myModel = new LibraryModel(myStore);
		assertNull(myModel.getrecentlyPlayed());
		myModel.addSongToLibrary( "wrongValue", "wrongValue");
		myModel.addSongToLibrary( "Daydreamer", "Adele");
		myModel.addSongToLibrary( "Chasing Pavements", "Adele");
		myModel.playSong("Daydreamer", "Adele");
		assertTrue(myModel.getrecentlyPlayed()[0] != null);
	}
	
	@Test
	public void testGetMostPlayed() throws FileNotFoundException {
		MusicStore myStore = new MusicStore();
		LibraryModel myModel = new LibraryModel(myStore);
		myModel.addSongToLibrary( "wrongValue", "wrongValue");
		myModel.addSongToLibrary( "Daydreamer", "Adele");
		myModel.addSongToLibrary( "Chasing Pavements", "Adele");
		myModel.playSong("Daydreamer", "Adele");
		assertTrue(myModel.getMostPlayed()[0] != null);
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
		assertTrue(myModel.addAlbumToLibrary( "19", "Adele"));
	}
	 
	@Test
	public void testHasSong() throws FileNotFoundException{
		MusicStore musicStore = new MusicStore();
        LibraryModel libraryModel = new LibraryModel(musicStore);
        
        // Get the first song from the allSongs list from music store
        List<Song> allSongs = musicStore.getAllSongs();
        assertFalse(allSongs.isEmpty());
        // get the title and artist from that song
        String songTitle = allSongs.get(0).getTitle();
        String songArtist = allSongs.get(0).getArtist();
        
        // search for a song that exists. 
        libraryModel.addSongToLibrary(songTitle, songArtist);
        assertTrue(libraryModel.hasSong(songTitle, songArtist));
        
        //Search for song that does not exist.
        assertFalse(libraryModel.hasSong("nonsense", "nonsense"));
	}
	
	@Test
	public void testHasAlbum() throws FileNotFoundException{
		MusicStore musicStore = new MusicStore();
        LibraryModel libraryModel = new LibraryModel(musicStore);
        
        List<Album> allAlbums = musicStore.getAllAlbums();
        assertFalse(allAlbums.isEmpty());
        
        String title = allAlbums.get(0).getTitle();
        String artist = allAlbums.get(0).getArtist();
        //search for an album that exists.
        libraryModel.addAlbumToLibrary(title, artist);
        assertTrue(libraryModel.hasAlbum(title));
        
        //search for an album that does not exist. 
        assertFalse(libraryModel.hasAlbum("nonsense"));
        
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
        
        List<Album> results = libraryModel.searchLibAlbumsWithTitle(albumTitle);
        assertFalse(results.isEmpty());
        assertEquals(albumTitle, results.get(0).getTitle());
        
        results = libraryModel.searchLibAlbumsWithTitle("title doesnt exist");
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
    
    @Test
    public void testSongsSortedByTitle() throws FileNotFoundException {
        // Create a mock or real MusicStore with test songs
        MusicStore musicStore = new MusicStore();
        LibraryModel libraryModel = new LibraryModel(musicStore);
        
        // Assuming these songs exist in the store or can be added to it
        libraryModel.addSongToLibrary("Someone Like You", "Adele");
        libraryModel.addSongToLibrary("Chasing Pavements", "Adele");
        libraryModel.addSongToLibrary("Daydreamer", "Adele");
        
        // Get sorted songs
        List<Song> sortedSongs = libraryModel.songsSortedByTitle();
        
        // Check titles are in correct alphabetical order
        assertEquals("Chasing Pavements", sortedSongs.get(0).getTitle());
        assertEquals("Daydreamer", sortedSongs.get(1).getTitle());
        assertEquals("Someone Like You", sortedSongs.get(2).getTitle());
    }

    @Test
    public void testSongsSortedByArtist() throws FileNotFoundException {
        MusicStore musicStore = new MusicStore();
        LibraryModel libraryModel = new LibraryModel(musicStore);
        
        List<Song> allSongs = musicStore.getAllSongs();
        assertFalse(allSongs.isEmpty());
        
        Song song1 = allSongs.get(0);
        Song song2 = allSongs.get(1);
        Song song3 = allSongs.get(2);
        
        libraryModel.addSongToLibrary(song1.getTitle(), song1.getArtist());
        libraryModel.addSongToLibrary(song2.getTitle(), song2.getArtist());
        libraryModel.addSongToLibrary(song3.getTitle(), song3.getArtist());
        
        List<Song> sortedSongs = libraryModel.songsSortedByArtist();
                
        // Check if the list is sorted by artist
        for (int i = 0; i < sortedSongs.size() - 1; i++) {
            String artist1 = sortedSongs.get(i).getArtist().toLowerCase();
            String artist2 = sortedSongs.get(i + 1).getArtist().toLowerCase();
            
            boolean orderIsCorrect = artist1.compareTo(artist2) <= 0;
            assertTrue(orderIsCorrect);
        }
    }


    @Test
    public void testSongsSortedByRating() throws FileNotFoundException {
        MusicStore musicStore = new MusicStore();
        LibraryModel libraryModel = new LibraryModel(musicStore);
        
        // Add songs to the library
        libraryModel.addSongToLibrary("Daydreamer", "Adele");
        libraryModel.addSongToLibrary("Chasing Pavements", "Adele");
        libraryModel.addSongToLibrary("Someone Like You", "Adele");
        libraryModel.addSongToLibrary("Tired", "Adele");
        
        // Rate the songs (highest, medium, lowest)
        libraryModel.rateSong("Chasing Pavements", "Adele", 5);
        libraryModel.rateSong("Daydreamer", "Adele", 3);
        libraryModel.rateSong("Someone Like You", "Adele", 1);
        
        List<Song> sortedSongs = libraryModel.songsSortedByRating();
                 
        // Verify sorting order by highest rating first
        assertEquals(Integer.valueOf(5), sortedSongs.get(0).getRating());
        assertEquals("Chasing Pavements", sortedSongs.get(0).getTitle());
        
        assertEquals(Integer.valueOf(3), sortedSongs.get(1).getRating());
        assertEquals("Daydreamer", sortedSongs.get(1).getTitle());
        
        assertEquals(Integer.valueOf(1), sortedSongs.get(2).getRating());
        assertEquals("Someone Like You", sortedSongs.get(2).getTitle());
        
    }
    
    
    @Test
    public void testRemoveSongFromLibrary() throws FileNotFoundException {
        MusicStore musicStore = new MusicStore();
        LibraryModel libraryModel = new LibraryModel(musicStore);
        
        // Get a song from the music store
        List<Song> allSongs = musicStore.getAllSongs();
        assertFalse(allSongs.isEmpty());
        String songTitle = allSongs.get(0).getTitle();
        String songArtist = allSongs.get(0).getArtist();
        
        // Add the song to the library
        libraryModel.addSongToLibrary(songTitle, songArtist);
        
        List<Song> librarySongs = libraryModel.getAllLibrarySongs();
        
        // Create a playlist and add the song to it
        libraryModel.createPlaylist("my playlist");
        libraryModel.addSongToPlaylist("my playlist", songTitle, songArtist);
        
        // Remove the song
        boolean removeSong = libraryModel.removeSongFromLibrary(songTitle, songArtist);
        assertTrue(removeSong);
        
        // Verify that the song is removed from the library
        librarySongs = libraryModel.getAllLibrarySongs();
        assertTrue(librarySongs.isEmpty());
        
        // Verify that the song is also removed from the playlist
        Playlist playlist = libraryModel.getPlaylistWithName("my playlist"); 
        assertTrue(playlist.getSongs().isEmpty());
        
        // Test removing a song that doesn't exist
        removeSong = libraryModel.removeSongFromLibrary("a song that doesn't exist", "an artist that doesn't exist");
        assertFalse(removeSong);
    }

    @Test
    public void testRemoveAlbumFromLibrary() throws FileNotFoundException {
        MusicStore musicStore = new MusicStore();
        LibraryModel libraryModel = new LibraryModel(musicStore);
        
        List<Album> allAlbums = musicStore.getAllAlbums();
        assertFalse(allAlbums.isEmpty());
        String albumTitle = allAlbums.get(0).getTitle();
        String albumArtist = allAlbums.get(0).getArtist();
        
        libraryModel.addAlbumToLibrary(albumTitle, albumArtist);
        
        // Verify that the album is in the library
        List<Album> libraryAlbums = libraryModel.getAllLibraryAlbums();
        
        // Get the songs from the album
        List<Song> albumSongs = musicStore.getSongsFromAlbum(albumTitle, albumArtist);
        assertFalse(albumSongs.isEmpty());
        
        // Verify that songs are added to library
        List<Song> librarySongs = libraryModel.getAllLibrarySongs();
        
        // Create a playlist and add a song from the album to it
        Song firstSong = albumSongs.get(0);
        libraryModel.createPlaylist("my playlist");
        libraryModel.addSongToPlaylist("my playlist", firstSong.getTitle(), firstSong.getArtist());
        
        // Remove the album
        boolean removeResult = libraryModel.removeAlbumFromLibrary(albumTitle, albumArtist);
        assertTrue(removeResult);
        
        // Verify that the album is removed from the library
        libraryAlbums = libraryModel.getAllLibraryAlbums();
        assertTrue(libraryAlbums.isEmpty());
        
        // Verify that songs are removed from the library
        librarySongs = libraryModel.getAllLibrarySongs();
        assertEquals(0, librarySongs.size());
        
        // Verify the song is removed from the playlist
        Playlist playlist = libraryModel.getPlaylistWithName("my playlist");
        assertTrue(playlist.getSongs().isEmpty());
        
        // Test removing an album that doesn't exist
        removeResult = libraryModel.removeAlbumFromLibrary("a song that doesn't exist", "an artist that doesn't exist");
        assertFalse(removeResult);
    }
    
    
    @Test 
    public void testInitializeAutomaticPlaylists() throws FileNotFoundException {
        MusicStore musicStore = new MusicStore();
        LibraryModel libraryModel = new LibraryModel(musicStore);
        
        // Get all automatic playlists
        List<Playlist> automaticPlaylists = libraryModel.getAutoPlaylists();
                
        // Booleans to track if both automatic playlists are found
        boolean favoritesIsFound = false;
        boolean topRatedIsFound = false;
        
        // Iterate through all automatic playlists checking their names
        for (Playlist playlist : automaticPlaylists) {
        	// Check if this playlist is the Favorites automatic playlist
            if (playlist.getName().equals("Automatic Favorites: ")) {
            	favoritesIsFound = true;
            // Check if this playlist is the Top Rated automatic playlist
            } else if (playlist.getName().equals("Automatic Top Rated: ")) {
            	topRatedIsFound = true;
            }
        }
        
        // Verify it was created
        assertTrue(favoritesIsFound);
        assertTrue(topRatedIsFound);
        
        // Check that all automatic playlists start out as empty
        for (Playlist playlist : automaticPlaylists) {
            assertTrue(playlist.getSongs().isEmpty());
        }
    }
    
    @Test
    public void testGetAllPlaylists() throws FileNotFoundException {
        MusicStore musicStore = new MusicStore();
        LibraryModel libraryModel = new LibraryModel(musicStore);
        
        List<Playlist> initialPlaylists = libraryModel.getAllPlaylists();
        
        // Checks that the automatic playlists exist by their names
        boolean favoritesIsFound = false;
        boolean topRatedIsFound = false;
        
        for (Playlist playlist : initialPlaylists) {
            if (playlist.getName().equals("Automatic Favorites: ")) {
            	favoritesIsFound = true;
            } else if (playlist.getName().equals("Automatic Top Rated: ")) {
            	topRatedIsFound = true;
            }
        }
        
        assertTrue(favoritesIsFound);
        assertTrue(topRatedIsFound);

        String playlist1 = "NatsPlaylist1";
        String playlist2 = "NatsPlaylist2";
        libraryModel.createPlaylist(playlist1);
        libraryModel.createPlaylist(playlist2);
        
        // Get all playlists again
        List<Playlist> updatedPlaylists = libraryModel.getAllPlaylists();
                
        // Checks that the my playlists exist by their names
        boolean playlist1IsFound = false;
        boolean playlist2IsFound = false;
        
        for (Playlist playlist : updatedPlaylists) {
            if (playlist.getName().equals(playlist1)) {
            	playlist1IsFound = true;
            } else if (playlist.getName().equals(playlist2)) {
            	playlist2IsFound = true;
            }
        }
        
        assertTrue(playlist1IsFound);
        assertTrue(playlist2IsFound);
        
        for (Playlist playlist : updatedPlaylists) {
            if (playlist.getName().equals("Automatic Favorites: ")) {
            	favoritesIsFound = true;
            } else if (playlist.getName().equals("Automatic Top Rated: ")) {
            	topRatedIsFound = true;
            }
        }
         
        assertTrue(favoritesIsFound);
        assertTrue(topRatedIsFound);
        
        // Creates a genre playlist by adding enough songs of the same genre
        List<Song> allSongs = musicStore.getAllSongs();
        
        // Gets the first song and its genre
        Song firstSong = allSongs.get(0);
        String genre = firstSong.getGenre();
        
        // Add at least 10 songs from this genre to trigger the genre playlist being made
        int songsAdded = 0;
        for (Song song : allSongs) {
            if (song.getGenre().equals(genre)) {
                libraryModel.addSongToLibrary(song.getTitle(), song.getArtist());
                songsAdded++;
                
                if (songsAdded >= 10) {
                    break;
                }
            }
        }
         
        if (songsAdded >= 10) {
            List<Playlist> playlistsWithGenre = libraryModel.getAllPlaylists();
                        
            boolean genrePlaylistIsFound = false;
            String genrePlaylistName = "Automatic Genre: " + genre;
            
            for (Playlist playlist : playlistsWithGenre) {
                if (playlist.getName().equals(genrePlaylistName)) {
                	genrePlaylistIsFound = true;
                    break;
                }
            }
            
            assertTrue(genrePlaylistIsFound);
        }
    }
    
    
    
}