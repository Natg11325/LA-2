//package src;
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
                if (currSong.getTitle().toLowerCase().equals(songTitle.toLowerCase()) && currSong.getArtist().toLowerCase().equals(artist.toLowerCase())) {
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
    		if(song.getArtist().toLowerCase().equals(searchArtist)) {
    			results.add(song);
    		}
    	}
    	return results;
    } 
    
    
    public List<Album> searchLibAlbumsWithTitle(String title){
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
    		if(album.getArtist().toLowerCase().equals(searchArtist)) {
    			results.add(album);
    		}
    	}
    	return results;
    }
    
 // Search for songs by title in the music store
    public List<Song> searchStoreSongsWithTitle(String title) {
        return musicStore.searchSongsWithTitle(title);
    }
    
    
    // Search for songs by artist in the music store
    public List<Song> searchStoreSongsWithArtist(String artist) {
        return musicStore.searchSongsWithArtist(artist);
    }
    
    
    // Search for albums by title in the music store
    public List<Album> searchStoreAlbumsWithTitle(String title) {
        return musicStore.searchAlbumsWithTitle(title);
    }
    
    
    // Search for albums by artist in the music store
    public List<Album> searchStoreAlbumsWithArtist(String artist) {
        return musicStore.searchAlbumsWithArtist(artist);
    }
    
 // Get all songs in the user's library
    public List<Song> getAllLibrarySongs() {
        return new ArrayList<>(myLibrarySongs);
    }
    
    
    // Get all albums in the user's library
    public List<Album> getAllLibraryAlbums() {
        return new ArrayList<>(myLibraryAlbums);
    }
    
    
    // Get all artists in the user's library
    public List<String> getAllLibraryArtists() {
        List<String> artists = new ArrayList<>();
        for (Song song : myLibrarySongs) {
            String artist = song.getArtist();
            if (!artists.contains(artist)) {
                artists.add(artist);
            }
        }
        return artists;
    }
    
    // Get all playlists
    public List<Playlist> getAllPlaylists() {
        return new ArrayList<>(playlists);
    }
    
    
    // Get all favorite songs
    public List<Song> getFavoriteSongs() {
        List<Song> favorites = new ArrayList<>();
        for (Song song : myLibrarySongs) {
            if (song.isFavorite()) {
                favorites.add(song);
            }
        }
       return favorites;
    }
    

    //Returns a list of songs in the library sorted by title in ascending order
    public List<Song> songsSortedByTitle() {
        List<Song> sortedSongs = new ArrayList<>(myLibrarySongs);
        
        // the songs are sorted using bubble sort
        // every time it iterates through the list the largest unsorted song moves up to its correct position
        for (int i = 0; i < sortedSongs.size() - 1; i++) {
        	// each iteration reduces the number of comparisons because the last i items are already sorted
            for (int j = 0; j < sortedSongs.size() - i - 1; j++) {
                String title1 = sortedSongs.get(j).getTitle().toLowerCase();
                String title2 = sortedSongs.get(j + 1).getTitle().toLowerCase();
                // compared the two lower case titles alphabetically
                if (title1.compareTo(title2) > 0) {
                    // Swap elements if they are in wrong order
                    Song temp = sortedSongs.get(j); // temp variable that holds the first song during swap
                    sortedSongs.set(j, sortedSongs.get(j + 1));
                    sortedSongs.set(j + 1, temp);
                }
            }
        }
        return sortedSongs;
    }

   
     // Returns a list of songs in the library sorted by artist in ascending order
    public List<Song> songsSortedByArtist() {
        List<Song> sortedSongs = new ArrayList<>(myLibrarySongs);
        
        // also uses bubble sort
        for (int i = 0; i < sortedSongs.size() - 1; i++) {
            for (int j = 0; j < sortedSongs.size() - i - 1; j++) {
                String artist1 = sortedSongs.get(j).getArtist().toLowerCase();
                String artist2 = sortedSongs.get(j + 1).getArtist().toLowerCase();
                
                if (artist1.compareTo(artist2) > 0) {
                    Song temp = sortedSongs.get(j);
                    sortedSongs.set(j, sortedSongs.get(j + 1));
                    sortedSongs.set(j + 1, temp);
                }
            }
        }    
        return sortedSongs;
    }

     // Returns a list of songs in the library sorted by rating in ascending order
    public List<Song> songsSortedByRating() {
        // new list that only includes songs with non-null ratings
        List<Song> songsWithRatings = new ArrayList<>();
        
        // only sort songs with ratings
        for (Song song : myLibrarySongs) {
            if (song.getRating() != null) {
                songsWithRatings.add(song);
            }
        }
        
        for (int i = 0; i < songsWithRatings.size() - 1; i++) {
            for (int j = 0; j < songsWithRatings.size() - i - 1; j++) {
                Integer rating1 = songsWithRatings.get(j).getRating();
                Integer rating2 = songsWithRatings.get(j + 1).getRating();
                
                if (rating1 < rating2) {
                    Song temp = songsWithRatings.get(j);
                    songsWithRatings.set(j, songsWithRatings.get(j + 1));
                    songsWithRatings.set(j + 1, temp);
                }
            }
        }
        return songsWithRatings;
    }
     
    public boolean removeSongFromLibrary(String title, String artist) {
        // Find the song in the user's library
        Song songToRemove = null;
        for (Song song : myLibrarySongs) {
            if (song.getTitle().equals(title) && song.getArtist().equals(artist)) {
                songToRemove = song;
                break;
            }
        }
        
        // If song was found, remove it
        if (songToRemove != null) {
            myLibrarySongs.remove(songToRemove);
            
            // Also remove the song from all playlists
            for (Playlist playlist : playlists) {
                playlist.removeSong(title, artist);
            }
            
            return true;
        }
        return false;
    }
    
    public boolean removeAlbumFromLibrary(String title, String artist) {
        // Find the album in the user's library
        Album albumToRemove = null;
        for (Album album : myLibraryAlbums) {
            if (album.getTitle().equals(title) && album.getArtist().equals(artist)) {
                albumToRemove = album;
                break;
            }
        }
        
        // If album was found then remove it
        if (albumToRemove != null) {
            myLibraryAlbums.remove(albumToRemove); 
            // Get all songs from this album
            List<Song> songsToRemove = new ArrayList<>();
            for (Song song : myLibrarySongs) {
                if (song.getAlbum().equals(title) && song.getArtist().equals(artist)) {
                    songsToRemove.add(song);
                }
            }
            
            // Remove all songs that belong to that album
            for (Song song : songsToRemove) {
                    // Also remove the song from all playlists
                    for (Playlist playlist : playlists) {
                        playlist.removeSong(song.getTitle(), song.getArtist());
                    }
                }
            return true;
        }
        return false;
    }
    
}
  
