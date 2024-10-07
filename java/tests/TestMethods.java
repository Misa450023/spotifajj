package tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import frontend.GetTheCode;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojos.Add_toPlaylist;
import pojos.Playlist;
import rest.Context;
import rest.EContentType;
import rest.Methods;
import rest.RestUtil;
import utility.MyKeys;
import utility.UtilClass;

public class TestMethods {

	public static Response getAccessToken_Client_Credentials() {

		Context context = new Context();
		String encodedCredentials = UtilClass.encode(MyKeys.client_id + ":" + MyKeys.client_secret);
		context.requestHeaderParams.put("Authorization", "Basic " + encodedCredentials);
		context.formParams.put("grant_type", "client_credentials");
		context.requestContentType = EContentType.URLENC;
		context.baseURL = "https://accounts.spotify.com/api";
		context.URI = "/token";

		return Methods.POST(context);
	}

	public static Response getAccessToken_Auth_Code() throws IOException, InterruptedException {

//MyKeys.code=GetTheCode.getCode(MyKeys.urlForCode);

		Context context = new Context();
		String encodedCredentials = UtilClass.encode(MyKeys.client_id + ":" + MyKeys.client_secret);
		String urlenc = UtilClass.encode("https://www.google.com/");
		context.requestHeaderParams.put("Authorization", "Basic " + encodedCredentials);
		context.requestHeaderParams.put("content-type", "application/x-www-form-urlencoded");
		context.formParams.put("grant_type", "authorization_code");
		context.formParams.put("code", MyKeys.code);
		context.formParams.put("redirect_uri", "https://www.google.com/");
		context.baseURL = "https://accounts.spotify.com/api";
		context.URI = "/token";

		return Methods.POST(context);
	}

	public static Response getUser() {

		Context context = new Context();
		context.baseURL = "https://api.spotify.com/v1";
		context.URI = "/me";
		context.headers.put("Authorization: ", "Bearer " + MyKeys.token);

		return Methods.GET(context);
	}

	public static Response getPlaylists() {

		Context context = new Context();
		context.baseURL = "https://api.spotify.com/v1";
		context.URI = "/me/playlists";
		context.headers.put("Authorization: ", "Bearer " + MyKeys.token);

		return Methods.GET(context);

	}

	public static Response getTracks(String playlist_id) {

		Context context = new Context();
		context.baseURL = "https://api.spotify.com/v1";
		context.URI = "/playlists/" + playlist_id + "/tracks";
		context.headers.put("Authorization: ", "Bearer " + MyKeys.token);

		return Methods.GET(context);
	}

	public static Response getArtist(String id) {

		Context context = new Context();
		context.baseURL = "https://api.spotify.com/v1";
		context.URI = "/artists/{id}";
		context.headers.put("Authorization: ", "Bearer " + MyKeys.token);
		context.pathParams.put("id", id);

		return Methods.GET(context);
	}

	public static Response searchArtists(String name) {

		Context context = new Context();
		context.baseURL = "https://api.spotify.com/v1";
		context.URI = "/search";
		context.headers.put("Authorization: ", "Bearer " + MyKeys.token);
		context.queryParams.put("type", "artist");
		context.queryParams.put("q", name);
		context.queryParams.put("limit", 1);

		return Methods.GET(context);
	}

	public static Response searchSongs(String name, String song) {

		Context context = new Context();
		context.baseURL = "https://api.spotify.com/v1";
		context.URI = "/search";
		context.headers.put("Authorization: ", "Bearer " + MyKeys.token);
		context.queryParams.put("type", "track");
		context.queryParams.put("q", song + " artist:" + name);
		context.queryParams.put("limit", 1);

		return Methods.GET(context);
	}

	public static Response getAlbums(String name) {

		Context context = new Context();
		context.baseURL = "https://api.spotify.com/v1";
		context.URI = "/search";
		context.headers.put("Authorization: ", "Bearer " + MyKeys.token);
		context.queryParams.put("type", "track");
		context.queryParams.put("q", name);
		context.queryParams.put("include_groups", "album");
		context.queryParams.put("limit", 20);

		return Methods.GET(context);
	}

	public static Response getAlbum(String id) {

		Context context = new Context();
		context.baseURL = "https://api.spotify.com/v1";
		context.URI = "/albums/{id}";
		context.headers.put("Authorization: ", "Bearer " + MyKeys.token);
		context.requestHeaderParams.put("Content-Type", "application/json");
		context.queryParams.put("market", "ES");
		context.pathParams.put("id", id);

		return Methods.GET(context);
	}

	public static List<String> Get_All_Songsof_artist(String artistName) {

		Response resp = getAlbums(artistName);
		List<String> albumsIDs = resp.jsonPath().getList("tracks.items.album.id");

		List<List<String>> songs = new ArrayList<>();

		for (int a = 0; a <= albumsIDs.size(); a++) {
			try {
				List<String> temp = new ArrayList<>();
				Response r = getAlbum(albumsIDs.get(a));
				temp = r.jsonPath().getList("tracks.items.name");
				songs.add(temp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		List<String> flattenedList = new ArrayList<>();
		songs.stream().forEach(flattenedList::addAll);

		return flattenedList;
	}

	public static Response createPlaylist(String name, String description) throws JsonProcessingException {

		Context context = new Context();
		context.baseURL = "https://api.spotify.com/v1";
		context.URI = "/users/{user_id}/playlists";
		context.pathParams.put("user_id", MyKeys.userID);
		context.headers.put("Authorization: ", "Bearer " + MyKeys.token);
		context.requestBodyString = RestUtil.toJson(new Playlist(name, description, false));
		context.requestContentType = EContentType.JSON;

		return Methods.POST(context);
	}

	public static Response addSongs(String artist, String song, String playlistName) {

		String url = RestUtil.parse(searchSongs(artist, song), "tracks.items[0].href");
		Add_toPlaylist payload = new Add_toPlaylist(Arrays.asList(url), 0);
		Response resp = getPlaylists();
		List<String> IDs = resp.jsonPath().getList("items.id");
		resp.jsonPath().getList("items.name").indexOf(payload);

		Context context = new Context();
		context.baseURL = "https://api.spotify.com/v1";
		context.URI = "//playlists/{playlist_id}/tracks";
		context.pathParams.put("playlist_id", MyKeys.createPlayistID);
		context.headers.put("Authorization: ", "Bearer " + MyKeys.token);
		context.requestContentType = EContentType.JSON;

		return Methods.POST(context);
	}

}
