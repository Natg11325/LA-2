package src;
import java.util.ArrayList;

public class Playlist {
	
	private String name;
	private ArrayList<Song> songs;
	
	public Playlist(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void addSong(Song song) {
		songs.add(song);
	}
	
	public int getSongAmt() {
		return songs.size();
	}
	
	public void removeSong(String title, String artist) {
		//removes a specified song from the play list.
		for(Song s: songs) {
			if(title.toLowerCase().equals(s.getTitle().toLowerCase()) &&
					(artist.toLowerCase().equals(s.getArtist().toLowerCase()))) {
				songs.remove(s);
				break;
			}
		}
	}
	
	
}
