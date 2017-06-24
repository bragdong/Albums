package testSpark;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class IOClass {
	public static final String FILE_PATH = "albums2.ser";
	
	public void loadAlbums2(AlbumList albumList) {
		try {
			System.out.println("Loading albums from " + FILE_PATH + "...");
			FileInputStream fis = new FileInputStream(FILE_PATH);
			ObjectInputStream ois = new ObjectInputStream(fis);
			while (true) {
				try {
					albumList.albums = (ArrayList<Album>) ois.readObject();
					albumList.nextID = albumList.albums.size();
					System.out.println("Number of albums loaded from file = " + albumList.nextID);
				} catch (IOException e) {
					break;
				}
				fis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public void persistAlbums(ArrayList<Album> albums) {
		try {
			System.out.println("Saving Album data to file " + IOClass.FILE_PATH + "...");
			FileOutputStream fileOut = new FileOutputStream(IOClass.FILE_PATH);			
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(albums);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved.");			
		} catch (IOException i) {
			i.printStackTrace();
		}
	}	
	
}
