package tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import frontend.GetTheCode;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import junit.framework.Assert;
import pojos.Playlist;
import rest.Context;
import rest.EContentType;
import rest.Methods;
import rest.RestUtil;
import utility.MyKeys;
import utility.UtilClass;

public class TestClass {

//Obtaining the bearer token
//@Test(priority=0)	
	public void test_00_getToken() {

		Response resp = TestMethods.getAccessToken_Client_Credentials();
		int expire = RestUtil.parse(resp, "expires_in");

		Assert.assertEquals(resp.getStatusCode(), 200);
		Assert.assertEquals(RestUtil.parse(resp, "token_type"), "Bearer");
		Assert.assertEquals(expire, 3600);

		MyKeys.token = RestUtil.parse(resp, "access_token");

	}

//@Test(priority=0)
	public void test00_getAccessToken_auth_code_flow() throws IOException, InterruptedException {

		Response resp = TestMethods.getAccessToken_Auth_Code();
		MyKeys.token = RestUtil.parse(resp, "access_token");
		int expire = RestUtil.parse(resp, "expires_in");

		Assert.assertEquals(resp.getStatusCode(), 200);
		Assert.assertEquals(RestUtil.parse(resp, "token_type"), "Bearer");
		Assert.assertEquals(expire, 3600);
		System.out.println(MyKeys.token);
	}

//*** INFO OF CURRENT USER ***
//@Test(priority=1)
	public void test_01_getUser() {

		Response resp = TestMethods.getUser();

		Assert.assertEquals(resp.getStatusCode(), 200);
		Assert.assertEquals(RestUtil.parse(resp, "display_name"), "budza");
		Assert.assertEquals(RestUtil.parse(resp, "country"), "RS");

		resp.then().log().all();
	}

//*** GET ALL PLAYLIST OF CURRENT USER ***
//@Test(priority=2)
	public void test_02_getPlaylists() {

		Response resp = TestMethods.getPlaylists();

		Assert.assertEquals(resp.getStatusCode(), 200);
		Assert.assertNotNull(RestUtil.parse(resp, "items[0].id"));
		Assert.assertEquals(RestUtil.parse(resp, "items[0].owner.display_name"), "budza");

		MyKeys.myPlaylistID = RestUtil.parse(resp, "items[0].id");
//items.id
		resp.then().log().all();

		List<String> plIDS = resp.jsonPath().getList("items.name");
		resp.jsonPath().getList("items.name");
		System.out.println(plIDS);
	}

// *** GET SONGS FROM THE MY INITIAL PLAYLIST ***
//@Test(priority=3)
	public void test_02_getTrack_In_Playlist() {

		Response resp = TestMethods.getTracks(MyKeys.myPlaylistID);
//resp.then().log().all();
		String pathArtists = "items.track.artists.name";
		String pathSongs = "items.track.name";
		List<String> artists = UtilClass.flatMyList(resp.jsonPath().getList(pathArtists));
		List<String> songs = resp.jsonPath().getList(pathSongs);

		Assert.assertEquals(resp.getStatusCode(), 200);
		Assert.assertEquals(artists, Arrays.asList("Nino", "Nightwish", "Sia"));
		Assert.assertTrue(songs.stream().anyMatch(el -> el.equals("Sta cu mala s tobom")));

		MyKeys.myArtistsIDs = UtilClass.flatMyList(resp.jsonPath().getList("items.track.artists.id"));

		System.out.println(MyKeys.myArtistsIDs);
	}

//GET THE ARTISTS FROM MY DEFAULT PLAYLISTS - DEPENDS ON METHOD BEFORE
//@Test(priority=4)
	public void test_03_GetMy_FavoriteArtists() {

		List<String> names = new ArrayList<>();
		List<List<String>> genres = new ArrayList<>();

		for (int a = 0; a < MyKeys.myArtistsIDs.size(); a++) {
			Response resp = TestMethods.getArtist(MyKeys.myArtistsIDs.get(a));
			Assert.assertEquals(resp.getStatusCode(), 200);
			names.add(resp.jsonPath().get("name"));
			genres.add(resp.jsonPath().getList("genres"));
		}

		Assert.assertTrue(names.containsAll(Arrays.asList("Nino", "Nightwish", "Sia")));
		Assert.assertTrue(UtilClass.flatMyList(genres).size() > 8);
		Assert.assertTrue(UtilClass.flatMyList(genres).stream().anyMatch(el -> el.equals("bosnian pop")));

	}

// *** SEARCH FOR ARTIST
//@Test
	public void test_04_searchFor_Artists() {

		String targetArtist = "rade lackovic";
		Response resp = TestMethods.searchArtists(targetArtist);

		resp.then().log().all();
		String name = RestUtil.parse(resp, "artists.items[0].name");
		String id = RestUtil.parse(resp, "artists.items[0].id");
		List<String> genres = resp.jsonPath().getList("artists.items[0].genres");

		Assert.assertEquals(resp.getStatusCode(), 200);
		Assert.assertTrue(name.equalsIgnoreCase(targetArtist));
		Assert.assertTrue(genres.stream().allMatch(el -> el != null) && genres.stream().count() >= 1L);

		System.out.println(name);
		System.out.println(id);
		System.out.println(genres);
	}

//@Test
	public void test_05_searchFor_song() {

		String targetArtist = "Nightwish";
		String targetSong = "ever dream";
		Response resp = TestMethods.searchSongs(targetArtist, targetSong);

		resp.then().log().all();
		String songName = RestUtil.parse(resp, "tracks.items[0].name");
		String id = RestUtil.parse(resp, "tracks.items[0].id");
		String name = RestUtil.parse(resp, "tracks.items[0].artists[0].name");

		Assert.assertEquals(resp.getStatusCode(), 200);
		Assert.assertTrue(name.equalsIgnoreCase(targetArtist));
		Assert.assertTrue(songName.equalsIgnoreCase(targetSong));
		Assert.assertNotNull(id);
		System.out.println(songName);
		System.out.println(id);
		System.out.println(name);

//String url=RestUtil.parse(resp, "tracks.items[0].href");
//System.out.println(url);

	}

//*** GET ALL ALBUMS OF ARTIST
//@Test(priority=0)
	public void test_06_GetAlbums() {

		Response resp = TestMethods.getAlbums("Aca Lukas");

		resp.then().log().all();

		List<String> namesOfAlbums = resp.jsonPath().getList("tracks.items.album.name");
		MyKeys.albumsIDs = resp.jsonPath().getList("tracks.items.album.id");
		System.out.println(namesOfAlbums);
		System.out.println(MyKeys.albumsIDs);

	}

//*** GET SPECIFIC ALBUM BY ID 
//@Test
	public void test_07_Get_Album() {

		String id2 = "17s65rAqwmg5jYUqBxu6tL";
		String id3 = "0tfYA1ijI66dGujI9JrZAJ";
		String id4 = "2Kjct4I1kKJMxWMSiKARdQ";

		Response resp = TestMethods.getAlbum(id2);
		resp.then().log().all();

		List<String> songs = resp.jsonPath().getList("tracks.items.name");
		List<String> IDs = resp.jsonPath().getList("tracks.items.id");

		System.out.println(songs);
		System.out.println(IDs);
	}

// *** GET LIST OF SONG OF ARTIST
//@Test(priority=1)
	public void test_08_getAllSongOfArtist() {

//test_06_GetAlbums("dj krmak");	

		List<String> songs = TestMethods.Get_All_Songsof_artist("Rade Lackovic");
		System.out.println(songs.size());
		System.out.println(songs.get(0));
		System.out.println(songs);

	}

//**** CREATE A NEW PLAYLIST
//@Test
	public void test_09_CreatePlaylist() throws JsonProcessingException {

		Response resp = TestMethods.createPlaylist("septembar" + UtilClass.randomTxt(), "anadolija");
		MyKeys.createPlayistID = RestUtil.parse(resp, "id");

		Assert.assertEquals(resp.getStatusCode(), 201);
		Assert.assertEquals(RestUtil.parse(resp, "description"), "kazan");
		Assert.assertTrue(RestUtil.parse(resp, "name").toString().contains("septembar"));
		Assert.assertNotNull(MyKeys.createPlayistID);

		RestUtil.parse(resp, "name");
		resp.then().log().all();
	}

	public void test_09_Add_SongsToPlaylist() {

	}

//@Test
//public void estam() {
//	
//String list=UtilClass.randomTxt();
//System.out.println(list);
//}

}
