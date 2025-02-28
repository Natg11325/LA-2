import java.util.ArrayList;
import java.util.List;



public class Album {
	private String title;
	private String artist;
	private String genre;
	private int year;
	private List<String> songs;
	
	public Album(String title, String artist, String genre, int year) {
		this.title = title;
		this.artist = artist;
		this.genre = genre;
		this.year = year;
		this.songs = new ArrayList<>();
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public int getYear() {
		return year;
	}
	
	public List<String> getSongs(){
		return new ArrayList<>(songs);
	}

	public List<String> getSongTitles(){
		return new ArrayList<>(songs);
	}
	
	public void addSong(String songTitle) {
		songs.add(songTitle);
	}

	public void addSongTitle(String songTitle) {
		songs.add(songTitle);
	}
	 
	public int getNumOfSongs() {
		return songs.size();
	}
		
//Album Title,Artist,Genre,Year
    @Override
    public String toString() {
        return "Album: " + title + ", " + artist + ", " + genre + ", " + year;
    }

}
