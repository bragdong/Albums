package testSpark;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.*;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.FileOutputStream;
import java.util.ArrayList;

import com.google.gson.Gson;

import spark.Request;
import spark.Response;
import spark.Route;

public class AlbumPage {

	public static void main(String[] args) {
		port(3002);
		IOClass ioClass = new IOClass();
		AlbumList albumList = new AlbumList();
		ioClass.loadAlbums2(albumList);

		// *** Display using the method defined in AlbumList class.
		// URL=http://localhost:3002/
		get("/", (req, res) -> {
			return albumList.formatDisplay();
		});

		// *** Display using Jtwig. URL=http://localhost:3002/jtwig
		get("/jtwig", (req, res) -> {
			JtwigTemplate template = JtwigTemplate
					.classpathTemplate("jtwigDisplay.jtwig");
			JtwigModel model = JtwigModel.newModel().with("albums",
					albumList.albums);
			return template.render(model);
		});

		// *** Return data in Json format. URL=http://localhost:3002/parseJson
		get("/parseJson", (req, res) -> {
			Gson gson = new Gson();
			System.out.println(
					"In Json route. Json format = " + gson.toJson(albumList));
			return gson.toJson(albumList.albums);
		});

		// *** Display Album list contents using jtwig template.
		// URL=http://localhost:3002/displayJson
		get("/displayJson", (req, res) -> {
			System.out.println("In Parse Json route");
			JtwigTemplate template = JtwigTemplate
					.classpathTemplate("jsonDisplay.jtwig");
			JtwigModel model = JtwigModel.newModel().with("albums", albumList);
			return template.render(model);
		});

		// *** Retrieve an Album by ID
		get("/getAlbum/:id", (request, response) -> {
			Album album = albumList
					.getAlbum(Integer.parseInt(request.params(":id")));
			if (album == null) {
				return "Album was not found.";
			}
			String htmlString = "<html><body><table><tr><th>Title</th><th>Album</th><th>Genre</th></tr>";
			htmlString += "<tr><td>" + album.getTitle() + "</td><td>"
					+ album.getArtist() + "</td><td>" + album.getGenre()
					+ "</td></tr>";
			return (htmlString);
		});

		// *** Add a new Album to the Album list
		get("/addAlbum/:title/:artist/:genre", (request, response) -> {
			int newID = albumList.addAlbum(request.params(":title"),
					request.params(":artist"), request.params(":genre"));
			return ("Album ID# " + newID + " added to Album list. There are "
					+ albumList.albums.size() + " albums in your list now.");
		});

		// *** Create Album input page. URL=http://localhost:3002/addAlbum2
		get("/addAlbum2", (req, res) -> {
			JtwigTemplate template = JtwigTemplate
					.classpathTemplate("albumInput.jtwig");
			JtwigModel model = JtwigModel.newModel();
			return template.render(model);
		});

		// *** process the data input and call addAlbum method
		post("/albumInput_submit", (request, response) -> {
			String title = request.queryParams("title");
			String artist = request.queryParams("artist");
			String genre = request.queryParams("genre");
			int newID = albumList.addAlbum(title, artist, genre);
			return ("Album ID# " + newID + " added to Album list. There are "
					+ albumList.albums.size()
					+ " albums in your list now (ID list starts at 0).");
		});

		// *** Retrieve an Album input page. URL=http://localhost:3002/search
		get("/search", (req, res) -> {
			JtwigTemplate template = JtwigTemplate
					.classpathTemplate("searchAlbum.jtwig");
			JtwigModel model = JtwigModel.newModel();
			return template.render(model);
		});

		// *** process the search album request and display results
		post("/search_submit", (request, response) -> {
			String searchID = request.queryParams("search");
			System.out.println(searchID);
			return (albumList.getAlbum(Integer.parseInt(searchID)));
		});

	}

}
