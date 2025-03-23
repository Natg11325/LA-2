package src;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
 
public class LibraryModel {
    private MusicStore musicStore;
    private List<Song> myLibrarySongs;
    private List<Album> myLibraryAlbums;
    private List<Playlist> playlists;
    private Queue<Song> recentlyPlayedSongs;

    public LibraryModel(MusicStore musicStore) {
        this.musicStore = musicStore;
        this.myLibrarySongs = new ArrayList<>();
        this.myLibraryAlbums = new ArrayList<>();
        this.playlists = new ArrayList<>();
        this.recentlyPlayedSongs = new LinkedList<Song>();
    }
    
    
    public void sortByPlays(ArrayList<Song> songs) {
    	Collections.sort(songs, new Comparator<>() {

			@Override
			public int compare(Song a, Song b) {
				// TODO Auto-generated method stub
				return b.getPlays() - a.getPlays();
			}
    		
    	});
    }
    
    public void removePlaylist(String name) {
    	for(int i = 0; i < playlists.size(); i++) {
    		if(playlists.get(i).getName() == name) {
    			playlists.remove(i);
    		}
    	}
    }
    
    public Song[] getMostPlayed(){
    	ArrayList<Song> songs = new ArrayList<Song>();
    	for(int i = 0; i < myLibrarySongs.size()-1; i++) {
    		Song song = myLibrarySongs.get(i);
    		if(song.getPlays() > 0) {
    			songs.add(song);
    		}
    	}
    	sortByPlays(songs);
    	Song[] mostPlayed = new Song[10];
    	for(int i = 0; i < 10 && i < songs.size(); i++) {
    		mostPlayed[i] = songs.get(i);
    	}
    	return mostPlayed;
    }
    
//    public ArrayList<Song> getMostPlayed() {
//    	ArrayList<Song> songs = new ArrayList<Song>(myLibrarySongs);
//    	sortByPlays(songs);
////    	ArrayList<Song> songs = new ArrayList<Song>();
////    	int i = 0;
//    	for(int i = 0; i < 10 && i < myLibrarySongs.size(); i++) {
//    		songs.add(myLibrarySongs.get(i));
//    	}
//    	return songs;
//    }
    
    public Object[] getrecentlyPlayed() {
    	if(!(recentlyPlayedSongs.size() < 1)) {
    		Object[] songs = recentlyPlayedSongs.toArray();
        	return songs;
    	}else {
    		return null;
    	}
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
    
    public boolean playSong(String title, String artist) {
    	for (Song song : myLibrarySongs) {
            if (song.getTitle().equals(title) && song.getArtist().equals(artist)) {
            	song.play();
            	recentlyPlayedSongs.add(song);
            	if(recentlyPlayedSongs.size() > 10) {
            		recentlyPlayedSongs.remove();
            	}
                return true;
            }
        }
        return false;
    }
    
    public boolean hasSong(String title, String artist) {
    	for (Song song : myLibrarySongs) {
            if (song.getTitle().equals(title) && song.getArtist().equals(artist)) {
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
    

    
}
  
