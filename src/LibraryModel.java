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
    


   
}
  