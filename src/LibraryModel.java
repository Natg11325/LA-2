//package src;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.List;
import java.util.Queue;
 
public class LibraryModel {
    private MusicStore musicStore;
    private List<Song> myLibrarySongs;
    private List<Album> myLibraryAlbums;
    private List<Playlist> playlists;
    private Map<String, Playlist> automaticPlaylists;
    private Queue<Song> recentlyPlayedSongs;


    public LibraryModel(MusicStore musicStore) {
        this.musicStore = musicStore;
        this.myLibrarySongs = new ArrayList<>();
        this.myLibraryAlbums = new ArrayList<>();
        this.playlists = new ArrayList<>();
        this.automaticPlaylists = new HashMap<>();
        this.recentlyPlayedSongs = new LinkedList<Song>();
        initializeAutomaticPlaylists();
        
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
     			//if the song has been played at least once.
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
     
    public void addAutoPlaylist(String key, Playlist playlist) {
    	automaticPlaylists.put(key, playlist);
    }
    
    public boolean removeAutoPlaylist(String key) {
    	return automaticPlaylists.remove(key) != null;
    }
    
    // this helper method initializes the automatic playlists with empty playlists to start out 
    private void initializeAutomaticPlaylists() {
        // Create the Favorites playlist
        Playlist favoritesPlaylist = new Playlist("Automatic Favorites: ");
        // Adds the favorites playlist to the automaticPlaylists hashmap
        automaticPlaylists.put("Automatic Favorites: ", favoritesPlaylist);
        
        // Create the Top Rated playlist
        Playlist topRatedPlaylist = new Playlist("Automatic Top Rated: ");
        // Adds the top rated playlist to the automaticPlaylists hashmap
        automaticPlaylists.put("Automatic Top Rated: ", topRatedPlaylist);
    }
    
    // Update all automatic playlists
    private void updateAutoPlaylists() {
        updateFavoritesPlaylist();
        updateTopRatedPlaylist();
        updateGenrePlaylists();
    }
    
    
    // Update the Favorites playlist with all the songs marked as favorite or rated 5
    private void updateFavoritesPlaylist() {
        Playlist favoritesPlaylist = automaticPlaylists.get("Automatic Favorites: ");
        if (favoritesPlaylist == null) {
            favoritesPlaylist = new Playlist("Automatic Favorites: ");
            automaticPlaylists.put("Automatic Favorites: ", favoritesPlaylist);
        }
        
        // Clear the current songs in the playlist
        List<Song> currentSongs = favoritesPlaylist.getSongs();
        for (Song song : currentSongs) {
            favoritesPlaylist.removeSong(song.getTitle(), song.getArtist());
        }
        
        // Add all favorite songs to the playlist
        for (Song song : myLibrarySongs) {
            if (song.isFavorite() || (song.getRating() != null && song.getRating() == 5)) {
                favoritesPlaylist.addSong(song);
            }
        }
    }
    
    // Update the Top Rated playlist with all the songs rated 4 or 5
    private void updateTopRatedPlaylist() {
        Playlist topRatedPlaylist = automaticPlaylists.get("Automatic Top Rated: ");
        if (topRatedPlaylist == null) {
            topRatedPlaylist = new Playlist("Automatic Top Rated: ");
            automaticPlaylists.put("Automatic Top Rated: ", topRatedPlaylist);
        }
        
        // Clear the current songs in the playlist
        List<Song> currentSongs = topRatedPlaylist.getSongs();
        for (Song song : currentSongs) {
            topRatedPlaylist.removeSong(song.getTitle(), song.getArtist());
        }
        
        // Add all songs rated 4 or 5 to the playlist
        for (Song song : myLibrarySongs) {
            if (song.getRating() != null && (song.getRating() == 4 || song.getRating() == 5)) {
                topRatedPlaylist.addSong(song);
            }
        }
    }
    
    private void updateGenrePlaylists() {
        // Count songs by genre
        Map<String, Integer> countForEachGenre = new HashMap<>();
        for (Song song : myLibrarySongs) {
            String genre = song.getGenre();
            // Check if the genre already exists in the map
            if (countForEachGenre.containsKey(genre)) {
                // If it exists increment the count
                int currentCount = countForEachGenre.get(genre);
                countForEachGenre.put(genre, currentCount + 1);
            } else {
                // If it doesn't exist add it with count 1
            	countForEachGenre.put(genre, 1);
            }
        }
        
        // Check and remove any existing genre playlists that are no longer valid
        List<String> playlistsToRemove = new ArrayList<>();
        for (String playlistName : automaticPlaylists.keySet()) {
            if (playlistName.startsWith("Automatic Genre: ")) {
                String genre = playlistName.substring("Automatic Genre: ".length());
                if (!countForEachGenre.containsKey(genre) || countForEachGenre.get(genre) < 10) {
                    playlistsToRemove.add(playlistName);
                }
            }
        }
        
        for (String playlistName : playlistsToRemove) {
            automaticPlaylists.remove(playlistName);
        }
        
        // Create and update playlists for genres with at least 10 songs
        for (String genre : countForEachGenre.keySet()) {
            int count = countForEachGenre.get(genre);
            
            if (count >= 10) {
                String playlistName = "Automatic Genre: " + genre;
                Playlist genrePlaylist = automaticPlaylists.get(playlistName);
                
                if (genrePlaylist == null) {
                    genrePlaylist = new Playlist(playlistName);
                    automaticPlaylists.put(playlistName, genrePlaylist);
                }
                
                // Clear the current songs in the playlist
                List<Song> currentSongs = genrePlaylist.getSongs();
                for (Song song : currentSongs) {
                    genrePlaylist.removeSong(song.getTitle(), song.getArtist());
                }
                
                // Add all songs of this genre to the playlist
                for (Song song : myLibrarySongs) {
                    if (song.getGenre().equals(genre)) {
                        genrePlaylist.addSong(song);
                    }
                }
            }
        }

    }
  
    
    public boolean addSongToLibrary(String title, String artist) {
    	// Retrieve a song from the database by matching exact title and artist name
        Song song = musicStore.getSongWithTitleAndArtist(title, artist); 
        // Only add the song if it exists in the store and isn't already in the user's library
        if (song != null && !myLibrarySongs.contains(song)) {
            myLibrarySongs.add(song);
            // Get the album information from the song
            String albumTitle = song.getAlbum();
            // checks if the albums is already in the user's library
            Album isInLibrary = null;
    		for (Album album : myLibraryAlbums) {
                if (album.getTitle().equals(albumTitle) && album.getArtist().equals(artist)) {
                	isInLibrary = album;
                    break;
                }
            }
            
    		// if not in the library
    		if (isInLibrary == null) {
                // Get the full album
                Album storeAlbum = musicStore.getAlbumWithTitleAndArtist(albumTitle, artist);
                if (storeAlbum != null) {
                    // Creates a new album with the same data but an empty song list
                	isInLibrary = new Album(albumTitle, artist, storeAlbum.getGenre(), storeAlbum.getYear());
                    myLibraryAlbums.add(isInLibrary);
                }
            }
    		
    		// Adds the song title to the album if the album exists and doesn't already have this song in it
            if (isInLibrary != null && !isInLibrary.getSongTitles().contains(title)) {
            	isInLibrary.addSongTitle(title);
            }
            
            updateAutoPlaylists();
            return true;
        }
        return false;
    }
    
    public boolean addAlbumToLibrary(String title, String artist) {
        // Retrieve an album from the database by matching exact title and artist name
        Album storeAlbum = musicStore.getAlbumWithTitleAndArtist(title, artist);
        
        // Only continue if the album exists in the store
        if (storeAlbum != null) {
            // Checks if this album already exists in the user's library, this helps avoid the album showing up twice
            Album existingAlbum = null;
            for (Album album : myLibraryAlbums) {
                if (album.getTitle().equals(title) && album.getArtist().equals(artist)) {
                    existingAlbum = album;
                    break;
                }
            }
            
            // If album doesn't exist in library yet then add it
            if (existingAlbum == null) {
                // Create a new album with the same info as the store album
                existingAlbum = new Album(title, artist, storeAlbum.getGenre(), storeAlbum.getYear());
                myLibraryAlbums.add(existingAlbum);
            }
            
            // Adds all songs from the store album to the library
            List<Song> albumSongs = musicStore.getSongsFromAlbum(title, artist);
            boolean addedAtLeastOneSong = false;
            
            // Adds each song that isn't already in the library, makes sure to avoid duplicates
            for (Song song : albumSongs) {
                if (!myLibrarySongs.contains(song)) {
                    myLibrarySongs.add(song);
                    addedAtLeastOneSong = true;
                    
                    // Add the song title to the album if it's not already there
                    if (!existingAlbum.getSongTitles().contains(song.getTitle())) {
                        existingAlbum.addSongTitle(song.getTitle());
                    }
                }
            }
            updateAutoPlaylists();
            return addedAtLeastOneSong || existingAlbum != null;
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
        //return null;
        return automaticPlaylists.get(name);
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
                updateAutoPlaylists();
                return true;
            }
        }      
        return false;
    }
    
    public boolean hasAlbum(String title) {
    	for(Album album: myLibraryAlbums) {
    		if(album.getTitle().equals(title)) {
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
                updateAutoPlaylists();
                return true;
            }
        }
        return false;
    } 
    
    public List<Song> searchLibrarySongsWithGenre(String genre){
    	List<Song> results = new ArrayList<>();
    	String searchGenre = genre.toLowerCase();
    	for(Song song : myLibrarySongs) {
    		if(song.getTitle().toLowerCase().equals(searchGenre)) {
    			results.add(song);
    		}
    	}
    	return results;
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
    
    public List<Song> searchStoreSongsWithGenre(String genre){
    	return musicStore.searchSongsWithGenre(genre);
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
        //return new ArrayList<>(playlists);
        List<Playlist> allPlaylists = new ArrayList<>(playlists);
        allPlaylists.addAll(automaticPlaylists.values());
        return allPlaylists;
    }

    // Get only automatic playlists
    public List<Playlist> getAutoPlaylists() {
        return new ArrayList<>(automaticPlaylists.values());

        //return new ArrayList<>(playlists);!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11

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
                    myLibrarySongs.remove(song);
                }
            return true;
        }
        return false;
    }
    
    public List<Song> getShuffledLibrarySongs() {
        List<Song> shuffledSongs = new ArrayList<>(myLibrarySongs);
        // get the copy of the library songs and shuffle them using collections sort
        Collections.shuffle(shuffledSongs);
        return shuffledSongs;
    }
    
    
    // shuffles a playlist given the playlists name 
    public Playlist shufflePlaylist(String playlistName) {
        Playlist playlist = getPlaylistWithName(playlistName);
        if (playlist != null) {
            playlist.shuffle();
            return playlist;
        }
        return null;
    }
    
     
}
  