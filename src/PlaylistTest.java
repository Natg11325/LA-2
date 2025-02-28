//package src;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlaylistTest {

//	@Test
//	void test() {
//		fail("Not yet implemented");
//	}
	
	@Test
	public void test_getName() {
		Playlist playlist1 = new Playlist("Play list one");
		assertEquals("Play list one", playlist1.getName());
	}
	
	@Test
	public void test_addSongs() {
		Playlist playlist1 = new Playlist("Play list one");
		Song song1 = new Song("19", "Adele", "Daydreamer", "Pop", 2008);
		playlist1.addSong(song1);
		assertEquals(song1, playlist1.getSongs().get(0));
		assertEquals(1, playlist1.getSongAmt());
	}
	
	@Test
	public void test_removeSong() {
		Playlist playlist1 = new Playlist("Play list one");
		Song song1 = new Song("19", "Adele", "Daydreamer", "Pop", 2008);
		playlist1.addSong(song1);
		int length = playlist1.getSongAmt();
		playlist1.removeSong("18", "Adele");
		assertEquals(length, playlist1.getSongAmt());
		
		Song song2 = new Song("19", "Adele", "Chasing Pavements", "Pop", 2008);
		playlist1.addSong(song2);
		length = playlist1.getSongAmt();
		playlist1.removeSong("19", "Kendrick Lamar");
		assertEquals(length, playlist1.getSongAmt());
		
		playlist1.removeSong("19", "Adele");
		assertFalse(length == playlist1.getSongAmt());
	}
	
	@Test
	public void test_toString() {
		Playlist playlist1 = new Playlist("Likes");
		Song song1 = new Song("19", "Adele", "Daydreamer", "Pop", 2008);
		playlist1.addSong(song1);
		assertEquals("Playlist: Likes, 1 songs", playlist1.toString());
	}

}
