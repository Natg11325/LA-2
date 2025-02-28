//package src;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MusicStore {
	private List<Album> albums;
    private List<Song> songs;
    
    public MusicStore() throws FileNotFoundException {
        this.albums = new ArrayList<>();
        this.songs = new ArrayList<>();
        loadAlbums();
    }
	
    private void loadAlbums() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileReader("albums/albums.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Split line into album title and artist
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                	// trim album and artist name
                    String albumTitle = parts[0].trim();
                    String artist = parts[1].trim();
                    
                    // Create the album filename based on the album title and artist
                    String filename = albumTitle + "_" + artist + ".txt";
                    loadAlbumFromFile(filename);
                }
            } 
            scanner.close();

    }

    // Load a specific album from its file
    private void loadAlbumFromFile(String filename) throws FileNotFoundException {
    	// open the file to be read
        Scanner scanner = new Scanner(new FileReader("albums/" + filename));
            // Read album header
            if (scanner.hasNextLine()) {
                String header = scanner.nextLine();
                String[] parts = header.split(",");
                if (parts.length >= 4) {
                    String albumTitle = parts[0].trim();
                    String artist = parts[1].trim();
                    String genre = parts[2].trim();
                    int year = Integer.parseInt(parts[3].trim());
                    
                    // Create new album object and add it to the list
                    Album album = new Album(albumTitle, artist, genre, year);
                    albums.add(album);
                    
                    // Read all the song titles from the file
                    while (scanner.hasNextLine()) {
                        String songTitle = scanner.nextLine();
                        // ignore empty lines
                        if (!songTitle.trim().isEmpty()) {
                            // Add song title to album
                            album.addSongTitle(songTitle);
                            
                            // Create and store the song
                            Song song = new Song(songTitle, artist, albumTitle, genre, year);
                            songs.add(song);
                        }
                    }
                }
            }
            scanner.close();
    }
    
    
    public List<Song> searchSongsWithTitle(String title) {
    	List<Song> results = new ArrayList<>();
        String searchTitle = title.toLowerCase();
       
        for (Song song : songs) {
            if (song.getTitle().toLowerCase().equals(searchTitle)) {
                results.add(song);
            }
        }
        return results;
    }
    
    public List<Song> searchSongsWithArtist(String artist) {
    	List<Song> results = new ArrayList<>();
    	String searchArtist= artist.toLowerCase();
    	
    	for(Song song : songs) {
    		if(song.getArtist().toLowerCase().equals(searchArtist)) {
    			results.add(song);
    		}
    	}       
        return results;
        
    }
    
    public List<Album> searchAlbumsWithTitle(String title) {
    	List<Album> results = new ArrayList<>();
        String searchTitle = title.toLowerCase();
        
        for (Album album : albums) {
            if (album.getTitle().toLowerCase().equals(searchTitle)) {
                results.add(album);
            }
        }
        return results;
    }
    
    public List<Album> searchAlbumsWithArtist(String artist) {
    	List<Album> results = new ArrayList<>();
        String searchArtist = artist.toLowerCase();
        
        for (Album album : albums) {
            if (album.getArtist().toLowerCase().equals(searchArtist)) {
                results.add(album);
            }
        }
        return results; 	
    }
    
    public Song getSongWithTitleAndArtist(String title, String artist) {
    	for (Song song : songs) {
            if (song.getTitle().equals(title) && song.getArtist().equals(artist)) {
                return song;
            }
        }
        return null;
    }
    
    public Album getAlbumWithTitleAndArtist(String title, String artist) {
        for (Album album : albums) {
            if (album.getTitle().equals(title) && album.getArtist().equals(artist)) {
                return album;
            }
        }
        return null;
    }
    
    
    // Get all songs from a specific album
    public List<Song> getSongsFromAlbum(String albumTitle, String artist) {
        List<Song> albumSongs = new ArrayList<>();
        
        // Find the album first to get the song order
        Album wantedAlbum = null;
        // iterate through albums to find a match
        for (Album album : albums) {
            if (album.getTitle().equals(albumTitle) && album.getArtist().equals(artist)) {
            	// store the match if its found
            	wantedAlbum = album;
                break;
            }
        }
        // If the album exists, retrieve its songs in the correct order
        if (wantedAlbum != null) {
        	// iterate through the stored song titles in album
            for (String songTitle : wantedAlbum.getSongTitles()) {
            	// Find the corresponding song object in the songs list
                for (Song song : songs) {
                    if (song.getTitle().equals(songTitle) && song.getAlbum().equals(albumTitle) && song.getArtist().equals(artist)) {
                        albumSongs.add(song);
                        break;
                    }
                }
            }
        }
        // Return the ordered list of songs from the album
        return albumSongs;
    }
    
    
    // Get all albums
    public List<Album> getAllAlbums() {
        return new ArrayList<>(albums);
    }
    
    // Get all songs
    public List<Song> getAllSongs() {
        return new ArrayList<>(songs);
    }

}
