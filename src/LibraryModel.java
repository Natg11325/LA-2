import java.util.ArrayList;
import java.util.List;
 
public class LibraryModel {
    private MusicStore musicStore;
    private List<Song> myLibrarySongs;
    private List<Album> myLibraryAlbums;
    private List<Playlist> playlists;

    public LibraryModel(MusicStore musicStore) {
        this.musicStore = musicStore;
        this.myLibrarySongs = new ArrayList<>();
        this.myLibraryAlbums = new ArrayList<>();
        this.playlists = new ArrayList<>();
    }
    
    
    public boolean addSongToLibrary(String title, String artist) {
    	// Retrieve a song from the database by matching exact title and artist name
        Song song = musicStore.getSongWithTitleAndArtist(title, artist); 
        // Only add the song if it exists in the store and isn't already in the user's library
        if (song != null && !myLibrarySongs.contains(song)) {
            myLibrarySongs.add(song);
            return true;
        }
        return false;
    }


    public boolean addAlbumToLibrary(String title, String artist) {
    	// Retrieve an album from the database by matching exact title and artist name
    	Album album = musicStore.getAlbumWithTitleAndArtist(title, artist);
        // Only add the album if it exists in the store and isn't already in the user's library
    	if (album != null && !myLibraryAlbums.contains(album)) {
            myLibraryAlbums.add(album);
            
            // Add all songs from the album to the user's library
            List<Song> albumSongs = musicStore.getSongsFromAlbum(title, artist);
            // This is a last check for duplicates
            for (Song song : albumSongs) {
                if (!myLibrarySongs.contains(song)) {
                    myLibrarySongs.add(song);
                }
            }
            return true;
        } 
        return false;
    }
    

    public boolean createPlaylist(String name) {
        // Checks if a playlist with this name already exists
        for (Playlist playlist : playlists) {
            if (playlist.getName().equals(name)) {
                return false;
            }
        }
        // If there's no duplicates, this created and adds a new Playlist 
        playlists.add(new Playlist(name));
        return true;
    }
    
    
    public Playlist getPlaylistWithName(String name) {
        for (Playlist playlist : playlists) {
            if (playlist.getName().equals(name)) {
                return playlist;
            }
        } 
        return null;
    }
    
    
    public boolean addSongToPlaylist(String playlistName, String songTitle, String artist) {
        Playlist playlist = getPlaylistWithName(playlistName);      
        if (playlist != null) {
            // Find the song in the user's library
            Song song = null;
            for (Song currSong : myLibrarySongs) {
                if (currSong.getTitle().equals(songTitle) && currSong.getArtist().equals(artist)) {
                    song = currSong;
                    break;
                }
            }
            // Add the song to the playlist if it was found in the user's library
            if (song != null) {
                playlist.addSong(song);
                return true;
            }
        }  
        return false;
    }

   
    public boolean removeSongFromPlaylist(String playlistName, String title, String artist) {
        Playlist playlist = getPlaylistWithName(playlistName);
        if (playlist != null) {
            // Checks if the song exists in the playlist before removing it
            List<Song> songs = playlist.getSongs();
            for (Song song : songs) {
            	if (song.getTitle().toLowerCase().equals(title.toLowerCase()) && song.getArtist().toLowerCase().equals(artist.toLowerCase())) {
            		playlist.removeSong(title, artist);
            		return true;
                }
            }
        }
        return false;
    }
    
    
    public boolean markSongAsFav(String title, String artist) {
    	// Searches for the song in the user's library
        for (Song song : myLibrarySongs) {
            if (song.getTitle().equals(title) && song.getArtist().equals(artist)) {
                song.setFavorite(true);
                return true;
            }
        }      
        return false;
    }


    public boolean rateSong(String title, String artist, int rating) {
        if (rating < 1 || rating > 5) {
            return false;
        }
        // Searches for the song in the user's library        
        for (Song song : myLibrarySongs) {
            if (song.getTitle().equals(title) && song.getArtist().equals(artist)) {
                song.setRating(rating);
                return true;
            }
        }
        return false;
    } 
    

    public List<Song> searchLibSongsWithTitle(String title) {
        List<Song> results = new ArrayList<>();
        String searchTitle = title.toLowerCase();
 
        for (Song song : myLibrarySongs) {
            if (song.getTitle().toLowerCase().equals(searchTitle)) {
                results.add(song);
            }
        }
        return results;
    }
    
    
    public List<Song> searchLibSongsWithArtist(String artist){
    	List<Song> results = new ArrayList<>();
    	String searchArtist = artist.toLowerCase();
    	
    	for(Song song : myLibrarySongs) {
    		if(song.getTitle().toLowerCase().equals(searchArtist)) {
    			results.add(song);
    		}
    	}
    	return results;
    } 
    
    
    public List<Album> searchLibAlbumWithTitle(String title){
    	List<Album> results = new ArrayList<>();
    	String searchTitle = title.toLowerCase();
    	
    	for(Album album : myLibraryAlbums) {
    		if(album.getTitle().toLowerCase().equals(searchTitle)) {
    			results.add(album);
    		}
    	}
    	return results;
    }
    
    
    public List<Album> searchLibAlbumsWithArtist(String artist){
    	List<Album> results = new ArrayList<>();
    	String searchArtist = artist.toLowerCase();
    	
    	for(Album album : myLibraryAlbums) {
    		if(album.getTitle().toLowerCase().equals(searchArtist)) {
    			results.add(album);
    		}
    	}
    	return results;
    }
 
     
}
  