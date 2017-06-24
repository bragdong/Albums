package testSpark;

//import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
//import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class AlbumList implements Serializable {
	int nextID; // variable used to assign next ID when adding albums

	public AlbumList() {
		this.albums = new ArrayList<Album>();
		nextID = 0;
		// *** Manual load of the data prior to incorporating function to write
		// and load from disk
		// albums.add(new Album("Different Days","The
		// Charlatans","BBC",nextID++));
		// albums.add(new Album("Common Sense","J Hus","BBC",nextID++));
		// albums.add(new Album("More Life","Drake","BBC",nextID++));
		// albums.add(new Album("The Amazons","The Amazons","BBC",nextID++));
		// albums.add(new Album("One More Light","Linkin Park","BBC",nextID++));
		// nextID = 5;
	}

	ArrayList<Album> albums;

	public Album getAlbum(int id) {
		for (int i = 0; i < albums.size(); i++) {
			Album album = (Album) albums.get(i);
			if (album.getAlbumID() == id) {
				return album;
			}
		}
		return null;
	}

	public int addAlbum(String title, String artist, String genre) {
		int id = nextID++;
		// System.out.println(id);
		Album album = new Album(title, artist, genre, id);
		albums.add(album);
		IOClass ioClass = new IOClass();
		ioClass.persistAlbums(this.albums);
		return id;
	}

	public String formatDisplay() {
		String htmlString = "<html><body><table border=\"1\"><tr><th>Album ID#</th><th>Title</th><th>Album</th><th>Genre</th></tr>";
		for (int i = 0; i < albums.size(); i++) {
			Album album = (Album) albums.get(i);
			htmlString += "<tr><td>" + album.getAlbumID() + "</td><td>"
					+ album.getTitle() + "</td><td>" + album.getArtist()
					+ "</td><td>" + album.getGenre() + "</td></tr>";
		}
		htmlString += "</table></body></html>";
		return htmlString;
	}
}
