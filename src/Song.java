package src;
public class Song {
	private String title;
	private String artist;
	private String album;
	private String genre;
	private int year;
	private boolean isAFavorite;
	private Integer rating;
	private int plays;

    public Song(String title, String artist, String album, String genre, int year) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.year = year;
        this.isAFavorite = false; // this will switch to true if it is checked as a favorite
        this.rating = null; // this will be null until/ if the song is rated 
        this.plays = 0;
    }
    
    public int getPlays() {
     	return 0 + plays;
     }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }

    public boolean isFavorite() {
        return isAFavorite;
    }

    public Integer getRating() {
        return rating;
    }
    
    public int getPlays() {
    	return 0 + plays;
    }

    public void setFavorite(boolean favorite) {
    	isAFavorite = favorite;
    }

    public void setRating(Integer rating) {
    	// rating has to be between 1 and 5
        if (rating != null && rating >= 1 && rating <= 5) {
            this.rating = rating;
            if (rating == 5) {
                this.isAFavorite = true;
            }
        }
    }
    
    public void play() {
     	plays += 1;
     }


    @Override
    public String toString() {
        return title + " by " + artist + " from album " + album;
    }
    
    @Override
    public boolean equals(Object other) {
    	Song otherSong = (Song) other;
    	return (this.title.toLowerCase().equals(otherSong.title.toLowerCase())) && 
    			(this.artist.toLowerCase().equals(otherSong.artist.toLowerCase()));
    }
}


