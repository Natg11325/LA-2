//package src;

import java.io.FileNotFoundException;

public class Account {
	private String username;
	private String password;
	private LibraryModel library;
	private String salt;
	
	public Account(String username, String password, LibraryModel library, String salt) throws FileNotFoundException {
		this.username = username;
		this.password = password;
		this.library = library;
		this.salt = salt;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getSalt() {
		return this.salt;
	}

	public LibraryModel getLibrary() {
        	return library;
    }
	
}
